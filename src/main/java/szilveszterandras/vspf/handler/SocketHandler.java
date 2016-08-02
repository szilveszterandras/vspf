package szilveszterandras.vspf.handler;

import java.util.UUID;

import szilveszterandras.vspf.SocketObject;
import szilveszterandras.vspf.UserConnection;

public interface SocketHandler {
	public UUID getConnectionId();
	public String getTopic();
	public SocketHandler init(String channel, SocketObject data, UserConnection connection);
	public SocketHandler authorize();
	public SocketHandler run();
	public void destroy();
}