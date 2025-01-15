package com.example.messaging.sender;

import org.springframework.stereotype.Component;

import com.example.messaging.model.Message;

@Component("appSender")
public class AppPushSender implements MessageSender {

    @Override
    public void send(Message message) {
        System.out.println("App 알림을 전송. " + message);
    }
}