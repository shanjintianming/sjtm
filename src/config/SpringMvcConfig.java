package config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

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
	 * 描述 : <基于cookie的本地化资源处理器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	@Bean(name = "localeResolver")
	public CookieLocaleResolver cookieLocaleResolver() {
		return new CookieLocaleResolver();
	}

	/**
	 * 描述 : <注册html视图处理器>. <br>
	 * 
	 * @return
	 */
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource config = new ReloadableResourceBundleMessageSource();
		config.setBasenames("classpath:property/ValidationMessages",
				"classpath:org/hibernate/validator/ValidationMessages_zh_CN");
		config.setDefaultEncoding("UTF-8");
		config.setUseCodeAsDefaultMessage(false);
		config.setCacheSeconds(60);
		return config;
	}

	@Override
	protected MessageCodesResolver getMessageCodesResolver() {
		MessageCodesResolver config = new DefaultMessageCodesResolver();
		return config;
	}

	@Override
	protected Validator getValidator() {
		LocalValidatorFactoryBean config = new LocalValidatorFactoryBean();
		config.setProviderClass(HibernateValidator.class);
		config.setValidationMessageSource(messageSource());
		return config;
	}
	
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
	 * 描述 : <本地化拦截器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	/**
	 * 描述 : <资源访问处理器>. <br>
	 * <p>
	 * <可以在jsp中使用/static/**的方式访问/WebContent/static/下的内容>
	 * </p>
	 * 
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/index.html").addResourceLocations("/index.html");
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver config = new CommonsMultipartResolver();
		config.setDefaultEncoding("UTF-8");
		config.setMaxUploadSize(10485760000L);
		config.setMaxInMemorySize(40960);
		return config;
	}
	
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(jsonHttpMessageConverter());
	}
	
	@Bean(name = "stringHttpMessageConverter")
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter config = new StringHttpMessageConverter();

		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		Charset charset = Charset.forName("UTF-8");
		MediaType textMediaType = new MediaType("text", "plain", charset);
		MediaType jsonMediaType = new MediaType("application", "json", charset);
		supportedMediaTypes.add(textMediaType);
		supportedMediaTypes.add(jsonMediaType);
		config.setSupportedMediaTypes(supportedMediaTypes);
		return config;
	}
	
	@Bean(name = "mappingJacksonHttpMessageConverter")
	public MappingJackson2HttpMessageConverter jsonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter config = new MappingJackson2HttpMessageConverter();

		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		Charset charset = Charset.forName("UTF-8");
		MediaType textMediaType = new MediaType("text", "plain", charset);
		MediaType jsonMediaType = new MediaType("application", "json", charset);
		supportedMediaTypes.add(textMediaType);
		supportedMediaTypes.add(jsonMediaType);
		config.setSupportedMediaTypes(supportedMediaTypes);
		return config;
	}
}