package szilveszterandras.vspf.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.TimestampGson;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.Hash;
import szilveszterandras.vspf.payload.PhotoFilter;

public class StreamSinglePhotoHandler extends SimpleHandler<Hash> {
	public static final Logger logger = LoggerFactory.getLogger(StreamSinglePhotoHandler.class);
	private Long photoId;
	private User user;

	public StreamSinglePhotoHandler() {
		super(Hash.class);
		this.initSubscriptions();
	}
	@Override
	public SocketHandler run() {
		Photo p = DAOFactory.getInstance().getPhotoDAO().findByHash(payload.getHash());
		this.photoId = p.getId();
		this.user= DAOFactory.getInstance().getUserDAO().getUser(p.getUserId());
		
		this.sendEvent((new TimestampGson()).toJson(getPhotoFilter(p)));
		return this;
	}
	private void initSubscriptions() {
		Notifiable<Photo> photoUpdateHandler = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (data.getId() == photoId) {
					sendEvent((new TimestampGson()).toJson(getPhotoFilter(data)));
				}
			}
		};
		this.subscribe("photo/persist", photoUpdateHandler);
		this.subscribe("photo/update", photoUpdateHandler);
		this.subscribe("tag/persist", new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				if (data.getPhotoId() == photoId) {
					Photo p = DAOFactory.getInstance().getPhotoDAO().getPhoto(photoId);
					sendEvent((new TimestampGson()).toJson(getPhotoFilter(p)));
				}
			}
		});
	}
	private PhotoFilter getPhotoFilter(Photo p) {
		List<Tag> tags = DAOFactory.getInstance().getTagDAO().filterByPhotoId(p.getId());
		List<Review> reviews = DAOFactory.getInstance().getReviewDAO().filterByPhotoId(p.getId());
		return new PhotoFilter(p, this.user, tags, reviews);
	}
}
