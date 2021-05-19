package it.polimi.ingsw.CLITest;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleBlue;
import it.polimi.ingsw.Model.MarketBoard.MarbleGray;
import it.polimi.ingsw.Model.MarketBoard.MarbleRed;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.CLI;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

public class CLITest {
    private static CLI cli;
    private ReducedGameBoard model;
    private List<String> players;

    @BeforeEach
    public void initializeTest(){
        players = new LinkedList<>();
        players.add("test1");
        players.add("test2");
        model = new ReducedGameBoard("defaultConfiguration.xml");
        model.initializeNicknames(players);
        cli = new CLI(model, System.in, System.out);
        cli.initialize();
        System.out.println(" ");
    }

    @Test
    public void mainSightTest(){
        Map<String, Integer> faith = new HashMap<>();
        Map<String, List<ItemStatus>> sections = new HashMap<>();
        model.setPersonalNickname("test1");

        faith.put("test1", 1);
        faith.put("test2", 19);
        List<ItemStatus> test1 = new LinkedList<>();
        test1.add(ItemStatus.ACTIVE);
        test1.add(ItemStatus.ACTIVE);
        test1.add(ItemStatus.DISCARDED);
        List<ItemStatus> test2 = new LinkedList<>();
        test2.add(ItemStatus.DISCARDED);
        test2.add(ItemStatus.INACTIVE);
        test2.add(ItemStatus.DISCARDED);
        sections.put("test1", test1);
        sections.put("test2", test2);
        cli.showFaithUpdate(faith, sections, Optional.empty(), Optional.empty());

        List<ResQuantity> warehouse = new LinkedList<>();
        warehouse.add(new ResQuantity(new Coin(), 1));
        warehouse.add(new ResQuantity(new Faith(), 1));
        warehouse.add(new ResQuantity(new Stone(), 2));
        warehouse.add(new ResQuantity(new Coin(),5));
        warehouse.add(new ResQuantity(new Stone(),3));

        List<ResQuantity> strongbox = new LinkedList<>();
        strongbox.add(new ResQuantity(new Coin(),5));
        strongbox.add(new ResQuantity(new Stone(),3));
        strongbox.add(new ResQuantity(new Servant(),8));
        strongbox.add(new ResQuantity(new Shield(),4));

        cli.showBoxes(warehouse, strongbox, "test1");

        List<Marble> marbles = new LinkedList<>();
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleRed());
        marbles.add(new MarbleGray());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleRed());
        marbles.add(new MarbleGray());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleRed());
        marbles.add(new MarbleGray());
        marbles.add(new MarbleGray());

        cli.showPersonalProduction();
        cli.showMarketUpdate(marbles);

        List<DevelopmentCard> cardsD = ConfigurationParser.parseDevelopmentCard("defaultConfiguration.xml");
        List<LeaderCard> cardsL = ConfigurationParser.parseLeaderCard("defaultConfiguration.xml");
        List<Action> tokens = ConfigurationParser.parseActionTokens("defaultConfiguration.xml");

        Map<Integer, String> slots = new HashMap<>();
        slots.put(1, cardsD.get(5).getId());
        slots.put(2, cardsD.get(1).getId());
        slots.put(3, cardsD.get(2).getId());
        cli.showSlotsUpdate(slots, "test1");

        Map<Integer, String> leaders = new HashMap<>();
        Map<Integer, ItemStatus> status = new HashMap<>();
        status.put(1, ItemStatus.ACTIVE);
        status.put(2, ItemStatus.INACTIVE);
        leaders.put(1, "1");
        leaders.put(2, "2");
        cli.showLeaderCards(leaders, status, "test1");

        cli.showTopToken(Optional.of("1"));

        cli.plotView();

        leaders.put(3, "3");
        leaders.put(4, "4");
        status.put(3, ItemStatus.ACTIVE);
        status.put(4, ItemStatus.INACTIVE);
        cli.showLeaderCards(leaders, status, "test1");
        cli.plotView();
    }

    /*private static String[][] viewStatus;

    private static InputStream in;
    private static PrintStream out;
    private static Object busyView;
    private static Object busyInput;
    private static boolean availableUpdate;
    //private boolean availableReply;
    private static boolean availableInteraction;
    private static boolean availableInput;
    static StringBuilder typed;
    //StringBuilder reply;
    static StringBuilder interaction;

    public static void main(String[] args) throws InterruptedException {*/

        /*in = System.in;
        out = System.out;
        availableInput = false;
        availableInteraction = false;
        //availableReply = false;
        availableUpdate = false;
        typed = new StringBuilder();
        //reply = new StringBuilder();
        interaction = new StringBuilder();
        busyView = new Object();
        busyInput = new Object();
        viewStatus = new String[2][2];
        viewStatus[0][0] = "1";
        viewStatus[1][0] = "2";
        viewStatus[0][1] = "3";
        viewStatus[1][1] = "4";*

        //serve un altro mutex per lo stream di input. Il thread aggiunge
        //stringhe a typed. Per estrarre devo essere sicuro di essere sincronizzato.
        //soluzione: aggiungere 2 sottometod sincronizzati su un nuovo mutex,
        //uno per prendere da stream (da mettere nel thread) e uno per prendere l'informazione
        //da mettere nei metodi action della view.
        //Ci vuole anche un metodo che aggiorni availableReply e availableUpdate che sia sincronizzato.
        //magari puoi usare di nuovo busyView come mutex. Non credo ne serva un altro


       /* inputReader(in);
        //outputWriter(out);

        out.println("Input Test: ");
        out.println("Type something: ");
        out.println(getInput());
        out.println("Type again: ");
        out.println(getInput());*/

        /*out.println("We are done with the input, let's try with the output");
        out.println("Let's to a test output print");
        completedUpdate();
        Thread.sleep(1000);
        out.println("Did it write an horrible board?");
        out.println("What about writing a command?");
        addInteraction("CommandTest");
        Thread.sleep(1000);
        out.println("All ok? Did you see it?");
        out.println("Let's put the two together");
        completedUpdate();
        addInteraction("CommandTest");
        Thread.sleep(1000);
        out.println("Did you pass the output test?");*/

        /*out.println("Let's try again, this time without sleeping");
        out.println("Let's do a test output print");
        completedUpdate();
        out.println("Did it write an horrible board?");
        out.println("What about writing a command?");
        addInteraction("CommandTest");
        out.println("All ok? Did you see it?");
        out.println("Let's put the two together");
        completedUpdate();
        addInteraction("CommandTest");
        out.println("Did you pass the output test?");*/

        /*addInteraction("Probably, my words and the tests have been mixed a bit.");
        addInteraction("Now we will use only the new two threads for output.");
        addInteraction("Let's try again, this time without sleeping");
        addInteraction("Let's do a test output print");
        completedUpdate();
        addInteraction("Did it write an horrible board?");
        addInteraction("What about writing a command?");
        addInteraction("CommandTest");
        addInteraction("All ok? Did you see it?");
        addInteraction("Let's put the two together");
        completedUpdate();
        addInteraction("CommandTest");
        addInteraction("Did you pass the output test?");*/

        /*addInteraction("Now it's time to check the threads altogether.");
        addInteraction("Type something: ");
        addInteraction(getInput());
        addInteraction("Let's do a test output print");
        completedUpdate();
        addInteraction("Did it write an horrible board?");
        addInteraction("What about writing a command?");
        addInteraction("CommandTest");
        addInteraction("Type again: ");
        addInteraction(getInput());
        addInteraction("Let's put the two together");
        completedUpdate();
        addInteraction("CommandTest");
        addInteraction("Did you pass the output test?");



    }*/

    /*private static void inputReader(InputStream input){

        new Thread(() ->
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            try {
                String s = "";
                while ((s = in.readLine()) != null) {
                    //System.out.println(s);
                    addInput(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //qui sarebbe utile terminare il client e chiudere la connessione.
                //Devi chiudere anche lo stream di input.
            }
        }).start();
    }

    private static void addInput(String s) {
        synchronized (busyInput) {
            typed.append(s);
            availableInput = true;
            busyInput.notifyAll();
        }
    }

    private static String getInput() {
        synchronized (busyInput) {
            if (!availableInput) {
                busyInput.notifyAll();
                try {
                    busyInput.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            availableInput = false;
            String s = typed.toString();
            typed.setLength(0);
            return s;
        }
    }

    /*private static void outputWriter(OutputStream out) {

        new Thread(() ->
        {
            while (true) {
                synchronized (busyView) {
                    if (!isAvailableUpdate()) {
                        busyView.notifyAll();
                        try {
                            busyView.wait();
                        } catch (InterruptedException e) {
                            //Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    printUpdates();
                }
            }
        }).start();
    }

    private static boolean isAvailableUpdate() {
        return (availableUpdate || availableInteraction);
    }

    private static void printUpdates() {
        if (availableInteraction)
            // availableReply = false;
            printInteraction();
        if (availableUpdate)
            //availableUpdate = false;
            plot();
    }

    private static void printInteraction() {
        if (availableInteraction) {
            out.println(interaction.toString());
            interaction.setLength(0);
            availableInteraction = false;
        }
    }

    private static void addInteraction(String s) {
        synchronized (busyView) {
            if(availableInteraction) {
                busyView.notifyAll();
                try {
                    busyView.wait();
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            interaction.append(s);
            availableInteraction = true;
            busyView.notifyAll();
        }
    }

    private static void completedUpdate() {
        synchronized (busyView) {
            if(availableUpdate) {
                busyView.notifyAll();
                try {
                    busyView.wait();
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            availableUpdate = true;
            busyView.notifyAll();
        }
    }


    public static void plot() {
        if (availableUpdate) {
            for (int i = 0; i < viewStatus.length; i++) {
                System.out.println();
                for (int j = 0; j < viewStatus[i].length; j++) {
                    out.println(viewStatus[i][j]);
                }
            }
            availableUpdate = false;
        }
    }*/
}
