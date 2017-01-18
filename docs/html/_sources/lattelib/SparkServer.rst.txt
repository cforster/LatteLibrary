.. java:import:: org.eclipse.jetty.websocket.api Session

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketClose

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketConnect

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketMessage

.. java:import:: org.eclipse.jetty.websocket.api.annotations WebSocket

.. java:import:: org.json JSONObject

.. java:import:: java.io IOException

.. java:import:: java.util.concurrent CountDownLatch

SparkServer
===========

.. java:package:: lattelib
   :noindex:

.. java:type:: public class SparkServer

   Created by charlie on 8/22/16.

Fields
------
messageReceiver
^^^^^^^^^^^^^^^

.. java:field:: protected static MessageReceiver messageReceiver
   :outertype: SparkServer

session
^^^^^^^

.. java:field:: protected static Session session
   :outertype: SparkServer

socketConnectionSync
^^^^^^^^^^^^^^^^^^^^

.. java:field:: protected static CountDownLatch socketConnectionSync
   :outertype: SparkServer

Constructors
------------
SparkServer
^^^^^^^^^^^

.. java:constructor::  SparkServer(MessageReceiver messageReceiver)
   :outertype: SparkServer

Methods
-------
sendMessage
^^^^^^^^^^^

.. java:method::  void sendMessage(String type, String payload)
   :outertype: SparkServer

