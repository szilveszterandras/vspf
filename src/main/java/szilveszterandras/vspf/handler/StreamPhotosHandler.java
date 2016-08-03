package szilveszterandras.vspf.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.Notifier;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.payload.PhotoFilter;

public class StreamPhotosHandler extends AuthorizedHandler<Object> {
	public static final Logger logger = LoggerFactory.getLogger(StreamPhotosHandler.class);

	public StreamPhotosHandler() {
		super(Object.class);
		
		Notifier.getInstance().subscribe("photo/persist", new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				sendEvent((new Gson()).toJson(wrap(new PhotoFilter(data))));
			}
		}, this.hashCode());
		Notifier.getInstance().subscribe("photo/update", new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				sendEvent((new Gson()).toJson(wrap(new PhotoFilter(data))));
			}
		}, this.hashCode());
		Notifier.getInstance().subscribe("photo/remove", new Notifiable<Long>() {
			@Override
			public void onEvent(Long id) {
				sendEvent((new Gson()).toJson(wrap(id)));
			}
		}, this.hashCode());
	}

	@Override
	public SocketHandler run() {
		// Send snapshot
		List<Photo> photos = DAOFactory.getInstance().getPhotoDAO().getAllPhotos();
		List<PhotoFilter> f = new ArrayList<PhotoFilter>();
		for (Photo p : photos) {
			f.add(new PhotoFilter(p));
		}
		this.sendEvent((new Gson()).toJson(f));
		return this;
	}

	@Override
	public void destroy() {
		Notifier.getInstance().unsubscribe("photo/persist", this.hashCode());
		Notifier.getInstance().unsubscribe("photo/update", this.hashCode());
		Notifier.getInstance().unsubscribe("photo/remove", this.hashCode());
	}
}
