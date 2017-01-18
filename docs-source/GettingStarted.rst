Getting Started
===============


Hello World
-----------

To get started, you'll need to import the jar file.  LINK THAT HERE

In an eclipse project, drag to include, then right click and say "add to build path"

VIDEO HERE OF HOW TO DO THIS

Then try making this simple hello world program:

.. code-block:: java
    :linenos:

        public class HelloLatte {
            public static void main(String[] args) {
                WebLatte frame = new WebLatte();
                frame.println("hello world");
            }
        }


to test your code, run this, then go to http://localhost:8081/ (use chrome).  You should see this:

IMAGE HERE OF HELLO WORLD

Using the Console
-----------------

A console is a simple way to communicate in text with the user.  WebLatte includes some basic console features.

The WebLatte console supports two major functions

``println(String output)``:
    this will print some text to the webframe.

the println() function can accept html tags, so if you want to style or color your text you can do that.  For example you could write ``frame.println("hello <b>world</b>");`` to make world bold.  You could also be fancy and add to the style attribute like this ``frame.println("hello <span style="color:green;">world</span>");``


``nextLine()``:
    this will get some input from the webframe.

to get the data type you are hoping for, you should call the appropriate "to" function.  for example:

.. code-block:: java
    String s = frame.nextLine().toString();
    int i = frame.nextLine().toInt();
    double d = frame.nextLine().toDouble();
    char c = frame.nextLine().toChar();

* the ``toInt()`` and ``toDouble()`` will get a number anywhere in the input line.  so for example "I am 3 years old" would return 3
* the ``toChar()`` will get the first char, so "charlie" would return 'c'


Drawing to the screen
---------------------

You might be interested in drawing to the screen.  The basic process for this is to use the draw functions, and then to paint.

.. code-block:: java
    :linenos:

    public class DrawDemo {
        public static void main(String[] args) {
            WebLatte frame = new WebLatte();
            frame.drawCircle(100, 100, 30, ColorLatte.Aqua);
            frame.drawImage("https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/800px-Smiley.svg.png", 200, 200, 50, 50, 0);
    		frame.paint();
        }
    }

this will draw an aqua circle at x,y = (100,100) with a 30 pixel radius.  For the image, you may use a link to an image online, or you can include a file in your project folder and reference that.  PRO TIP: WebLatte serves files from the system property ``user.dir``.

You can set a special color if you like by replacing the color with a color constructed with RGB: ``frame.drawCircle(1,1,1,new ColorLatte(255, 255, 0));``.  you can get color values using the Digital Color Meter or at kuler.adobe.com.

Animation
---------

You can animate things you draw by looping and including the paint() function.

.. code-block:: java
    :linenos:

    public class AnimateDemo {
        public static void main(String[] args) {
            WebLatte frame = new WebLatte();
            int move = 0;

            while(true) {
                frame.clearPaint();
                frame.drawRectangle(move, 100, 30, 30, 0, ColorLatte.Brown);
        		frame.paint();
        		move++;
            }
        }
    }


*   the ``move`` variable increases on each iteration of the loop, and since it is in the x coordinate fo the drawrectangle, the rectangle will move in each iteration.  Try adding move to to other parameters
*   ``clearPaint()`` removes any images from the previous frame.  If you don't call it between frames, the box will blur across the screen.
*   the ``paint()`` function has a default delay time, but you can slow it down if you like by passing a number of milliseconds as the parameter (``paint(500)`` will wait half a second before the next frame is painted).

Making Noise with Midi
----------------------

content here

Making Noise with the Synthesizer
---------------------------------

content here

Talking Computer
----------------

content here

Using the Dictionary
--------------------

content here

Getting a Stock
---------------

content here

