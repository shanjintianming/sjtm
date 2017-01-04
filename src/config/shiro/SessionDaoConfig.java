package config.shiro;

import javax.annotation.Resource;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={SessionIdGeneratorConfig.class})
public class SessionDaoConfig {
	
	@Resource(name="sessionIdGenerator")
	public SessionIdGenerator sessionIdGenerator;
	
	@Bean(name="sessionDAO")  
	public EnterpriseCacheSessionDAO shiroFilterFactoryBean() {	
		EnterpriseCacheSessionDAO config = new EnterpriseCacheSessionDAO();
		config.setSessionIdGenerator(sessionIdGenerator);
		return config;
	}
}
