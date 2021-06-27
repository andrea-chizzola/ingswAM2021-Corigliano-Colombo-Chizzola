package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Client.ClientController.InteractionObserver;
import it.polimi.ingsw.Client.ReducedModel.ReducedBoard;
import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Exceptions.IllegalIDException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Boards.TurnType;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.InteractionTranslator.*;
import it.polimi.ingsw.View.PlayerInteractions.*;
import it.polimi.ingsw.View.SubjectView;
import it.polimi.ingsw.View.View;

import java.io.*;
import java.util.*;

/**
 * this class represents a CLI. It gives all the methods to "paint" the current status of the game
 * and retrieve information from the player
 */
public class CLI implements View, SubjectView {

    /**
     * the following attributes represent the constants used to paint the items of the CLI
     */
    private final int VIEW_VERTICAL_SIZE = 55;
    private final int DECKS_VERTICAL_SIZE = 55;
    private final int HORIZONTAL_SIZE = 200;
    private final int VERTICAL_SIZE = 32;

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

    private final int END_X = 53;
    private final int END_Y = 19;

    private BuildMessage builder;
    private InteractionTranslator interactionTranslator;

    /**
     * this attribute is a reference to the reduced model of the view
     */
    private final ReducedGameBoard model;

    /**
     * this attribute is a matrix that contains the current state of the CLI
     */
    protected String[][] viewStatus;

    /**
     * this attribute is a matrix that contains the current state of the players board
     */
    private final Map<String, String[][]> playersBoard;

    /**
     * this attribute represents a matrix that contains all the cards in common decks
     */
    private final String[][] decksStatus;

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
    private final Object busyInput;

    /**
     * this attribute is true if an input is available
     */
    private boolean availableInput;

    /**
     * this method is used to collect the Strings that will be printed
     */
    private StringBuilder typed;

    /**
     * this attribute is used to collect the Strings that comes from the input stream
     */
    StringBuilder interaction;

    /**
     * this attribute represents an observer of the interactions of a player
     */
    InteractionObserver interactionObserver;

    /**
     * this is the constructor of the class
     * @param model is an instance of the reduced model of the view
     */
    public CLI(ReducedGameBoard model, InputStream in, PrintStream out){
        this.model=model;
        viewStatus = new String[VIEW_VERTICAL_SIZE][HORIZONTAL_SIZE];
        decksStatus = new String[DECKS_VERTICAL_SIZE][HORIZONTAL_SIZE];
        RESOURCES_Y = (model.getConfiguration().getnRows()+1)*(CLIPainter.getSphereLength() + 1) + 3;

        this.in = in;
        this.out = out;
        availableInput = false;
        typed = new StringBuilder();
        interaction = new StringBuilder();
        busyInput = new Object();
        playersBoard = new HashMap<>();
        inputReader(in);

        CLIPainter.printLogo();
    }

    /**
     * this method creates a thread that looks for Strings on the input Stream
     * @param input represents the input Stream
     */
    private void inputReader(InputStream input){

        new Thread(() ->
        {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                String s;
                while ((s = in.readLine()) != null) {
                    manageInput(s);
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("\nCannot open the input stream of CLI");
                notifyParsingError();
            }
        }).start();
    }

    /**
     * this method is used to manage the input of a player
     * @param s is the input String
     */
    private void manageInput(String s){
        if(s.length()>=10 && s.equals("DISCONNECT")) {
            notifyDisconnection();
            return;
        }
        if(s.length() >=5 && s.startsWith("SEE")){
            showOthers(s.substring(4));
            return;
        }
        if(s.length() >= 4 && s.startsWith("UNDO") && isUndoPossible()){
            notifyInteraction(new UndoInteraction());
        }
        addInput(s);
    }

    /**
     * this helper method is used to check if an UNDO may be performed
     * @return true if the UNDO can be performed
     */
    private boolean isUndoPossible(){
        TurnType state = model.getModelState();
        return (state != TurnType.INITIALIZATION_LEADERS) &&
                (state != TurnType.INITIALIZATION_RESOURCE) &&
                (state != TurnType.MANAGE_MARBLE);
    }

    /**
     * this private helper is used to take an input from the Stream
     * @param s is the String coming from the Stream
     */
    private void addInput(String s){
        synchronized(busyInput){
            typed.append(s);
            availableInput = true;
            busyInput.notifyAll();
        }
    }

    /**
     * this helper method is used to notify a disconnection
     */
    private void notifyDisconnection(){
        try {
            notifyInteraction(MessageFactory.buildDisconnection(
                    "I want to be disconnected", model.getPersonalNickname()));
        } catch (MalformedMessageException e) {
            System.out.println("\nParsing error... closing the connection");
            notifyParsingError();
        }
    }

    /**
     * @return the content of the input buffer
     */
    private String getInput(){
        synchronized(busyInput){
            while(!availableInput){
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
     * this method is used to plot the personal board of another player
     * @param nickname is the name of the target player
     */
    public void plotOthers(String nickname){
        String[][] target = playersBoard.get(nickname);
        plot(target);
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
        System.out.println("\n");
    }

    /**
     * this method is used to initialize the state of the view
     */
    @Override
    public void initialize() {
        List<Integer> shelves = model.getConfiguration().getShelves();
        List<String> players = model.getNicknames();
        int nSlots = model.getConfiguration().getSlotNumber();
        int nPlayers = players.size();
        String self = model.getPersonalNickname();
        PLAYERS_Y = (((nPlayers == 1)? 2 : nPlayers) + 1) * (CLIPainter.getSquareLength() + 1);

        for(String name : players) {
            String[][] view = new String[VERTICAL_SIZE + PLAYERS_Y][HORIZONTAL_SIZE];
            playersBoard.put(name, view);
            CLIPainter.fill(view, 0, 0, HORIZONTAL_SIZE, VERTICAL_SIZE + PLAYERS_Y);
            CLIPainter.paintWarehouse(view, WAREHOUSE_Y + PLAYERS_Y, WAREHOUSE_X, shelves, new LinkedList<>());
            CLIPainter.paintExtraSlots(view, EXTRA_Y + PLAYERS_Y, EXTRA_X, new LinkedList<>());
            CLIPainter.paintStrongbox(view, STRONGBOX_Y + PLAYERS_Y, STRONGBOX_X, new LinkedList<>());
            showPersonalProduction(name);

            for (int i = 0; i < nSlots; i++)
                CLIPainter.devCardPainter(view, PLAYERS_Y + 1, BOXES_X + 30 * i + 20, "EMPTY");
        }
        CLIPainter.fill(decksStatus, 0, 0, HORIZONTAL_SIZE, DECKS_VERTICAL_SIZE);
        viewStatus = playersBoard.get(self);
    }

    @Override
    public void reply(boolean answer, String body, String nickName) {
        if(!answer) {
            out.println("Error:" + body + "Something seems wrong... What about trying again?");
            return;
        }
        out.println("Action successfully performed. Let's wait for the game to start...");

    }

    /**
     * this method is used to show a message
     * @param body is the content of the message
     */
    @Override
    public void showGameStatus(String body, String nickname, TurnType state) {
        out.println(body);
    }

    /**
     * this method is used to show an update of the marketBoard
     * @param tray is the current state of the market tray
     */
    @Override
    public void showMarketUpdate(List<Marble> tray) {
        int nRows = model.getConfiguration().getnRows(), nColumns = model.getConfiguration().getnColumns();
        CLIPainter.paintMarketBoard(viewStatus, MARKET_Y, MARKET_X, tray, nRows, nColumns);
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
            int row = i/N_DECKS_X, column = i%N_DECKS_X;
            try {
                DevelopmentCard card = model.getConfiguration().getDevelopmentCard(decks.get(i));
                CLIPainter.devCardPainter(decksStatus, 1 + length*row, BOXES_X + width*column, card.toString());
            } catch (IllegalIDException e) {
                System.out.println("Parsing failure! Card ID: " + decks.get(i) + " not found!");
            }
        }
    }

    /**
     * this method is used to show an update of one's warehouse and strongbox
     * @param warehouse represent the current state of the warehouse
     * @param strongBox represent the current state of the strongbox
     */
    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {

        String[][] view = playersBoard.get(nickName);
        List<ResQuantity> resources = new LinkedList<>();
        List<ResQuantity> extra = new LinkedList<>();
        int defaultSlots = model.getConfiguration().getSlotNumber();

        for(int i=0; i<warehouse.size(); i++){
            ResQuantity r = warehouse.get(i);
            if(i<=defaultSlots) {
                resources.add(r);
            }
            else {
                extra.add(r);
            }
        }

        extra.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        CLIPainter.paintWarehouse(view, WAREHOUSE_Y+PLAYERS_Y, WAREHOUSE_X, model.getConfiguration().getShelves(), resources);
        CLIPainter.paintExtraSlots(view, EXTRA_Y+PLAYERS_Y, EXTRA_X, extra);
        CLIPainter.paintStrongbox(view, STRONGBOX_Y+PLAYERS_Y, STRONGBOX_X, strongBox);
    }

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     */
    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickname) {
        String[][] view = playersBoard.get(nickname);
        for(int i : slots.keySet()){
            try {
                DevelopmentCard card = model.getConfiguration().getDevelopmentCard(slots.get(i));
                CLIPainter.devCardPainter(view, PLAYERS_Y+1, BOXES_X + 30*(i-1)+20, card.toString());
            } catch (IllegalIDException e) {
                System.out.println("Parsing failure! Card ID: "+slots.get(i)+" not found!");
            }
        }
    }


    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     */
    @Override
    public void showLeaderCards(Map<Integer,String> cards, Map<Integer,ItemStatus> status, String nickName){
        String[][] view = playersBoard.get(nickName);
        int num = model.getConfiguration().getNumLeader();

        for(int i=1; i<=num; i++){
            if(cards.containsKey(i)) {
                String id = cards.get(i);
                try {
                    LeaderCard card = model.getConfiguration().getLeaderCard(id);
                    card.setStatus(status.get(i).getBoolValue());
                    CLIPainter.leaderCardPainter(view, PLAYERS_Y + 1 + LEADER_Y, BOXES_X + 28 * (i - 1) + 8, card.toString());
                } catch (IllegalIDException e) {
                    System.out.println("Parsing failure! Card ID: "+id+" not found!");
                }
            }
            else CLIPainter.leaderCardPainter(view, PLAYERS_Y + 1 + LEADER_Y, BOXES_X + 28 * (i - 1) + 8, "EMPTY");
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
        List<Integer> start = model.getConfiguration().getSectionsStart(),
                end = model.getConfiguration().getSectionsEnd(),
                points = model.getConfiguration().getSectionsPoints(),
                faithTrack = model.getConfiguration().getTrackPoints();
        List<String> names = model.getNicknames();
        if (faithLorenzo.isPresent() && sectionsLorenzo.isPresent()) {
            faith.put("Lorenzo", faithLorenzo.get());
            sections.put("Lorenzo", new LinkedList<>(sectionsLorenzo.get()));
            names.add("Lorenzo");
        }

        for(String name : playersBoard.keySet()) {
            String[][] view = playersBoard.get(name);
            CLIPainter.paintFaithTrack(view,  FAITHTRACK_Y, FAITHTRACK_X, faithTrack, names, faith, start, end);
            CLIPainter.paintPopeFavours(view,  FAITHTRACK_Y, FAITHTRACK_X, names.size(), faithTrack, points);
            CLIPainter.paintSectionsStatus(view,  FAITHTRACK_Y, FAITHTRACK_X, sections, names);
        }
    }

    /**
     * this method is used to show
     * @param action is the ID of the top token
     */
    @Override
    public void showTopToken(Optional<String> action) {
        if(action.isEmpty()) return;
        try {
            String content = model.getConfiguration().getActionTokenCard(action.get()).toString();
            CLIPainter.paintToken(viewStatus,TOKEN_Y+RESOURCES_Y+PERSONAL_Y, TOKEN_X, content);
        } catch (IllegalIDException e) {
            System.out.println("Parsing failure! TopToken ID: "+action.get()+" not found!");
        }
    }

    /**
     * this method is used to show the points achieved at the end of the game
     * @param players contains the name of the players and the points obtained
     * @param winner contains the name of the winner
     */
    @Override
    public void showEndGame(Map<String, Integer> players, String winner) {
        CLIPainter.paintEndGameBox(viewStatus, END_Y, END_X, players, winner);
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

    /**
     * this method is used to add a player to the game
     */
    public void launch() {
        String player, first, reconnect = "";
        String num;
        do {
            out.println("Welcome to Masters of Renaissance, before starting playing, you should give me some information:" +
                    "\nTell me who you are: ");
            player = getInput();
        }while(player.length()<=0);


        do{
            out.println("Tell me if you want to start a solo game [YES/NO]: ");
            first = getAssertion(getInput());
        }while(!first.equals("true") && !first.equals("false"));
        if(first.equals("true")){
            notifyNickname(player);
            try {
                notifySoloInteraction(MessageFactory.buildConnection("Connection request", player, true, 1));
            }catch(MalformedMessageException e){
                System.out.println("Parsing error... closing the connection");
                notifyParsingError();
            }
            return;
        }


        do{
            out.println("Tell me if you want to start a new game [YES/NO]: ");
            first = getAssertion(getInput());
        }while(!first.equals("true") && !first.equals("false"));

        if(first.equals("true")){
            do {
                out.println("Give me the number of players in the game: ");
                num = getInput();
            }while(!isInt(num) || Integer.parseInt(num)<=0 || Integer.parseInt(num)>4);
        }
        else num = "0";

        if(first.equals("false")){
            do{
                out.println("Do you want to reconnect to an existing game? [YES/NO]");
                reconnect = getAssertion(getInput());
            }while(!reconnect.equals("true") && !reconnect.equals("false"));
        }

        notifyNickname(player);

        try {
            if(reconnect.equals("true"))
                notifyInteraction(MessageFactory.buildReconnection("Reconnection request", player));
            else
                notifyInteraction(MessageFactory.buildConnection("Connection request", player, Boolean.parseBoolean(first), Integer.parseInt(num)));
        }catch(MalformedMessageException e){
            System.out.println("Parsing error... closing the connection");
            notifyParsingError();
        }

    }

    /**
     * this method is used to check whether an assertion is correct
     * @param assertion is String containing an assertion of the type "YES" or "NO"
     * @return the String "true" if the assertion is "YES", "false" is the assertion is "NO", and "wrong input" otherwise
     */
    private String getAssertion(String assertion){
        if(assertion.equals("YES")) return "true";
        if(assertion.equals("NO")) return "false";
        else return "wrong input";
    }

    /**
     * this method is used to check if a String is a correct representation of an integer
     * @param s is the String to be checked
     * @return return true if the String is a correct representation of an integer, false otherwise
     */
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
    public void showAvailableTurns(List<String> turns, String player){
        plotView();
        StringBuilder available = new StringBuilder();
        for(String string : turns) available.append(string).append(", ");
        available.append("SHOW_DECKS;");
        String s;
        do{
            out.println("Select your turn type; available turns: " + available);
            s = getInput();
            if(isCancelledAction(s)) return;

            if(s.equals("SHOW_DECKS")) plotDecks();
            else if(!turns.contains(s)) out.println("Not existent turn type.");
        }
        while(!turns.contains(s));
        actionMapper(s);
    }

    /**
     * this helper method is used to perform a player action basing on their input
     * @param action is the action selected by the player
     */
    private void actionMapper(String action){
        switch(action){
            case("BUY_CARD"): {
                notifyInteraction(new BuyCardInteraction());
                break;
            }
            case("DO_PRODUCTION"): {
                notifyInteraction(new DoProductionInteraction());
                break;
            }
            case("MANAGE_LEADER"): {
                notifyInteraction(new ManageLeaderInteraction());
                break;
            }
            case("TAKE_RESOURCES"): {
                notifyInteraction(new TakeResourcesInteraction());
                break;
            }
            case("SWAP"): {
                notifyInteraction(new SwapInteraction());
                break;
            }
            case("EXIT"): {
                try {
                    notifyInteraction(MessageFactory.buildExit("End of turn selection"));
                } catch (MalformedMessageException e) {
                    System.out.println("Parsing error... closing the connection");
                    notifyParsingError();
                }
                break;
            }
        }
    }

    /**
     * this method is used to catch the LeaderCards selected by a player
     */
    @Override
    public void selectLeaderAction() {
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildLeaderUpdate();
        String currentPlayer = model.getCurrentPlayer();
        ReducedBoard board = model.getBoard(currentPlayer);

        String action;
        String[] selections;
        Map<Integer, String> cards = board.getLeadersID();
        plotView();
        do {
            out.println("Select two of your cards. Command:- position1:position2\n" +
                    "eg. type 1:2 to select cards one and two");
            action = getInput();
            selections = action.split(":");
        } while (selections.length != 2 && !isIntSequence(selections, 1) && !containsKeys(cards, selections));

        interactionTranslator.setLeaderCards(action);
        notifyInteraction(builder.buildMessage(interactionTranslator));
    }

    /**
     * this method is used to check if an array of String is a correct representation of sequence of integer
     * @param sequence is the array to be checked
     * @param offset is the offset between the strings in the array
     * @return true if the array is a correct representation of a sequence of integer with the given offset
     */
    private boolean isIntSequence(String[] sequence, int offset){
        for (int i=0; i<sequence.length; i+=offset) {
            if (!isInt(sequence[i])) return false;
        }
        return true;
    }

    /**
     * this method is used to check if the KeySet of a map contains all the String in an array of Strings
     * @param map is the map to be checked
     * @param sequence is the sequence to be checked
     * @return true if the map contains all the Strings, false otherwise
     */
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
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildMarketSelection();
        String s;
        out.println("Select your resources from the market. Choose wisely");
        do {
            out.println("Select a row or a column.\nCommand Type:- row:number or column:number");
            s = getInput();
            if(isCancelledAction(s)) return;
        } while(!isValidMarketSelection(s));
        String[] selection = s.split(":");
        interactionTranslator.setMarketTray(selection[0]);
        interactionTranslator.setMarketNumber(Integer.parseInt(selection[1]));

        notifyInteraction(builder.buildMessage(interactionTranslator));
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

        if(selection[0].toUpperCase().equals("ROW")) limit = model.getConfiguration().getnRows();
        else if(selection[0].toUpperCase().equals("COLUMN")) limit = model.getConfiguration().getnColumns();
        else return false;

        try{
            n = Integer.parseInt(selection[1]);
        }catch(NumberFormatException e){
            return false;
        }
        return n > 0 && n<=limit;
    }

    /**
     * this method is used to catch the decision of a player on a selection of marbles
     */
    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildActionMarble();

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<marblesTray.size(); i++){
            stringBuilder.append(marblesTray.get(i).toString()).append(", ");
        }
        out.println("You have selected the marbles: " + stringBuilder);

        if(whiteModifications.size()>0) {
            stringBuilder.setLength(0);
            for (Marble whiteModification : whiteModifications) {
                stringBuilder.append(whiteModification.toString()).append(", ");
            }
            out.println("The possible transformations for white marbles are: " + stringBuilder);
        }
        out.println("Type your action. Command:- MarbleColor:ACTION:TargetSlot\n " +
                "eg. MarbleBlue:INSERT:2:MarbleYellow:DISCARD:0");
        String action = getInput();

        interactionTranslator.setMarblesActions(action);
        notifyInteraction(builder.buildMessage(interactionTranslator));
    }

    /**
     * this method is used to catch the action of a player on a LeaderCard
     */
    @Override
    public void leaderAction() {
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildLeaderAction();

        String action, player = model.getCurrentPlayer();
        ReducedBoard board = model.getBoard(player);

        Map<Integer,String> leadersID = board.getLeadersID();
        String[] sequence;
        int position;

        do {
            out.println("It's time to put your leader cards in action. " +
                    "\nYou can both discard and activate your cards. Command :- number:ACTION" +
                    "\ne.g: 1:INSERT or 2:DISCARD");
            action = getInput();
            if(isCancelledAction(action)) return;
            sequence = action.split(":");
        }while(sequence.length!=2 || !isInt(sequence[0]) || !leadersID.containsKey(Integer.parseInt(sequence[0])));

        position = Integer.parseInt(sequence[0]);

        interactionTranslator.setLeaderCard(position);
        interactionTranslator.setAction(sequence[1]);

        notifyInteraction(builder.buildMessage(interactionTranslator));
    }

    /**
     * this method is used to catch the action of a player of a shared DevelopmentCard
     */
    @Override
    public void buyCardAction(){
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildBuyCard();

        plotDecks();
        String[] selection;
        String action;

        out.println("What about buying a new card? The decks are ordered from the left to the right.");
        do{
            out.println("Give me the card position and the target slot. Command :- position:slot");
            action = getInput();
            if(isCancelledAction(action)) return;
            selection = action.split(":");
        }while(!isCardOK(selection));
        int position = Integer.parseInt(selection[0]), slot = Integer.parseInt(selection[1]);

        interactionTranslator.setPosition(position);
        interactionTranslator.setSlot(slot);

        out.println("Now select your resources from the warehouse.");
        String warehouse = helpWarehouse();
        if(isCancelledAction(warehouse)) return;
        interactionTranslator.setWarehouse(warehouse);

        out.println("You can also get something from your strongbox.");
        String strongbox = helpResSequence();
        if(isCancelledAction(strongbox)) return;
        interactionTranslator.setStrongbox(strongbox);

        notifyInteraction(builder.buildMessage(interactionTranslator));
    }

    /**
     * this helper method is used to build a selection in the warehouse
     * @return a String that represents the selection
     * or a String that represents the cancellation of the action
     */
    private String helpWarehouse(){

        String action;
        String[] selection;
        do{
            out.println("Give me the resources and quantities. Command :- shelf:quantity or press ENTER to skip");
            action = getInput();
            if(isCancelledAction(action)) return "UNDO";
            selection = action.split(":");
        }while(!action.isEmpty() && (selection.length%2!=0 && !isIntSequence(selection, 1)));

        return action;
    }

    /**
     * this helper method is used to build a selection in the strongbox
     * @return a String that represents the selection
     * or a String that represents the cancellation of the action
     */
    private String helpResSequence(){
        String action;
        String[] selection;
        do{
            out.println("Give me the resources and quantities. Command :- resource:quantity  or press ENTER to skip");
            action = getInput();
            if(isCancelledAction(action)) return "UNDO";
            selection = action.split(":");
        }while(!action.isEmpty() && (selection.length%2!=0));

        return action;
    }
    /**
     * this private helper is used to check if a has been correctly selected
     * @param target the sequence to be check
     * @return true if the sequence has been correctly formed, false otherwise;
     */
    private boolean isCardOK(String[] target){

        if (target.length!=2 || !isIntSequence(target, 1)) return false;

        int position = Integer.parseInt(target[0]), selected = Integer.parseInt(target[1]);
        Map<Integer, String> decks = model.getDecks();
        int nSlots = model.getConfiguration().getSlotNumber();
        return decks.containsKey(position) && selected <= nSlots;
    }

    /**
     * this method is used to catch the action of a player on their productions
     */
    @Override
    public void doProductionsAction(){
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildDoProduction();
        String currentPlayer = model.getCurrentPlayer(), selection;
        ReducedBoard board = model.getBoard(currentPlayer);

        String action, leaders, developments;

        out.println("So you want to produce some resources... good choice. Let's start");

        do{
            out.println("Do you want to use your personal production? [YES/NO]");
            selection = getInput();
            if(isCancelledAction(selection)) return;
            action = getAssertion(selection);
        }while(!action.equals("true") && !action.equals("false"));

        if(Boolean.parseBoolean(action))
            interactionTranslator.setPersonalProduction();

        if(board.getLeadersID().keySet().size()>=1) {
            out.println("What about leader cards? Select them. Command:- card1:card2... [positions]");
            leaders = helpCards(board.getLeadersID());
            if(isCancelledAction(leaders)) return;
            interactionTranslator.setLeaderCards(leaders);
        }
        if(board.getSlots().keySet().size()>=1) {
            out.println("Don't forget your development cards! Select them. Command:- card1:card2... [positions]");
            developments = helpCards(board.getSlots());
            if(isCancelledAction(developments)) return;
            interactionTranslator.setDevelopmentCards(developments);
        }

        out.println("Select your custom materials:");
        selection = helpResSequence();
        if(isCancelledAction(selection)) return;
        interactionTranslator.setChosenMaterials(selection);

        out.println("Select your custom products:");
        selection = helpResSequence();
        if(isCancelledAction(selection)) return;
        interactionTranslator.setChosenProducts(selection);

        out.println("What is a production without some waste of resources?");
        out.println("Now select your resources from the warehouse.");
        selection = helpWarehouse();
        if(isCancelledAction(selection)) return;
        interactionTranslator.setWarehouse(selection);

        out.println("You can also get something from your strongbox.");
        selection = helpResSequence();
        if(isCancelledAction(selection)) return;
        interactionTranslator.setStrongbox(selection);

        notifyInteraction(builder.buildMessage(interactionTranslator));
    }

    /**
     * this helper method is used to select a list of cards. The cards must be contained in the map cards
     * @param cards is a map of positions and cards IDs
     * @return a String that represents the selected cards
     */
    private String helpCards(Map<Integer, String> cards){
        String action;
        String[] selection;

        do{
            out.println("Select your cards. Command:- position1:position2:... or ENTER to skip");
            action = getInput();
            if(isCancelledAction(action)) return "UNDO";
            selection = action.split(":");
        }while(!action.isEmpty()  && (!isIntSequence(selection,1) || !containsCard(selection, cards)));

        if(selection.length == 0) return "";
        return action;
    }

    /**
     * this helper method checks if an array of Strings is a correct
     * sequence of integer and if the KeySet of a map
     * contains all these integers
     * @param selection is the array to be checked
     * @param cards is the map to be checked
     * @return true if the map contains all the integers represented by the Strings in the array
     */
    private boolean containsCard(String[] selection, Map<Integer, String> cards){
        try {
            for (String s : selection) {
                int position = Integer.parseInt(s);
                if (!cards.containsKey(position)) return false;
            }
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }
        return true;
    }


    /**
     * this method is used to catch the resources chosen by a player
     */
    @Override
    public void getResourcesAction() {
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildSelectedResources();

        int playerPosition, number, players;
        String self = model.getPersonalNickname(), selection = "";
        playerPosition = model.getNicknames().indexOf(self);
        number = model.getConfiguration().getInitialResources().get(playerPosition);
        players = model.getNicknames().size();

        if(players!=1) plotView();

        if(number>0) {
            String[] sequence;
            do {
                out.println("Let's get some starting resources:" +
                        "\nYou can select" + number + "resources. Command:- targetSlot:resource");
                selection = getInput();
                sequence = selection.split(":");
            } while (sequence.length % 2 != 0 && isIntSequence(sequence, 2));
        }
        interactionTranslator.setInitResources(selection);

        notifyInteraction(builder.buildMessage(interactionTranslator));
    }


    /**
     * this method show the player's personal production.
     */
    public void showPersonalProduction(String nickname) {
        Production production = model.getConfiguration().getPersonalProduction();
        String[][] view = playersBoard.get(nickname);
        CLIPainter.paintPersonalProduction(view, PRODUCTION_Y + RESOURCES_Y, PRODUCTION_X,
                production.getMaterials(), production.getProducts(),
                production.getCustomMaterials(), production.getCustomProducts());

    }

    /**
     * this method is used to catch a swap in the Warehouse
     */
    @Override
    public void swapAction() {
        interactionTranslator = new InteractionTranslator(model);
        builder = new BuildSwap();

        String decision;
        String[] selections;

        do{
            out.println("Select the source and the target slots for swapping. Command:- source:target");
            decision = getInput();
            if(isCancelledAction(decision)) return;
            selections = decision.split(":");
        }
        while(!isIntSequence(selections,1));

        interactionTranslator.setSwap(Integer.parseInt(selections[0]));
        interactionTranslator.setSwap(Integer.parseInt(selections[1]));

        notifyInteraction(builder.buildMessage(interactionTranslator));
    }

    /**
     * this method is used to show the board of a player (different from the current one)
     * @param nickname is the nickname of the target player
     */
    @Override
    public void showOthers(String nickname) {
        if(!playersBoard.containsKey(nickname)){
            out.println("The player may not be logged in.");
            return;
        }
        plotOthers(nickname);
        out.println("\n");
    }

    /**
     * this method is used to undo the player's action
     */
    @Override
    public void undoAction(){
        String self = model.getPersonalNickname();
        List<String> turns = model.getAvailableTurns();
        showAvailableTurns(turns, self);
    }

    /**
     * this helper method is used to check if an action has been cancelled
     * @param target is a String representing the action.
     * @return true if the action has been canceled
     */
    private boolean isCancelledAction(String target){
        return target.startsWith("UNDO");
    }

    /**
     * this method is used to attach a ClientController to a view
     * @param observer is the observer to be attached
     */
    @Override
    public void attachInteractionObserver(InteractionObserver observer) {
        this.interactionObserver = observer;
    }

    /**
     * this method is used to notify a performed interaction
     * @param interaction is the notified interaction
     */
    @Override
    public void notifyInteraction(PlayerInteraction interaction) {
        interactionObserver.updateInteraction(interaction);
    }

    /**
     * this method is used to notify a performed interaction
     * @param message is the representation of the interaction
     */
    @Override
    public void notifyInteraction(String message) {
        interactionObserver.updateInteraction(message);
    }

    /**
     * this method is used to notify the SOLO game
     *
     * @param message is the representation of the interaction
     */
    @Override
    public void notifySoloInteraction(String message) {
        interactionObserver.updateInteractionSolo(message);
    }

    /**
     * this method is used to notify the nickname selected by a player to the observers
     * @param nickname is the name chosen by the players
     */
    @Override
    public void notifyNickname(String nickname) {
        interactionObserver.updatePersonalNickname(nickname);
    }

    /**
     * this method is used to notify a parsing error to the observer
     */
    @Override
    public void notifyParsingError(){
        interactionObserver.close();
    }
}
