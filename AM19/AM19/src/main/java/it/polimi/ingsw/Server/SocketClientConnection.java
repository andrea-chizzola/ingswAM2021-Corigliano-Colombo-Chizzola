package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Messages.*;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles server side the connection between client and server
 */
public class SocketClientConnection implements ClientConnection, Runnable {

    private Socket socket;
    private Server server;
    private boolean pong;
    private String socketID;
    private ConnectionListener controller;
    private BufferedReader in;
    private NetworkBuffer buffer;
    private PrintWriter out;

    public SocketClientConnection(Socket socket, Server server, String socketID) {

        this.socket = socket;
        this.server = server;
        this.socketID = socketID;
        //this.listener = listener; AGGIUNGERE LISTENER A CONTRUCTOR
        pong = true;

    }

    /**
     * @return returns the socket's listener
     */
    @Override
    public ConnectionListener getListener() {
        return controller;
    }

    /**
     * Closes the connection between the server and the client
     */
    @Override
    public synchronized void closeConnection() {

        System.out.println("[CONNECTION] Closing socket connection...");
        try {
            socket.close();
            in.close();
            out.close();
            System.out.println("[CONNECTION] Closed socket connection.");
        } catch (IOException e) {
            System.err.println("[CONNECTION] Error when closing socket");
        }

    }

    /**
     * Adds a new listener of the socket connection between server and client
     *
     * @param listener represents the new listener
     */
    @Override
    public void addListener(ConnectionListener listener) {

        this.controller = listener;

    }

    /**
     * sends a new message to the client
     *
     * @param message represents the message to send
     */
    @Override
    public synchronized void send(String message) {

        out.println(message);
        out.flush();

    }

    /**
     * sends a new message to the client asynchronously
     *
     * @param message represents the message to send
     */
    @Override
    public void asyncSend(String message) {

        new Thread(() -> send(message)).start();

    }

    /**
     * handles the messages received from the client
     * @param read represents the message received
     */
    private void messageHandler(String read) {

        try {
            buffer.append(read);
        }catch (Exception e){
            controller.onReceivedMessage(read, socketID);
        }


        if(buffer.getPong()) pong = true;
        else if(buffer.getPing()) asyncSend("<pong/>");
        else {
            try {
                controller.onReceivedMessage(buffer.get(), socketID);
            } catch (EmptyBufferException e){
                System.out.println("[SERVER] The buffer is empty!");
            }
        }
    }

    /**
     * starts the ping pong protocol between server and client
     */
    public void startPingTimer() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (pong) {
                    asyncSend("<ping/>");
                    pong = false;
                } else {
                    System.out.println("[SERVER] No pong received. Client will now be disconnected.");
                    controller.onMissingPong(socketID);
                    closeConnection();
                    timer.cancel();
                }

            }
        };
        timer.scheduleAtFixedRate(task, 0, 15000);

    }


    /**
     * manages server-client connection and communication
     */
    @Override
    public void run() {

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            buffer = new NetworkBuffer();
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {

            e.printStackTrace();
            System.err.println("[SERVER] Error occurred while opening input and output streams.");

        }

        server.addConnection(socketID,this);
        startPingTimer();
        String read = "";

        try {

            while ((read = in.readLine()) != null) {

                System.out.println("[SERVER] Received: " + read);

                messageHandler(read);

            }
            controller.onMissingPong(socketID);
            closeConnection();

        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.err.println("[SERVER] Connection error.");

        }
    }

}