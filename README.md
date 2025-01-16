# 통합 메시징 서비스 (Unified Messaging Service)

## 1. 프로젝트 개요
이 프로젝트는 통합 메시징 시스템을 설계하여, SMS, Email, App Push 등 다양한 메시징 채널을 통해 메시지를 전송하는 서비스입니다.  
메시지 요청은 메인 함수에서 직접 생성되며, 워커(worker)가 메시지 요청 및 전송 실패 메시지의 재처리를 관리합니다.  
전송 실패 내역은 최대 재시도 횟수를 기반으로 재처리되며, 재처리 한계를 초과한 경우 실패 내역으로 기록됩니다.

---

## 2. 주요 기능
1. **메시지 요청 생성**
   - 메인 함수에서 SMS, Email, App Push 메시지 요청을 생성.

2. **통합 메시지 처리**
   - 메시지 요청을 데이터베이스(임시 저장소)에 저장.
   - Worker가 주기적으로 메시지를 조회하여 적합한 Sender(SMS, Email, App Push)를 호출.

3. **전송 실패 내역 재처리**
   - 전송 실패(`FAILED`) 상태의 메시지는 설정된 재시도 횟수(`retry_count`)가 남아 있는 경우 재처리됩니다.
   - 메시지가 조회될 때마다 재시도 횟수는 1씩 감소하며, 재시도 횟수가 0이 되면 실패 내역으로 기록됩니다.

4. **확장 가능한 구조**
   - 새로운 메시징 채널 추가 시 코드 수정 없이 확장 가능하도록 설계.

---

## 3. 프로젝트 구조

### Java 소스
- `Main.java`: 애플리케이션 실행 엔트리포인트 및 메시지 처리 루프.
- `MessageWorker.java`: 메시지를 처리하는 워커 클래스.
- `MessageRepository.java`: 메시지 요청 및 상태를 관리하는 저장소.
- `SmsSender.java`: SMS 메시지 전송 구현체.
- `EmailSender.java`: Email 메시지 전송 구현체.
- `AppPushSender.java`: App Push 메시지 전송 구현체.
- `Message.java`: 메시지 요청 객체 모델.

---

## 4. 메시지 처리 흐름

1. **메시지 요청 생성**
   - 메인 함수에서 `MessageRepository`를 통해 SMS, Email, App Push 메시지 요청을 생성.

2. **메시지 처리**
   - `MessageWorker`는 주기적으로 저장소에서 `NEW` 상태의 메시지를 조회.
   - 메시지의 `channel_type`에 따라 적합한 Sender를 호출.

3. **전송 실패 내역 재처리**
   - `MessageRepository.failedMessagesRetryCount()`로 `FAILED` 상태이며 `retry_count > 0`인 메시지를 조회.
   - 조회된 메시지는 재처리되며, 실패 시 `retry_count`를 1 감소.
   - `retry_count`가 0이 되면 `FAILED_FINAL` 상태로 기록.

4. **상태 업데이트**
   - 메시지 전송 성공 시 상태를 `SENT`로 업데이트.
   - 재시도 실패 시 상태를 `FAILED`로 유지하거나, 재시도 한계 초과 시 `FAILED_FINAL`로 기록.

5. **주기적 처리**
   - 메시지 처리는 무한 루프 내에서 5초 간격으로 실행.

---

## 5. 테이블 설계

### 주요 테이블

#### 1. `message_request` (메시지 요청 테이블)
- **역할**: 클라이언트로부터 수신한 메시지 요청을 저장.
- **컬럼**:
  - `id_message`: 메시지 고유 ID.
  - `recipient`: 수신자 정보 (예: 전화번호, 이메일 주소).
  - `message_content`: 메시지 내용.
  - `channel_type`: 전송 채널 (예: SMS, EMAIL, APP).
  - `status`: 메시지 상태 (예: NEW, SENT, FAILED, FAILED_FINAL).
  - `retry_count`: 남은 재시도 횟수.
  - `created_at`: 요청 생성 시간.

#### 2. `message_log` (메시지 로그 테이블)
- **역할**: 메시지 전송 결과를 기록.
- **컬럼**:
  - `id_log`: 메시지 로그 고유 ID.
  - `id_message`: 메시지 요청 ID (참조: `message_request`).
  - `channel_type`: 전송 채널.
  - `status`: 메시지 상태 (예: SENT, FAILED).
  - `error_message`: 전송 실패 시 에러 메시지.
  - `sent_at`: 전송 시간.

#### 3. `message_failure` (메시지 실패 테이블)
- **역할**: 재시도 한계를 초과한 메시지 실패 내역을 기록.
- **컬럼**:
  - `id_failure`: 실패 기록 고유 ID.
  - `id_message`: 메시지 요청 ID (참조: `message_request`).
  - `channel_type`: 전송 채널.
  - `last_error_message`: 마지막 실패 에러 메시지.
  - `final_attempt_at`: 최종 시도 시간.

---

## 6. 코드 주요 내용

### 재처리 로직 가이드
1. `message_request` 테이블에서 `status = 'FAILED'` AND `retry_count > 0` 조건으로 메시지 조회:
   - `MessageRepository.failedMessagesRetryCount()` 사용.
2. 조회된 메시지의 상태를 `PROCESSING`으로 업데이트하여 중복 처리를 방지.
3. 메시지를 처리하고 성공 시 `SENT`로 업데이트.
4. 처리 실패 시 `retry_count`를 1 감소:
   - `retry_count == 0`이 되면 `FAILED_FINAL` 상태로 업데이트.
5. 최종 실패 내역은 `message_failure` 테이블에 기록.

---

## 7. 기술 스택
- **프레임워크**: 순수 Java  
- **빌드 도구**: Maven  
- **언어**: Java  
