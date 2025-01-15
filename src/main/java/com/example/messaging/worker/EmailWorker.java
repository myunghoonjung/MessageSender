package com.example.messaging.worker;

/**
 * EmailWorker - Email 작업을 처리하는 클래스
 */
public class EmailWorker extends Worker {
    @Override
    protected String getLoadQuery() {
        return "SELECT * FROM email_messages WHERE status='PENDING'; // 실제 전송할 알람 내용을 조회합니다.";
    }
}