package springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint tjp) {
		
		String theMethod = tjp.getSignature().toShortString();
		myLogger.info("====>>> in @Before: calling method: " + theMethod);
		
		Object[] args = tjp.getArgs();
		
		for (Object temp: args) {
			myLogger.info("====>>> argument: " + temp);
		}
	}
	
	@AfterReturning(pointcut="forAppFlow()", returning="theResult")
	public void afterReturning(JoinPoint tjp, Object theResult) {
		
		String theMethod = tjp.getSignature().toShortString();
		myLogger.info("====>>> in @AfterReturning: from method: " + theMethod);
		
		myLogger.info("====>>> result: " + theResult);
	}
}
