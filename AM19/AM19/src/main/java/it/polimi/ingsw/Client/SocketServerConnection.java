package it.polimi.ingsw.Client;

import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.NetworkBuffer;
import it.polimi.ingsw.Messages.ClientConnectionListener;

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
public class SocketServerConnection implements ServerConnection, Runnable{

    private Socket socket;
    private Client client;
    private boolean pong;
    private ClientConnectionListener clientController;
    private BufferedReader in;
    private NetworkBuffer buffer;
    private PrintWriter out;

    public SocketServerConnection(Socket socket, ClientConnectionListener clientController, Client client){

        this.socket = socket;
        this.client = client;
        this.clientController = clientController;
        pong = true;

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            buffer = new NetworkBuffer();
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e){

            e.printStackTrace();
            System.err.println("[CLIENT] Error occurred while opening input and output streams.");

        }

    }

    /**
     * Closes the connection between client and server
     */
    @Override
    public void closeConnection() {

        System.out.println("[CONNECTION] Closing socket connection...");
        try {
            socket.close();
            in.close();
            out.close();
            System.out.println("[CONNECTION] Closed socket connection.");
        } catch (IOException e) {
            System.err.println("[CONNECTION] Error when closing socket");
        }
        System.exit(0);

    }

    /**
     * @return returns the socket's listener
     */
    @Override
    public ClientConnectionListener getListener() {
        return clientController;
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
     * sends a new message to the sever asynchronously
     *
     * @param message represents the message sent
     */
    @Override
    public void asyncSend(String message) {

        new Thread(() -> send(message)).start();

    }

    /**
     * starts the ping pong protocol between client and server
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
        else if(buffer.getPing()) asyncSend("<pong/>");
        else {
            try {
                clientController.onReceivedMessage(buffer.get());
            } catch (EmptyBufferException e){
                System.out.println("[SERVER] The buffer is empty!");
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

            System.err.println(e.getMessage());
            System.err.println("[CLIENT] Connection error.");

        }

    }
}
