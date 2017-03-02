package springdemo.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class LogAop {
	
	Logger logger =  LoggerFactory.getLogger(LogAop.class);
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* springDemo.*.service..*(..))")
	public void logAspect() { }
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@AfterThrowing(pointcut = "logAspect()", throwing = "e")
	public void beforeLogAspect(JoinPoint joinPoint, Throwable e) { 
		logger.error("error" , e);
	}
}
