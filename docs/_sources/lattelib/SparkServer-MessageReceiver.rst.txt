.. java:import:: org.eclipse.jetty.websocket.api Session

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketClose

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketConnect

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketMessage

.. java:import:: org.eclipse.jetty.websocket.api.annotations WebSocket

.. java:import:: org.json JSONObject

.. java:import:: java.io IOException

.. java:import:: java.util.concurrent CountDownLatch

SparkServer.MessageReceiver
===========================

.. java:package:: lattelib
   :noindex:

.. java:type::  interface MessageReceiver
   :outertype: SparkServer

Methods
-------
receive
^^^^^^^

.. java:method::  void receive(JSONObject json)
   :outertype: SparkServer.MessageReceiver

