package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.StatusResponse;
import szilveszterandras.vspf.payload.UpdateUserRequest;

public class UpdateUserHandler extends AuthorizedHandler<UpdateUserRequest> {
	public UpdateUserHandler() {
		super(UpdateUserRequest.class);
	}

	@Override
	public SocketHandler run() {
		User currentUser = DAOFactory.getInstance().getUserDAO().getUser(this.connection.getSession().getUserId());
		currentUser.setUsername(this.payload.getUsername());
		currentUser.setName(this.payload.getName());
		currentUser.setEmail(this.payload.getEmail());
		
		DAOFactory.getInstance().getUserDAO().updateUser(currentUser);
		this.sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
