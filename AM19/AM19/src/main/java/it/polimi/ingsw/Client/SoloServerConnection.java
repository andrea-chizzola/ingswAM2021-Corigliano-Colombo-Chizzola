package it.polimi.ingsw.Client;

public class SoloServerConnection implements ServerConnection,Runnable{

    /**
     * represents the client controller
     */
    private ConnectionListener clientController;

    /**
     * represents the solo connection handler which manages the messages exchanged in case of a local match
     */
    private SoloConnectionHandler socket;


    public SoloServerConnection(SoloConnectionHandler socket, ConnectionListener clientController){

        this.socket = socket;
        this.clientController = clientController;

    }


    /**
     * Closes the connection between client and the pretended server
     */
    @Override
    public void closeConnection() {
        System.out.println("[CONNECTION] Closing socket connection...");
        System.exit(0);
    }

    /**
     * sends a new message to the pretended server
     *
     * @param message represents the message sent
     */
    @Override
    public void send(String message) {
        socket.notifyServer(message);
    }

    /**
     * manages client-pretended server connection and communication
     */
    @Override
    public void run() {
        while (true){
            String message = socket.readBufferClient();
            clientController.onReceivedMessage(message);
        }
    }
}
