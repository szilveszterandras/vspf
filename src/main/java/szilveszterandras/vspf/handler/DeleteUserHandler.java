package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.payload.StatusResponse;

public class DeleteUserHandler extends AuthorizedHandler<Object> {
	public DeleteUserHandler() {
		super(Object.class);
	}

	@Override
	public SocketHandler run() {
		DAOFactory.getInstance().getUserDAO().deleteUser(this.connection.getSession().getUserId());
		this.sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
