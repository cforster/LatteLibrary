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
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by charlie on 7/19/16.
 */
public class DaltonWebFrame extends SocketAndWebServer {
    public static void main(String[] args) {
        DaltonWebFrame frame = new DaltonWebFrame();
        frame.println("hello what's <b>your</b> name");
        String s = frame.nextLine();
//        String uname = frame.login();
//        System.err.println(uname);

        frame.clearConsole();

        frame.addButton("stop", 10,10);

        int i =10;

        while(true) {
            frame.clearPaint();
            Element r = frame.drawRectangle(100, 200, 300, 50, i, Color.AliceBlue);
            Element t = frame.drawText("hello " + s, 100, 240, 50, 0, Color.GoldenRod);
            t.setAttribute("transform", r.getAttribute("transform"));
            frame.drawText(frame.getLeapX() + "," +frame.getLeapY(), 100, 100, 20, 0, Color.IndianRed);
            frame.paint(0);
            i+=5;
            if(frame.nextClick(25)!=null) break;
        }
        frame.stop();
    }


    private List<Element> draws = new LinkedList<Element>();
    DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    Document svgdoc;
    private Transformer transformer;


    public DaltonWebFrame() {
        super("localhost", 8080, new File("webroot/"), true); //last elem is quiet
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

        clearPaint();
    }

    public String login() {
        loginLatch = new CountDownLatch(1);
        try{
            sendSockFrame("logi");
            loginLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getValue("username");
    }

    public int getLeapX() {
        return (int)Double.parseDouble(getValue("leap-x"));
    }

    public int getLeapY() {
        return (int) Double.parseDouble(getValue("leap-y"));
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

    public String nextClick(long timeout) {
        clickLatch = new CountDownLatch(1);
        clickValue = null;
        try {
            clickLatch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clickValue;
    }

    public String nextLine() {
        inputLatch = new CountDownLatch(1);
        try {
            sendSockFrame("coin");
            inputLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getValue("in");
    }

    public void stop() {
        super.stop();
        System.out.println("Server stopped.\n");
    }

    public void println(String s) {
        sendSockFrame("cout" + s);
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

            sendSockFrame("svgt" + output);
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

    public void clearPaint() {
        svgdoc = impl.createDocument(svgNS, "svg", null);
        svgdoc.getDocumentElement().setAttribute("id", "user-svg");
        draws.clear();
    }

    public void clearConsole() {
        sendSockFrame("cocl");
    }

    public void clearElements() {
        sendSockFrame("cldi");
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
            sendSockFrame("html" + writer.getBuffer().toString().replaceAll("\n|\r", "") );
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    //include any input and it will be available through getValue([name attribute])
    public void addHTML(String html) {
        sendSockFrame( "html" + html );
    }

    public void addButton(String name, int x, int y) {
        try {
            Document htmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element button = htmldoc.createElement("button");
            button.setAttribute("type", "button");
            button.setAttribute("class", "clickable");
            button.setAttribute("name", name);
            button.setAttribute("style", "position:fixed; left:" + x + "px; top:" + y + "px;");
            button.setTextContent(name);
            htmldoc.appendChild(button);
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(htmldoc), new StreamResult(writer));
            sendSockFrame( "html" + writer.getBuffer().toString().replaceAll("\n|\r", "") );
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public Element drawRectangle(double x, double y, double w, double h, double rot, Color color) {
        Element rectangle = svgdoc.createElementNS(svgNS, "rect");
        rectangle.setAttributeNS(null, "x", Double.toString(x));
        rectangle.setAttributeNS(null, "y", Double.toString(y));
        rectangle.setAttributeNS(null, "width", Double.toString(w));
        rectangle.setAttributeNS(null, "height", Double.toString(h));
        rectangle.setAttributeNS(null, "fill", color.toString());
        if(rot!=0) rectangle.setAttributeNS(null, "transform", "rotate(" + rot + " " + (x+w/2) + " " + (y+h/2) + ")");
        draws.add(rectangle);
        return rectangle;
    }

    public Element drawCircle(double cx, double cy, double r, Color color) {
        Element circle = svgdoc.createElementNS(svgNS, "circle");
        circle.setAttributeNS(null, "cx", Double.toString(cx));
        circle.setAttributeNS(null, "cy", Double.toString(cy));
        circle.setAttributeNS(null, "r", Double.toString(r));
        circle.setAttributeNS(null, "fill", color.toString());
        draws.add(circle);
        return circle;
    }

    public Element drawEllipse(double cx, double cy, double rx, double ry, double rot, Color color) {
        Element ellipse = svgdoc.createElementNS(svgNS, "ellipse");
        ellipse.setAttributeNS(null, "cx", Double.toString(cx));
        ellipse.setAttributeNS(null, "cy", Double.toString(cy));
        ellipse.setAttributeNS(null, "rx", Double.toString(rx));
        ellipse.setAttributeNS(null, "ry", Double.toString(ry));
        if(rot!=0) ellipse.setAttributeNS(null, "transform", "rotate(" + rot + " " + cx + " " + cy + ")");
        ellipse.setAttributeNS(null, "fill", color.toString());
        draws.add(ellipse);
        return ellipse;
    }

    public Element drawLine(double x1, double y1, double x2, double y2, double thick, Color color) {
        Element line = svgdoc.createElementNS(svgNS, "line");
        line.setAttributeNS(null, "x1", Double.toString(x1));
        line.setAttributeNS(null, "y1", Double.toString(y1));
        line.setAttributeNS(null, "x2", Double.toString(x2));
        line.setAttributeNS(null, "y2", Double.toString(y2));
        line.setAttributeNS(null, "stroke", color.toString());
        line.setAttributeNS(null, "stroke-width", Double.toString(thick));
        draws.add(line);
        return line;
    }


    public Element drawText(String s, double x, double y, int size, double rot, Color color)
    {
        Element text = svgdoc.createElementNS(svgNS, "text");
        text.setAttributeNS(null, "x", Double.toString(x));
        text.setAttributeNS(null, "y", Double.toString(y));
        text.setAttributeNS(null, "font-size", Integer.toString(size));
        text.setAttributeNS(null, "style", "fill: " +color.toString() +";");
        if(rot!=0) text.setAttributeNS(null, "transform", "rotate(" + rot + " " + x + " " + y + ")");
        text.setTextContent(s);
        draws.add(text);
        return text;
    }

    public Element drawNoun(String name, double x, double y, double w, double h, double rot) {
        return drawImage("resources/nouns/" + name + ".png", x,y,w,h,rot);
        //TODO: catch if this noun doesn't exist
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
