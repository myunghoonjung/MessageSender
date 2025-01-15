package com.example.messaging.sender;

import org.springframework.stereotype.Component;

import com.example.messaging.model.Message;

@Component("smsSender")
public class SmsSender implements MessageSender {

    @Override
    public void send(Message message) {
        System.out.println("SMS 메시지를 전송. " + message);
    }
}