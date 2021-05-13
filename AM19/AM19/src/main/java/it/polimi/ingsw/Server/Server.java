package it.polimi.ingsw.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Handles the game setup and all incoming connections
 */
public class Server {

    /**
     * represents the port opened by the server to accept new connections
     */
    private final int port;

    /**
     * represents the server socket
     */
    private ServerSocket serverSocket;

    /**
     * contains a thread pool to manage all the clients connecting to the server
     */
    private ExecutorService executor;

    private AtomicLong idCounter;

    /**
     * keeps track of the active connections associating them to a unique ID
     */
    private Map<String, ClientConnection> activeConnections;

    /**
     * creates a new server accepting clients to the port selected
     * @param port represents the port opened by the server to accept new connections
     */
    public Server(int port) {

        this.port = port;
        idCounter = new AtomicLong();
        activeConnections = new HashMap<>();

    }

    /**
     * adds a new connection to the active ones
     * @param id represents the id associated to the connection
     * @param connection represents the client's socket connection
     */
    public synchronized void addConnection(String id, ClientConnection connection){
        activeConnections.put(id, connection);
    }

    /**
     *
     * @param id represents the id associated to the connection
     * @return returns the connection associated to the selected id
     */
    public ClientConnection getConnectionById(String id){
        return activeConnections.get(id);
    }

    /**
     *
     * @return returns a new Id associated to the client's connection
     */
    public String createId(){
        return String.valueOf(idCounter.getAndIncrement());
    }

    /**
     * Initializes and starts the server
     */
    public void startServer(){

        executor = Executors.newCachedThreadPool();

        try {

            serverSocket = new ServerSocket(port);

        } catch (IOException e) {

            System.err.println("[SERVER] Connection error. Unavailable port");
            e.printStackTrace();

        }

        System.out.println("[SERVER] Server ready on port " + port);

        while(true){

            try {

                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] Accepted new connection");
                executor.submit(new SocketClientConnection(socket, this, createId()));

            } catch (IOException e) {

                System.err.println("[SERVER] Connection error. Closing socket");
                break;

            }

        }

        executor.shutdown();

    }

    public static void main(String[] args ){

        final int DEFAULT_PORT = 1234;
        int port = DEFAULT_PORT;

        System.out.println("[SERVER] Masters of Renaissance server. Welcome!");

        if(args.length > 0) {

            port = Integer.parseInt(args[1]);
            if(port < 1024 || port > 49151){
                port = DEFAULT_PORT;
                System.out.println("[SERVER] Selected port is unavailable. The server will be initialized with default settings.");
            }else System.out.println("[SERVER] Port selected correctly.");

        }

        System.out.println("[SERVER] Initializing server...");

        Server server = new Server(port);
        server.startServer();

    }
    
}
