package szilveszterandras.vspf.handler;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Session;
import szilveszterandras.vspf.payload.ValidateRequest;
import szilveszterandras.vspf.payload.ValidateResponse;

public class ValidateHandler extends SimpleHandler<ValidateRequest> {
	public static final Logger logger = LoggerFactory.getLogger(ValidateHandler.class);

	public ValidateHandler() {
		super(ValidateRequest.class);
	}

	@Override
	public ValidateHandler run() {
		Session s = DAOFactory.getInstance().getSessionDAO().findByToken(this.payload.getToken());
		Boolean isValid = s != null && (new Date()).compareTo(s.getExpiresAt()) <= 0;
		if (isValid) {
			// Update expiry of token
			Date now = new Date();
			Calendar expires = Calendar.getInstance();
			expires.setTime(now);
			expires.add(Calendar.SECOND, 10);

			s.setLastUpdated(now);
			s.setExpiresAt(expires.getTime());

			DAOFactory.getInstance().getSessionDAO().updateSession(s);
			this.connection.authenticate(s);
		} else {
			// Delete session
			this.connection.dropSession();
			DAOFactory.getInstance().getSessionDAO().deleteSession(s.getId());
		}
		this.sendEvent((new Gson()).toJson(new ValidateResponse(isValid)));
		return this;
	}
}