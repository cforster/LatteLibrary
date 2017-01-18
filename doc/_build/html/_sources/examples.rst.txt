Examples
========

Here are some example programs using LatteLib

Hello World
-----------

Here's a super simple program

.. code-block:: java
    :linenos:

    WebLatte frame = new WebLatte();
    frame.println("hello world");


A Silly Example
---------------

This needs to be replaced with better examples

.. code-block:: java
    :linenos:

    WebLatte frame = new WebLatte();

    frame.drawImage("bunny.jpg", 0, 0, 400, 400, 0);
    frame.paint();

    frame.println("hello world");
    String name = frame.nextLine().toString();
    frame.println("hello " + name);
    frame.drawCircle(30, 30, 50, ColorLatte.BLACK);
    frame.paint();
    frame.addInput("noun", 100, 100);
    frame.addButton("done", 140, 140);
    frame.nextClick();
    String noun = frame.getValue("noun");
    frame.println(noun);