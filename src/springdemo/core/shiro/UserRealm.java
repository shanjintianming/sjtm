package springdemo.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import springdemo.user.service.UserService;
import springdemo.user.vo.OauthUser;

public class UserRealm extends AuthorizingRealm  implements ApplicationContextAware {
	
	//手动注入bean
	private ApplicationContext applicationContext;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermission("主页");
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		
		// 用户名
		String userName = token.getUsername();
		
		// 密码
		String password = new String(token.getPassword());
		
		// 获取用户Service
		UserService userService = (UserService) applicationContext.getBean("userService");
		
		// 查询用户
		OauthUser oauthUser = userService.selectByUserName(userName);
		
		// 用户是否存在
		if (!userName.equals(oauthUser.getUserName())) {
			throw new UnknownAccountException();
		}
		
		// 密码是否正确
		if (!password.equals(oauthUser.getUserPassword())) {
			throw new IncorrectCredentialsException();
		}
		
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		new Principal(oauthUser.getUserId(), userName), //用户信息
        		password, //密码
                getName()  //realm name
        );
        
        return authenticationInfo;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext = arg0;
	}

}
