package com.ibmmqconsumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {
    final JmsTemplate jmsTemplate;
    final ObjectMapper customObjectMapper;

    //    @JmsListener(destination = "${queues.queueDev}", selector = "ADAPTER = 'CAMEL'")
    public void receiveHight(Message message) throws JMSException, JsonProcessingException {
        log.info("T24: " + message.getPayload(), customObjectMapper.writeValueAsString(message));
//        if (message instanceof JMSMessage) {
//            TextMessage textMessage = (TextMessage) message;
//            System.out.println("aaa: " + textMessage.getText());
//        }
    }

    @JmsListener(destination = "${queues.queueDev1}", selector = "ADAPTER = 'CONSUMER'")
    public void receiveLow(Message message) throws JMSException {
        log.info("\nreceive from DEV.QUEUE.1: "+ message.getPayload());
    }

    @JmsListener(destination = "${queues.queueDev2}", selector = "ADAPTER = 'CONSUMER'")
    public void receiveConsume(Message message) throws JMSException {
        log.info("\nreceive from DEV.QUEUE.2: "+ message.getPayload());
    }

}