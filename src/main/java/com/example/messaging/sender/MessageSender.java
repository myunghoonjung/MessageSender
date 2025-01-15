package com.example.messaging.sender;

import com.example.messaging.model.Message;

public interface MessageSender {
    void send(Message message);
}