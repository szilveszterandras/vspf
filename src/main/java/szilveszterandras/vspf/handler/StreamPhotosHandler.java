package szilveszterandras.vspf.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.TimestampGson;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.payload.DeleteResponse;
import szilveszterandras.vspf.payload.PhotoFilter;
import szilveszterandras.vspf.payload.Username;

public class StreamPhotosHandler extends AuthorizedHandler<Username> {
	public static final Logger logger = LoggerFactory.getLogger(StreamPhotosHandler.class);
	private Long userId;

	public StreamPhotosHandler() {
		super(Username.class);
		initSubscriptions();
	}

	@Override
	public SocketHandler run() {
		// Send snapshot
		this.userId = DAOFactory.getInstance().getUserDAO().findByUsername(payload.getUsername()).getId();
		List<Photo> photos = DAOFactory.getInstance().getPhotoDAO().filterByUserId(this.userId);
		List<PhotoFilter> f = new ArrayList<PhotoFilter>();
		for (Photo p : photos) {
			f.add(getPhotoFilter(p));
		}
		this.sendEvent((new TimestampGson()).toJson(f));
		return this;
	}
	private void initSubscriptions() {
		Notifiable<Photo> photoUpdateHandler = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (data.getUserId() == userId) {
					sendEvent((new TimestampGson()).toJson(wrap(getPhotoFilter(data))));
				}
			}
		};
		this.subscribe("photo/persist", photoUpdateHandler);
		this.subscribe("photo/update", photoUpdateHandler);
		this.subscribe("photo/remove", new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				sendEvent((new Gson()).toJson(new DeleteResponse(data.getId())));
			}
		});
		
		Notifiable<Tag> onTagUpdate = new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				Photo p = DAOFactory.getInstance().getPhotoDAO().getPhoto(data.getPhotoId());
				if (p.getUserId() == userId) {
					sendEvent((new TimestampGson()).toJson(wrap(getPhotoFilter(p))));
				}
			}
		};
		this.subscribe("tag/persist", onTagUpdate);
		this.subscribe("tag/remove", onTagUpdate);
	}
	private PhotoFilter getPhotoFilter(Photo p) {
		List<Tag> tags = DAOFactory.getInstance().getTagDAO().filterByPhotoId(p.getId());
		return new PhotoFilter(p, payload.getUsername(), tags);
	}
}
