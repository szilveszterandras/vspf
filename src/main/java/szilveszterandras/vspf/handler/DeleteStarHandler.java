package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Star;
import szilveszterandras.vspf.payload.Hash;
import szilveszterandras.vspf.payload.StatusResponse;

public class DeleteStarHandler extends AuthorizedHandler<Hash> {
	public DeleteStarHandler() {
		super(Hash.class);
	}
	@Override
	public SocketHandler run() {
		Photo p = DAOFactory.getInstance().getPhotoDAO().findByHash(payload.getHash());
		Star s = DAOFactory.getInstance().getStarDAO().findByIds(connection.getSession().getUserId(), p.getId());
		DAOFactory.getInstance().getStarDAO().deleteStar(s.getId());
		sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
