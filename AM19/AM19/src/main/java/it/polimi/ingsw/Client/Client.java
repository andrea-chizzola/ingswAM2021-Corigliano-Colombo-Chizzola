package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.ClientController.ClientController;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Server.Server;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GUIHandler;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * handles the game setup client side
 */
public class Client implements MessageSender {

    private static final String IP_ARG = "-ip";
    private static final String PORT_ARG = "-port";
    private static final String GUI_ARG = "--gui";
    private static final String CLI_ARG = "--cli";

    /**
     * represents the connection to the server (or pretended server in case of a local match)
     */
    private ServerConnectionHandler connection;

    /**
     * represents the IP address associated to the server
     */
    private String ip;

    /**
     * represents the port number associated to the server
     */
    private int port;

    /**
     * true if the client selected to start the game using CLI interface, false otherwise
     */
    private boolean useCli;

    /**
     * references the client controller
     */
    private ClientController clientController;

    /**
     * creates a new client given the selected parameters
     * @param ip represents the server's ip
     * @param port represents the server's port
     * @param useCli true if the player wants to play using the CLI interface, false to play using GUI
     */
    public Client(String ip, int port, boolean useCli) {
        this.ip = ip;
        this.port = port;
        this.useCli = useCli;
    }

    /**
     * this method is used to send a String message to a Client
     *
     * @param message is the content of the message
     */
    @Override
    public void sendMessage(String message) {
        connection.send(message);
    }


    /**
     *  manages the first message exchanged in case of a local match
     * @param message is the content of the message
     */
    @Override
    public void firstMessageSolo(String message) {
        establishConnectionSolo();
        connection.send(message);
    }

    /**
     * this method is used to close the client
     */
    @Override
    public void close() {
        connection.closeConnection();
        System.exit(0);
    }

    /**
     * creates the necessary components to allow the message exchange in case of a local match
     */
    private void establishConnectionSolo(){
        SoloConnectionHandler socket = new SoloConnectionHandler();
        SoloServerConnectionHandler soloConnection = new SoloServerConnectionHandler(socket,clientController);
        connection = soloConnection;
        new Thread(soloConnection).start();
        socket.attachServerConnection(connection);
        Server server = new Server(socket);
        server.startServerSolo();
    }

    /**
     * manages the first message exchanged between Client and Server
     *
     * @param message is the content of the message
     */
    @Override
    public void firstMessage(String message) {
        establishConnection();
        connection.send(message);
    }

    /**
     * establishes the connection to the server
     */
    private void establishConnection(){
        try {
            Socket socket = new Socket(ip, port);

            System.out.println("[CLIENT] Connecting to server...");
            System.out.println("[CLIENT] Connected to server on port " + port);
            SocketServerConnection serverConnection = new SocketServerConnection(socket, clientController);
            connection = serverConnection;
            new Thread(serverConnection).start();

        } catch (IOException e) {

            System.out.println("[CLIENT] Connection error. Unavailable sever");
            System.exit(0);

        }
    }

    /**
     * instantiates client's components depending on the parameters passed to the main
     */
    public void startClient(){

        ReducedGameBoard reducedModel = new ReducedGameBoard("defaultConfiguration.xml");

        if(useCli){
            CLI cli = new CLI(reducedModel, System.in, System.out);
            clientController = new ClientController(reducedModel, cli, this);
            cli.attachInteractionObserver(clientController);
            clientController.runController();
            cli.launch();
        }else{
            GUI gui = new GUI(reducedModel);
            GUIHandler.setInstanceReference(gui, reducedModel);
            clientController = new ClientController(reducedModel, gui, this);
            clientController.runController();
            gui.attachInteractionObserver(clientController);
            GUIHandler.main(null);
        }
    }

    /**
     * manages the initialization of the client
     * @param args contains the initialization information required to start a new client
     */
    public static void main(String[] args){

        final String DEFAULT_IP = "127.0.0.1";
        final int DEFAULT_PORT = 1234;
        String ip = DEFAULT_IP;
        int port = DEFAULT_PORT;
        boolean cli = true;

        System.out.println("[CLIENT] Masters of Renaissance client. Welcome!");

        List<String> argList = Arrays.asList(args);

        if (argList.contains(IP_ARG) && argList.contains(PORT_ARG)) {

            int ipIndex = argList.indexOf(IP_ARG) + 1;
            ip = argList.get(ipIndex);

            int portIndex = argList.indexOf(PORT_ARG) + 1;

            try {
                port = Integer.parseInt(argList.get(portIndex));
            } catch (NumberFormatException e){
                System.out.println("[CLIENT] Invalid port format. The client will be initialized with default settings.");
                port = DEFAULT_PORT;
            }

            if(port < 1024 || port > 49151){
                port = DEFAULT_PORT;
                System.out.println("[CLIENT] Selected port is unavailable. The connection will be established with default settings.");
            }else System.out.println("[CLIENT] Port selected correctly.");

        }
        if (argList.contains(GUI_ARG)) {
            cli = false;
            System.out.println("[CLIENT] Client will be initialized using GUI settings.");
        }else if (argList.contains(CLI_ARG)) {
            System.out.println("[CLIENT] Client will be initialized using CLI settings.");
        }

        Client client = new Client(ip, port, cli);
        client.startClient();

    }

}
