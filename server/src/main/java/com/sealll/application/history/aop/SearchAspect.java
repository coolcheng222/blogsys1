package com.sealll.application.history.aop;

import com.sealll.application.history.service.HistoryService;
import com.sealll.bean.Msg;
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
@Component
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
        String uid = args[0].toString();
        String title = args[1].toString();
        boolean b = historyService.addSearchHistory(uid, title);
        if(b){
            logger.info("保存搜索历史记录成功");
        }
    }
}
