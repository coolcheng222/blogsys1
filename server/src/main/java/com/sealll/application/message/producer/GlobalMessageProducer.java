package com.sealll.application.message.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    public GlobalMessageProducer() throws JMSException {
    }

    public boolean produceMessage(Message2 message2) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(message2.getUid());
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(message2.getUid());
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        TextMessage message = null;
        try {
            message = session.createTextMessage(
                    objectMapper.writeValueAsString(message2)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        producer.send(message);
        messageService.addMessage(message2);
        session.close();
        connection.close();
        producer.close();
        return true;
    }
}
