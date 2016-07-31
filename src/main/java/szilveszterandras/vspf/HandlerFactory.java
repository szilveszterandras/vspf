package szilveszterandras.vspf;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.SocketIOClient;

public abstract class HandlerFactory {
	public static final Logger logger = LoggerFactory.getLogger(HandlerFactory.class);
	public static SocketHandler createHandler(SocketObject data, SocketIOClient client) throws IOException {
		// TODO switch by topic
		return new LoginHandler(data, client);
	}
}
