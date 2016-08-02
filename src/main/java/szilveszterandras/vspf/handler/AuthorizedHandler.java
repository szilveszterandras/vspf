package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.SocketObject;
import szilveszterandras.vspf.UserConnection;
import szilveszterandras.vspf.payload.StatusResponse;

public class AuthorizedHandler<T> extends SimpleHandler<T> {
	protected String token;
	
	public AuthorizedHandler(Class<T> payloadType) {
		super(payloadType);
	}

	@Override
	public SocketHandler init(String channel, SocketObject data, UserConnection connection) {
		super.init(channel, data, connection);
		this.token = data.getToken();
		return this;
	}

	@Override
	public SocketHandler authorize() throws SecurityException {
		if (!this.connection.getIsAuthenticated()) {
			this.sendEvent((new Gson()).toJson(new StatusResponse("User not authenticated")));
			throw new SecurityException("User not authenticated");
		}
		return this;
	}

	protected void sendEvent(String json) {
		SocketObject out = new SocketObject(this.topic, this.requestId, this.token, json);
		this.connection.getClient().sendEvent(this.channel, out);
		logger.debug(String.format("Sent %s", out.toString()));
	}
}
