package com.example.messaging.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.messaging.worker.MessageWorker;

@Component
public class MessageScheduler {

    @Autowired
    private MessageWorker messageWorker;

    // 5초마다 실행
    @Scheduled(fixedRate = 5000)
    public void run() {
        messageWorker.processMessages();
    }
}

