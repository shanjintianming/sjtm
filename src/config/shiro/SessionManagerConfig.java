package config.shiro;

import javax.annotation.Resource;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={SessionDaoConfig.class})
public class SessionManagerConfig {
	
	@Resource(name="sessionDAO")
	public EnterpriseCacheSessionDAO sessionDAO;
	
	@Bean(name="sessionManager")  
	public DefaultSessionManager shiroFilterFactoryBean() {	
		DefaultSessionManager config = new DefaultSessionManager();
		config.setSessionDAO(sessionDAO);
		config.setGlobalSessionTimeout(1800000);
		config.setDeleteInvalidSessions(true);
		config.setSessionValidationSchedulerEnabled(false);
		return config;
	}
}
