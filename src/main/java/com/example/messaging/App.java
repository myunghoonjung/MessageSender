package com.example.messaging;

import com.example.messaging.model.Message;
import com.example.messaging.repository.MessageRepository;
import com.example.messaging.sender.AppPushSender;
import com.example.messaging.sender.EmailSender;
import com.example.messaging.sender.SmsSender;
import com.example.messaging.worker.MessageWorker;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws InterruptedException {
        // Repository 및 Sender 인스턴스 생성
        MessageRepository messageRepository = new MessageRepository();
        SmsSender smsSender = new SmsSender();
        EmailSender emailSender = new EmailSender();
        AppPushSender appSender = new AppPushSender();

        // Worker 인스턴스 생성
        MessageWorker messageWorker = new MessageWorker(messageRepository, smsSender, emailSender, appSender);

        // 메시지 샘플 추가
        messageRepository.saveMessage(new Message(1L, "010-1234-5678", "SMS 메시지입니다.", "SMS", "NEW"));
        messageRepository.saveMessage(new Message(2L, "user@example.com", "이메일 메시지입니다.", "EMAIL", "NEW"));
        messageRepository.saveMessage(new Message(3L, "user123", "앱 알림 메시지입니다.", "APP", "NEW"));

        // 메시지 처리 루프
        while (true) {
            messageWorker.processMessages();
            Thread.sleep(5000); // 5초 대기
        }
    }
}
