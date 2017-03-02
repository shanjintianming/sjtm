package config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={SqlSessionFactory.class})
public class DaoConfig {
	
	@Bean(name="MapperScannerConfigurer")  
	public MapperScannerConfigurer mapperScannerConfigurer() {	
		MapperScannerConfigurer config = new MapperScannerConfigurer();
		config.setSqlSessionFactoryBeanName("SqlSessionFactor");
		config.setBasePackage("springdemo.*.dao");
		return config;
	}
}
