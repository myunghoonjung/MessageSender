package com.example.messaging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.messaging.model.Message;
import com.example.messaging.repository.MessageRepository;

@Component
public class MessageManager {

    @Autowired
    private MessageRepository messageRepository;

    // 메시지를 저장
    public void saveMessage(Message message) {
        messageRepository.saveMessage(message);
    }

    // 새로운 메시지를 조회
    public List<Message> getNewMessages() {
        return messageRepository.findNewMessages();
    }

    // 메시지 상태를 업데이트
    public void markMessageAsSent(Message message) {
        message.setStatus("SENT");
        messageRepository.updateMessageStatus(message);
    }
}

