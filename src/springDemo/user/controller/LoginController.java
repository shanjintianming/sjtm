package springDemo.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springDemo.user.vo.OauthUser;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(@Validated OauthUser oauthUser, BindingResult result) {
		
		if (result.hasErrors()) {
			return "login";
		}
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(oauthUser.getUserName(), 
				oauthUser.getUserPassword());
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			//这个函数有好几个重载的变体
			result.rejectValue("userName", "userName.unknow", "aaa");
			
			for(ObjectError error : result.getAllErrors()){
				System.out.print(error.getDefaultMessage());
			}
			
			return "login";
		} catch (IncorrectCredentialsException e) {
			//这个函数有好几个重载的变体
			result.rejectValue("userName", "userName.unknow", "用户名不存在或密码错误");
			
			return "login";
		}
		
		return "upload";
	}
}
