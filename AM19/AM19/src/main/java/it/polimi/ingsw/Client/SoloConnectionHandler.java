package it.polimi.ingsw.Client;

import it.polimi.ingsw.Server.ClientConnection;

import java.util.LinkedList;

public class SoloConnectionHandler {

    /**
     * buffer for messages sent to the server
     */
    private final LinkedList<String> bufferServer;

    /**
     * buffer for messages sent to the client
     */
    private final LinkedList<String> bufferClient;

    private final Object lockBufferServer;
    private final Object lockBufferClient;

    private ServerConnection serverConnection;
    private ClientConnection clientConnection;


    public SoloConnectionHandler() {
        lockBufferClient = new Object();
        lockBufferServer = new Object();

        bufferServer = new LinkedList<>();
        bufferClient = new LinkedList<>();
    }


    public void attachServerConnection(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public void attachClientConnection(ClientConnection clientConnection){
        this.clientConnection = clientConnection;
    }

    public void notifyServer(String message){
        synchronized (lockBufferServer) {

            bufferServer.add(message);
            lockBufferServer.notifyAll();
        }
    }

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

    public void notifyClient(String message){
        synchronized (lockBufferClient) {

            bufferClient.add(message);
            lockBufferClient.notifyAll();
        }
    }


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
