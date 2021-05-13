package it.polimi.ingsw.Client;

import it.polimi.ingsw.Messages.ClientConnectionListener;
import it.polimi.ingsw.View.CLI;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.ViewModel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client implements ViewObserver{

    private static final String IP_ARG = "-ip";
    private static final String PORT_ARG = "-port";
    private static final String GUI_ARG = "--gui";
    private static final String CLI_ARG = "--cli";

    private final String file = "defaultConfiguration.xml";
    private SocketServerConnection connection;
    private String ip;
    private int port;
    private boolean useCli;
    private View ui;

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
    public void update(String message) {
        connection.send(message);
    }

    public void startClient(){


        ViewModel viewModel = new ViewModel(file, new ArrayList<>()); //DA CAMBIARE IL COSTRUTTORE DELLA VIEWMODEL
        String player = ""; //viewModel.getName();  DIVENTA String player = viewModel.getName(); CON METODO GETNAME DA AGGIUNGERE

        /*
        if(useCli){
            ui = new CLI(viewModel);
        }else ui = new GUI();
        */
        ClientConnectionListener clientController = new ClientController(viewModel, ui);

        ui.initialize();


        try {

            Socket socket = new Socket(ip, port);
            System.out.println("[CLIENT] Connected to server on port " + port);
            connection = new SocketServerConnection(socket, clientController, player, this);
            new Thread(connection).start();

        } catch (IOException e) {

            System.err.println("[CLIENT] Connection error. Unavailable sever");
            e.printStackTrace();

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

        System.out.println("[CLIENT] Connecting to server...");

        Client client = new Client(ip, port, cli);
        client.startClient();

    }

}
