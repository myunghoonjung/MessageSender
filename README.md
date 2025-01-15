# 통합 메시징 시스템

- **멀티 채널 지원**: SMS, 이메일, 앱 푸시를 통해 메시지 전송.
- **Worker 기반 아키텍처**: 각 전송 매체에 대해 전용 Worker 클래스가 메시지 처리를 담당.
- **지속적인 처리**: 쓰레드를 활용한 메시지 큐 조회 및 자동 처리.
- **확장성**: Worker 클래스를 구현하여 새로운 채널 추가 가능.

## 작동 방식

1. **클라이언트 요청**: 메시지 전송 요청이 들어오면 데이터베이스에 저장.
2. **지속적인 쿼리**: 시스템이 지속적으로 데이터베이스를 조회하여 준비된 메시지를 서치.
3. **메시지 전송**: 각 채널의 Worker가 해당 메시지를 처리하여 전송.

### 사용법

1. **Messaging Manager 초기화**:
   ```java
   MessagingManager manager = new MessagingManager();
   ```

2. **메시지 삽입**:
   ```java
   manager.getSmsWorker().insertMessage("1234567890", "SMS 메시지입니다.");
   ```

3. **지속적인 처리 시작**:
   ```java
   manager.startContinuousProcessing();
   ```
