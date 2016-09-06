package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.HandlerPool;
import szilveszterandras.vspf.payload.RequestId;
import szilveszterandras.vspf.payload.StatusResponse;

public class UnsubscribeHandler extends AuthorizedHandler<RequestId> {

	public UnsubscribeHandler() {
		super(RequestId.class);
	}
	
	@Override
	public SocketHandler run() {
		HandlerPool.getInstance().destroyHandler(this.connection.getConnectionId(), this.payload.getRequestId());
		sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
