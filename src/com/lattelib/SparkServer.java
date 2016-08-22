package com.lattelib;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import static spark.Spark.*;


/**
 * Created by charlie on 8/22/16.
 */
public class SparkServer {
    protected static CountDownLatch socketConnectionSync = new CountDownLatch(1); //TODO: make this reset!!!
    protected static MessageReceiver messageReceiver;
    protected static Session session;

    SparkServer(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;

        port(8080);
        webSocket("/websocket", SparkWebSocket.class);
        staticFiles.location(""); // Static files

        init();
        awaitInitialization(); // Wait for server to be initialized
        System.err.println("server started");

        try {
            socketConnectionSync.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void sendMessage(String type, String payload) {


        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("payload", payload);

        try {
            session.getRemote().sendString(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @WebSocket
    public static class SparkWebSocket {
        @OnWebSocketConnect
        public void connected(Session session) {
            SparkServer.socketConnectionSync.countDown();
            SparkServer.session = session;
            System.err.println("socket open");
        }

        @OnWebSocketClose
        public void closed(Session session, int statusCode, String reason) {
            System.err.println("socket closed");
            System.exit(0); //if the client disconnects.
        }

        @OnWebSocketMessage
        public void message(Session session, String message) throws IOException {
            System.out.println("Got: " + message);   // Print message

            JSONObject o = new JSONObject(message);
            messageReceiver.receive(o);
        }
    }


    interface MessageReceiver {
        void receive(JSONObject json);
    }

}