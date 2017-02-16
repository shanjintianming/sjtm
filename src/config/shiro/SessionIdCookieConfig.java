package config.shiro;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionIdCookieConfig {
	
	@Bean(name="sessionIdCookie")  
	public SimpleCookie sessionIdCookie() {	
		SimpleCookie config = new SimpleCookie();
		config.setName("sid");
		config.setHttpOnly(true);
		return config;
	}
}
