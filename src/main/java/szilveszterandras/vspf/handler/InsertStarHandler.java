package szilveszterandras.vspf.handler;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Star;
import szilveszterandras.vspf.payload.Hash;
import szilveszterandras.vspf.payload.StatusResponse;

public class InsertStarHandler extends AuthorizedHandler<Hash> {
	public InsertStarHandler() {
		super(Hash.class);
	}
	@Override
	public SocketHandler run() {
		Photo p = DAOFactory.getInstance().getPhotoDAO().findByHash(payload.getHash());
		Star s = new Star(connection.getSession().getUserId(), p.getId());
		DAOFactory.getInstance().getStarDAO().insertStar(s);
		sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
}
