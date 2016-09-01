package szilveszterandras.vspf.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.payload.ReviewFilter;
import szilveszterandras.vspf.payload.StatusResponse;

public class UpdateReviewHandler extends AuthorizedHandler<ReviewFilter> {
	public static final Logger logger = LoggerFactory.getLogger(InsertPhotoHandler.class);
	public UpdateReviewHandler() {
		super(ReviewFilter.class);
	}

	@Override
	public SocketHandler run() {
		Review r = DAOFactory.getInstance().getReviewDAO().getReview(payload.getId());
		r.setTitle(payload.getTitle());
		r.setDescription(payload.getDescription());
		r.setRating(payload.getRating());
		try {
			DAOFactory.getInstance().getReviewDAO().updateReview(r);
			sendEvent((new Gson()).toJson(new StatusResponse(true)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sendEvent((new Gson()).toJson(new StatusResponse("Unable to save review")));
		}
		return this;
	}
}
