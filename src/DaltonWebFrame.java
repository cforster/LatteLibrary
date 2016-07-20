import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by charlie on 7/19/16.
 */
public class DaltonWebFrame extends SocketAndWebServer {
    public static void main(String[] args) {
        DaltonWebFrame frame = new DaltonWebFrame();
        int i =10;
        while(true) {
            frame.clear();
            frame.drawRectangle(i + 10, 20, 30, 40, "blue");
            frame.drawRectangle(i+20, 20, 30, i*2, "red");
            frame.drawRectangle(30, i + 20, 30, 40, "green");
            frame.render();
            i++;
        }


//        System.out.println("hit Enter to stop.\n");
//        try {
//            System.in.read();
//        } catch (IOException ignored) {
//        }
//        frame.stop();
//        frame.stop();



        //color:
        // String colorFormat(Color c) {
        //return "\"" + String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()) + "\"";
        // }
    }


    private List<Element> draws = new LinkedList<Element>();
    DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    Document doc;

    public DaltonWebFrame() {
        super("localhost", 8080, new File("webroot/"), false);
        try {
            start();
            System.out.println("\nRunning! Point your browers to http://localhost:8080/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        clear();
    }

    public void stop() {
        super.stop();
        System.out.println("Server stopped.\n");
    }

    public void render() {
        render(34);
    }

    public void render(long timeout) {

        for(Element e : draws) {
            doc.getDocumentElement().appendChild(e);
        }

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
            sendSockFrame(output);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        doc = impl.createDocument(svgNS, "svg", null);
        draws.clear();
    }

    public void drawRectangle(double x, double y, double w, double h, String color) {
        Element rectangle = doc.createElementNS(svgNS, "rect");
        rectangle.setAttributeNS(null, "x", Double.toString(x));
        rectangle.setAttributeNS(null, "y", Double.toString(y));
        rectangle.setAttributeNS(null, "width", Double.toString(w));
        rectangle.setAttributeNS(null, "height", Double.toString(h));
        rectangle.setAttributeNS(null, "fill", color);

        draws.add(rectangle);
    }


}
