package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.payload.PhotoFilter;
import szilveszterandras.vspf.payload.SearchTerm;

public class SearchPhotosHandler extends AuthorizedHandler<SearchTerm> {
	public SearchPhotosHandler() {
		super(SearchTerm.class);
		
		Notifiable<Photo> n = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				returnFitting();
			}
		};
		this.subscribe("photo/persist", n);
		this.subscribe("photo/update", n);
		this.subscribe("photo/remove", n);
	}
	
	@Override
	public SocketHandler run() {
		returnFitting();
		return this;
	}
	private void returnFitting() {
		List<PhotoFilter> photos = DAOFactory.getInstance().getPhotoDAO().getAllPhotos().stream()
				.filter(p -> p.getTitle().contains(payload.getTerm()))
				.sorted((p1, p2) -> p1.getTitle().compareTo(p2.getTitle()))
				.map(p -> new PhotoFilter(p))
				.collect(Collectors.toList());

		sendEvent((new Gson()).toJson(photos));
	}
}
