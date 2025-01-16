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
    
    public List<Message> failedMessagesRetryCount() {
        List<Message> failedMessages = new ArrayList<>();

        // FAILED 상태의 메시지를 조회하며 retry_count가 0보다 큰 경우에만 대상 추가
        for (Message message : failedMessages) {
            if ("FAILED".equals(message.getStatus()) && message.getRetryCount() > 0) {
                failedMessages.add(message);

                // 조회된 메시지 상태를 'PROCESSING'으로 업데이트
                message.setStatus("PROCESSING");
            }
        }

        return failedMessages;
    }

    // 메시지 상태를 업데이트
    public void updateMessageStatus(Message message) {
        message.setStatus("SENT");
        System.out.println("DB에서 메시지 상태를 업데이트: " + message);
    }
}
