.. java:import:: org.eclipse.jetty.websocket.api Session

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketClose

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketConnect

.. java:import:: org.eclipse.jetty.websocket.api.annotations OnWebSocketMessage

.. java:import:: org.eclipse.jetty.websocket.api.annotations WebSocket

.. java:import:: org.json JSONObject

.. java:import:: java.io IOException

.. java:import:: java.util.concurrent CountDownLatch

SparkServer.SparkWebSocket
==========================

.. java:package:: lattelib
   :noindex:

.. java:type:: @WebSocket public static class SparkWebSocket
   :outertype: SparkServer

Methods
-------
closed
^^^^^^

.. java:method:: @OnWebSocketClose public void closed(Session session, int statusCode, String reason)
   :outertype: SparkServer.SparkWebSocket

connected
^^^^^^^^^

.. java:method:: @OnWebSocketConnect public void connected(Session session)
   :outertype: SparkServer.SparkWebSocket

message
^^^^^^^

.. java:method:: @OnWebSocketMessage public void message(Session session, String message) throws IOException
   :outertype: SparkServer.SparkWebSocket

