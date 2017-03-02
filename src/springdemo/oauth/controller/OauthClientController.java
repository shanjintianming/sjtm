package springdemo.oauth.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springdemo.util.JsonUtil;

@Controller
@RequestMapping("/oauthClient")
public class OauthClientController {
	
	@RequestMapping(value = "/authorize")
	public String authorize(HttpServletRequest request) {
		
		String code = request.getParameter("code");

		// OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		// OAuthClientRequest accessTokenRequest = OAuthClientRequest
		// .tokenLocation("")
		// .setGrantType(GrantType.AUTHORIZATION_CODE)
		// .setClientId("c1ebe466-1cdc-4bd3-ab69-77c3561b9dee")
		// .setClientSecret("d8346ea2-6017-43ed-ad68-19c0f971738b")
		// .setCode(code)
		// .setRedirectURI("http://localhost:8010/authorize")
		// .buildQueryMessage();

//		HttpPost post = new HttpPost("http://localhost:8080/oauth/accessToken");
//
//		// 创建参数队列
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));  

//		HttpUtil tokenPost = new HttpUtil(new URI("http://localhost:8080/oauth/accessToken"));
//
//		tokenPost.addParam("client_id", "c1ebe466-1cdc-4bd3-ab69-77c3561b9dee");
//		tokenPost.addParam("client_secret", "d8346ea2-6017-43ed-ad68-19c0f971738b");
//		tokenPost.addParam("code", code);
//		tokenPost.addParam("grant_type", "authorization_code");
//		tokenPost.addParam("redirect_uri", "http://localhost:8010/authorize");
//		
//		tokenPost.sendPostRequest();
		
		CloseableHttpClient httpclient = HttpClients.custom().build();

		HttpUriRequest tokenPost;
		try {
			tokenPost = RequestBuilder
					.post()
					.setUri(new URI("http://localhost:8080/oauth/accessToken"))
					.addParameter("client_id", "c1ebe466-1cdc-4bd3-ab69-77c3561b9dee")
					.addParameter("client_secret", "d8346ea2-6017-43ed-ad68-19c0f971738b")
					.addParameter("code", code)
					.addParameter("grant_type", "authorization_code")
					.addParameter("redirect_uri", "http://localhost:8010/authorize").build();
			
			CloseableHttpResponse response = httpclient.execute(tokenPost);
			
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity);
			
			Map<String, Object> responseObj = JsonUtil.Json.jsonToMap(responseContent, HashMap.class, String.class, Object.class);
			responseObj.get("accessToken");
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		//
		// //获取access token
		// OAuthAccessTokenResponse oAuthResponse =
		// oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
		// String accessToken = oAuthResponse.getAccessToken();
		// Long expiresIn = oAuthResponse.getExpiresIn();

		// //获取user info
		// OAuthClientRequest userInfoRequest =
		// new OAuthBearerClientRequest(userInfoUrl)
		// .setAccessToken(accessToken).buildQueryMessage();
		// OAuthResourceResponse resourceResponse = oAuthClient.resource(
		// userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
		// String username = resourceResponse.getBody();
		// return username;
		return "login";
	}
}
