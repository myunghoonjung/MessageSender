package com.example.messaging;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        MessagingManager manager = new MessagingManager();

        // 메시지 전송 요청 테스트
        System.out.println("[Client] SMS 메시지 전송 요청");
        manager.getSmsWorker().insertMessage("1234567890", "This is a test SMS message.");

        System.out.println("[Client] Email 메시지 전송 요청");
        manager.getEmailWorker().insertMessage("user@example.com", "This is a test Email message.");

        System.out.println("[Client] App Push 메시지 전송 요청");
        manager.getAppPushWorker().insertMessage("device_token", "This is a test App Push message.");

        // Sender를 통해 지속적으로 메시지를 감지하고 전송
        System.out.println("[System] 통합 Sender가 loadQuery를 지속적으로 조회하여 메시지 전송을 처리합니다.");
        manager.startContinuousProcessing();
    }
}
