package com.sealll.application.messaging.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.application.message.bean.Message2;
import com.sealll.application.messaging.mapper.MessagingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 14:12
 */
@Repository
public class MessagingMapperImpl implements MessagingMapper {
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public Integer getCount(String uid) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(uid);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(uid);
        connection.start();
        QueueBrowser browser = session.createBrowser(queue);
        Enumeration enumeration = browser.getEnumeration();
        Integer i = Collections.list(enumeration).size();
        session.close();
        connection.close();
        return i;
    }

    @Override
    public List<Message2> getUnread(String uid) throws JMSException {

        Connection connection = connectionFactory.createConnection();
        connection.setClientID(uid);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(uid);
        connection.start();
        List<Message2> list = new ArrayList<>();
        MessageConsumer consumer = session.createConsumer(queue);
        try{
            while(true){
                Message receive = consumer.receive(1000);
                if(receive == null){
                    break;
                }
                if(receive instanceof TextMessage){
                    String text = ((TextMessage) receive).getText();
                    Message2 message2 = objectMapper.readValue(text, Message2.class);
                    list.add(message2);
                }
            }
            return list;
        }catch (Exception e){
            return list;
        }

    }
}
