package springDemo.user.controller;

import java.util.Locale;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springDemo.core.ajax.AjaxResult;
import springDemo.user.vo.OauthUser;

@Controller
public class LoginController {

	@Autowired()
	private ReloadableResourceBundleMessageSource messageSource;
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin() {
		
		Subject subject = SecurityUtils.getSubject();
		
		// 用户是否登录
		if (subject.isAuthenticated()) {
			return "uploadView";
		}
		
		return "login";
	}
	
	/**
	 * 登录操作
	 * @param oauthUser
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult doLogin(@Validated OauthUser oauthUser, BindingResult result) {
		
		AjaxResult ajaxResult = new AjaxResult();
		
		Subject subject = SecurityUtils.getSubject();
		
		// 用户是否登录
		if (subject.isAuthenticated()) {
			ajaxResult.setResult(true);
			ajaxResult.setUrl("uploadView");
			return ajaxResult;
		}
		
		// 页面录入错误
		if (result.hasErrors()) {
			// 获取错误信息
			String errorMsg = messageSource.getMessage("userName.unknow", null, Locale.CHINA);
			ajaxResult.setMessage(errorMsg);
						
			return ajaxResult;
		}
		
		UsernamePasswordToken token = new UsernamePasswordToken(oauthUser.getUserName(), 
				oauthUser.getUserPassword());
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			// 获取错误信息
			String errorMsg = messageSource.getMessage("userName.unknow", null, Locale.CHINA);
			ajaxResult.setMessage(errorMsg);
			return ajaxResult;
		} catch (IncorrectCredentialsException e) {
			// 获取错误信息
			String errorMsg = messageSource.getMessage("userName.unknow", null, Locale.CHINA);
			ajaxResult.setMessage(errorMsg);
			
			return ajaxResult;
		}
		
		ajaxResult.setResult(true);
		ajaxResult.setUrl("upload");
		
		return ajaxResult;
	}
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		
		// 用户是否登录
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		
		return "index";
	}
}
