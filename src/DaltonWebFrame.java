import org.apache.batik.dom.svg.SVGDOMImplementation;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by charlie on 7/19/16.
 */
public class DaltonWebFrame extends SocketAndWebServer {
    public static void main(String[] args) {
        DaltonWebFrame frame = new DaltonWebFrame();
        int i =10;
        frame.addInput("myinput", 50, 300);
        frame.addButton("mybutton", 50, 350);
//        while(i<100) {
//            frame.clear();
//            frame.drawRectangle(i + 10, 20, 30, 40, 0, "blue");
////            frame.drawRectangle(i + 20, 20, 30, i * 2, i, "red");
////            frame.drawRectangle(30, i + 20, 30, 40, 0, "green");
////            frame.drawCircle(50, 50, i, "violet");
////            frame.drawEllipse(100, 100, i, i * 2, i, "yellow");
            frame.drawText("hello <em>world</em>", 200, 200, 20, i, "orange");
////            frame.drawLine(0, 0, i, i, 2, "grey");
////            frame.drawImage("resources/images/bunny.jpeg", 300, 300, 100, 400, i);
////            frame.drawSVGElement("<rect fill=\"blue\" x=\"725.0\" width=\"30.0\" height=\"40.0\" y=\"20.0\"></rect>");
//            frame.paint(40);
//            i++;
//        }
        frame.drawCircle(50, 50, 100, "violet");
        frame.paint();
//
        System.err.println(frame.nextClick());
        System.err.println(frame.getValue("myinput"));


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
    Document svgdoc;
    private Transformer transformer;


    public DaltonWebFrame() {
        super("localhost", 8080, new File("webroot/"), false);
        try {
            start();
            System.out.println("\nRunning! Point your browers to http://localhost:8080/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        clear();
    }

    public String nextClick() {
        clickLatch = new CountDownLatch(1);
        try {
            clickLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clickValue;
    }


    public void stop() {
        super.stop();
        System.out.println("Server stopped.\n");
    }

    public void paint() {
        paint(25);
    }

    public void paint(long timeout) {

        for(Element e : draws) {
            svgdoc.getDocumentElement().appendChild(e);
        }

        try {
            //draws:
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(svgdoc), new StreamResult(writer));
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
        svgdoc = impl.createDocument(svgNS, "svg", null);
        draws.clear();
    }

    public void clearElements() {
        sendSockFrame("cleardiv");
    }

    public String getValue(String name) {
        return dataset.get(name);
    }

    public void addInput(String name, int x, int y) {
        try {
            Document htmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element input = htmldoc.createElement("input");
            input.setAttribute("type", "text");
            input.setAttribute("name", name);
            input.setAttribute("style", "position:fixed; left:" + x + "px; top:" + y +"px;");
            htmldoc.appendChild(input);
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(htmldoc), new StreamResult(writer));
            sendSockFrame( writer.getBuffer().toString().replaceAll("\n|\r", "") );
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    //include any input and it will be available through getValue([name attribute])
    public void addHTML(String html) {
        sendSockFrame( html );
    }

    public void addButton(String name, int x, int y) {
        try {
            Document htmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element button = htmldoc.createElement("button");
            button.setAttribute("type", "button");
            button.setAttribute("name", name);
            button.setAttribute("style", "position:fixed; left:" + x + "px; top:" + y + "px;");
            button.setTextContent(name);
            htmldoc.appendChild(button);
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(htmldoc), new StreamResult(writer));
            sendSockFrame( writer.getBuffer().toString().replaceAll("\n|\r", "") );
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public Element drawRectangle(double x, double y, double w, double h, double rot, String color) {
        Element rectangle = svgdoc.createElementNS(svgNS, "rect");
        rectangle.setAttributeNS(null, "x", Double.toString(x));
        rectangle.setAttributeNS(null, "y", Double.toString(y));
        rectangle.setAttributeNS(null, "width", Double.toString(w));
        rectangle.setAttributeNS(null, "height", Double.toString(h));
        rectangle.setAttributeNS(null, "fill", color);
        if(rot!=0) rectangle.setAttributeNS(null, "transform", "rotate(" + rot + " " + (x+w/2) + " " + (y+h/2) + ")");
        draws.add(rectangle);
        return rectangle;
    }

    public Element drawCircle(double cx, double cy, double r, String color) {
        Element circle = svgdoc.createElementNS(svgNS, "circle");
        circle.setAttributeNS(null, "cx", Double.toString(cx));
        circle.setAttributeNS(null, "cy", Double.toString(cy));
        circle.setAttributeNS(null, "r", Double.toString(r));
        circle.setAttributeNS(null, "fill", color);
        draws.add(circle);
        return circle;
    }

    public Element drawEllipse(double cx, double cy, double rx, double ry, double rot, String color) {
        Element ellipse = svgdoc.createElementNS(svgNS, "ellipse");
        ellipse.setAttributeNS(null, "cx", Double.toString(cx));
        ellipse.setAttributeNS(null, "cy", Double.toString(cy));
        ellipse.setAttributeNS(null, "rx", Double.toString(rx));
        ellipse.setAttributeNS(null, "ry", Double.toString(ry));
        if(rot!=0) ellipse.setAttributeNS(null, "transform", "rotate(" + rot + " " + cx + " " + cy + ")");
        ellipse.setAttributeNS(null, "fill", color);
        draws.add(ellipse);
        return ellipse;
    }

    public Element drawLine(double x1, double y1, double x2, double y2, double thick, String color) {
        Element line = svgdoc.createElementNS(svgNS, "line");
        line.setAttributeNS(null, "x1", Double.toString(x1));
        line.setAttributeNS(null, "y1", Double.toString(y1));
        line.setAttributeNS(null, "x2", Double.toString(x2));
        line.setAttributeNS(null, "y2", Double.toString(y2));
        line.setAttributeNS(null, "stroke", color);
        line.setAttributeNS(null, "stroke-width", Double.toString(thick));
        draws.add(line);
        return line;
    }


    public Element drawText(String s, double x, double y, int size, double rot, String color)
    {
        Element text = svgdoc.createElementNS(svgNS, "text");
        text.setAttributeNS(null, "x", Double.toString(x));
        text.setAttributeNS(null, "y", Double.toString(y));
        text.setAttributeNS(null, "font-size", Integer.toString(size));
        text.setAttributeNS(null, "style", "fill: " +color +";");
        if(rot!=0) text.setAttributeNS(null, "transform", "rotate(" + rot + " " + x + " " + y + ")");
        text.setTextContent(s);
        draws.add(text);
        return text;
    }

    public Element drawImage(String file, double x, double y, double w, double h, double rot) {
        Element image = svgdoc.createElementNS(svgNS, "image");
        image.setAttributeNS(null, "x", Double.toString(x));
        image.setAttributeNS(null, "y", Double.toString(y));
        image.setAttributeNS(null, "width", Double.toString(w));
        image.setAttributeNS(null, "height", Double.toString(h));
        image.setAttributeNS(null, "xlink:href", file);
        if(rot!=0) image.setAttributeNS(null, "transform", "rotate(" + rot + " " + (x + w / 2) + " " + (y + h / 2) + ")");
        draws.add(image);
        return image;
    }

    public Element drawSVGElement(String svg) {
        try {
            Element node =  DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(svg.getBytes()))
                    .getDocumentElement();
            node = (Element) svgdoc.importNode(node, true);
            draws.add(node);
            return node;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Element drawSVGElement(Element node) {
        node = (Element) svgdoc.importNode(node, true);
        draws.add(node);
        return node;
    }

}
class Color extends java.awt.Color {
    final public Color aliceblue = (Color) Color.decode("#F0F8FF");

    public Color(int r, int g, int b) {
        super(r, g, b);
    }

    public String toString() {
        return "\"" + String.format("#%02x%02x%02x", this.getRed(), this.getGreen(), this.getBlue()) + "\"";
    }
}
