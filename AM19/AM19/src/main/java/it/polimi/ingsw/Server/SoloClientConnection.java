package it.polimi.ingsw.Server;

import it.polimi.ingsw.Client.SoloConnectionHandler;


public class SoloClientConnection implements ClientConnection, Runnable{


    private SoloConnectionHandler socket;
    private final String socketID;
    private ConnectionListener handler;


    public SoloClientConnection(SoloConnectionHandler socket, String socketID, ConnectionListener handler) {

        this.socket = socket;
        this.socketID = socketID;
        this.handler = handler;
    }


    /**
     * Closes the connection between the server and the client
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
     * manages server-client connection and communication
     */
    @Override
    public void run() {
        handler.addActiveConnection(socketID, this);
        while (true){
            handler.onReceivedMessage(socket.readBufferServer(),socketID);
        }
    }
}
