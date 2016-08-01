package szilveszterandras.vspf;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Session;
import szilveszterandras.vspf.dal.UserPassword;
import szilveszterandras.vspf.payload.LoginRequest;
import szilveszterandras.vspf.payload.LoginResponse;

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
		UserPassword up = DAOFactory.getInstance().getUserPasswordDAO().findByUsername(this.payload.getUsername());
		if (up != null && up.getPassword().equals(this.payload.getPassword())) {
			Session s = lookupSession(up);
			// If no session was found, create a new one, else update expiry
			if (s == null) {
				s = createSession(up.getId());
			} else {
				updateExpiry(s);
			}
			sendResponse(true, s.getToken());
		} else {
			this.sendResponse(false, null);
		}
	}

	private Session lookupSession(UserPassword up) {
		return DAOFactory.getInstance().getSessionDAO().findByUserId(up.getId());
	}

	private Session createSession(Long userId) {
		String token = UUID.randomUUID().toString();
		Date now = new Date();
		Calendar expires = Calendar.getInstance();
		expires.setTime(now);
		expires.add(Calendar.HOUR, 24);

		Session s = new Session(token, userId, now, expires.getTime());
		DAOFactory.getInstance().getSessionDAO().insertSession(s);
		return s;
	}
	
	private void updateExpiry(Session s) {
		Date now = new Date();
		Calendar expires = Calendar.getInstance();
		expires.setTime(now);
		expires.add(Calendar.HOUR, 24);
		
		s.setLastUpdated(now);
		s.setExpiresAt(expires.getTime());
		
		DAOFactory.getInstance().getSessionDAO().updateSession(s);
	}
	
	private void sendResponse(Boolean isValid, String token) {
		String json = (new Gson())
				.toJson(isValid ? new LoginResponse(token) : new Error("Login failed, invalid username or password"));
		this.client.sendEvent("request", new SocketObject("login", this.requestId, json));
	}
}
