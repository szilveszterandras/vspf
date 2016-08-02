package szilveszterandras.vspf.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.Notifier;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.User;

public class UsersStreamHandler extends AuthorizedHandler<Object> {
	public static final Logger logger = LoggerFactory.getLogger(UsersStreamHandler.class);

	public UsersStreamHandler() {
		super(Object.class);
		
		Notifier.getInstance().subscribe("user/persist", new Notifiable<User>() {
			@Override
			public void onEvent(User data) {
				sendEvent((new Gson()).toJson(wrap(data)));
			}
		}, this.hashCode());
		Notifier.getInstance().subscribe("user/update", new Notifiable<User>() {
			@Override
			public void onEvent(User data) {
				sendEvent((new Gson()).toJson(wrap(data)));
			}
		}, this.hashCode());
		Notifier.getInstance().subscribe("user/remove", new Notifiable<Long>() {
			@Override
			public void onEvent(Long id) {
				sendEvent((new Gson()).toJson(wrap(id)));
			}
		}, this.hashCode());
	}

	@Override
	public SocketHandler run() {
		// Send snapshot
		List<User> ul = DAOFactory.getInstance().getUserDAO().getAllUsers();
		this.sendEvent((new Gson()).toJson(ul));
		return this;
	}

	@Override
	public void destroy() {
		Notifier.getInstance().unsubscribe("user/persist", this.hashCode());
		Notifier.getInstance().unsubscribe("user/update", this.hashCode());
		Notifier.getInstance().unsubscribe("user/remove", this.hashCode());
	}
}
