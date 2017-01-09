package config.ehcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheManagerConfig {
	
	@Bean(name="ehcacheManager")  
	public EhCacheManagerFactoryBean getEhCacheManagerFactoryBean() {	
		EhCacheManagerFactoryBean config = new EhCacheManagerFactoryBean();
		config.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return config;
	}
	
	@Bean(name="cacheManager")  
	public EhCacheCacheManager getEhCacheCacheManager() {	
		EhCacheCacheManager config = new EhCacheCacheManager();
		config.setCacheManager(getEhCacheManagerFactoryBean().getObject());
		// config.setTransactionAware(transactionAware);
		return config;
	}
}
