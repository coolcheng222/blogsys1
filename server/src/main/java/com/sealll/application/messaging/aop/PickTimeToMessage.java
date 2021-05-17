package com.sealll.application.messaging.aop;

import com.sealll.application.message.bean.Message2;
import com.sealll.application.message.producer.GlobalMessageProducer;
import com.sealll.application.messaging.converter.Message2Converter;
import com.sealll.application.reply.bean.Reply;
import com.sealll.application.role.bean.Role;
import com.sealll.bean.Msg;
import org.apache.tomcat.jni.Global;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;

/**
 * @author sealll
 * @time 2021/5/17 11:12
 */
@Aspect
public class PickTimeToMessage {
   @Autowired
   private Message2Converter message2Converter;
   @Autowired
   private GlobalMessageProducer producer;

    @Pointcut("execution(public * com.sealll.application.reply.controller.ReplyController.addReply(..))")
    public void pointcut1(){}
    @Pointcut("execution(public * com.sealll.application.role.controller.RoleController.addRole(com.sealll.application.role.bean.Role))")
    public void pointcut2(){}
    @Pointcut("execution(public * com.sealll.application.sub.controller.SubscribeController.addSub(..))")
    public void pointcut3(){}
    @AfterReturning(value="pointcut1()",returning = "msg")
    public void cutReply(JoinPoint joinPoint, Msg msg) throws JMSException {
        System.out.println("==========point1");
        if(msg.getErrno() == 0){
            Object[] args = joinPoint.getArgs();
            Reply arg = (Reply)args[0];
            Message2 convert = message2Converter.convert(arg);
            producer.produceMessage(convert);
        }
    }

    @AfterReturning(value="pointcut2()",returning="msg")
    public void cutRole(JoinPoint joinPoint,Msg msg) throws JMSException {
        System.out.println("==========point2");
        if(msg.getErrno() == 0){
            Object[] args = joinPoint.getArgs();
            Role arg = (Role)args[0];
            Message2 convert = message2Converter.convert(arg);
            producer.produceMessage(convert);
        }
    }
    @AfterReturning(value="pointcut3()",returning="msg")
    public void cutSub(JoinPoint joinPoint,Msg msg) throws JMSException {
        System.out.println("==========point3");
        if(msg.getErrno() == 0){
            Object[] args = joinPoint.getArgs();
            String subber = (String)args[0];
            String subbed = (String)args[1];
            Message2 convert = message2Converter.convert(subber,subbed);
            producer.produceMessage(convert);
        }
    }


}
