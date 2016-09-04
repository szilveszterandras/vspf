package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.payload.TagRequest;

public class StreamByTagHandler extends FilterPhotosHandler<TagRequest> {
	public StreamByTagHandler() {
		super(TagRequest.class);
		// Handle photo updates and propagate on open handler
		this.subscribe("photo/persist", new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (getTagsForPhoto(data).contains(payload.getTag())) {
					addToActiveSet(data);
				}
			}
		});
		// Handle tag updates as well
		this.subscribe("tag/persist", new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				if (data.getName().equals(payload.getTag())) {
					addToActiveSet(DAOFactory.getInstance().getPhotoDAO().getPhoto(data.getPhotoId()));
				}
			}
		});
		this.subscribe("tag/remove", new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				if (data.getName().equals(payload.getTag())) {
					removeFromActiveSet(data.getPhotoId());
				}
			}
		});
	}
	
	@Override
	public Stream<Photo> filterPhotos() {
		return DAOFactory.getInstance().getPhotoDAO().filterByTag(payload.getTag()).stream();
	}

	private List<String> getTagsForPhoto(Photo p) {
		return DAOFactory.getInstance().getTagDAO().filterByPhotoId(p.getId()).stream().map(t -> t.getName())
				.collect(Collectors.toList());
	}
}
