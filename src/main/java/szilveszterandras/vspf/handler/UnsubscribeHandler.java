package szilveszterandras.vspf.handler;

import szilveszterandras.vspf.HandlerPool;
import szilveszterandras.vspf.payload.Message;

public class UnsubscribeHandler extends AuthorizedHandler<Message> {

	public UnsubscribeHandler() {
		super(Message.class);
	}
	
	@Override
	public SocketHandler run() {
		HandlerPool.getInstance().destroyHandler(this.connection.getConnectionId(), this.payload.getMessage());
		return this;
	}
}
