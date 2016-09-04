package szilveszterandras.vspf.handler;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.payload.Username;

public class StreamPhotosHandler extends FilterPhotosHandler<Username> {
	public static final Logger logger = LoggerFactory.getLogger(StreamPhotosHandler.class);
	private Long userId;

	public StreamPhotosHandler() {
		super(Username.class);
		this.persistNotifiable = new Notifiable<Photo>() {
			@Override
			public void onEvent(Photo data) {
				if (data.getUserId() == userId) {
					addToActiveSet(data);
				}
			}
		};
	}
	@Override
	public SocketHandler run() {
		this.userId = DAOFactory.getInstance().getUserDAO().findByUsername(payload.getUsername()).getId();
		return super.run();
	}
	@Override
	public Stream<Photo> filterPhotos() {
		return DAOFactory.getInstance().getPhotoDAO().filterByUserId(this.userId).stream();
	}
}
