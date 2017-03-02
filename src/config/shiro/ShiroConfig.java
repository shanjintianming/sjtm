package config.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={SecurityManagerConfig.class})
public class ShiroConfig {
	
	@Resource(name="securityManager")
	public DefaultWebSecurityManager securityManager;
	
	@Bean(name="shiroFilter")  
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean config = new ShiroFilterFactoryBean();
		config.setLoginUrl("/index");
		config.setUnauthorizedUrl("/toUnauthorized");
		config.setSecurityManager(securityManager);
		Map<String,String> filterChainDefinitionMap = new HashMap<String,String>();
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/index.html", "anon");
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/ajaxLogin", "anon");
		filterChainDefinitionMap.put("/oauth/**", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		config.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return config;
	}
}
