package springDemo.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import springDemo.user.service.TestService;

//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class LogAop {
	
	Logger logger =  LogManager.getLogger(TestService.class);
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* springDemo.*.service..*(..))")
	public void logAspect() { }
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Before("logAspect()")
	public void beforeLogAspect(JoinPoint joinPoint) { 
		
		joinPoint.getSourceLocation().getFileName();
		
		logger.trace(joinPoint.getSignature().getClass().toString() + joinPoint.getSignature().getName());
	}
}
