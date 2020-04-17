package indi.rui.study.redisson;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

/**
 * @author: yaowr
 * @create: 2020-02-01
 */
@Slf4j
@Aspect
@Component
public class SpendAspect {

    @Pointcut("execution(* indi.rui.study..*.*ServiceImpl.*(..))")
    public void anyMethod() {}

    @Around(value = "anyMethod()")
    public Object spend(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long spendTime = System.currentTimeMillis() - begin;
            log.info("\n{} 耗时【{}】", pjp.getSignature().getDeclaringTypeName() + "."
                    + pjp.getSignature().getName(), format(spendTime));
        }
    }

    private static DecimalFormat format = new DecimalFormat("000");

    public static String format(long timeMillis) {
        long z = timeMillis / 1000;
        if (z == 0) {
            return timeMillis + "ms";
        }
        return z + "." + format.format(timeMillis % 1000) + "s";
    }
}
