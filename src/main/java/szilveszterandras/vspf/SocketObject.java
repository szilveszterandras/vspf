package szilveszterandras.vspf;

public class SocketObject {
	private String topic;
	private String requestId;
	private String payload;
	
	public SocketObject() {
		
	}
	
	public SocketObject(String topic, String requestId, String payload) {
		this.topic = topic;
		this.requestId = requestId;
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
}
