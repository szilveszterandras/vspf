package szilveszterandras.vspf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * Hello world!
 *
 */
public class App 
{
	public static final String HOST = "localhost";
	public static final int PORT = 9092;
	public static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws InterruptedException
    {
    	Configuration config = new Configuration();
        config.setHostname(HOST);
        config.setPort(PORT);

        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				//client.sendEvent("login", "Connection successful");
				logger.info("Client connected: " + client.getSessionId());
			}
		});
        server.addEventListener("request", SocketObject.class, new DataListener<SocketObject>() {
            @Override
            public void onData(SocketIOClient client, SocketObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                // server.getBroadcastOperations().sendEvent("chatevent", data);
            	logger.info("Message recieved on WS");
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
