package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.TimestampGson;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.DeleteResponse;
import szilveszterandras.vspf.payload.PhotoFilter;
import szilveszterandras.vspf.payload.TagRequest;

public class StreamByTagHandler extends AuthorizedHandler<TagRequest> {
	public StreamByTagHandler() {
		super(TagRequest.class);
		// Handle photo updates and propagate on open handler
		Notifiable<Photo> onPhotoUpdate = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (getTagsForPhoto(data).contains(payload.getTag())) {
					sendEvent((new TimestampGson()).toJson(wrap(getPhotoFilter(data))));
				}
			}
		};
		this.subscribe("photo/persist", onPhotoUpdate);
		this.subscribe("photo/update", onPhotoUpdate);
		this.subscribe("photo/remove", new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (getTagsForPhoto(data).contains(payload.getTag())) {
					sendEvent((new Gson()).toJson(new DeleteResponse(data.getId())));
				}
			}
		});

		// Handle tag updates as well
		this.subscribe("tag/persist", new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				if (data.getName().equals(payload.getTag())) {
					sendEvent((new TimestampGson()).toJson(
							wrap(getPhotoFilter(DAOFactory.getInstance().getPhotoDAO().getPhoto(data.getPhotoId())))));
				}
			}
		});
		this.subscribe("tag/remove", new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				if (data.getName().equals(payload.getTag())) {
					sendEvent((new TimestampGson()).toJson(
							new DeleteResponse(data.getPhotoId())));
				}
			}
		});
	}

	@Override
	public SocketHandler run() {
		List<PhotoFilter> photos = DAOFactory.getInstance().getPhotoDAO().filterByTag(payload.getTag()).stream()
				.map(p -> getPhotoFilter(p)).collect(Collectors.toList());
		sendEvent((new TimestampGson()).toJson(photos));
		return this;
	}

	private PhotoFilter getPhotoFilter(Photo p) {
		User u = DAOFactory.getInstance().getUserDAO().getUser(p.getUserId());
		List<Tag> tags = DAOFactory.getInstance().getTagDAO().filterByPhotoId(p.getId());
		return new PhotoFilter(p, u.getUsername(), tags);
	}

	private List<String> getTagsForPhoto(Photo p) {
		return DAOFactory.getInstance().getTagDAO().filterByPhotoId(p.getId()).stream().map(t -> t.getName())
				.collect(Collectors.toList());
	}
}
