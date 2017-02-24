package springDemo.core.beetl.function;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Context;
import org.beetl.core.Function;

import springDemo.core.shiro.Principal;

public class UserFunction implements Function {

	@Override
	public Object call(Object[] arg0, Context arg1) {
		Subject subject = SecurityUtils.getSubject();
		
		// 用户是否登录
		if (subject.isAuthenticated()) {
			return (Principal)subject.getPrincipal();
		}
		
		return null;
	}

}
