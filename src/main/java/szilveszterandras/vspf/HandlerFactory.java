package szilveszterandras.vspf;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.handler.DeleteUserHandler;
import szilveszterandras.vspf.handler.InsertPhotoHandler;
import szilveszterandras.vspf.handler.InsertUserHandler;
import szilveszterandras.vspf.handler.LoginHandler;
import szilveszterandras.vspf.handler.LogoutHandler;
import szilveszterandras.vspf.handler.SocketHandler;
import szilveszterandras.vspf.handler.StreamPhotosHandler;
import szilveszterandras.vspf.handler.StreamTagsHandler;
import szilveszterandras.vspf.handler.StreamUsersHandler;
import szilveszterandras.vspf.handler.UnsubscribeHandler;
import szilveszterandras.vspf.handler.UpdateUserHandler;
import szilveszterandras.vspf.handler.ValidateHandler;

public abstract class HandlerFactory {
	public static final Logger logger = LoggerFactory.getLogger(HandlerFactory.class);
	public static SocketHandler createHandler(String channel, SocketObject data, UserConnection connection) throws IOException {
		SocketHandler h = null;
		switch (data.getTopic()) {
		case "login":
			h = new LoginHandler();
			break;
		case "validate":
			h = new ValidateHandler();
			break;
		case "logout":
			h = new LogoutHandler();
			break;
		case "user/new":
			h = new InsertUserHandler();
			break;
		case "user/update":
			h = new UpdateUserHandler();
			break;
		case "user/delete":
			h = new DeleteUserHandler();
			break;
		case "users/stream":
			h = new StreamUsersHandler();
			break;
		case "photo/new":
			h = new InsertPhotoHandler();
			break;
		case "photos/stream":
			h = new StreamPhotosHandler();
			break;
		case "tags/stream":
			h = new StreamTagsHandler();
			break;
		case "unsubscribe":
			h = new UnsubscribeHandler();
			break;
		default:
			String msg = String.format("Unable to map request handler for topic %s", data.getTopic());
			logger.warn(msg);
			throw new IOException(msg);
		}
		h.init(channel, data, connection);
		if (channel == "stream") {
			HandlerPool.getInstance().addHandler(h);
		}
		return h;
	}
}
