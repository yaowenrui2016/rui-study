package indi.rui.study.websocket.notify.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Component
@Aspect
public class NotifySenderLogAspect {
    @Pointcut("target(indi.rui.study.websocket.notify.sender.ISender)")
    public void sender() {}
    @Pointcut("execution(void send(..)) || execution(void done(..))")
    public void include() {}

    @Around(value = "sender() && include()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println(joinPoint.getSignature().getName());
            return joinPoint.proceed();
        } catch (Throwable throwable) {

            throw throwable;
        }
    }
}
