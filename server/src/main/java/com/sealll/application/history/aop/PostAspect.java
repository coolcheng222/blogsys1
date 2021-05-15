package com.sealll.application.history.aop;

import com.sealll.application.history.service.HistoryService;
import com.sealll.application.post.bean.Post;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sealll
 * @time 2021/5/15 23:10
 */
@Aspect
@Component
public class PostAspect {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private Logger log;

    @Pointcut("execution(public * com.sealll.application.post.controller.PostController.readPost(..))")
    public void point(){}

    @AfterReturning(value="point()",returning = "msg")
    public void addPostHistory(Msg msg){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            String uid = (String)subject.getSession().getAttribute(ParameterConstants.UID_SESSION_KEY);
            if(msg != null){
                Integer errno = msg.getErrno();
                if(errno == 0){
                    String pid = ((Post)msg.getExtend()).getpId();
                    boolean b = historyService.addPostHistory(uid, pid);
                    if(b){
                        log.info("添加历史记录路成功");
                    }
                }
            }
        }

    }
}
