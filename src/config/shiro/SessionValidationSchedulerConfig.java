package config.shiro;

import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionValidationSchedulerConfig {
	
	@Bean(name="sessionValidationScheduler")  
	public ExecutorServiceSessionValidationScheduler getSessionValidationScheduler() {	
		ExecutorServiceSessionValidationScheduler config = new ExecutorServiceSessionValidationScheduler();
		// config.setSessionManager(sessionManager);
		config.setInterval(1800000);
		return config;
	}
}
