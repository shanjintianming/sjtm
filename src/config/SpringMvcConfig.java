package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "springDemo.*.controller", useDefaultFilters=false, includeFilters={
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value={Controller.class})
})
public class SpringMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 描述 : <注册试图处理器>. <br>
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".html");
		viewResolver.setContentType("text/html;charset=UTF-8");
		return viewResolver;
	}

	/**
	 * 描述 : <注册servlet适配器>. <br>
	 * <p>
	 * <只需要在自定义的servlet上用@Controller("映射路径")标注即可>
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public HandlerAdapter servletHandlerAdapter() {
		return new SimpleServletHandlerAdapter();
	}

	/**                                                           
     * 描述 : <资源访问处理器>. <br>  
     *<p>  
        <可以在jsp中使用/static/**的方式访问/WEB-INF/static/下的内容>   
      </p>                                                                                                                                                                                                                                                 
     * @param registry                                                                                                       
     */    
    @Override  
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/WEB-INF/view/*.html").addResourceLocations("/WEB-INF/view/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");  
    }  
}