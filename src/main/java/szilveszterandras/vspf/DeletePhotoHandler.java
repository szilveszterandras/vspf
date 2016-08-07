package szilveszterandras.vspf;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.handler.AuthorizedHandler;
import szilveszterandras.vspf.handler.SocketHandler;
import szilveszterandras.vspf.payload.Id;
import szilveszterandras.vspf.payload.StatusResponse;

public class DeletePhotoHandler extends AuthorizedHandler<Id> {

	public DeletePhotoHandler() {
		super(Id.class);
	}
	
	@Override
	public SocketHandler run() {
		try {
			DAOFactory.getInstance().getPhotoDAO().deletePhoto(payload.getId());
		} catch (Exception e) {
			sendEvent((new Gson()).toJson(new StatusResponse("Can't delete photo")));
		}
		sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}

}
