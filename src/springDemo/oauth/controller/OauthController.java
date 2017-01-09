package springDemo.oauth.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import springDemo.oauth.service.OauthService;

@Controller
@RequestMapping("/oauth")
public class OauthController {

	@Autowired
	private OauthService oAuthService;

	@Autowired
	private CacheManager cacheManager;

	@RequestMapping("/authorize")
	public Object authorize(Model model, HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
		try {
			// 构建OAuth 授权请求
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

			// 检查传入的客户端id是否正确
			if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("客户端验证失败")
						.buildJSONMessage();

				return new ResponseEntity<Object>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			// 得到到客户端重定向地址
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

			Subject subject = SecurityUtils.getSubject();

			// 如果用户没有登录，跳转到登陆页面
			if (!subject.isAuthenticated()) {

				if (!login(subject, request)) {// 登录失败时跳转到登陆页面
					model.addAttribute("client", oAuthService.findByClientId(oauthRequest.getClientId()));
					model.addAttribute("redirectURI", redirectURI);
					return "oauth/login";
				}

			}

			String username = (String) subject.getPrincipal();

			// 生成授权码
			String authorizationCode = null;

			// responseType目前仅支持CODE，另外还有TOKEN
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if (responseType.equals(ResponseType.CODE.toString())) {
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				authorizationCode = oauthIssuerImpl.authorizationCode();

				Element lgEle = new Element(username + "_code", authorizationCode);
				cacheManager.getCache("oauthCache").put(lgEle);
			}

			// 进行OAuth响应构建
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
					HttpServletResponse.SC_FOUND);

			// 设置授权码
			builder.setCode(authorizationCode);

			// 构建响应
			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

			// 根据OAuthResponse返回ResponseEntity响应
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));

			return new ResponseEntity<Object>(headers, HttpStatus.valueOf(response.getResponseStatus()));

		} catch (OAuthProblemException e) {
			// 出错处理
			String redirectUri = e.getRedirectUri();

			if (OAuthUtils.isEmpty(redirectUri)) {

				// 告诉客户端没有传入redirectUri直接报错
				return new ResponseEntity<Object>("OAuth callback url needs to be provided by client!!!",
						HttpStatus.NOT_FOUND);
			}

			// 返回错误消息（如?error=）
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity<Object>(headers, HttpStatus.valueOf(response.getResponseStatus()));
		}
	}

	@RequestMapping("/accessToken")
	public HttpEntity<Object> token(HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
		try {
			// 构建OAuth请求
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

			// 检查提交的客户端id是否正确
			if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription("")
						.buildJSONMessage();
				return new ResponseEntity<Object>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			// 检查客户端安全KEY是否正确
			if (!oAuthService.checkClientSecret(oauthRequest.getClientSecret())) {
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
						.setErrorDescription("")
						.buildJSONMessage();
				return new ResponseEntity<Object>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);

			// 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
				Subject subject = SecurityUtils.getSubject();
				String username = (String) subject.getPrincipal();			
				Element lgEle = cacheManager.getCache("oauthCache").get(username + "_code");
				if (lgEle != null) {
					String authorizationCode = (String) lgEle.getObjectValue();
					if (!authorizationCode.equals(authCode)) {
						OAuthResponse response = OAuthASResponse
								.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
								.setError(OAuthError.TokenResponse.INVALID_GRANT)
								.setErrorDescription("错误的授权码")
								.buildJSONMessage();
						return new ResponseEntity<Object>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
					}	
				}
			}

			// 生成Access Token
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			final String accessToken = oauthIssuerImpl.accessToken();
			// oAuthService.addAccessToken(accessToken, oAuthService.getUsernameByAuthCode(authCode));

			// 生成OAuth响应
			OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken).setExpiresIn("Name").buildJSONMessage();
			
			// 根据OAuthResponse生成ResponseEntity
			return new ResponseEntity<Object>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
		} catch (OAuthProblemException e) {
			// 构建错误响应
			OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			return new ResponseEntity<Object>(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
		}
	}

	private boolean login(Subject subject, HttpServletRequest request) {
		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("name");
		String password = request.getParameter("password");

		if (username == null || password == null) {
			return false;
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			subject.login(token);
			return true;
		} catch (Exception e) {
			request.setAttribute("error", "登录失败:" + e.getClass().getName());
			return false;
		}
	}
}
