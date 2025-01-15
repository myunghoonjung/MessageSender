# 통합 메시징 시스템

- **멀티 채널 지원**: SMS, 이메일, 앱 푸시를 통해 메시지 전송.
- **Worker 기반 아키텍처**: 각 전송 매체에 대해 전용 Worker 클래스가 메시지 처리를 담당.
- **지속적인 처리**: 쓰레드를 활용한 메시지 큐 조회 및 자동 처리.
- **확장성**: Worker 클래스를 구현하여 새로운 채널 추가 가능.
