package szilveszterandras.vspf.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Session;
import szilveszterandras.vspf.dal.UserPassword;
import szilveszterandras.vspf.payload.LoginRequest;
import szilveszterandras.vspf.payload.LoginResponse;
import szilveszterandras.vspf.payload.StatusResponse;

public class LoginHandler extends SimpleHandler<LoginRequest> {
	public static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	public LoginHandler() {
		super(LoginRequest.class);
	}

	@Override
	public LoginHandler run() {
		UserPassword up = DAOFactory.getInstance().getUserPasswordDAO().findByUsername(this.payload.getUsername());
		if (up != null && up.getPassword().equals(this.payload.getPassword())) {
			Session s = createSession(up.getId());
			// Set current connection to authenticated
			this.connection.authenticate(s);
			sendResponse(true, s.getToken());
		} else {
			this.sendResponse(false, null);
		}
		return this;
	}

	// TODO Consider re-using session tokens
//	private Session lookupSession(UserPassword up) {
//		return DAOFactory.getInstance().getSessionDAO().findByUserId(up.getId());
//	}
//
	private Session createSession(Long userId) {
		String token = UUID.randomUUID().toString();
		Date now = new Date();
		Calendar expires = Calendar.getInstance();
		expires.setTime(now);
		expires.add(Calendar.SECOND, 10);

		Session s = new Session(token, userId, now, expires.getTime());
		DAOFactory.getInstance().getSessionDAO().insertSession(s);
		return s;
	}

//	private void updateExpiry(Session s) {
//		Date now = new Date();
//		Calendar expires = Calendar.getInstance();
//		expires.setTime(now);
//		expires.add(Calendar.SECOND, 10);
//
//		s.setLastUpdated(now);
//		s.setExpiresAt(expires.getTime());
//
//		DAOFactory.getInstance().getSessionDAO().updateSession(s);
//	}

	private void sendResponse(Boolean isValid, String token) {
		this.sendEvent((new Gson())
				.toJson(isValid ? new LoginResponse(token) : new StatusResponse("Login failed, invalid username or password")));
	}
}
