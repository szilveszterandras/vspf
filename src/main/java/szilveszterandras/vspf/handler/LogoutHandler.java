package szilveszterandras.vspf.handler;

import szilveszterandras.vspf.dal.DAOFactory;

public class LogoutHandler extends AuthorizedHandler<Object> {
	public LogoutHandler() {
		super(Object.class);
	}
	@Override
	public SocketHandler run() {
		DAOFactory.getInstance().getSessionDAO().deleteSession(this.connection.getSession().getId());
		this.connection.dropSession();
		return this;
	}
}
