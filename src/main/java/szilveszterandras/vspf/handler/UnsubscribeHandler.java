package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.HandlerPool;
import szilveszterandras.vspf.payload.Message;
import szilveszterandras.vspf.payload.StatusResponse;

public class UnsubscribeHandler extends AuthorizedHandler<Message> {

	public UnsubscribeHandler() {
		super(Message.class);
	}
	
	@Override
	public SocketHandler run() {
		HandlerPool.getInstance().destroyHandler(this.connection.getConnectionId(), this.payload.getMessage());
		sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
