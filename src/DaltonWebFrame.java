import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by charlie on 7/14/16.
 */
public class DaltonWebFrame {

    public static void main(String[] args) {
        DaltonWebFrame frame = new DaltonWebFrame();

        for (int i = 0; i < 100; i++) {
            frame.clear();
            frame.drawRect(10 + i * 2, 20 + i * 2, 100, 100, Color.gray);
            frame.drawSVG("M 100,100 h 50 v 50 h " + (50+i), Color.green);
            frame.render();
        }
        frame.close();
    }

    private List<Drawable> drawme = new LinkedList<Drawable>();
    private SocketServer sockserver;
    private HttpStaticFileServer fileserver;

    public DaltonWebFrame() {
        //declarations:
        final int STATIC_PORT = 8080;
        final int SOCKET_PORT = 9092;

        //start the servers:
        sockserver = new SocketServer(SOCKET_PORT);
        fileserver = new HttpStaticFileServer(STATIC_PORT);

        //file server:
        Thread closeThread = new Thread(new Runnable() {
            public void run() {
                System.out.println("hit Enter to stop.\n");
                try {
                    System.in.read();
                } catch (IOException ignored) {
                }
                close();
            }
        });
        closeThread.start();

        //await a connection to the sockserver:
        sockserver.awaitConnection();
    }

    public void close() {
        System.err.println("shutting down...");
        sockserver.close();
        fileserver.close();
        System.err.println("...all done!");
        System.exit(0);
    }

    public void render() {
        render(33);
    }

    public void render(long backoff) {
        //compile the json
        String json = "[";
        for(Drawable d : drawme) {
            json+=d.tojson() + ", ";
        }
        json=json.substring(0,Math.max(json.length()-2,0)); //trim the last comma
        json +="]";
        System.out.println(json);

        //send the socket frame
        sockserver.sendEvent("drawframe", json);
        try {
            Thread.sleep(backoff);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        this.drawme.clear();
    }

    public void drawRect(double x, double y, double width, double height, Color c) {
        Rectangle s = new Rectangle(x, y, width, height, c);
        this.drawme.add(s);
    }

    public void drawSVG(String s, Color c) {
        ShapeSVG svg = new ShapeSVG(s, c);
        this.drawme.add(svg);
    }

}

class Rectangle extends Drawable {
    Rectangle(double x, double y, double width, double height, Color c) {
        params.put("type","\"rectangle\"");
        params.put("x", x+"");
        params.put("y", y+"");
        params.put("width", width+"");
        params.put("height", height+"");
        params.put("color", colorFormat(c));
    }
}

class ShapeSVG extends Drawable {
    ShapeSVG(String s, Color c) {
        params.put("type", "\"svg\"");
        params.put("svg", "\"" + s + "\"");
        params.put("color", colorFormat(c));
    }
}

abstract class Drawable {
    protected Map<String, String> params = new HashMap<String, String>();

    String tojson() {
        String ret="";
        for (String s : params.keySet()) {
            ret+= "\"" + s + "\": " + params.get(s) + ", ";
        }
        ret = ret.substring(0,Math.max(ret.length()-2,0)); //trim the last comma
        return "{"+ret+"}";
    }

    String colorFormat(Color c) {
        return "\"" + String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()) + "\"";
    }
}
