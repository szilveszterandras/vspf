package szilveszterandras.vspf.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.payload.Id;
import szilveszterandras.vspf.payload.StatusResponse;

public class DeleteReviewHandler extends AuthorizedHandler<Id> {
	public static final Logger logger = LoggerFactory.getLogger(InsertPhotoHandler.class);
	public DeleteReviewHandler() {
		super(Id.class);
	}

	@Override
	public SocketHandler run() {
		try {
			DAOFactory.getInstance().getReviewDAO().deleteReview(payload.getId());
			sendEvent((new Gson()).toJson(new StatusResponse(true)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sendEvent((new Gson()).toJson(new StatusResponse("Unable to save review")));
		}
		return this;
	}
}
