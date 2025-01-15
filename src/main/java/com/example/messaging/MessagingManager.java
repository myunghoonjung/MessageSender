package com.example.messaging;

import com.example.messaging.worker.AppPushWorker;
import com.example.messaging.worker.EmailWorker;
import com.example.messaging.worker.SmsWorker;

class MessagingManager {
    private final SmsWorker smsWorker;
    private final EmailWorker emailWorker;
    private final AppPushWorker appPushWorker;

    public MessagingManager() {
        this.smsWorker = new SmsWorker();
        this.emailWorker = new EmailWorker();
        this.appPushWorker = new AppPushWorker();
    }

    public SmsWorker getSmsWorker() {
        return smsWorker;
    }

    public EmailWorker getEmailWorker() {
        return emailWorker;
    }

    public AppPushWorker getAppPushWorker() {
        return appPushWorker;
    }

    /**
     * 지속적으로 메시지를 처리하는 메서드 (별도의 쓰레드로 실행)
     */
    public void startContinuousProcessing() {
        Runnable task = () -> {
            while (true) {
                try {
                    smsWorker.process();
                    emailWorker.process();
                    appPushWorker.process();
                    Thread.sleep(5000); // 5초 간격으로 메시지 감지
                } catch (InterruptedException e) {
                    System.out.println("[System] Continuous processing interrupted.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}