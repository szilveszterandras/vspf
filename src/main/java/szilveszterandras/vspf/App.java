package szilveszterandras.vspf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.gson.JsonParseException;

import szilveszterandras.vspf.fileserver.HttpServer;

/**
 * Hello world!
 *
 */
public class App {
	public static Properties config = new Properties(); 
	public static final String HOST = "localhost";
	public static final int PORT = 9092;
	public static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws InterruptedException, IOException {
		// Load properties
		config.load(new FileInputStream("config.properties"));
		
		// Setup socket.io server on specified port
		Configuration config = new Configuration();
		config.setHostname(HOST);
		config.setPort(PORT);

		final SocketIOServer server = new SocketIOServer(config);
		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				ConnectionPool.getInstance().addConnection(new UserConnection(client));
				logger.info("Client connected: " + client.getSessionId());
			}
		});
		server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				HandlerPool.getInstance().purgeHandlers(client.getSessionId());
				ConnectionPool.getInstance().removeConnection(client.getSessionId());
				logger.info("Client disconnected: " + client.getSessionId());
			}
		});
		server.addEventListener("request", SocketObject.class, new DataListener<SocketObject>() {
			@Override
			public void onData(SocketIOClient client, SocketObject data, AckRequest ackRequest) {
				logger.debug(String.format("Recieved %s", data.toString()));
				try {
					HandlerFactory
							.createHandler("request", data, ConnectionPool.getInstance().findConnection(client.getSessionId()))
							.authorize().run();
				} catch (JsonParseException | SecurityException | IOException e) {
					logger.info("Request handler exception", e);
				}
			}
		});
		server.addEventListener("stream", SocketObject.class, new DataListener<SocketObject>() {
			@Override
			public void onData(SocketIOClient client, SocketObject data, AckRequest ackRequest) {
				logger.debug(String.format("Recieved %s", data.toString()));
				try {
					HandlerFactory
							.createHandler("stream", data, ConnectionPool.getInstance().findConnection(client.getSessionId()))
							.authorize().run();
				} catch (JsonParseException | SecurityException | IOException e) {
					logger.info("Request handler exception", e);
				}
			}
		});
		
		server.start();
		// Setup file server
		new HttpServer().run();

		Thread.sleep(Integer.MAX_VALUE);

		server.stop();
	}
}
