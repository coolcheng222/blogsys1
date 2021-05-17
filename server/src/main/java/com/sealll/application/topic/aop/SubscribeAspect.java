package com.sealll.application.topic.aop;

import com.sealll.application.post.bean.Post;
import com.sealll.bean.Msg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Text;

import javax.jms.*;

/**
 * @author sealll
 * @time 2021/5/17 21:44
 */
@Aspect
public class SubscribeAspect {
    @Pointcut("execution(public * com.sealll.application.sub.controller.SubscribeController.addSub(..))")
    public void pointcutx(){}

    @Pointcut("execution(public * com.sealll.application.sub.controller.SubscribeController.deleteSub(..))")
    public void pointcutz(){}

    @Pointcut("execution(public * com.sealll.application.post.controller.PostController.addPost(..))")
    public void pointcuty(){}

    @Autowired
    private ConnectionFactory connectionFactory;

    @AfterReturning(value="pointcutx()",returning = "msg")
    public void addSub(JoinPoint joinPoint, Msg msg) throws JMSException {
        System.out.println("test1111");
        if (msg.getErrno() == 0) {
            Object[] args = joinPoint.getArgs();
            String uid = (String) args[0];
            String subbed = (String) args[1];
            subscribe(uid, subbed);
            return;
        }
    }
    @AfterReturning(value="pointcutz()",returning = "msg")
    public void deleteSub(JoinPoint joinPoint, Msg msg) throws JMSException {
        System.out.println("test222");
        if (msg.getErrno() == 0) {
            Object[] args = joinPoint.getArgs();
            String uid = (String) args[0];
            String subbed = (String) args[1];
            unsubscribe(uid, subbed);
            return;
        }
    }

    @AfterReturning(value="pointcuty()",returning = "msg")
    public void post(JoinPoint joinPoint,Msg msg){
        if(msg.getErrno() == 0){
            Post post = (Post)joinPoint.getArgs()[0];

        }
    }

    private void subscribe(String uid, String subbed) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(uid);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        Topic topic = session.createTopic(subbed);
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, uid + subbed);
        connection.close();
        session.close();
        durableSubscriber.close();
    }
    private void unsubscribe(String uid, String subbed) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(uid);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        session.unsubscribe(uid + subbed);
        connection.close();
        session.close();
    }
    private void posting(String subbed) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        Topic topic = session.createTopic(subbed);
        MessageProducer producer = session.createProducer(topic);
        producer.send(session.createTextMessage(""));
        connection.close();
        session.close();
        producer.close();
    }

}
