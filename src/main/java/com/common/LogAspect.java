package com.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private ConcurrentHashMap<Long, Long> cache = new ConcurrentHashMap<>();

    @Pointcut("execution(public * com.controller.*.*(..))")
    public void logic() {}

    @Before("logic()")
    public void doBeforeLogic(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long threadId = Thread.currentThread().getId();
        long currTime = System.currentTimeMillis();
        log.info("Thread:[{}] of ip:[{}] is trying to visit url:[{}] as method:[{}], which mapping to:[{}] at:[{}]",
                threadId, request.getRemoteAddr(), request.getRequestURI(), request.getMethod(),
                joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName(), currTime);
        cache.put(threadId, currTime);
    }

    @AfterReturning(returning = "object", pointcut = "logic()")
    public void doAfterLogic(Object object) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long threadId = Thread.currentThread().getId();
        long currTime = System.currentTimeMillis();
        long fromTime = Optional.ofNullable(cache.get(threadId)).orElse(0L);
        log.info("Thread:[{}] Finished visit url:[{}] as method:[{}] at:[{}] took:[{}]",
                threadId, request.getRequestURI(), request.getMethod(), currTime, (currTime - fromTime));
        cache.remove(threadId);
    }

    @Pointcut("execution( * com.service.impl..*(..))")
    public void allServiceLogic() {}

    @Around("allServiceLogic()")
    public Object doAroundServiceLogic(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();

        log.info("Method:[{}], duration:[{}]", pjp.getSignature().getName(), (end - start));

        return result;
    }
}
