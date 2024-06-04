package com.aop;

import com.alibaba.fastjson.JSONObject;
import com.mapper.OperateLogMapper;
import com.pojo.OperateLog;
import com.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.aop.Log)") // 处理所有被@Log注解的方法
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获得请求头中的jwt令牌，解析出用户信息
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer operateUser = (Integer) claims.get("id"); // 用户id
        LocalDateTime operateTime = LocalDateTime.now(); // 操作时间
        String className = joinPoint.getTarget().getClass().getName(); // 操作类名
        String methodName = joinPoint.getSignature().getName(); // 操作方法名
        Object[] args = joinPoint.getArgs(); // 操作方法参数
        String methodParams = Arrays.toString(args);
        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // 执行目标方法
        long end = System.currentTimeMillis();
        String returnValue = JSONObject.toJSONString(result); // 操作方法返回值转json字符串
        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, methodName, methodParams, returnValue, end - begin);
        operateLogMapper.insert(operateLog); // 插入操作日志数据库
        log.info("Using AOP to record log.");
        return result;
    }
}
