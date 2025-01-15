package com.example.messaging.sender;

import org.springframework.stereotype.Component;

import com.example.messaging.model.Message;

@Component("emailSender")
public class EmailSender implements MessageSender {

    @Override
    public void send(Message message) {
        System.out.println("Email 메시지를 전송. " + message);
    }
}