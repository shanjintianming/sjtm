package config.shiro;

import javax.annotation.Resource;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.ehcache.CacheManagerConfig;
import springDemo.core.shiro.UserRealm;

@Configuration
@Import(value={RealmConfig.class, CacheManagerConfig.class})
public class SecurityManagerConfig {
	
	@Resource(name="userRealm")
	public UserRealm userRealm;
	
	@Resource(name="shiroCacheManager")
	public EhCacheManager shiroCacheManager;
	
	@Bean(name="securityManager")  
	public DefaultWebSecurityManager mapperScannerConfigurer() {	
		DefaultWebSecurityManager config = new DefaultWebSecurityManager();
		config.setRealm(userRealm);
		config.setCacheManager(shiroCacheManager);
		return config;
	}
}
