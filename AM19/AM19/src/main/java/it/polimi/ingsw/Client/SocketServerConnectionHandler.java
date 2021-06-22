package it.polimi.ingsw.Client;

import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.NetworkBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles client side the connection between client and server
 */
public class SocketServerConnectionHandler implements ServerConnectionHandler,Runnable{

    /**
     * represents the client's socket used to communicate to the server
     */
    private Socket socket;

    /**
     * true if a pong was received from the server
     */
    private boolean pong;

    /**
     * references the client controller
     */
    private ServerConnectionListener clientController;

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
     * @param socket represents the client's socket
     * @param clientController references the client controller
     */
    public SocketServerConnectionHandler(Socket socket, ServerConnectionListener clientController){

        this.socket = socket;
        this.clientController = clientController;
        pong = true;

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            buffer = new NetworkBuffer();
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e){

            e.printStackTrace();
            System.out.println("[CLIENT] Error occurred while opening input and output streams.");

        }

    }

    /**
     * Closes the connection between client and server
     */
    @Override
    public void closeConnection() {

        System.out.println("[CLIENT] Closing socket connection...");
        try {
            socket.close();
            in.close();
            out.close();
            System.out.println("[CLIENT] Closed socket connection.");
        } catch (IOException e) {
            System.out.println("[CLIENT] Error occurred when closing socket");
        }
        System.exit(0);

    }

    /**
     * sends a new message to the server
     *
     * @param message represents the message sent
     */
    @Override
    public void send(String message) {

        out.println(message);
        out.flush();

    }

    /**
     * starts the ping pong protocol between client and server
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
                    System.out.println("[CLIENT] No pong received. Client will now be disconnected.");
                    clientController.onMissingPong();
                    closeConnection();
                    timer.cancel();
                }

            }
        };
        timer.scheduleAtFixedRate(task, 0, 15000);

    }

    /**
     * handles the messages received from the server
     * @param read represents the message received
     */
    private void messageHandler(String read){

        try {
            buffer.append(read);
        }catch (MalformedMessageException e){
            System.out.println("[CLIENT] The message received is not in XML format.");
        }

        if(buffer.getPong()) pong = true;
        else if(buffer.getPing()) send("<pong/>");
        else {
            try {
                clientController.onReceivedMessage(buffer.get());
            } catch (EmptyBufferException e){
                System.out.println("[CLIENT] The buffer is empty!");
            }
        }

    }

    /**
     * manages client-server connection and communication
     */
    @Override
    public void run() {

        startPingTimer();
        String read = "";

        try {
            while ((read = in.readLine()) != null){

                //System.out.println("[CLIENT] Received: " + read);
                messageHandler(read);

            }
            closeConnection();

        }catch (IOException e){

            System.out.println(e.getMessage());
            System.out.println("[CLIENT] Connection closed.");

        }

    }
}
