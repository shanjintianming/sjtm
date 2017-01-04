package config.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springDemo.shiro.UserRealm;

@Configuration
public class RealmConfig {
	
	@Bean(name="userRealm")  
	public UserRealm mapperScannerConfigurer() {	
		UserRealm config = new UserRealm();
		return config;
	}
}
