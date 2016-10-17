package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration 
@PropertySource("classpath:/property/datasource.properties")
public class DataSourceConfig {  

	/* 
     * 绑定资源属性 
     */  
    @Value("${jdbc.master.driverClassName}")  
    String driverClass;  
    @Value("${jdbc.master.url}")  
    String url;  
    @Value("${jdbc.master.username}")  
    String userName;  
    @Value("${jdbc.master.password}")  
    String passWord;  
      
    @Bean(name = "dataSource")  
    public DruidDataSource dataSource() {  

        DruidDataSource dataSource = new DruidDataSource();  
        dataSource.setDriverClassName(driverClass);  
        dataSource.setUrl(url);  
        dataSource.setUsername(userName);  
        dataSource.setPassword(passWord);  
        dataSource.setValidationQuery("select 1");
        return dataSource;  
    }  
}  