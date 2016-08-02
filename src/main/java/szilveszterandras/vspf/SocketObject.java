package szilveszterandras.vspf;

public class SocketObject {
	private String topic;
	private String requestId;
	private String payload;
	private String token;
	
	public SocketObject() {};
	
	public SocketObject(String topic, String requestId, String token, String payload) {
		this.topic = topic;
		this.requestId = requestId;
		this.token = token;
		this.payload = payload;
	}
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return String.format("Socket Message: topic: %s | requestId: %s | token: %s | payload: %s", topic, requestId, token, payload);
	}
}
