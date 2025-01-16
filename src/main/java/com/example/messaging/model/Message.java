package com.example.messaging.model;

//메시지 데이터를 나타내는 클래스
public class Message {
	 private Long id;          // 메시지 ID
	 private String recipient; // 수신자 정보
	 private String content;   // 메시지 내용
	 private String messageType; // 메시지 유형 (APP, EMAIL, SMS)
	 private String status;    // 처리 상태 (NEW, SENT)
	 private int retryCount; // 재처리 count
	


	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public Message() {}
	
	 public Message(Long id, String recipient, String content, String messageType, String status) {
	     this.id = id;
	     this.recipient = recipient;
	     this.content = content;
	     this.messageType = messageType;
	     this.status = status;
	 }
	
	 public Long getId() {
	     return id;
	 }
	
	 public void setId(Long id) {
	     this.id = id;
	 }
	
	 public String getRecipient() {
	     return recipient;
	 }
	
	 public void setRecipient(String recipient) {
	     this.recipient = recipient;
	 }
	
	 public String getContent() {
	     return content;
	 }
	
	 public void setContent(String content) {
	     this.content = content;
	 }
	
	 public String getMessageType() {
	     return messageType;
	 }
	
	 public void setMessageType(String messageType) {
	     this.messageType = messageType;
	 }
	
	 public String getStatus() {
	     return status;
	 }
	
	 public void setStatus(String status) {
	     this.status = status;
	 }
	
	 @Override
	 public String toString() {
	     return "Message{" +
	             "id=" + id +
	             ", recipient='" + recipient + '\'' +
	             ", content='" + content + '\'' +
	             ", messageType='" + messageType + '\'' +
	             ", status='" + status + '\'' +
	             '}';
	 }
}
