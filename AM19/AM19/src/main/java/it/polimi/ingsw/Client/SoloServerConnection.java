package it.polimi.ingsw.Client;

import it.polimi.ingsw.Messages.ClientConnectionListener;

import java.io.IOException;

public class SoloServerConnection implements ServerConnection{


    private ClientConnectionListener clientController;
    private SoloConnectionHandler socket;


    public SoloServerConnection(SoloConnectionHandler socket, ClientConnectionListener clientController, Client client){

        this.socket = socket;
        this.clientController = clientController;

    }


    /**
     * Closes the connection between client and server
     */
    @Override
    public void closeConnection() {
        System.out.println("[CONNECTION] Closing socket connection...");
        System.exit(0);
    }

    /**
     * sends a new message to the server
     *
     * @param message represents the message sent
     */
    @Override
    public void send(String message) {
        socket.notifyServer(message);
    }


    /**
     * sends a new message to the sever asynchronously
     *
     * @param message represents the message sent
     */
    @Override
    public void asyncSend(String message) {

    }

    /**
     * manages client-server connection and communication
     */
    @Override
    public void run() {
        while (true){
            String message = socket.readBufferClient();
            clientController.onReceivedMessage(message);
        }
    }
}
