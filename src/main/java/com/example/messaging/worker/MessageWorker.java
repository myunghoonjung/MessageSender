package com.example.messaging.worker;

import java.util.List;

import com.example.messaging.model.Message;
import com.example.messaging.repository.MessageRepository;
import com.example.messaging.sender.AppPushSender;
import com.example.messaging.sender.EmailSender;
import com.example.messaging.sender.SmsSender;

public class MessageWorker {

    private final MessageRepository messageRepository;
    private final SmsSender smsSender;
    private final EmailSender emailSender;
    private final AppPushSender appSender;

    public MessageWorker(MessageRepository messageRepository, SmsSender smsSender, EmailSender emailSender, AppPushSender appSender) {
        this.messageRepository = messageRepository;
        this.smsSender = smsSender;
        this.emailSender = emailSender;
        this.appSender = appSender;
    }

    // 메시지를 처리
    public void processMessages() {
        List<Message> newMessages = messageRepository.findNewMessages();

        for (Message message : newMessages) {
            switch (message.getMessageType()) {
                case "SMS":
                    smsSender.send(message);
                    break;
                case "EMAIL":
                    emailSender.send(message);
                    break;
                case "APP":
                    appSender.send(message);
                    break;
                default:
                    System.out.println("지원하지 않는 메시지 유형입니다: " + message.getMessageType());
            }
            messageRepository.updateMessageStatus(message); // 상태를 SENT로 변경
        }
    }
}

