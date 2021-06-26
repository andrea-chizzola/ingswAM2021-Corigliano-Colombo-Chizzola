package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Exceptions.MalformedMessageException;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles server side the connection between client and server
 */
public class SocketClientConnection implements ClientConnectionHandler, Runnable {

    /**
     * represents the server's socket used to communicate to the client
     */
    private Socket socket;

    /**
     * true if a pong was received from the client
     */
    private boolean pong;

    /**
     * represents a unique id associated to the socket
     */
    private final String socketID;

    /**
     * references the games handler
     */
    private ClientConnectionListener handler;

    /**
     * represents the input stream
     */
    private BufferedReader in;

    /**
     * represents a buffer where a new received message is stored
     */
    private NetworkBuffer buffer;

    /**
     * represents the output stream
     */
    private PrintWriter out;

    /**
     * creates a new socket connection
     * @param socket represents the server's socket
     * @param socketID represents the socket id
     * @param handler references the game handler
     */
    public SocketClientConnection(Socket socket, String socketID, ClientConnectionListener handler) {

        this.socket = socket;
        this.socketID = socketID;
        this.handler = handler;
        pong = true;

    }

    /**
     * Closes the connection between the server and the client
     */
    @Override
    public synchronized void closeConnection() {

        System.out.println("[SERVER] Closing socket connection...");
        try {
            socket.close();
            in.close();
            out.close();
            System.out.println("[SERVER] Closed socket connection.");
        } catch (IOException e) {
            System.out.println("[SERVER] Error occurred when closing socket");
        }

    }

    /**
     * sends a new message to the client
     *
     * @param message represents the message to send
     */
    @Override
    public synchronized void send(String message) {
        System.out.println(message);
        out.println(message);
        out.flush();

    }

    /**
     * handles the messages received from the client
     * @param read represents the message received
     */
    private void messageHandler(String read) {

        try {
            buffer.append(read);
        }catch (MalformedMessageException e){
            System.out.println("[SERVER] The message received is not in XML format.");
        }

        if(buffer.getPong()) pong = true;
        else if(buffer.getPing()) send("<pong/>");
        else {
            try {
                String string = buffer.get();
                handler.onReceivedMessage(string, socketID);
            } catch (EmptyBufferException e){
                System.out.println("[SERVER] The buffer is empty!");
            }
        }
    }

    /**
     * starts the ping pong protocol between server and client
     */
    private void startPingTimer() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (pong) {
                    send("<ping/>");
                    pong = false;
                } else {
                    handler.onMissingPong(socketID);
                    timer.cancel();
                    System.out.println("[SERVER] Client disconnected.");
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
            System.out.println("[SERVER] Error occurred while opening input and output streams.");

        }

        handler.addActiveConnection(socketID, this);
        startPingTimer();
        String read = "";

        try {

            while ((read = in.readLine()) != null) {

                //System.out.println("[SERVER] Received: " + read + " from: "+socketID);
                messageHandler(read);

            }
            //handler.onMissingPong(socketID);
            closeConnection();

        } catch (IOException e) {

            System.out.println("[SERVER] " + e.getMessage());
            System.out.println("[SERVER] Connection closed.");

        }
    }

}