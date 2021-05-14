package com.sealll.application.role.aop;

import com.sealll.application.role.service.RoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sealll
 * @time 2021/5/14 19:53
 */
@Aspect
@Component
public class DeleteAspect {
    @Pointcut("execution(public * com.sealll.application.role.service.impl.RoleServiceImpl.*(..))")
    public void point(){}

    @Pointcut("execution(public * com.sealll.application.role.service.impl.RoleServiceImpl.deleteExpire(..)))")
    public void exclude(){}
    @Before("point() && !exclude()")
    public void deleteMore(JoinPoint joinPoint){
        RoleService target = (RoleService) joinPoint.getTarget();
        target.deleteExpire(new Date().getTime() /1000);
    }
}
