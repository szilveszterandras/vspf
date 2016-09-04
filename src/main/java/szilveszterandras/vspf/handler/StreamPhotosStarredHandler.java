package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Stream;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.Notifier;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Star;
import szilveszterandras.vspf.payload.Username;

public class StreamPhotosStarredHandler extends FilterPhotosHandler<Username> {
	private Long userId;

	public StreamPhotosStarredHandler() {
		super(Username.class);
	}
	@Override
	public SocketHandler run() {
		this.userId = DAOFactory.getInstance().getUserDAO().findByUsername(payload.getUsername()).getId();
		return super.run();
	}
	@Override
	public Stream<Photo> filterPhotos() {
		List<Star> stars = DAOFactory.getInstance().getStarDAO().getByUserId(userId);
		return stars.stream().map(s -> DAOFactory.getInstance().getPhotoDAO().getPhoto(s.getPhotoId()));
	}
	@Override
	public void listenToUpdates() {
		super.listenToUpdates();
		this.subscribe("star/persist", new Notifiable<Star>() {
			@Override
			public void onEvent(Star data) {
				if (data.getUserId() == userId) {
					addToActiveSet(DAOFactory.getInstance().getPhotoDAO().getPhoto(data.getPhotoId()));
				}
			}
		});
		
		this.subscribe("star/remove", new Notifiable<Star>() {
			@Override
			public void onEvent(Star data) {
				if (data.getUserId() == userId) {
					removeFromActiveSet(data.getPhotoId());
				}
			}
		});
	}
	@Override
	public void destroy() {
		Notifier.getInstance().unsubscribe("star/persist", this.hashCode());
		Notifier.getInstance().unsubscribe("star/remove", this.hashCode());
		super.destroy();
	}
}
