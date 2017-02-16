package config.shiro;

import javax.annotation.Resource;

import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={SessionDaoConfig.class, SessionIdGeneratorConfig.class,
		SessionIdCookieConfig.class, SessionValidationSchedulerConfig.class})
public class SessionManagerConfig {
	
	@Resource(name="sessionDAO")
	public EnterpriseCacheSessionDAO sessionDAO;
	
	@Resource(name="sessionIdGenerator")
	public SessionIdGenerator sessionIdGenerator;
	
	@Resource(name="sessionIdCookie")
	public SimpleCookie sessionIdCookie;
	
	@Resource(name="sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler sessionValidationScheduler;
	
	@Bean(name="sessionManager")  
	public DefaultWebSessionManager shiroFilterFactoryBean() {	
		DefaultWebSessionManager config = new DefaultWebSessionManager();
		config.setSessionDAO(sessionDAO);
		config.setGlobalSessionTimeout(1800000);
		config.setSessionIdCookie(sessionIdCookie);
		config.setSessionIdCookieEnabled(true);
		config.setSessionValidationScheduler(sessionValidationScheduler);
		config.setSessionValidationSchedulerEnabled(true);
		return config;
	}
}
