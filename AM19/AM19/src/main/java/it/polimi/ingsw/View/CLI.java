package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.ItemStatus;
import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.*;

/**
 * this class represents a CLI. It gives all the methods to "paint" the current status of the game
 * and retrieve information from the player
 */
public class CLI implements View {

    //TO DO: put all the configuration offsets inside a file
    /**
     * the following attributes represent the constants used to paint the items of the CLI
     */
    private final int VERTICAL_SIZE = 200;
    private final int HORIZONTAL_SIZE=200;

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

    /**
     * this attribute is a reference to the reduced model of the view
     */
    private ViewModel model;

    /**
     * this attribute is a matrix that contains the current state of the CLI
     */
    private String[][] viewStatus;

    /**
     * this is the constructor of the class
     * @param model is an instance of the reduced model of the view
     */
    public CLI(ViewModel model){
        this.model=model;
        viewStatus = new String[VERTICAL_SIZE][HORIZONTAL_SIZE];
        PLAYERS_Y = (model.getNicknames().size() + 1) * (CLIPainter.getSquareLength() + 1);
        RESOURCES_Y = (model.getnRows()+1)*(CLIPainter.getSphereLength() + 1) + 3;
        initialize();
    }

    /**
     * this method is used to initialize the state of the view
     */
    @Override
    public void initialize() {
        CLIPainter.printLogo();
        CLIPainter.fill(viewStatus, 0, 0, HORIZONTAL_SIZE, VERTICAL_SIZE);
    }

    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body is the content of the message
     */
    @Override
    public void showAnswer(boolean answer, String body) {

    }

    /**
     * this method is used to show an update of the marketBoard
     * @param tray is the current state of the market tray
     */
    @Override
    public void showMarketUpdate(List<Marble> tray) {
        CLIPainter.paintMarketBoard(viewStatus, MARKET_Y, MARKET_X, tray, model.getnRows(), model.getnColumns());
    }

    /**
     * this method is used to show an update of the shared decks on the GameBoard
     * @param decks contains the top cards of each deck
     */
    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {

    }

    /**
     * this method is used to show an update of one's warehouse
     * @param warehouse represent the current state of the warehouse
     */
    @Override
    public void showWarehouseUpdate(Map<Integer, ResQuantity> warehouse) {
        List<ResQuantity> resources = new LinkedList<>();
        List<ResQuantity> extra = new LinkedList<>();
        int defaultSlots = model.getSlotNumber();
        for(int i : warehouse.keySet()){
            if(i<=defaultSlots) resources.add(warehouse.get(i));
            else extra.add(warehouse.get(i));
        }
        extra.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        CLIPainter.paintWarehouse(viewStatus, WAREHOUSE_Y+PLAYERS_Y, WAREHOUSE_X, model.getShelves(), resources);
        CLIPainter.paintExtraSlots(viewStatus, EXTRA_Y+PLAYERS_Y, EXTRA_X, extra);
    }

    /**
     * this method is used to show an update of one's strongbox
     * @param strongBox represent the current state of the strongbox
     */
    @Override
    public void showStrongboxUpdate(List<ResQuantity> strongBox) {
        strongBox.sort(Comparator.comparing((ResQuantity re) -> re.getResource().toString()));
        CLIPainter.paintStrongbox(viewStatus, STRONGBOX_Y+PLAYERS_Y, STRONGBOX_X, strongBox);
    }

    /**
     * this method is used to show an update of one's card slots
     * @param slots represent the current state of one's card slots
     */
    @Override
    public void showSlotsUpdate(Map<Integer, String> slots) {
        for(int i : slots.keySet()){
            DevelopmentCard card = ConfigurationParser.getDevelopmentById(model.getConfigurationFile(), slots.get(i));
            CLIPainter.devCardPainter(viewStatus, PLAYERS_Y+1, BOXES_X + 30*(i-1)+14, card.toString());
        }
    }

    /**
     * this method is used to show an update of one's LeaderCards
     * @param cards represent the current state of one's leader cards
     */
    @Override
    public void showLeaderCards(Map<String, ItemStatus> cards) {
        List<LeaderCard> target = new ArrayList<>();
        for(String s: cards.keySet()){
            LeaderCard c = ConfigurationParser.getLeaderById(model.getConfigurationFile(), s);
            c.setStatus(true);
            target.add(c);
        }
        target.sort(Comparator.comparing((Card c1) -> c1.getId()));
        for(int i=0; i<target.size(); i++){
            LeaderCard card = target.get(i);
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
     * @param winner is the name of the winner
     */
    @Override
    public void showEndGame(Map<String, Integer> players, String winner) {

    }

    /**
     * this method is used to show the disconnection of a player
     * @param nickname is the name of the disconnected player
     */
    @Override
    public void showDisconnection(String nickname) {

    }

    /**
     * this method is used to add a player to the view
     */
    @Override
    public void newPlayer() {

    }

    /**
     * this method is used to catch the player's selected turn
     * @param turns is the list of available turns
     * @param player is the nickname of the current player
     */
    @Override
    public void selectTurnAction(LinkedList<String> turns, String player) {

    }

    /**
     * this method is used to catch the LeaderCards selected by a player
     * @param cards is the list of cards given to a player
     */
    @Override
    public void selectLeaderAction(List<String> cards) {

    }

    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    @Override
    public void selectMarketAction() {

    }

    /**
     * this method is used to catch the decision of a player on a selection of marbles
     * @param marbles is a set of marbles
     */
    @Override
    public void marbleAction(List<Marble> marbles) {

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
     * this method is used to print the current status of the CLI
     */
    public void plot(){
        for (int i = 0; i < viewStatus.length; i++) {
            System.out.println();
            for (int j = 0; j < viewStatus[i].length; j++) {
                System.out.print(viewStatus[i][j]);
            }
        }
    }
}
