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

        while(true) {
            for (int i = 0; i < 100; i++) {
                frame.clear();
                frame.drawRect(10 + i * 2, 20 + i * 2, 100, 100, i, Color.gray);
                frame.drawSVG("M70.16,30.021c-0.189,0-0.379,0.007-0.567,0.021v-0.59c0-4.142-3.37-7.512-7.513-7.512  c-0.189,0-0.379,0.007-0.567,0.022v-0.59c0-4.142-3.369-7.512-7.511-7.512c-3.692,0-6.771,2.676-7.398,6.189  c-1.016-0.513-2.163-0.803-3.377-0.803c-4.143,0-7.513,3.37-7.513,7.513v16.122c-1.401-1.708-3.511-2.737-5.806-2.737  c-0.984,0-1.947,0.191-2.864,0.57c-3.829,1.581-5.657,5.983-4.076,9.812l7.989,19.356C33.475,80.642,42.924,88.139,54,88.139  c13.054,0,23.673-10.619,23.673-23.672V37.534C77.673,33.392,74.303,30.021,70.16,30.021z M73.421,64.467  c0,10.708-8.712,19.42-19.421,19.42c-9.138,0-16.925-6.218-18.938-15.12l-8.165-19.862c-0.332-0.805-0.331-1.691,0.003-2.496  s0.962-1.431,1.766-1.763c0.399-0.165,0.817-0.248,1.242-0.248c1.327,0,2.512,0.792,3.018,2.018l3.601,8.728l3.439-4.17V26.761  c0-1.798,1.463-3.261,3.261-3.261s3.261,1.463,3.261,3.261V43.6v4.707h4.252V43.6V26.761v-5.388c0-1.797,1.464-3.26,3.263-3.26  c1.797,0,3.259,1.462,3.259,3.26v2.325c-1.645,1.379-2.693,3.446-2.693,5.755V51h4.252V29.453c0-1.24,0.704-2.308,1.728-2.859  c0.186-0.1,0.377-0.178,0.573-0.24c0.063-0.02,0.126-0.043,0.191-0.06c0.25-0.062,0.506-0.101,0.769-0.101  c1.798,0,3.261,1.462,3.261,3.26v2.325c-1.645,1.379-2.693,3.447-2.693,5.756V59.08h4.252V37.534c0-1.241,0.705-2.31,1.728-2.86  c0.186-0.1,0.377-0.178,0.573-0.239c0.063-0.02,0.126-0.044,0.191-0.06c0.25-0.062,0.506-0.101,0.769-0.101  c1.798,0,3.261,1.463,3.261,3.261V64.467z", Color.green);
                frame.render();
            }
        }
//        frame.close();
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

    public void drawRect(double x, double y, double width, double height, double rotation, Color c) {
        Rectangle s = new Rectangle(x, y, width, height, rotation, c);
        this.drawme.add(s);
    }

    public void drawSVG(String s, Color c) {
        ShapeSVG svg = new ShapeSVG(s, c);
        this.drawme.add(svg);
    }

}

class Rectangle extends Drawable {
    Rectangle(double x, double y, double width, double height, double rotation, Color c) {
        params.put("type","\"rectangle\"");
        params.put("x", x+"");
        params.put("y", y+"");
        params.put("width", width+"");
        params.put("height", height+"");
        params.put("rotation", rotation+"");
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
