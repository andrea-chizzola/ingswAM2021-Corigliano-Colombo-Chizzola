package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ViewObserver;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.io.*;
import java.util.*;

/**
 * this class represents a CLI. It gives all the methods to "paint" the current status of the game
 * and retrieve information from the player
 */
public class CLI implements View, SubjectView {

    //TO DO: put all the configuration offsets inside a file
    /**
     * the following attributes represent the constants used to paint the items of the CLI
     */
    private final int VIEW_VERTICAL_SIZE = 55;
    private final int DECKS_VERTICAL_SIZE = 80;
    private final int HORIZONTAL_SIZE = 200;

    private final int MARKET_X = 150;
    private final int MARKET_Y = 0;
    private int RESOURCES_Y;

    private final int STRONGBOX_X = 0;
    private final int STRONGBOX_Y = 14;

    private final int WAREHOUSE_X = 0;
    private final int WAREHOUSE_Y = 0;

    private final int FAITHTRACK_X = 20;
    private final int FAITHTRACK_Y = 0;
    private int PLAYERS_Y;

    private final int EXTRA_X = 0;
    private final int EXTRA_Y = 22;

    private final int PRODUCTION_X = 150;
    private final int PRODUCTION_Y = 0;
    private final int PERSONAL_Y = 14;

    private final int TOKEN_X = 150;
    private final int TOKEN_Y = 0;

    private final int BOXES_X = 23;

    private final int LEADER_Y = 16;

    private final int N_DECKS_X = 4;
    private final int N_DECKS_Y = 3;

    private final int END_X = 53;
    private final int END_Y = 19;

    /**
     * this attribute is a reference to the reduced model of the view
     */
    private ViewModel model;

    /**
     * this attribute is a matrix that contains the current state of the CLI
     */
    protected String[][] viewStatus;

    /**
     * this attribute represents a matrix that contains all the cards in common decks
     */
    private String[][] decksStatus;

    /**
     * this attribute represents the input stream
     */
    private InputStream in;

    /**
     * this attribute represents the output stream
     */
    private PrintStream out;

    /**
     * this attribute is used to synchronize the methods that interact with the input stream
     */
    protected final Object busyInput;

    /**
     * this attribute is true if an input is available
     */
    protected boolean availableInput;

    /**
     * this method is used to collect the Strings that will be printed
     */
    protected StringBuilder typed;

    /**
     * this attribute is used to collect the Strings that comes from the input stream
     */
    StringBuilder interaction;

    /**
     * this attribute represents an observer of the view
     */
    ViewObserver viewObserver;

    /**
     * this is the constructor of the class
     * @param model is an instance of the reduced model of the view
     */
    public CLI(ViewModel model, InputStream in, PrintStream out){
        this.model=model;
        viewStatus = new String[VIEW_VERTICAL_SIZE][HORIZONTAL_SIZE];
        decksStatus = new String[DECKS_VERTICAL_SIZE][HORIZONTAL_SIZE];
        PLAYERS_Y = (4 + 1) * (CLIPainter.getSquareLength() + 1);
        RESOURCES_Y = (model.getnRows()+1)*(CLIPainter.getSphereLength() + 1) + 3;

        this.in = in;
        this.out = out;
        availableInput = false;
        typed = new StringBuilder();
        interaction = new StringBuilder();
        busyInput = new Object();
    }

    /**
     * this method creates a thread that looks for Strings on the input Stream
     * @param input represents the input Stream
     */
    protected void inputReader(InputStream input){

        new Thread(() ->
        {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                String s = "";
                while ((s = in.readLine()) != null) {
                    addInput(s);
                    //System.out.println(s);
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("Cannot open the input stream of CLI");
                e.printStackTrace();
                //CLOSE CLIENT AND CONNECTION. CANNOT OPEN INPUT STREAM
            }
        }).start();
    }

    /**
     * this private helper is used to take an input from the Stream
     * @param s is the String coming from the Stream
     */
    protected void addInput(String s){
        synchronized(busyInput){
            typed.append(s);
            availableInput = true;
            busyInput.notifyAll();
        }
    }

    /**
     * @return the content of the input buffer
     */
    //da modificare togliendo la notifyAll. Non serve a niente.
    protected String getInput(){
        synchronized(busyInput){
            if(!availableInput){
                busyInput.notifyAll();
                try{
                    busyInput.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            availableInput=false;
            String s = typed.toString();
            typed.setLength(0);
            return s;
        }
    }

    /**
     * this method is used to print the current status of the CLI
     */
    public void plotView(){
        plot(viewStatus);
    }

    /**
     * this method is used to print the status of the shared decks
     */
    public void plotDecks(){
       plot(decksStatus);
    }

    /**
     * this helper method is used to print a matrix of Strings
     * @param target is the matrix to be printed
     */
    private void plot(String[][] target){
        for (int i = 0; i < target.length; i++) {
            System.out.println();
            for (int j = 0; j < target[i].length; j++) {
                System.out.print(target[i][j]);
            }
        }
    }

    /**
     * this method is used to initialize the state of the view
     */
    @Override
    public void initialize() {
        CLIPainter.printLogo();
        CLIPainter.fill(viewStatus, 0, 0, HORIZONTAL_SIZE, VIEW_VERTICAL_SIZE);
        CLIPainter.fill(decksStatus, 0, 0, HORIZONTAL_SIZE, DECKS_VERTICAL_SIZE);
        /*CLIPainter.paintWarehouse(viewStatus, WAREHOUSE_Y+PLAYERS_Y, WAREHOUSE_X, model.getShelves(), new LinkedList<>());
        CLIPainter.paintExtraSlots(viewStatus, EXTRA_Y+PLAYERS_Y, EXTRA_X, new LinkedList<>());
        CLIPainter.paintStrongbox(viewStatus, STRONGBOX_Y+PLAYERS_Y, STRONGBOX_X, new LinkedList<>());
        showPersonalProduction();

        int nSlots = model.getSlotNumber();
        for(int i=0; i<nSlots; i++){
            CLIPainter.devCardPainter(viewStatus, PLAYERS_Y+1, BOXES_X + 30*i+20, "EMPTY");
        }*/

        inputReader(in);
    }

    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body is the content of the message
     */
    @Override
    public void showGameStatus(boolean answer, String body, String nickname, TurnType state) {
        if(!answer){
            out.println("Error during: " + state + "\nYou've done something wrong, let's try again");
        }
        else{
            out.println("Action successfully performed. Let's proceed further.");
        }
    }

    /**
     * this method is used to show an update of the marketBoard
     * @param tray is the current state of the market tray
     */
    @Override
    public void showMarketUpdate(List<Marble> tray) {
        CLIPainter.paintMarketBoard(viewStatus, MARKET_Y, MARKET_X, tray, model.getnRows(), model.getnColumns());
        StringBuilder market = new StringBuilder();
        for(int i = 0; i<tray.size(); i++){
            market.append(tray.get(i).toString()).append(",");
        }
        out.println("The new status of the market (ordered by rows) is:\n" + market );
    }

    /**
     * this method is used to show an update of the shared decks on the GameBoard
     * @param decks contains the top cards of each deck
     */
    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {
        int width = CLIPainter.getCardWidth()+3, length = CLIPainter.getDevCardLength()+2;
        CLIPainter.fill(decksStatus, 0, 0,HORIZONTAL_SIZE, DECKS_VERTICAL_SIZE);
        for(int i : decks.keySet()){
            int row = i/N_DECKS_X,column = i%N_DECKS_X;
            DevelopmentCard card = ConfigurationParser.getDevelopmentById(model.getConfigurationFile(), decks.get(i));
            CLIPainter.devCardPainter(decksStatus, PLAYERS_Y + 1 + length*row, BOXES_X + width*column, card.toString());
        }
    }

    /**
     * this method is used to show an update of one's warehouse and stringbox
     * @param warehouse represent the current state of the warehouse
     * @param strongBox represent the current state of the strongbox
     */
    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {

        if(!nickName.equals(model.getPersonalNickname())) return;
        List<ResQuantity> resources = new LinkedList<>();
        List<ResQuantity> extra = new LinkedList<>();
        int defaultSlots = model.getSlotNumber();

        StringBuilder warehouseString = new StringBuilder();
        StringBuilder extraBoxString = new StringBuilder();
        StringBuilder strongboxString = new StringBuilder();
        for(int i=0; i<warehouse.size(); i++){
            ResQuantity r = warehouse.get(i);
            if(i<=defaultSlots) {
                resources.add(r);
                if(r.getQuantity()>0) warehouseString.append(r.toString());
                else warehouseString.append("EMPTY");
                warehouseString.append(" | ");
            }
            else {
                extraBoxString.append(r.toString()).append(" | ");
                extra.add(r);
            }
        }

        extra.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        CLIPainter.paintWarehouse(viewStatus, WAREHOUSE_Y+PLAYERS_Y, WAREHOUSE_X, model.getShelves(), resources);
        CLIPainter.paintExtraSlots(viewStatus, EXTRA_Y+PLAYERS_Y, EXTRA_X, extra);

        strongBox.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        for(ResQuantity r : strongBox){
            strongboxString.append(r.toString()).append(" / ");
        }
        CLIPainter.paintStrongbox(viewStatus, STRONGBOX_Y+PLAYERS_Y, STRONGBOX_X, strongBox);


        out.println("The new status of your boxes is:\nWarehouse: " + warehouseString);
        if(extra.size()>0) out.println("Extra shelves: " + extraBoxString);
        if(strongboxString.length()>0) out.println("StrongBox: " + strongboxString);
    }

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     */
    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickname) {
        if(!nickname.equals(model.getPersonalNickname())) return;
        for(int i : slots.keySet()){
            DevelopmentCard card = ConfigurationParser.getDevelopmentById(model.getConfigurationFile(), slots.get(i));
            CLIPainter.devCardPainter(viewStatus, PLAYERS_Y+1, BOXES_X + 30*(i-1)+20, card.toString());
        }
    }

    //devi gestire gli errori di parsing
    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     */
    @Override
    public void showLeaderCards(Map<Integer,String> cards, Map<Integer,ItemStatus> status, String nickName){
        if(!nickName.equals(model.getPersonalNickname())) return;
        int num = ConfigurationParser.getNumLeader(model.getConfigurationFile());

        for(int i=1; i<=num; i++){
            if(cards.containsKey(i)) {
                String id = cards.get(i);
                LeaderCard card = ConfigurationParser.getLeaderById(model.getConfigurationFile(), id);
                card.setStatus(status.get(i).getBoolValue());
                CLIPainter.leaderCardPainter(viewStatus, PLAYERS_Y + 1 + LEADER_Y, BOXES_X + 28 * (i - 1) + 8, card.toString());
            }
            else CLIPainter.leaderCardPainter(viewStatus, PLAYERS_Y + 1 + LEADER_Y, BOXES_X + 28 * (i - 1) + 8, "EMPTY");
        }
    }

    /**
     * this method is used to show an update of one's FaithTrack
     * @param faith is the amount of faith obtained by the players
     * @param sections is the state of the players' sections
     * @param faithLorenzo is the faith obtained by Lorenzo
     * @param sectionsLorenzo is the status of Lorenzo's sections
     */
    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections,
                                Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {

        List<String> names = model.getNicknames();
        if(faithLorenzo.isPresent() && sectionsLorenzo.isPresent()){
            faith.put("Lorenzo", faithLorenzo.get());
            sections.put("Lorenzo", new LinkedList<>(sectionsLorenzo.get()));
            names.add("Lorenzo");
            System.out.println("Debug");
        }
        PLAYERS_Y = (names.size() + 1) * (CLIPainter.getSquareLength() + 1);
        CLIPainter.paintFaithTrack (viewStatus, FAITHTRACK_Y, FAITHTRACK_X,
                model.getTrackPoints(), names, faith, sections);
    }

    /**
     * this method is used to show
     * @param action is the ID of the top token
     */
    @Override
    public void showTopToken(Optional<String> action) {
        if(action.isEmpty()) return;
        String content = ConfigurationParser.getActionTokenById(model.getConfigurationFile(), action.get()).toString();
        CLIPainter.paintToken(viewStatus,TOKEN_Y+RESOURCES_Y+PERSONAL_Y, TOKEN_X, content);
    }

    /**
     * this method is used to show the points achieved at the end of the game
     * @param players contains the name of the players and the points obtained
     */
    @Override
    public void showEndGame(Map<String, Integer> players) {
        CLIPainter.paintEndGameBox(viewStatus, END_Y, END_X, players);
        plotView();
    }

    /**
     * this method is used to show the disconnection of a player
     * @param nickname is the name of the disconnected player
     */
    @Override
    public void showDisconnection(String nickname) {
        out.println(nickname + "has been disconnected");
    }

    //MODIFY AS SOON YOU HAVE THE MESSAGES AND THE CONTROLLER
    /**
     * this method is used to add a player to the view
     */
    @Override
    public void newPlayer() {
        String player, first = "";
        String num;
        do {
            out.println("Welcome to Masters of Renaissance, before starting playing, you should give me some information:" +
                    "\nTell me who you are: ");
            player = getInput();
        }while(player.length()<=0);

        do{
            out.println("Tell me if you want to start a new game [YES/NO]: ");
            first = getAssertion(getInput());
        }while(!first.equals("true") && !first.equals("false"));

        if(first.equals("true")){
            do {
                out.println("Give me the number of players in the game: ");
                num = getInput();
            }while(!isInt(num));
        }
        else num = "0";
        model.setPersonalNickname(player);
        model.setCurrentPlayer(player);
        try {
            viewObserver.update(MessageFactory.buildConnection("Connection request", player, Boolean.parseBoolean(first), Integer.parseInt(num)));
        }catch(MalformedMessageException e){
            //CLOSE CONNECTION
        }

    }

    private String getAssertion(String assertion){
        if(assertion.equals("YES")) return "true";
        if(assertion.equals("NO")) return "false";
        else return "wrong input";
    }
    private boolean isInt(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * this method is used to catch the player's selected turn
     * @param turns is the list of available turns
     * @param player is the nickname of the current player
     */
    @Override
    public String selectTurnAction(List<String> turns, String player){
        plotView();
        StringBuilder available = new StringBuilder();
        for(String string : turns) available.append(string).append(", ");
        available.append("EXIT");
        String s = "";
        do{
            out.println("\nSelect your turn type; available turns: " + available);
            s = getInput();
            if(!turns.contains(s))
                showGameStatus(false, "Not existent turn type.", "name", model.getModelState());
        }
        while(!turns.contains(s));
        try {
            viewObserver.update(MessageFactory.buildSelectedTurn(s, "Selection of the turn type"));
        }catch(MalformedMessageException e){
            //exit from client
        }
        return s;
    }

    /**
     * this method is used to catch the LeaderCards selected by a player
     */
    //put the number of default leaders from configuration file
    @Override
    public void selectLeaderAction() {
        String action;
        String[] selections;
        Map<Integer, String> cards = model.getLeadersID(model.getCurrentPlayer());
        plotView();
        do {
            out.println("\nSelect two of your cards. Command:- position1:position2\n" +
                    "eg. type 1:2 to select cards one and two");
            action = getInput();
            selections = action.split(":");
        } while (selections.length != 2 && !isIntSequence(selections, 1) && !containsKeys(cards, selections));

        Map<Integer, ItemStatus> map = new HashMap<>();
        for(int i : cards.keySet()) map.put(i, ItemStatus.DISCARDED);
        for (String selection : selections) map.put(Integer.parseInt(selection), ItemStatus.ACTIVE);

        try{
            viewObserver.update(MessageFactory.buildLeaderUpdate(cards, map,
                    "Leader cards initialization managing.", model.getCurrentPlayer()));
        }catch(MalformedMessageException e){
            //exit from client
        }
    }

    private boolean isIntSequence(String[] sequence, int offset){
        for (int i=0; i<sequence.length; i+=offset) {
            if (!isInt(sequence[i])) return false;
        }
        return true;
    }

    private boolean containsKeys(Map<Integer,String> map, String[] sequence){
        for (String s : sequence) {
            if (!map.containsKey(s)) return false;
        }
        return true;
    }
    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    @Override
    public void selectMarketAction() {
        String s;
        out.println("Select your resources from the market. Choose wisely");
        do {
            out.println("Select a row or a column.\nCommand Type:- row:number or column:number");
            s = getInput();
        } while(!isValidMarketSelection(s));
        String[] selection = s.split(":");
        try {
            viewObserver.update(MessageFactory.buildMarketSelection(selection[0], Integer.parseInt(selection[1])
                    , "Selection of a row or a column from the market."));
        }catch (MalformedMessageException e){
            //exit from client
        }
    }

    /**
     * this helper method is used to check if a market selection is correct
     * @param s is the command to be checked
     * @return true if the command is correct, else otherwise
     */
    private boolean isValidMarketSelection(String s){

        String[] selection = s.split(":");
        if(selection.length!=2) return false;
        int n, limit;

        if(selection[0].equals("row")) limit = model.getnRows();
        else if(selection[0].equals("column")) limit = model.getnColumns();
        else return false;

        try{
            n = Integer.parseInt(selection[1]);
        }catch(NumberFormatException e){
            return false;
        }
        return n > 0 && n<limit;
    }

    /**
     * this method is used to catch the decision of a player on a selection of marbles
     */
    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {
        StringBuilder builder = new StringBuilder();

        for(int i=0; i<marblesTray.size(); i++){
            builder.append(marblesTray.get(i).toString()).append(", ");
        }
        out.println("You have selected the marbles: " + builder);

        if(whiteModifications.size()>0) {
            builder.setLength(0);
            for (Marble whiteModification : whiteModifications) {
                builder.append(whiteModification.toString()).append(", ");
            }
            out.println("The possible transformations for white marbles are: " + builder);
        }
        out.println("Type your action. Command:- MarbleColor:ACTION:TargetSlot\n " +
                "eg. MarbleBlue:INSERT:2:MarbleYellow:DISCARD:0");
        String action = getInput();
        try {
            viewObserver.update(MessageFactory.buildActionMarble(action, "Marbles managing"));
        }
        catch(MalformedMessageException e){
            //Close the client because of the error
        }
    }

    /**
     * this method is used to catch the action of a player on a LeaderCard
     */
    @Override
    public void leaderAction() {
        String action, player = model.getCurrentPlayer();
        Map<Integer,String> leadersID = model.getLeadersID(player);
        String[] sequence;
        do {
            out.println("It's time to put your leader cards in action. " +
                    "\nYou can both discard and activate your cards. Command :- number:ACTION" +
                    "\ne.g: 1:INSERT or 2:DISCARD");
            action = getInput();
            sequence = action.split(":");
        }while(sequence.length!=2 && !isInt(sequence[0]) && !leadersID.containsKey(Integer.parseInt(sequence[0])));

        int position = Integer.parseInt(sequence[0]);
        try {
            viewObserver.update(MessageFactory.buildLeaderAction(leadersID.get(position), position, sequence[1], "Action on leader"));
        }catch(MalformedMessageException e){
            //exit from client;
        }

    }

    /**
     * this method is used to catch the action of a player of a shared DevelopmentCard
     */
    @Override
    public void buyCardAction() throws MalformedMessageException {
        plotDecks();
        String[] selection;
        String action;
        String warehouse;
        String strongbox;
        String file = model.getConfigurationFile();

        out.println("\nWhat about buying a new card? The decks are ordered from the left to the right.");
        do{
            out.println("Give me the card position and the target slot. Command :- position:slot");
            action = getInput();
            selection = action.split(":");
        }while(!isCardOK(selection));
        int position = Integer.parseInt(selection[0]), slot = Integer.parseInt(selection[1]);
        String id = model.getDecks().get(position-1);
        System.out.println("Debug");
        DevelopmentCard card = ConfigurationParser.getDevelopmentById(file, id);


        out.println("Now select your resources from the warehouse.");
        warehouse = helpWarehouse();

        out.println("You can also get something from your strongbox.");
        strongbox = helpResSequence();

        viewObserver.update(MessageFactory.buildBuyCard(card.getCardColor().getColor(),
                card.getCardLevel(), slot, id,"Buy card", warehouse, strongbox));
    }

    private String helpWarehouse(){

        String action;
        String[] selection;
        do{
            out.println("Give me the resources and quantities. Command :- shelf:quantity");
            action = getInput();
            selection = action.split(":");
        }while(selection.length%2!=0 && !isIntSequence(selection, 1));

        return action;
    }

    private String helpResSequence(){
        String action;
        String[] selection;
        do{
            out.println("Give me the resources and quantities. Command :- resource:quantity");
            action = getInput();
            selection = action.split(":");
        }while(selection.length%2!=0 && !isIntSequence(selection, 2));

        return action;
    }
    /**
     * this private helper is used to check if a has been correctly selected
     * @param target the sequence to be check
     * @return true if the sequence has been correctly formed, false otherwise;
     */
    private boolean isCardOK(String[] target){

        if (target.length!=2 && !isIntSequence(target, 1)) return false;

        int position = Integer.parseInt(target[0]), selected = Integer.parseInt(target[1]);
        return model.getDecks().containsKey(position) && selected <= model.getSlotNumber();
    }

    /**
     * this method is used to catch the action of a player on their productions
     */
    @Override
    public void doProductionsAction() throws MalformedMessageException {
        String action, warehouse, strongbox, customResources, customProducts, leaders = "", developments = "";
        boolean decision;
        out.println("So you want to produce some resources... good choice. Let's start");

        do{
            out.println("Do you want to use your personal production? [YES/NO]");
            action = getAssertion(getInput());
        }while(!action.equals("true") && !action.equals("false"));

        decision = Boolean.parseBoolean(action);

        if(model.getLeadersID(model.getCurrentPlayer()).keySet().size()>1) {
            out.println("What about leader cards? Select them. Command:- card1:card2... [positions]");
            leaders = helpCards(model.getLeadersID(model.getCurrentPlayer()));
        }
        if(model.getSlots(model.getCurrentPlayer()).keySet().size()>1) {
            out.println("Don't forget your development cards! Select them. Command:- card1:card2... [positions]");
            developments = helpCards(model.getSlots(model.getCurrentPlayer()));
        }
        out.println("Select your custom resources:");
        customResources = helpResSequence();

        out.println("Select your custom products:");
        customProducts = helpResSequence();

        out.println("What is a production without some waste of resources?");
        out.println("Now select your resources from the warehouse.");
        warehouse = helpWarehouse();
        out.println("You can also get something from your strongbox.");
        strongbox = helpResSequence();

        viewObserver.update(MessageFactory.BuildDoProduction(decision,developments,
                leaders,customResources,customProducts,warehouse, strongbox, "Do production"));

    }

    private String helpCards(Map<Integer, String> cards){
        String action, file = model.getConfigurationFile();
        String[] selection;
        StringBuilder sequence = new StringBuilder();
        out.println("Select your cards. Command:- position1:position2:...");
        do{
            action = getInput();
            selection = action.split(":");
        }while(!action.isEmpty()  && !isIntSequence(selection,1) && !containsCard(selection, cards));

        if(action.isEmpty()) return action;
        for(String s : selection){
            int position = Integer.parseInt(s);
            sequence.append(position).append(":").append(cards.get(position)).append(":");
        }
        return sequence.substring(0, sequence.length()-1);
    }

    private boolean containsCard(String[] selection, Map<Integer, String> cards){
        for (String s : selection) {
            int position = Integer.parseInt(s);
            if (!cards.containsKey(position)) return false;
        }
        return true;
    }


    /**
     * this action is used to catch the resources chosen by a player
     */
    //add default initialization resources to model. Waiting Marco's push
    @Override
    public void getResourcesAction() {
        int playerPosition, number;
        String file = model.getConfigurationFile(), self = model.getPersonalNickname(), selection = "";
        playerPosition = model.getNicknames().indexOf(self);
        number = ConfigurationParser.getInitializationResources(file).get(playerPosition);

        plotView();

        if(number>0) {
            String[] sequence;
            do {
                out.println("\nLet's get some starting resources:" +
                        "\nYou can select" + number + "resources. Command:- targetSlot:resource");
                selection = getInput();
                sequence = selection.split(":");
            } while (sequence.length % 2 != 0 && isIntSequence(sequence, 2));
        }

        try{
            viewObserver.update(MessageFactory.buildSelectedResources(selection, "Selection of resources during initialization"));
        }catch(MalformedMessageException e){
            //exit from client
        }
    }


    /**
     * this method show the player's personal production.
     */
    @Override
    //add personal production materials and products to parser or ViewModel
    public void showPersonalProduction() {
        Production production = model.getPersonalProduction();
        CLIPainter.paintPersonalProduction(viewStatus, PRODUCTION_Y + RESOURCES_Y, PRODUCTION_X,
                production.getMaterials(), production.getProducts(),
                production.getCustomMaterials(), production.getCustomProducts());

    }

    /**
     * this method is used to catch a swap in the Warehouse
     */
    @Override
    public boolean swapAction() {
        String decision;
        String[] selections;
        do {
            out.println("Would you like to perform a slot swap? [YES/NO]");
            decision = getInput();
        }while(!decision.toUpperCase().equals("YES") && !decision.toUpperCase().equals("NO"));
        if(decision.toUpperCase().equals("NO")) return false;
        do{
            out.println("Select the source and the target slots for swapping. Command:- source:target");
            decision = getInput();
            selections = decision.split(":");
        }
        while(!isIntSequence(selections,1));
        try{
            viewObserver.update(MessageFactory.buildSwap(Integer.parseInt(selections[0]),Integer.parseInt(selections[1])
                    , "Swapping two shelves of the warehouse"));
        }
        catch(MalformedMessageException e){
            //exit from client
        }
        return true;
    }

    /**
     * this method is used to attach a Client to a view
     * @param observer is the observer to be attached
     */
    @Override
    public void attachObserver(ViewObserver observer) {
        this.viewObserver = observer;
    }

    /**
     * this method is used to notify a Client of an action
     * @param message is the String that represent the action
     */
    @Override
    public void notifyObserver(String message) {
        viewObserver.update(message);
    }
}
