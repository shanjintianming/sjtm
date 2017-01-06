package initializer;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

@Order(1)  
public class CommonInitializer implements WebApplicationInitializer{  
  
    @Override  
    public void onStartup(ServletContext servletContext)  
            throws ServletException { 
    	
    	// Log4jServletContextListener  
        servletContext.addListener(Log4jServletContextListener.class);
        
        // Log4jServletFilter  
        Log4jServletFilter logFilter = new Log4jServletFilter(); 
        
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(  
                "logFilter", logFilter);  
        filterRegistration.addMappingForUrlPatterns(  
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR), false, "/*");
        
        // shiroFilter  
        DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy();
        shiroFilter.setTargetFilterLifecycle(true);
        FilterRegistration.Dynamic shiroRegistration = servletContext.addFilter(  
                "shiroFilter", shiroFilter);  
        shiroRegistration.addMappingForUrlPatterns(  
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
    }
}  
