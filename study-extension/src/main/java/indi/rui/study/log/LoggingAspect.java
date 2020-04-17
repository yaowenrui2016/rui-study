package indi.rui.study.log;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2019-10-14
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * indi.rui.study..*.*(..))")
    public void anyMethod() {}

    @Before("anyMethod()")
    public void writeLog(JoinPoint joinPoint) {
        log.info("Logged: {} {} {}", joinPoint.getTarget(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
}
