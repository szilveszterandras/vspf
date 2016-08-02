package szilveszterandras.vspf.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.InsertUserRequest;
import szilveszterandras.vspf.payload.StatusResponse;

public class InsertUserHandler extends AuthorizedHandler<InsertUserRequest> {
	public static final Logger logger = LoggerFactory.getLogger(InsertUserHandler.class);

	public InsertUserHandler() {
		super(InsertUserRequest.class);
	}

	@Override
	public SocketHandler run() {
		User u = new User(this.payload.getUsername(), this.payload.getName(), this.payload.getEmail());
		DAOFactory.getInstance().getUserDAO().insertUser(u);
		this.sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
