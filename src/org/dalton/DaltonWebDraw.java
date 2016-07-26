package org.dalton;

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
public class DaltonWebDraw extends SocketAndWebServer {
    public static void main(String[] args) {
        DaltonWebDraw frame = new DaltonWebDraw();
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


    /**
     * default constructor
     */
    public DaltonWebDraw() {
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


    /**
     * open a login modal that requires a login from the user
     * @return the username from a successful user login
     */
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

    /**
     * the x coordinate of a pointer on the leap controller
     * @return the coordinate in pixels from the upper left corner
     */
    public int getLeapX() {
        return (int)Double.parseDouble(getValue("leap-x"));
    }

    /**
     * the y coordinate of a pointer on the leap controller
     * @return the coordinate in pixels from the upper left corner
     */
    public int getLeapY() {
        return (int) Double.parseDouble(getValue("leap-y"));
    }

    /**
     * Waits for a button to be clicked
     * @return the name of the button that was clicked
     *
     * you can make any element clickable by adding the "clickable" id and giving it a name
     * ex:
     * Element r = drawRectangle(...)
     * r.setAttribute("id", "clickable");
     * r.setAttribute("name", "myrectangle");
     */
    public String nextClick() {
        clickLatch = new CountDownLatch(1);
        try {
            clickLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clickValue;
    }

    /**
     * Waits for a button to be clicked
     * @param timeout if it takes more than this number of millis, return null
     * @return the name of the button that was clicked
     */
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

    /**
     * wait for the user to input a string
     * @return the user inputted string
     */
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

    /**
     * end the program
     */
    public void stop() {
        super.stop();
        System.out.println("Server stopped.\n");
    }

    /**
     * print a line to the screen.  can include html tags
     * @param s the string to print
     */
    public void println(String s) {
        sendSockFrame("cout" + s);
    }

    /**
     * paint the drawing to the screen.  the program waits a default 25 milliseconds.
     */
    public void paint() {
        paint(25);
    }

    /**
     * paint the drawing to the screen.
     * @param timeout the program waits for this number of milliseconds.
     */
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

    /**
     * clear any drawings in the window
     */
    public void clearPaint() {
        svgdoc = impl.createDocument(svgNS, "svg", null);
        svgdoc.getDocumentElement().setAttribute("id", "user-svg");
        draws.clear();
    }

    /**
     * clear any printed text in the window
     */
    public void clearConsole() {
        sendSockFrame("cocl");
    }

    /**
     * clear any input elements in the window
     */
    public void clearElements() {
        sendSockFrame("cldi");
    }

    /**
     * gives the value stored in the input element
     * @param name the input element's name
     * @return the value typed in
     *
     * this will work with any input element
     */
    public String getValue(String name) {
        return dataset.get(name);
    }

    /**
     * draw a text input element into the window
     * @param name the name of the text input
     * @param x the x coordinate of the upper left corner
     * @param y the y coordinate of the upper left corner
     */
    public void addInput(String name, int x, int y) {
        try {
            Document htmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element input = htmldoc.createElement("input");
            input.setAttribute("type", "text");
            input.setAttribute("name", name);
            input.setAttribute("style", "position:fixed; left:" + x + "px; top:" + y +"px;");
            input.setAttribute("placeholder", name);
            htmldoc.appendChild(input);
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(htmldoc), new StreamResult(writer));
            sendSockFrame("html" + writer.getBuffer().toString().replaceAll("\n|\r", "") );
            dataset.put(name, "");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /*
    * add any html you'd like to the page (advanced function)
    * include any input and it will be available through getValue([name attribute])
    * can be cleared with clearElements()
     */
    public void addHTML(String html) {
        sendSockFrame( "html" + html );
    }

    /**
     * add a button to the window.  listen for a click with nextClick()
     * @param name the name of the button
     * @param x the x coordinate of the upper left corner of the button
     * @param y the y coordinate of the upper left corner of the button
     */
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

    /**
     * Draw a rectangle into the window.
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param x the x coordinate of the upper left corner
     * @param y the y coordinate of the upper left corner
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     * @param rot rotate the rectangle from its center
     * @param color the color of the rectangle
     * @return the rectangle element, which you can modify before you paint.
     */
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

    /**
     * Draw a circle into the window
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param cx the x coordinate of the center of the circle
     * @param cy the y coordinate of the center of the circle
     * @param r the radius of the circle
     * @param color the color of the circle
     * @return the circle element, which you can modify before you paint.
     */
    public Element drawCircle(double cx, double cy, double r, Color color) {
        Element circle = svgdoc.createElementNS(svgNS, "circle");
        circle.setAttributeNS(null, "cx", Double.toString(cx));
        circle.setAttributeNS(null, "cy", Double.toString(cy));
        circle.setAttributeNS(null, "r", Double.toString(r));
        circle.setAttributeNS(null, "fill", color.toString());
        draws.add(circle);
        return circle;
    }

    /**
     * Draw an ellipse into the window
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param cx the x coordinate of the center of the ellipse
     * @param cy the y coordinate of the center of the ellipse
     * @param rx the radius in the x direction
     * @param ry the radius in the y direction
     * @param rot the rotation about the center
     * @param color the color of the ellipse
     * @return the ellipse element, which you can modify before you paint.
     */
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

    /**
     * Draw a line into the window
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param x1 the x coordinate of the starting point
     * @param y1 the y coordinate of the starting point
     * @param x2 the x coordinate of the ending point
     * @param y2 the y coordinate of the ending point
     * @param thick the thickness of the line
     * @param color the color of the line
     * @return the line element, which you can modify before you paint.
     */
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


    /**
     * Draw some text into the window
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param s the string to draw
     * @param x the x coordinate of the lower left corner
     * @param y the y coordinate of the lower left corner
     * @param size the height of the text
     * @param rot  the rotation of the text
     * @param color the color of the text
     * @return the text element, which you can modify before you paint.
     */
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

    /**
     * Draw an image from the noun project
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param name the name of the noun
     * @param x the x coordinate of the upper left corner
     * @param y the y coordinate of the upper left corner
     * @param w the width of the image
     * @param h the height of the image
     * @param rot the rotation of the image about the center
     * @return the image element, which you can modify before you paint.
     */
    public Element drawNoun(String name, double x, double y, double w, double h, double rot) {
        return drawImage("resources/nouns/" + name + ".png", x,y,w,h,rot);
        //TODO: catch if this noun doesn't exist
    }

    /**
     * Draw an image from a local file
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param file the name of the noun
     * @param x the x coordinate of the upper left corner
     * @param y the y coordinate of the upper left corner
     * @param w the width of the image
     * @param h the height of the image
     * @param rot the rotation of the image about the center
     * @return the image element, which you can modify before you paint.
     */
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

    /**
     * draw any svg element, see the svg documentation for html5 (advanced function)
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param svg a string containing valid svg xml
     * @return the svg element, which you can modify before you paint.
     */
    public Element drawSVGElement(String svg) {
        try {
            Element node =  DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(svg.getBytes()))
                    .getDocumentElement();
            return drawSVGElement(node);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * draw any svg element, see the svg documentation for html5 (advanced function)
     * will not be visible until you run the paint() function
     * can be cleared with the clearPaint() function
     * @param node a node containing valid svg representation
     * @return the svg element, which you can modify before you paint.
     */
    public Element drawSVGElement(Element node) {
        node = (Element) svgdoc.importNode(node, true);
        draws.add(node);

        return node;
    }

}
