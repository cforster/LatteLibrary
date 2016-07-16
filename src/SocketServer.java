import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by charlie on 7/16/16.
 */
public class SocketServer {
    private final SocketIOServer server;

    public SocketServer(int port) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(port);

        server= new SocketIOServer(config);

        server.start();
    }

    public void sendEvent(String s, String t) {
        server.getBroadcastOperations().sendEvent(s, t);
    }

    public void awaitConnection() {
        CountDownLatch connectedSignal = new CountDownLatch(1);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                connectedSignal.countDown();
            }
        });
        try {
            connectedSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        server.stop();
    }

}
