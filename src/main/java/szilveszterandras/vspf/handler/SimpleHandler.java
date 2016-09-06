package szilveszterandras.vspf.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.Notifier;
import szilveszterandras.vspf.SocketObject;
import szilveszterandras.vspf.UserConnection;
import szilveszterandras.vspf.payload.StatusResponse;

public abstract class SimpleHandler<T> implements SocketHandler {
	public static final Logger logger = LoggerFactory.getLogger(SimpleHandler.class);
	protected Class<T> payloadType;
	protected T payload;
	protected String topic;
	protected String requestId;
	protected String channel;
	protected UserConnection connection;
	private List<String> subscriptions = new ArrayList<String>();
		
	public SimpleHandler(Class<T> payloadType) {
		this.payloadType = payloadType;
	}
	
	@Override
	public SocketHandler init(String channel, SocketObject data, UserConnection connection) {
		this.channel = channel;
		this.topic = data.getTopic();
		this.requestId = data.getRequestId();
		this.connection = connection;
		try {
			this.payload = (new Gson()).fromJson(data.getPayload(), this.payloadType);
		} catch (JsonParseException e) {
			this.sendEvent((new Gson()).toJson(new StatusResponse("Error parsing payload")));
			throw e;
		}
		return this;
	}
	
	@Override
	public SocketHandler authorize() {
		return this;
	}
	
	@Override
	public SocketHandler run() {
		return this;
	}
	
	@Override
	public void destroy() {
		for (String key : this.subscriptions) {
		    Notifier.getInstance().unsubscribe(key, this.hashCode());
		}
	}

	@Override
	public UUID getConnectionId() {
		return this.connection.getConnectionId();
	}

	@Override
	public String getTopic() {
		return this.topic;
	}
	@Override
	public String getRequestId() {
		return this.requestId;
	}

	protected void sendEvent(String json) {
		SocketObject out = new SocketObject(this.topic, this.requestId, null, json);
		this.connection.getClient().sendEvent(this.channel, out);
		logger.debug(String.format("Sent %s", out.toString()));
	}
	protected <S> List<S> wrap(S u) {
		List<S> l = new ArrayList<S>();
		l.add(u);
		return l;
	}
	protected void subscribe(String s, Notifiable<?> n) {
		Notifier.getInstance().subscribe(s, n, this.hashCode());
	    this.subscriptions.add(s);
	}
}
