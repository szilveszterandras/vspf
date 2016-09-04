package szilveszterandras.vspf.handler;

import java.util.Comparator;
import java.util.stream.Stream;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.TimestampGson;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.payload.PhotoFilter;
import szilveszterandras.vspf.payload.SearchTerm;

public class SearchPhotosHandler extends FilterPhotosHandler<SearchTerm> {
	public SearchPhotosHandler() {
		super(SearchTerm.class);
		
		this.persistNotifiable = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (data.getTitle().contains(payload.getTerm())) {
					PhotoFilter pf = getPhotoFilter(data);
					activeSet.add(pf);
					activeSet.sort(new Comparator<PhotoFilter>() {
						@Override
						public int compare(PhotoFilter p1, PhotoFilter p2) {
							return p1.getTitle().compareTo(p2.getTitle());
						}
					});
					sendEvent((new TimestampGson()).toJson(activeSet));
				}
			}
		};
		this.updateNotifiable = new Notifiable<PhotoFilter>() {
			@Override
			public void onEvent(PhotoFilter data) {
				activeSet.sort(new Comparator<PhotoFilter>() {
					@Override
					public int compare(PhotoFilter p1, PhotoFilter p2) {
						return p1.getTitle().compareTo(p2.getTitle());
					}
				});
				sendEvent((new TimestampGson()).toJson(activeSet));
			}
		};
		this.removeNotifiable = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				PhotoFilter p = findPhoto(activeSet, data.getId());
				if (p != null) {
					activeSet.remove(p);
					p.destroy();
					
					activeSet.sort(new Comparator<PhotoFilter>() {
						@Override
						public int compare(PhotoFilter p1, PhotoFilter p2) {
							return p1.getTitle().compareTo(p2.getTitle());
						}
					});
					sendEvent((new TimestampGson()).toJson(activeSet));
				}
			}
		};
	}
	
	@Override
	public Stream<Photo> filterPhotos() {
		return DAOFactory.getInstance().getPhotoDAO().getAllPhotos().stream()
			.filter(p -> p.getTitle().contains(payload.getTerm()))
			.sorted((p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
	}

}
