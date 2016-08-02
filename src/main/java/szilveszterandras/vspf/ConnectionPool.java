package szilveszterandras.vspf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectionPool {
	private static ConnectionPool instance = null;
	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	private List<UserConnection> connections = new ArrayList<UserConnection>();
	public void addConnection(UserConnection c) {
		connections.add(c);
	}

	public void removeConnection(UUID sessionId) {
		UserConnection c = this.findConnection(sessionId);
		if (c != null) {
			this.connections.remove(c);
		}
	}
	public UserConnection findConnection(UUID connectionId) {
		for (UserConnection c : this.connections) {
			if (c.getConnectionId().equals(connectionId)) {
				return c;
			}
		}
		return null;
	}
}
