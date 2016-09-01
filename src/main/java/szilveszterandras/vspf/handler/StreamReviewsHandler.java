package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.TimestampGson;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.DeleteResponse;
import szilveszterandras.vspf.payload.Hash;
import szilveszterandras.vspf.payload.ReviewFilter;

public class StreamReviewsHandler extends AuthorizedHandler<Hash> {
	public static final Logger logger = LoggerFactory.getLogger(StreamPhotosHandler.class);
	private Photo photo;

	public StreamReviewsHandler() {
		super(Hash.class);
		initSubscriptions();
	}

	@Override
	public SocketHandler run() {
		// Send snapshot
		photo = DAOFactory.getInstance().getPhotoDAO().findByHash(payload.getHash());
		List<ReviewFilter> reviews = DAOFactory.getInstance().getReviewDAO().filterByPhotoId(photo.getId()).stream()
			.map(r -> getReviewFilter(r))
			.collect(Collectors.toList());
		
		this.sendEvent((new TimestampGson()).toJson(reviews));
		return this;
	}
	private void initSubscriptions() {
		Notifiable<Review> reviewUpdateHandler = new Notifiable<Review>() {
			@Override
			public void onEvent(Review data) {
				if (data.getPhotoId() == photo.getId()) {
					sendEvent((new TimestampGson()).toJson(wrap(getReviewFilter(data))));
				}
			}
		};
		this.subscribe("review/persist", reviewUpdateHandler);
		this.subscribe("review/update", reviewUpdateHandler);
		this.subscribe("review/remove", new Notifiable<Review>() {
			@Override
			public void onEvent(Review data) {
				sendEvent((new Gson()).toJson(new DeleteResponse(data.getId())));
			}
		});
	}
	private ReviewFilter getReviewFilter(Review r) {
		User u = DAOFactory.getInstance().getUserDAO().getUser(r.getUserId());
		return new ReviewFilter(r, photo, u);
	}
}
