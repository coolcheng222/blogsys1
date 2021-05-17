package com.sealll.application.message.producer;

import com.sealll.application.message.bean.Message2;
import com.sealll.application.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author sealll
 * @time 2021/5/17 10:29
 */
@Component
public class GlobalMessageProducer {
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private MessageService messageService;

    private Connection connection = connectionFactory.createConnection();

    public GlobalMessageProducer() throws JMSException {
    }

    public boolean produceMessage(Message2 message2) throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(message2.getUid());
        MessageProducer producer = session.createProducer(queue);
        ObjectMessage message = session.createObjectMessage(message2);
        producer.send(message);
        session.close();
        return true;
    }
}
