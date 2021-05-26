package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.View.GUI.GUI;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Client implements MessageSender {

    private static final String IP_ARG = "-ip";
    private static final String PORT_ARG = "-port";
    private static final String GUI_ARG = "--gui";
    private static final String CLI_ARG = "--cli";

    private final String file = "defaultConfiguration.xml";
    private SocketServerConnection connection;
    private String ip;
    private int port;
    private boolean useCli;
    private ClientController clientController;

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

    @Override
    public void firstMessage(String message) {
        establishConnection();
        connection.send(message);
    }

    private void establishConnection(){
        try {
            Socket socket = new Socket(ip, port);

            System.out.println("[CLIENT] Connecting to server...");
            System.out.println("[CLIENT] Connected to server on port " + port);
            connection = new SocketServerConnection(socket, clientController,this);
            new Thread(connection).start();

        } catch (IOException e) {

            System.err.println("[CLIENT] Connection error. Unavailable sever");
            e.printStackTrace();

        }
    }

    /**
     * this method is used to notify the willingness of the player to disconnect
     */
    /*@Override
    public void notifyDisconnection() {
        connection.closeConnection();
    }*/

    public void startClient(){

        ReducedGameBoard reducedModel = new ReducedGameBoard(file);

        if(useCli){
            CLI cli = new CLI(reducedModel, System.in, System.out);
            clientController = new ClientController(reducedModel, cli, this);
            cli.attachInteractionObserver(clientController);
            clientController.runController();
            cli.launch();
        }else{
            GUI gui = new GUI();
            clientController = new ClientController(reducedModel, gui, this);
            clientController.runController();
            gui.attachInteractionObserver(clientController);
            GUI.main(null);
        }
    }

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
            port = Integer.parseInt(argList.get(portIndex));

            if(port < 1024 || port > 49151){
                port = DEFAULT_PORT;
                System.out.println("[CLIENT] Selected port is unavailable. The connection will be established with default settings.");
            }else System.out.println("[CLIENT] Port selected correctly.");

        }
        if (argList.contains(GUI_ARG)) {
            cli = false;
            System.out.println("[CLIENT] Client will be initialized using GUI settings.");
        }else if (argList.contains(CLI_ARG)) {
            cli = true;
            System.out.println("[CLIENT] Client will be initialized using CLI settings.");
        }

        Client client = new Client(ip, port, cli);
        client.startClient();

    }

}
