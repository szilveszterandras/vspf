package szilveszterandras.vspf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.handler.SocketHandler;

public class HandlerPool {
	public static final Logger logger = LoggerFactory.getLogger(HandlerPool.class);
	private static HandlerPool instance = null;

	public static HandlerPool getInstance() {
		if (instance == null) {
			instance = new HandlerPool();
		}
		return instance;
	}

	private List<SocketHandler> handlers = new ArrayList<SocketHandler>();

	public void addHandler(SocketHandler h) {
		handlers.add(h);
		logger.debug(String.format("Added stream handler for topic %s and connectionId %s", h.getTopic(),
				h.getConnectionId()));	
	}

	public void destroyHandler(UUID sessionId, String topic) {
		List<SocketHandler> toRemove = new ArrayList<SocketHandler>();
		for (SocketHandler h : this.handlers) {
			if (h.getConnectionId().equals(sessionId) && h.getTopic().equals(topic)) {
				destroyHandler(h);
				toRemove.add(h);
			}
		}
		this.handlers.removeAll(toRemove);
	}
	public void purgeHandlers(UUID sessionId) {
		List<SocketHandler> toRemove = new ArrayList<SocketHandler>();
		for (SocketHandler h : this.handlers) {
			if (h.getConnectionId().equals(sessionId)) {
				destroyHandler(h);
				toRemove.add(h);
			}
		}
		this.handlers.removeAll(toRemove);
	}
	private void destroyHandler(SocketHandler h) {
		h.destroy();

		logger.debug(String.format("Removed stream handler for topic %s and connectionId %s", h.getTopic(),
				h.getConnectionId()));
	}
}
