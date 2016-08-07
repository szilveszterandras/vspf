package szilveszterandras.vspf.handler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Star;
import szilveszterandras.vspf.payload.StarResponse;
import szilveszterandras.vspf.payload.Username;

public class StreamStarsHandler extends AuthorizedHandler<Username> {
	private Long userId;

	public StreamStarsHandler() {
		super(Username.class);

		this.subscribe("star/persist", new Notifiable<Star>() {
			@Override
			public void onEvent(Star data) {
				sendEvent((new Gson()).toJson(new StarResponse("add", wrap(getStarHash(data)))));
			}
		});
		this.subscribe("star/remove", new Notifiable<Star>() {
			@Override
			public void onEvent(Star data) {
				// TODO Auto-generated method stub
				sendEvent((new Gson()).toJson(new StarResponse("remove", wrap(getStarHash(data)))));
			}
		});
	}

	@Override
	public SocketHandler run() {
		this.userId = DAOFactory.getInstance().getUserDAO().findByUsername(payload.getUsername()).getId();
		List<String> stars = new ArrayList<String>();
		for (Star s : DAOFactory.getInstance().getStarDAO().getByUserId(userId)) {
			stars.add(getStarHash(s));
		}
		sendEvent((new Gson()).toJson(new StarResponse("add", stars)));
		return this;
	}

	private String getStarHash(Star s) {
		Photo p = DAOFactory.getInstance().getPhotoDAO().getPhoto(s.getPhotoId());
		return p.getHash();
	}
}
