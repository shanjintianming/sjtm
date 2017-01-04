package config.shiro;

import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionIdGeneratorConfig {
	
	@Bean(name="sessionIdGenerator")  
	public SessionIdGenerator sessionIdGenerator() {	
		SessionIdGenerator config = new JavaUuidSessionIdGenerator();
		return config;
	}
}
