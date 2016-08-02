package szilveszterandras.vspf;

import java.util.UUID;

import com.corundumstudio.socketio.SocketIOClient;

import szilveszterandras.vspf.dal.Session;

public class UserConnection {
	private UUID connectionId;
	private SocketIOClient client;
	private Boolean isAuthenticated = false;
	private Session session;
	
	public UserConnection(SocketIOClient client) {
		this.connectionId = client.getSessionId();
		this.client = client;
	}

	public UUID getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(UUID connectionId) {
		this.connectionId = connectionId;
	}

	public SocketIOClient getClient() {
		return client;
	}

	public void setClient(SocketIOClient client) {
		this.client = client;
	}

	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}
	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public void dropSession() {
		this.isAuthenticated = false;
		this.session = null;
	}
	
	public void authenticate(Session s) {
		this.isAuthenticated = true;
		this.session = s;
	}
}
