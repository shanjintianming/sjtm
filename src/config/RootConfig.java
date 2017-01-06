package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import config.shiro.ShiroFilterConfig;

@Configuration 
@ComponentScan(basePackages = "springDemo", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
@EnableAspectJAutoProxy(proxyTargetClass=true)  
//启用注解事务管理，使用CGLib代理  
@EnableTransactionManagement(proxyTargetClass = true)
@Import(value={DaoConfig.class, ShiroFilterConfig.class})
public class RootConfig {
	
}
