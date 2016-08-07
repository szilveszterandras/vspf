package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.payload.SearchTerm;
import szilveszterandras.vspf.payload.UserFilter;

public class SearchUsersHandler extends AuthorizedHandler<SearchTerm> {
	public SearchUsersHandler() {
		super(SearchTerm.class);
		
		Notifiable<User> n = new Notifiable<User>() {
			@Override
			public void onEvent(User data) {
				returnFitting();
			}
		};
		this.subscribe("user/persist", n);
		this.subscribe("user/update", n);
		this.subscribe("user/remove", n);
	}
	
	@Override
	public SocketHandler run() {
		returnFitting();
		return this;
	}
	private void returnFitting() {
		List<UserFilter> users = DAOFactory.getInstance().getUserDAO().getAllUsers().stream()
				.filter(u -> u.getUsername().contains(payload.getTerm()))
				.sorted((u1, u2) -> u1.getUsername().compareTo(u2.getUsername()))
				.map(u -> new UserFilter(u))
				.collect(Collectors.toList());

		sendEvent((new Gson()).toJson(users));
	}
}
