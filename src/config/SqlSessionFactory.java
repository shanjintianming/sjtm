package config;

import java.io.IOException;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration 
@Import(value={DataSourceConfig.class})
public class SqlSessionFactory {
	
	@Resource(name="dataSource")
	public DruidDataSource dataSource;
	
	@Bean(name="SqlSessionFactor")
	public SqlSessionFactoryBean sqlSessionFactor() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();       
        String typeAliasesPackage = "classpath*:springDemo/**/mapper/*.xml";    

		try {
			org.springframework.core.io.Resource[] resources = resolver.getResources(typeAliasesPackage);
			sqlSessionFactoryBean.setMapperLocations(resources);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		
		return sqlSessionFactoryBean;
	}
	
	@Bean(name="transactionManager")
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}
}
