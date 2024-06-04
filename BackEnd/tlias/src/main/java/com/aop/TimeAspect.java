package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeAspect {

    @Around("execution(* com.service.*.*(..))") // 拦截service包下所有类的所有方法
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis(); // 在目标方法执行之前记录时间
        Object result = joinPoint.proceed(); // 执行目标原方法
        long end = System.currentTimeMillis(); // 在目标方法执行之后记录时间
        System.out.println(joinPoint.getSignature() + "Time: " + (end - begin) + "ms");
        return result; // 返回目标方法的返回值
    }
}
