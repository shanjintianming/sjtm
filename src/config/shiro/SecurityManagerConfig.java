package config.shiro;

import javax.annotation.Resource;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springDemo.shiro.UserRealm;

@Configuration
@Import(value={RealmConfig.class})
public class SecurityManagerConfig {
	
	@Resource(name="userRealm")
	public UserRealm userRealm;
	
	@Bean(name="securityManager")  
	public DefaultWebSecurityManager mapperScannerConfigurer() {	
		DefaultWebSecurityManager config = new DefaultWebSecurityManager();
		config.setRealm(userRealm);
		return config;
	}
}
