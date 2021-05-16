package com.sealll.application.history.aop;

import com.sealll.application.history.service.HistoryService;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sealll
 * @time 2021/5/15 22:55
 */
@Aspect
public class SearchAspect {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private Logger logger;

    @Pointcut("execution(public * com.sealll.application.search.controller.SearchController.record(..))")
    public void pointCut1(){}

    @AfterReturning("pointCut1()")
    public void addHistory(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            String uid = (String)subject.getSession().getAttribute(ParameterConstants.UID_SESSION_KEY);
            String title = args[0].toString();
            boolean b = historyService.addSearchHistory(uid, title);
            if(b){
                logger.info("保存搜索历史记录成功");
            }
        }
    }
}
