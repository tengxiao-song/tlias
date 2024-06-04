package com.aop;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Order(1)
public class MyAspect1 {

    private final HikariDataSource dataSource;

    public MyAspect1(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 定义切入点, 保存复用切入点表达式
    // * 代表任意单个返回值, .. 代表任意个任意类型参数，根据需要可以使用 || 或者 && 连接多个表达式
    @Pointcut("execution(* com.service.*.*(..))")
    // 使用自定义注解, 在想要的方法上使用 @MyLog 注解建立连接
//    @Pointcut("@annotation(com.aop.MyLog)")
    private void m1() {}

    @Before("m1()")
    public void before(JoinPoint joinPoint) throws Throwable {
        log.info("before");
        System.out.println("使用JoinPoint: " + joinPoint.getSignature());
    }

    @Around("m1()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("around before");
        Object result = joinPoint.proceed(); // 手动调用目标方法
        log.info("around after");
        return result; // 返回目标方法的返回值
    }

    @After("m1()")
    public void after() {
        log.info("after");
    }

    @AfterReturning("m1()")
    public void afterReturning() {
        log.info("afterReturning");
    }

    @AfterThrowing("m1")
    public void afterThrowing() {
        log.info("afterThrowing");
    }
}
