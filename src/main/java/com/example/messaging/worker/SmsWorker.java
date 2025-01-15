package com.example.messaging.worker;

/**
 * SmsWorker - SMS 작업을 처리하는 클래스
 */
public class SmsWorker extends Worker {
    @Override
    protected String getLoadQuery() {
        return "SELECT * FROM sms_messages WHERE status='PENDING'; // 실제 전송할 알람 내용을 조회합니다.";
    }
}