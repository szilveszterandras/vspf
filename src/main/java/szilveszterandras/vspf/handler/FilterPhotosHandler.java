package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.TimestampGson;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.DeleteResponse;
import szilveszterandras.vspf.payload.PhotoFilter;

public class FilterPhotosHandler<T> extends AuthorizedHandler<T> {
	public List<PhotoFilter> activeSet;
	public Notifiable<Photo> persistNotifiable;
	public Notifiable<PhotoFilter> updateNotifiable;
	public Notifiable<Photo> removeNotifiable;

	public FilterPhotosHandler(Class<T> payloadType) {
		super(payloadType);
		
		persistNotifiable = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				// Do nothing
				logger.info("New photo inserted, no action");
			}
		};
		updateNotifiable = new Notifiable<PhotoFilter>() {
			@Override
			public void onEvent(PhotoFilter data) {
				sendEvent((new TimestampGson()).toJson(wrap(data)));
			}
		};
		removeNotifiable = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				PhotoFilter p = findPhoto(activeSet, data.getId());
				if (p != null) {
					activeSet.remove(p);
					p.destroy();
					sendEvent((new Gson()).toJson(new DeleteResponse(p.getId())));
				}
			}
		};
	}

	public SocketHandler run() {
		this.activeSet = this.filterPhotos().map(p -> getPhotoFilter(p)).collect(Collectors.toList());

		sendEvent((new TimestampGson()).toJson(this.activeSet));
		listenToUpdates();
		return this;
	}

	public Stream<Photo> filterPhotos() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void destroy() {
		for (PhotoFilter p : activeSet) {
			p.destroy();
		}
		super.destroy();
	}
	public void listenToUpdates() {
		this.subscribe("photo/persist", persistNotifiable);
		this.subscribe("photo/remove", removeNotifiable);
	}
	protected PhotoFilter getPhotoFilter(Photo p) {
		List<Tag> tags = DAOFactory.getInstance().getTagDAO().filterByPhotoId(p.getId());
		List<Review> reviews = DAOFactory.getInstance().getReviewDAO().filterByPhotoId(p.getId());
		User u = DAOFactory.getInstance().getUserDAO().getUser(p.getUserId());
		return new PhotoFilter(p, u, tags, reviews, updateNotifiable);
	}

	protected PhotoFilter findPhoto(List<PhotoFilter> list, Long id) {
		for (PhotoFilter p : list) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	protected void addToActiveSet(Photo photo) {
		PhotoFilter pf = getPhotoFilter(photo);
		activeSet.add(pf);
		sendEvent((new TimestampGson()).toJson(wrap(pf)));
	}
	protected void removeFromActiveSet(Long id) {
		PhotoFilter p = findPhoto(activeSet, id);
		if (p != null) {
			sendEvent((new Gson()).toJson(new DeleteResponse(p.getId())));
			activeSet.remove(p);
			p.destroy();
		}
	}
}
