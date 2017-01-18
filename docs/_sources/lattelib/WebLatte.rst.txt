.. java:import:: org.apache.batik.dom.svg SVGDOMImplementation

.. java:import:: org.w3c.dom DOMImplementation

.. java:import:: org.w3c.dom Document

.. java:import:: org.w3c.dom Element

.. java:import:: org.xml.sax SAXException

.. java:import:: javax.xml.parsers DocumentBuilderFactory

.. java:import:: javax.xml.parsers ParserConfigurationException

.. java:import:: javax.xml.transform.dom DOMSource

.. java:import:: javax.xml.transform.stream StreamResult

.. java:import:: java.io ByteArrayInputStream

.. java:import:: java.io IOException

.. java:import:: java.io StringWriter

.. java:import:: java.util HashMap

.. java:import:: java.util LinkedList

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util.concurrent CountDownLatch

.. java:import:: java.util.concurrent TimeUnit

WebLatte
========

.. java:package:: lattelib
   :noindex:

.. java:type:: public class WebLatte

   Created by charlie on 7/19/16.


Constructors
------------
WebLatte
^^^^^^^^

.. java:constructor:: public WebLatte()
   :outertype: WebLatte

   default constructor

Methods
-------
addButton
^^^^^^^^^

.. java:method:: public void addButton(String name, int x, int y)
   :outertype: WebLatte

   add a button to the window. listen for a click with nextClick()

   :param name: the name of the button
   :param x: the x coordinate of the upper left corner of the button
   :param y: the y coordinate of the upper left corner of the button

addHTML
^^^^^^^

.. java:method:: public void addHTML(String html)
   :outertype: WebLatte

addInput
^^^^^^^^

.. java:method:: public void addInput(String name, int x, int y)
   :outertype: WebLatte

   draw a text input element into the window

   :param name: the name of the text input
   :param x: the x coordinate of the upper left corner
   :param y: the y coordinate of the upper left corner

clearConsole
^^^^^^^^^^^^

.. java:method:: public void clearConsole()
   :outertype: WebLatte

   clear any printed text in the window

clearElements
^^^^^^^^^^^^^

.. java:method:: public void clearElements()
   :outertype: WebLatte

   clear any input elements in the window

clearPaint
^^^^^^^^^^

.. java:method:: public void clearPaint()
   :outertype: WebLatte

   clear any drawings in the window

drawCircle
^^^^^^^^^^

.. java:method:: public Element drawCircle(double cx, double cy, double r, ColorLatte color)
   :outertype: WebLatte

   Draw a circle into the window will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param cx: the x coordinate of the center of the circle
   :param cy: the y coordinate of the center of the circle
   :param r: the radius of the circle
   :param color: the color of the circle
   :return: the circle element, which you can modify before you paint.

drawEllipse
^^^^^^^^^^^

.. java:method:: public Element drawEllipse(double cx, double cy, double rx, double ry, double rot, ColorLatte color)
   :outertype: WebLatte

   Draw an ellipse into the window will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param cx: the x coordinate of the center of the ellipse
   :param cy: the y coordinate of the center of the ellipse
   :param rx: the radius in the x direction
   :param ry: the radius in the y direction
   :param rot: the rotation about the center
   :param color: the color of the ellipse
   :return: the ellipse element, which you can modify before you paint.

drawImage
^^^^^^^^^

.. java:method:: public Element drawImage(String file, double x, double y, double w, double h, double rot)
   :outertype: WebLatte

   Draw an image from a local file will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param file: the name of the noun
   :param x: the x coordinate of the upper left corner
   :param y: the y coordinate of the upper left corner
   :param w: the width of the image
   :param h: the height of the image
   :param rot: the rotation of the image about the center
   :return: the image element, which you can modify before you paint.

drawLine
^^^^^^^^

.. java:method:: public Element drawLine(double x1, double y1, double x2, double y2, double thick, ColorLatte color)
   :outertype: WebLatte

   Draw a line into the window will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param x1: the x coordinate of the starting point
   :param y1: the y coordinate of the starting point
   :param x2: the x coordinate of the ending point
   :param y2: the y coordinate of the ending point
   :param thick: the thickness of the line
   :param color: the color of the line
   :return: the line element, which you can modify before you paint.

drawNoun
^^^^^^^^

.. java:method:: public Element drawNoun(String name, double x, double y, double w, double h, double rot)
   :outertype: WebLatte

   Draw an image from the noun project will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param name: the name of the noun
   :param x: the x coordinate of the upper left corner
   :param y: the y coordinate of the upper left corner
   :param w: the width of the image
   :param h: the height of the image
   :param rot: the rotation of the image about the center
   :return: the image element, which you can modify before you paint.

drawRectangle
^^^^^^^^^^^^^

.. java:method:: public Element drawRectangle(double x, double y, double w, double h, double rot, ColorLatte color)
   :outertype: WebLatte

   Draw a rectangle into the window. will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param x: the x coordinate of the upper left corner
   :param y: the y coordinate of the upper left corner
   :param w: the width of the rectangle
   :param h: the height of the rectangle
   :param rot: rotate the rectangle from its center
   :param color: the color of the rectangle
   :return: the rectangle element, which you can modify before you paint.

drawSVGElement
^^^^^^^^^^^^^^

.. java:method:: public Element drawSVGElement(String svg)
   :outertype: WebLatte

   draw any svg element, see the svg documentation for html5 (advanced function) will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param svg: a string containing valid svg xml
   :return: the svg element, which you can modify before you paint.

drawSVGElement
^^^^^^^^^^^^^^

.. java:method:: public Element drawSVGElement(Element node)
   :outertype: WebLatte

   draw any svg element, see the svg documentation for html5 (advanced function) will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param node: a node containing valid svg representation
   :return: the svg element, which you can modify before you paint.

drawText
^^^^^^^^

.. java:method:: public Element drawText(String s, double x, double y, int size, double rot, ColorLatte color)
   :outertype: WebLatte

   Draw some text into the window will not be visible until you run the paint() function can be cleared with the clearPaint() function

   :param s: the string to draw
   :param x: the x coordinate of the lower left corner
   :param y: the y coordinate of the lower left corner
   :param size: the height of the text
   :param rot: the rotation of the text
   :param color: the color of the text
   :return: the text element, which you can modify before you paint.

getLeapX
^^^^^^^^

.. java:method:: public int getLeapX()
   :outertype: WebLatte

   the x coordinate of a pointer on the leap controller

   :return: the coordinate in pixels from the upper left corner

getLeapY
^^^^^^^^

.. java:method:: public int getLeapY()
   :outertype: WebLatte

   the y coordinate of a pointer on the leap controller

   :return: the coordinate in pixels from the upper left corner

getValue
^^^^^^^^

.. java:method:: public String getValue(String name)
   :outertype: WebLatte

   gives the value stored in the input element

   :param name: the input element'sparkServer name
   :return: the value typed in this will work with any input element

login
^^^^^

.. java:method:: public String login()
   :outertype: WebLatte

   open a login modal that requires a login from the user

   :return: the username from a successful user login

makeClickable
^^^^^^^^^^^^^

.. java:method:: public static void makeClickable(Element e)
   :outertype: WebLatte

nextClick
^^^^^^^^^

.. java:method:: public String nextClick()
   :outertype: WebLatte

   Waits for a button to be clicked

   :return: the name of the button that was clicked you can make any element clickable by adding the "clickable" id and giving it a name ex: Element r = drawRectangle(...) r.setAttribute("class", "clickable"); r.setAttribute("name", "myrectangle");

nextClick
^^^^^^^^^

.. java:method:: public String nextClick(long timeout)
   :outertype: WebLatte

   Waits for a button to be clicked

   :param timeout: if it takes more than this number of millis, return null
   :return: the name of the button that was clicked

nextLine
^^^^^^^^

.. java:method:: public Line nextLine()
   :outertype: WebLatte

   wait for the user to input a string

   :return: the user inputted string

paint
^^^^^

.. java:method:: public void paint()
   :outertype: WebLatte

   paint the drawing to the screen. the program waits a default 25 milliseconds.

paint
^^^^^

.. java:method:: public void paint(long timeout)
   :outertype: WebLatte

   paint the drawing to the screen.

   :param timeout: the program waits for this number of milliseconds.

println
^^^^^^^

.. java:method:: public void println(String s)
   :outertype: WebLatte

   print a line to the screen. can include html tags

   :param s: the string to print

setTitle
^^^^^^^^

.. java:method:: public void setTitle(String title)
   :outertype: WebLatte

