package it.polimi.ingsw.CLITest;

import it.polimi.ingsw.Client.ClientController.ClientController;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.View.CLI.CLI;

import java.io.*;

public class CLI_ClientController_Test {
    private InputStream player;
    private BufferedReader server;
    private ReducedGameBoard model;
    private ClientController controller;
    private CLI cli;
    private String typing;
    private String fromServer;

    /*private class messageReceiver implements MessageSender {

        @Override
        public void sendMessage(String message) {
            System.out.println(
                            "-----------------------------------------------------\n"
                            + message +
                            "\n-----------------------------------------------------");
        }

        @Override
        public void notifyDisconnection() {
            System.out.println("Disconnection executed");
        }
    }
/*
    @Test
    public void InitializationSequenceSinglePlayer() throws IOException, InterruptedException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Initialization_Single.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/Initialization_Single.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //LOGIN COMPLETED DURING THE SETUP. UPDATE OF THE VIEWMODEL
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //END OF BEGINNING UPDATES. BEGIN OF LEADER SELECTION
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //END OF INITIALIZATION LEADERS. BEGIN OF INITIALIZATION RESOURCES
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        Thread.sleep(200);
   }


    @Test
    public void InitializationSequenceFourPlayers() throws IOException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Initialization_Four_Players.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/Initialization_Four_Players.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //LOGIN COMPLETED DURING THE SETUP. UPDATE WHILE WAITING FOR MY TURN:
        for(int i=0; i<22; i++){
            message = server.readLine();
            //System.out.println(message);
            controller.onReceivedMessage(message);
        }
        //INITIALIZATION OF MY LEADER CARDS
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //LEADER CARDS UPDATE
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //RESOURCE SELECTION
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //RESOURCES UPDATE
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);

        //FINAL PLOT TO CHECK IF UPDATES ARE CORRECT
        cli.plotView();

    }

    @Test
    public void showEndGameTest() throws IOException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Initialization_Four_Players.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/End_Game.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //ALL PHASES AND UPDATES + ENDGAME
        for(int i=0; i<27; i++){
            message = server.readLine();
            //System.out.println(message);
            controller.onReceivedMessage(message);
        }
    }

    @Test
    public void multiplayerMarketTest() throws IOException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Multiplayer_Market.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/Multiplayer_Market.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //INITIALIZATION OF 2 PLAYERS + UPDATES. The board has been initialized so that Davide starts the game with
        //two extra resources in the warehouse and Davide has two possible white marbles transformations
        for(int i=0; i<16; i++){
            message = server.readLine();
            //System.out.println(message);
            controller.onReceivedMessage(message);
        }
        //DAVIDE SELECTS HIS TURN. HE CHOOSES TO TAKE SOMETHING FROM MARKETBOARD
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);

        //FIRST, DAVIDE PERFORMS A SWAP
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //UPDATE OF WAREHOUSE
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //DAVIDE PERFORMS ANOTHER SWAP
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //UPDATE OF WAREHOUSE
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //DAVIDE STOPS DOING SWAPS
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //DAVIDE SELECTS ROW 2 OF THE MARKET AND RECEIVE 4 MARBLES. HE PUT THEM INSIDE THE WAREHOUSE
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //THE INSERTION IS NOT CORRECT. DAVIDE RECEIVE AN ERROR. HE HAS TO PERFORM THE ACTION AGAIN
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        //THE OPERATION IS PERFORMED. THE MODEL IS UPDATED
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        //System.out.println(message);
        controller.onReceivedMessage(message);

        cli.plotView();


        //MARCO SELECTS FROM MARKET


    }

    @Test
    public void multiplayerBuyCardTest() throws IOException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Multiplayer_Buy_Card.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/Multiplayer_Buy_Card.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //INITIALIZATION OF 2 PLAYERS + UPDATES. The board has been initialized so that Davide starts the game with
        //extra resources in the warehouse and strongbox
        for(int i=0; i<16; i++){
            message = server.readLine();
            System.out.println(message);
            controller.onReceivedMessage(message);
        }

        //DAVIDE SELECTS HIS TURN. HE CHOOSES TO BUY A CARD
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //DAVIDE CHOOSES HIS CARD.
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //UPDATE OF DAVIDE's SLOTS
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        cli.plotView();
    }


    @Test
    public void multiplayerManageLeadersTest() throws IOException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Multiplayer_Manage_Leader.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/Multiplayer_Manage_Leader.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //INITIALIZATION OF 2 PLAYERS + UPDATES. The board has been initialized so that Davide starts the game with
        //extra resources in the warehouse and strongbox
        for(int i=0; i<16; i++){
            message = server.readLine();
            System.out.println(message);
            controller.onReceivedMessage(message);
        }

        //DAVIDE SELECTS HIS TURN. HE CHOOSES TO MANAGE HIS LEADER CARDS
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //DAVIDE CHOOSES TO ACTIVATE HIS FIRST LEADER CARD AND DISCARD THE SECOND
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //UPDATE OF DAVIDE's CARDS
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        cli.plotView();
    }


    @Test
    public void multiplayerDoProduction() throws IOException {
        //SetUp of the Input streams
        typing = "src/test/java/it/polimi/ingsw/CLITest/Input_Simulation/Multiplayer_Do_Production.txt";
        fromServer = "src/test/java/it/polimi/ingsw/CLITest/Server_Simulation/Multiplayer_Do_Production.txt";
        player = new FileInputStream(typing);
        server =new BufferedReader(new InputStreamReader(new FileInputStream(fromServer)));
        String message;

        //SetUp of the ViewModel, CLI, and CLIController
        model = new ReducedGameBoard("defaultConfiguration.xml");
        cli = new CLI(model, player, System.out);
        cli.attachObserver(new messageReceiver());
        controller = new ClientController(model, cli);

        //START OF THE LOGIN
        controller.runController();

        //INITIALIZATION OF 2 PLAYERS + UPDATES. The board has been initialized so that Davide starts the game with
        //extra resources in the warehouse and strongbox
        for(int i=0; i<16; i++){
            message = server.readLine();
            System.out.println(message);
            controller.onReceivedMessage(message);
        }

        //DAVIDE SELECTS HIS TURN. HE CHOOSES TO USE HIS PRODUCTIONS AND DEV CARDS
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //DAVIDE CHOOSES TO ACTIVATE HIS FIRST TWO DEVELOPMENT CARDS, HIS FIRST LEADER CARD, AND HIS PERSONAL PRODUCTION
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        //UPDATE OF DAVIDE'S RESOURCES
        message = server.readLine();
        System.out.println(message);
        controller.onReceivedMessage(message);

        cli.plotView();
    }

*/

}
