package it.polimi.ingsw.Client;

import it.polimi.ingsw.Server.ClientConnectionHandler;

import java.util.LinkedList;

/**
 * handles the communication between client and pretended server
 */
public class SoloConnectionHandler {

    /**
     * buffer for messages sent to the pretended server
     */
    private final LinkedList<String> bufferServer;

    /**
     * buffer for messages sent to the client
     */
    private final LinkedList<String> bufferClient;

    /**
     * lock acquired to add a new message directed to the pretended server
     */
    private final Object lockBufferServer;

    /**
     * lock acquired to add a new message directed to the client
     */
    private final Object lockBufferClient;

    /**
     * reference to the connection from client to the pretended server
     */
    private ServerConnectionHandler serverConnectionHandler;

    /**
     * reference to the connection from the pretended server to client
     */
    private ClientConnectionHandler clientConnection;

    /**
     * creates a new solo connection handler
     */
    public SoloConnectionHandler() {
        lockBufferClient = new Object();
        lockBufferServer = new Object();

        bufferServer = new LinkedList<>();
        bufferClient = new LinkedList<>();
    }

    /**
     * attaches the selected server connection
     * @param serverConnectionHandler represents the connection from client to the pretended server
     */
    public void attachServerConnection(ServerConnectionHandler serverConnectionHandler){
        this.serverConnectionHandler = serverConnectionHandler;
    }

    /**
     * attaches the selected client connection
     * @param clientConnection represents the connection from the pretended server to client
     */
    public void attachClientConnection(ClientConnectionHandler clientConnection){
        this.clientConnection = clientConnection;
    }

    /**
     * Adds a new message to the buffer containing the messages directed to the pretended server
     * @param message represents the added message
     */
    public void notifyServer(String message){
        synchronized (lockBufferServer) {

            bufferServer.add(message);
            lockBufferServer.notifyAll();
        }
    }

    /**
     *
     * @return the least recent message among the ones directed to the pretended server
     */
    public String readBufferServer(){
        synchronized (lockBufferServer){
            while (bufferServer.isEmpty()){
                try {
                    lockBufferServer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bufferServer.removeFirst();
        }
    }

    /**
     * Adds a new message to the buffer containing the messages directed to the client
     * @param message represents the added message
     */
    public void notifyClient(String message){
        synchronized (lockBufferClient) {

            bufferClient.add(message);
            lockBufferClient.notifyAll();
        }
    }

    /**
     *
     * @return the least recent message among the ones directed to the client
     */
    public String readBufferClient(){
        synchronized (lockBufferClient){
            while (bufferClient.isEmpty()){
                try {
                    lockBufferClient.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bufferClient.removeFirst();
        }
    }
}
