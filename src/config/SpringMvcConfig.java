package config;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

@Configuration
@ComponentScan(basePackages = "springDemo.*.controller", useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
public class SpringMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 描述 : <注册jsp试图处理器>. <br>
	 * 
	 * @return
	 */
	/*
	 * @Bean public ViewResolver viewResolver() { InternalResourceViewResolver
	 * viewResolver = new InternalResourceViewResolver();
	 * viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.
	 * class); viewResolver.setPrefix("/WEB-INF/view/");
	 * viewResolver.setSuffix(".jsp");
	 * viewResolver.setContentType("text/html;charset=UTF-8"); return
	 * viewResolver; }
	 */

	/**
	 * 描述 : <注册html视图处理器>. <br>
	 * 
	 * @return
	 */
	@Bean
	public BeetlGroupUtilConfiguration beetlConfig() {
		BeetlGroupUtilConfiguration beetlConfig = new BeetlGroupUtilConfiguration();
		beetlConfig.setRoot(null);
		beetlConfig.init();
		return beetlConfig;
	}
	
	@Bean
	public BeetlSpringViewResolver viewResolver() {
		BeetlSpringViewResolver viewResolver = new BeetlSpringViewResolver();
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
	 * 描述 : <HandlerMapping需要显示声明，否则不能注册资源访问处理器>. <br>
	 * <p>
	 * <这个比较奇怪,理论上应该是不需要的>
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public HandlerMapping resourceHandlerMapping() {
		return super.resourceHandlerMapping();
	}

	/**
	 * 描述 : <资源访问处理器>. <br>
	 * <p>
	 * <可以在jsp中使用/static/**的方式访问/WEB-INF/static/下的内容>
	 * </p>
	 * 
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/login.html").addResourceLocations("/static/html/login.html");
		registry.addResourceHandler("/index.html").addResourceLocations("/static/html/index.html");
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	public ClassLoader getDefaultClassLoader()
	/*      */ {
		/* 158 */ ClassLoader cl = null;
		/*      */ try {
			/* 160 */ cl = Thread.currentThread().getContextClassLoader();
			/*      */ }
		/*      */ catch (Throwable localThrowable) {
		}
		/*      */
		/*      */
		/* 165 */ if (cl == null)
		/*      */ {
			/* 167 */ cl = SpringMvcConfig.class.getClassLoader();
			/* 168 */ if (cl == null) {
				/*      */ try
				/*      */ {
					/* 171 */ cl = ClassLoader.getSystemClassLoader();
					/*      */ }
				/*      */ catch (Throwable localThrowable1) {
				}
				/*      */ }
			/*      */ }
		/*      */
		/*      */
		/* 178 */ return cl;
		/*      */ }
}