package initializer;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import config.RootConfig;
import config.SpringMvcConfig;

@Order(2)  
public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {SpringMvcConfig.class};
	}

	 /* 
     * DispatcherServlet的映射路径 
     */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}    
}  
