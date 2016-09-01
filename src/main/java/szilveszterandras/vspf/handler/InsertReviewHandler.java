package szilveszterandras.vspf.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.payload.ReviewFilter;
import szilveszterandras.vspf.payload.StatusResponse;

public class InsertReviewHandler extends AuthorizedHandler<ReviewFilter> {
	public static final Logger logger = LoggerFactory.getLogger(InsertPhotoHandler.class);
	public InsertReviewHandler() {
		super(ReviewFilter.class);
	}

	@Override
	public SocketHandler run() {
		Photo p = DAOFactory.getInstance().getPhotoDAO().findByHash(payload.getHash());
		Review r = new Review(payload, p.getId(), connection.getSession().getUserId());
		
		try {
			DAOFactory.getInstance().getReviewDAO().insertReview(r);
			sendEvent((new Gson()).toJson(new StatusResponse(true)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sendEvent((new Gson()).toJson(new StatusResponse("Unable to save review")));
		}
		return this;
	}
}
