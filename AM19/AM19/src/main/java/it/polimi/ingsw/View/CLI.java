package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ViewObserver;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
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
    private final int VERTICAL_SIZE = 200;
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

    private final int END_X = 5;
    private final int END_Y = 5;

    /**
     * this attribute is a reference to the reduced model of the view
     */
    private ViewModel model;

    /**
     * this attribute is a matrix that contains the current state of the CLI
     */
    private String[][] viewStatus;

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
    private final Object busyInput;

    /**
     * this attribute is true if an input is available
     */
    private boolean availableInput;

    /**
     * this method is used to collect the Strings that will be printed
     */
    StringBuilder typed;

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
    public CLI(ViewModel model){
        this.model=model;
        viewStatus = new String[VERTICAL_SIZE][HORIZONTAL_SIZE];
        decksStatus = new String[VERTICAL_SIZE][HORIZONTAL_SIZE];
        PLAYERS_Y = (model.getNicknames().size() + 1) * (CLIPainter.getSquareLength() + 1);
        RESOURCES_Y = (model.getnRows()+1)*(CLIPainter.getSphereLength() + 1) + 3;
        initialize();

        in = System.in; //se cambi l'input stream (permetti di settarlo come parametro della cli) puoi fare i
                        //test del client controller usando i file.
        out = System.out;
        availableInput = false;
        typed = new StringBuilder();
        interaction = new StringBuilder();
        busyInput = new Object();
    }

    /**
     * this method creates a thread that looks for Strings on the input Stream
     * @param input represents the input Stream
     */
    private void inputReader(InputStream input){

        new Thread(() ->
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    addInput(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //CLOSE CLIENT AND CONNECTION. CANNOT OPEN INPUT STREAM
            }
        }).start();
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
     * @return the content of the input buffer
     */
    private String getInput(){
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
    public void plot(){

        for (int i = 0; i < viewStatus.length; i++) {
            System.out.println();
            for (int j = 0; j < viewStatus[i].length; j++) {
                out.println(viewStatus[i][j]);
            }
        }
    }

    /**
     * this method is used to initialize the state of the view
     */
    @Override
    public void initialize() {
        CLIPainter.printLogo();
        CLIPainter.fill(viewStatus, 0, 0, HORIZONTAL_SIZE, VERTICAL_SIZE);
        inputReader(in);
    }

    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body is the content of the message
     */
    @Override
    public void showAnswer(boolean answer, String body, String nickname) {
        if(answer){
            out.println("Error: "+ body + "\nYou've done something wrong, let's try again");
        }
        else{
            out.println("Action successfully performed. Let's proceed further");
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
        CLIPainter.fill(decksStatus, 0, 0,HORIZONTAL_SIZE, VERTICAL_SIZE);
        for(int i : decks.keySet()){
            int row = i/N_DECKS_X, column = i%N_DECKS_X;
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
                warehouseString.append(r.toString()).append("/");
            }
            else {
                extraBoxString.append(r.toString()).append("/");
                extra.add(r);
            }
        }

        extra.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        CLIPainter.paintWarehouse(viewStatus, WAREHOUSE_Y+PLAYERS_Y, WAREHOUSE_X, model.getShelves(), resources);
        CLIPainter.paintExtraSlots(viewStatus, EXTRA_Y+PLAYERS_Y, EXTRA_X, extra);

        strongBox.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        for(ResQuantity r : strongBox){
            strongboxString.append(r.toString()).append("/");
        }
        CLIPainter.paintStrongbox(viewStatus, STRONGBOX_Y+PLAYERS_Y, STRONGBOX_X, strongBox);


        out.println("The new status of your boxes is:\nWarehouse: " + warehouseString);
        if(extra.size()>0) out.println("Extra shelves: " + extraBoxString);
        out.println("StrongBox: " + strongboxString);
    }

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     */
    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickname) {
        for(int i : slots.keySet()){
            DevelopmentCard card = ConfigurationParser.getDevelopmentById(model.getConfigurationFile(), slots.get(i));
            CLIPainter.devCardPainter(viewStatus, PLAYERS_Y+1, BOXES_X + 30*(i-1)+14, card.toString());
        }
    }

    //devi gestire gli errori di parsing
    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     */
    @Override
    public void showLeaderCards(Map<Integer,String> cards, Map<Integer,ItemStatus> status, String nickName){
        List<LeaderCard> target = new LinkedList<>();
        for(int i: cards.keySet()){
            String id = cards.get(i);
            LeaderCard card = ConfigurationParser.getLeaderById(model.getConfigurationFile(), id);
            card.setStatus(status.get(i).getBoolValue());
            CLIPainter.leaderCardPainter(viewStatus, PLAYERS_Y+1+LEADER_Y, BOXES_X + 30*i+14, card.toString());
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
        if(faithLorenzo.isPresent() && sectionsLorenzo.isPresent()){
            faith.put("Lorenzo", faithLorenzo.get());
            sections.put("Lorenzo", new LinkedList<>(sectionsLorenzo.get()));
        }
        CLIPainter.paintFaithTrack (viewStatus, FAITHTRACK_Y, FAITHTRACK_X,
                model.getTrackPoints(), model.getNicknames(), faith, sections);
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
        CLIPainter.paintEndGameBox(viewStatus, END_X, END_Y,players);
        plot();
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
            out.println("Tell me if you want to start a new game (true/false): ");
            first = getInput();
        }while(!first.equals("true") && !first.equals("false"));

        if(first.equals("true")){
            do {
                out.println("Give me the number of players in the game: ");
                num = getInput();
            }while(!isInt(num));
        }
        else num = "0";
        try {
            viewObserver.update(MessageFactory.buildConnection("Connection request", player, Boolean.parseBoolean(first), Integer.parseInt(num)));
        }catch(MalformedMessageException e){
            //CLOSE CONNECTION
        }

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
        plot();
        StringBuilder available = new StringBuilder();
        for(String string : turns) available.append(string).append(", ");
        String s = "";
        do{
            out.println("Select your turn type; available turns: " + available);
            s = getInput();
            if(!turns.contains(s)) showAnswer(false, "Not existent turn type.", "name");
        }
        while(!turns.contains(s));
        //qui mando il messaggio al client
        return s;
    }

    /**
     * this method is used to catch the LeaderCards selected by a player
     * @param cards is the list of cards given to a player
     */
    @Override
    public void selectLeaderAction(Map<Integer,String> cards) {

    }

    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    @Override
    public void selectMarketAction() {
        String s;
        do {
            out.println("Select a row or a column.\nCommand Type:- row:number or column:number");
            s = getInput();
        } while(!isValidMarketSelection(s));
        //mandare messaggio al client
    }

    /**
     * this helper method is used to check if a market selection is correct
     * @param s is the command to be checked
     * @return true if the command is correct, else otherwise
     */
    private boolean isValidMarketSelection(String s){

        String selection = s.substring(0, s.length()-2);
        int n, limit;

        if(selection.equals("row")) limit = model.getnRows();
        else if(selection.equals("column")) limit = model.getnColumns();
        else return false;

        try{
            n = Integer.parseInt(s.substring(s.length()-1, s.length()));
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
            for (int i = 0; i < whiteModifications.size(); i++) {
                builder.append(whiteModifications.get(i).toString()).append(", ");
            }
            out.println("The possible transformations for white marbles are: " + builder);
        }
        out.println("Type your action. Command: MarbleColor:ACTION:TargetSlot\n " +
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

    }

    /**
     * this method is used to catch the action of a player of a shared DevelopmentCard
     */
    @Override
    public void buyCardAction() {

    }

    /**
     * this method is used to catch the action of a player on their productions
     */
    @Override
    public void doProductionsAction() {

    }

    /**
     * this action is used to catch the resources chosen by a player
     */
    @Override
    public void getResourcesAction() {

    }

    /**
     * this method show the player's personal production.
     */
    @Override
    public void showPersonalProduction() {
        Production production = model.getPersonalProduction();
        List<CLIColors> materials = new LinkedList<>();
        List<CLIColors> products = new LinkedList<>();
        CLIPainter.paintPersonalProduction(viewStatus, PRODUCTION_Y + RESOURCES_Y, PRODUCTION_X,
                production.getMaterials(), production.getProducts(),
                production.getCustomMaterials(), production.getCustomProducts());

    }

    /**
     * this method is used to catch a swap in the Warehouse
     */
    @Override
    public void swapAction() {

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
