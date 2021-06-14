package it.polimi.ingsw.Server;

import it.polimi.ingsw.Client.SoloConnectionHandler;


public class SoloClientConnection implements ClientConnection, Runnable{

    /**
     * represents the solo connection handler which manages the messages exchanged in case of a local match
     */
    private SoloConnectionHandler socket;

    /**
     * represents the id associated to the local player
     */
    private final String socketID;

    /**
     * represents the client controller
     */
    private ConnectionListener handler;


    public SoloClientConnection(SoloConnectionHandler socket, String socketID, ConnectionListener handler) {

        this.socket = socket;
        this.socketID = socketID;
        this.handler = handler;
    }


    /**
     * Closes the connection between the pretended server and the client
     */
    @Override
    public void closeConnection() {
        System.exit(0);
    }


    /**
     * sends a new message to the client
     * @param message represents the message sent
     */
    @Override
    public void send(String message) {
        socket.notifyClient(message);
    }

    /**
     * manages pretended server-client connection and communication
     */
    @Override
    public void run() {
        handler.addActiveConnection(socketID, this);
        while (true){
            handler.onReceivedMessage(socket.readBufferServer(),socketID);
        }
    }
}
