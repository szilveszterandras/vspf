package szilveszterandras.vspf.handler;

import java.util.Calendar;
import java.util.stream.Stream;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;

public class StreamPhotosNewHandler extends FilterPhotosHandler<Object> {
	public StreamPhotosNewHandler() {
		super(Object.class);
		
		persistNotifiable = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				addToActiveSet(data);
			}
		};
	}
	@Override
	public Stream<Photo> filterPhotos() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		return DAOFactory.getInstance().getPhotoDAO().getAllPhotos().stream()
			.filter(p -> p.getUploadedAt().after(cal.getTime()))
			.sorted((p1, p2) -> p2.getUploadedAt().compareTo(p1.getUploadedAt()));
	}

}
