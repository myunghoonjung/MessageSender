package com.example.messaging.worker;

abstract class Worker {
    protected abstract String getLoadQuery();

    /**
     * 메시지를 DB에 삽입 (클라이언트 요청 시 호출)
     */
    public void insertMessage(String recipient, String message) {
        System.out.println("[Worker] 메시지 데이터 삽입: Recipient=" + recipient + ", Message=" + message);
    }

    /**
     * loadQuery를 통해 메시지를 지속적으로 조회하고 처리
     */
    public void process() {
        System.out.println("[Worker] loadQuery 실행: " + getLoadQuery());
        System.out.println("[Worker] 메시지 전송 로직 실행");
    }
}