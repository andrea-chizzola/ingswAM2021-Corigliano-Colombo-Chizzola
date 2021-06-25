package it.polimi.ingsw.Server;



import it.polimi.ingsw.Client.SoloConnectionHandler;
import it.polimi.ingsw.Controller.GamesHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Handles the game setup and all incoming connections
 */
public class Server {

    /**
     * represents the parameter used to set a port
     */
    private static final String PORT_ARG = "-port";

    /**
     * represents the port opened by the server to accept new connections
     */
    private final int port;

    /**
     * represents the server socket
     */
    private ServerSocket serverSocket;

    /**
     * represents the GamesHandler
     */
    private ClientConnectionListener handler;

    /**
     * contains a thread pool to manage all the clients connecting to the server
     */
    private ExecutorService executor;

    /**
     * represents a progressive number associated to a socket connection
     */
    private AtomicLong idCounter;

    /**
     * represents the SoloConnectionHandler
     */
    private SoloConnectionHandler socket;


    /**
     * creates a new server accepting clients to the port selected
     * @param port represents the port opened by the server to accept new connections
     */
    public Server(int port) {
        this.port = port;
        idCounter = new AtomicLong();
        this.handler = new GamesHandler();

    }

    /**
     * creates a new server with a SoloConnectionHandler
     * @param socket represents the connection handler
     */
    public Server(SoloConnectionHandler socket){
        this.port = 1234;
        idCounter = new AtomicLong();
        this.handler = new GamesHandler();
        this.socket = socket;
    }


    /**
     * Initializes and starts the server (solo game)
     */
    public void startServerSolo(){
        SoloClientConnection connection = new SoloClientConnection(socket,createId(),handler);
        new Thread(connection).start();
        socket.attachClientConnection(connection);
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

            System.out.println("[SERVER] Connection closed. Unavailable port");
            e.printStackTrace();

        }

        System.out.println("[SERVER] Server ready on port " + port);

        while(true){

            try {

                Socket socket = serverSocket.accept();
                String id = createId();
                System.out.println("[SERVER] Accepted new connection (ID = "+ id + ")");
                executor.submit(new SocketClientConnection(socket, id, handler));

            } catch (IOException e) {

                System.out.println("[SERVER] Connection closed. Closing socket.");
                break;

            }

        }

        executor.shutdown();

    }

    /**
     * manages the initialization of the server
     * @param args contains the initialization information required to start a new server
     */
    public static void main(String[] args ){

        final int DEFAULT_PORT = 1234;
        int port = DEFAULT_PORT;

        System.out.println("[SERVER] Masters of Renaissance server. Welcome!");

        List<String> argList = Arrays.asList(args);

        if(argList.contains(PORT_ARG)) {

            int portIndex = argList.indexOf(PORT_ARG) + 1;
            port = Integer.parseInt(argList.get(portIndex));

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
