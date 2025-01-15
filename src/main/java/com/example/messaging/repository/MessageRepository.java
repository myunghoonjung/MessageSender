package com.example.messaging.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.messaging.model.Message;

public class MessageRepository {

    private final List<Message> messageDB = new ArrayList<>();

    // 메시지를 저장
    public void saveMessage(Message message) {
        messageDB.add(message);
        System.out.println("DB에 메시지를 저장: " + message);
    }

    // 상태가 NEW인 메시지를 조회
    public List<Message> findNewMessages() {
        List<Message> newMessages = new ArrayList<>();
        for (Message message : messageDB) {
            if ("NEW".equals(message.getStatus())) {
                newMessages.add(message);
            }
        }
        return newMessages;
    }

    // 메시지 상태를 업데이트
    public void updateMessageStatus(Message message) {
        message.setStatus("SENT");
        System.out.println("DB에서 메시지 상태를 업데이트: " + message);
    }
}
