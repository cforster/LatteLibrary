To get started, you'll need to import the jar file.  LINK THAT HERE


In Eclipse
""""""""""

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

    to test your code, run this, then go to http://localhost:8080/ (use chrome).  You should see this:

IMAGE HERE OF HELLO WORLD

Using the console
-----------------

the