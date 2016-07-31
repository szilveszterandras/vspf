package szilveszterandras.vspf;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.UserPassword;

public class LoginHandler implements SocketHandler {
	public static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	private String requestId;
	private LoginRequest payload;
	private SocketIOClient client;

	public LoginHandler(SocketObject data, SocketIOClient client) {
		this.requestId = data.getRequestId();
		this.payload = (new Gson()).fromJson(data.getPayload(), LoginRequest.class);
		this.client = client;
	}
	@Override
	public void run() {
		try {
			UserPassword up = DAOFactory.getInstance().getUserPasswordDAO().findUserPassword(this.payload.getUsername());
			if (!up.getPassword().equals(this.payload.getPassword())) {
				throw new NoResultException();
			}
			client.sendEvent("request", new SocketObject("login", this.requestId, 
				"Login successful! TODO Send sessionToken"));
		} catch (NoResultException e) {
			client.sendEvent("request", new SocketObject("login", this.requestId, "Login failed!"));
		}
	}
}
