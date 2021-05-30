package it.polimi.ingsw.View.GUI.Messages;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.View.GUI.GUIHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class helps to create messages
 */
public class Accumulator {

    private final String splitter = ":";


    /**
     * StringBuilder useful to generate the string which represents the resources selected by the player during the initialization of the game
     */
    private StringBuilder initResources = new StringBuilder();

    //market
    /**
     * It represents the choice of the marketboard
     */
    private String tray;
    /**
     * it represents the number of row/column of the marketBoard
     */
    private int trayNumber;
    /**
     * StringBuilder useful to generate the string which represents the marbles selected by the player and the correspondent actions
     */
    private StringBuilder marblesActions = new StringBuilder();
    /**
     * the first shelf to swap
     */
    private int source;
    /**
     * the second shelf to swap
     */
    private int target;
    /**
     * true if source has been initialized, false otherwise
     */
    private boolean sourceSet = false;

    //
   // private StringBuilder warehouse = new StringBuilder();
    /**
     * Map useful to generate the string which represents  the resources selected by the player from the warehouse
     */
    private Map<Integer,Integer> warehouse = new HashMap<>();
    /**
     * StringBuilder useful to generate the string which represents the resources selected by the player from the strongBox
     */
    private StringBuilder strongbox = new StringBuilder();

    //buy_card
    /**
     * It represents the position of the development card in the decks
     */
    private int position;
    /**
     * It represents the slot selected by the player during a buy card turn
     */
    private int slot;

    //do_production
    /**
     * StringBuilder useful to generate the string which represents the development cards selected by the player to perform the production
     */
    private StringBuilder developmentCards = new StringBuilder();
    /**
     * StringBuilder useful to generate the string which represents the leader cards selected by the player to perform the production
     */
    private StringBuilder leaderCards = new StringBuilder();
    /**
     * StringBuilder useful to generate the string which represents the chosen materials selected by the player to perform the production
     */
    private StringBuilder chosenProducts = new StringBuilder();
    /**
     * StringBuilder useful to generate the string which represents the chosen products selected by the player to perform the production
     */
    private StringBuilder chosenMaterials = new StringBuilder();
    /**
     * It represents the choice of the player about the personal production
     */
    private boolean personalProduction = false;

    //manage_leader
    /**
     * It represents the position of the leader card
     */
    private int leaderCard;
    /**
     * It represents the action selected by the player about the leader card
     */
    private String action;


    /**
     * Adds the resource passed as parameter to the initialization resources
     * @param resource the resource selected
     */
    public void setInitResources(String resource){
        initResources.append(resource).append(splitter);
    }

    /**
     * Adds the shelf passed as parameter to the correspondent resource
     * @param shelf the number of shelf
     */
    public void setInitResourcesShelf(String shelf){
        initResources.append(shelf).append(splitter);
    }

    /**
     * sets the choice of the marketBoard
     * @param tray
     */
    public void setMarketTray(String tray){ this.tray = tray;}

    /**
     * sets the number of row/column of the marketBoard
     * @param trayNumber number of row/column
     */
    public void setMarketNumber(int trayNumber){
        this.trayNumber = trayNumber;
    }

    /**
     * Appends to the StringBuilder associated with the marbles actions the String passed as parameter
     * @param string
     */
    public void setMarblesActions(String string){
        marblesActions.append(string).append(splitter);
    }

    /**
     * sets the swap source/target
     * @param shelf the number of shelf
     */
    public void setSwap(int shelf){
        if(sourceSet)
            this.target = shelf;
        else {
            this.source = shelf;
            this.sourceSet = true;
        }
    }

    /**
     * sets the position of the development card in the decks
     * @param position of development card
     */
    public void setPosition(int position){
        this.position = position;
    }

    /**
     * sets the slot selected by the player
     * @param slot selected by the player
     */
    public void setSlot(int slot){
        this.slot = slot;
    }
/*
    public void setWarehouse(String warehouse){
        this.warehouse.append(warehouse).append(splitter);
    }*/

    /**
     * adds one to the number of resources that the players selects from the shelf
     * @param shelf the shelf selected by the player
     */
    public void setWarehouse(int shelf){
        warehouse.put(shelf,warehouse.getOrDefault(shelf,0)+1);
    }

    /**
     * Appends to the StringBuilder associated with the strongBox the String passed as parameter
     * @param strongbox
     */
    public void setStrongbox(String strongbox){
        this.strongbox.append(strongbox).append(splitter);
    }

    /**
     * sets to true the personal production
     */
    public void setPersonalProduction(){
        personalProduction = true;
    }

    /**
     * Appends to the StringBuilder associated with the development cards the number of card passed as parameter
     * @param card the number of slot of the card
     */
    public void setDevelopmentCards(String card){
        developmentCards.append(card).append(splitter);
    }

    /**
     * Appends to the StringBuilder associated with the leader cards the number of card passed as parameter
     * @param card the position of the card
     */
    public void setLeaderCards(String card){
        leaderCards.append(card).append(splitter);
    }

    /**
     * Appends to the StringBuilder associated with the chosen products the String passed as parameter
     * @param string
     */
    public void setChosenProducts(String string){
        chosenProducts.append(string).append(splitter);
    }

    /**
     * Appends to the StringBuilder associated with the chosen materials the String passed as parameter
     * @param string
     */
    public void setChosenMaterials(String string){
        chosenMaterials.append(string).append(splitter);
    }

    /**
     * sets the leader card to be managed
     * @param position the position of the leader card
     */
    public void setLeaderCard(int position){
        leaderCard = position;
    }

    /**
     * sets the action to be performed on the selected leader card
     * @param action the action selected by the player
     */
    public void setAction(String action){
        this.action = action;
    }

    /**
     * @return a String which is the message selected resources
     */
    public String buildSelectedResources(){
        String resources = initResources.length()==0 ? "" : initResources.substring(0, initResources.length()-1);
        try {
            return MessageFactory.buildSelectedResources(resources,"Selection of resources during initialization");
        } catch (MalformedMessageException e) {
            return "";
            //exit
        }
    }

    /**
     * @return a String which represents the field warehouse of the message
     */
    private String getStringWarehouse(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i : warehouse.keySet()){
            stringBuilder.append(i).append(splitter);
            stringBuilder.append(warehouse.get(i)).append(splitter);
        }
        return stringBuilder.length()==0 ? "" : stringBuilder.substring(0, stringBuilder.length()-1);
    }

    /**
     * @return a String which is the message leader update
     */
    public String buildLeaderUpdate(){

        String input = leaderCards.length()==0 ? "" : leaderCards.substring(0, leaderCards.length()-1);
        String[] selections = input.split(splitter);
        ReducedGameBoard model = GUIHandler.getGUIReference().getModelReference();
        Map<Integer, String> cards = model.getBoard(model.getCurrentPlayer()).getLeadersID();
        Map<Integer, ItemStatus> map = new HashMap<>();
        for(int i : cards.keySet()) map.put(i, ItemStatus.DISCARDED);
        try {
            for (String selection : selections) map.put(Integer.parseInt(selection), ItemStatus.ACTIVE);
        }catch (NumberFormatException e){}

        try{
            return MessageFactory.buildLeaderUpdate(cards, map,
                    "Leader cards initialization managing.", model.getCurrentPlayer());
        }catch(MalformedMessageException e){
            //exit from client
            return "";
        }
    }

    /**
     * @return a String which is the message market selection
     */
    public String buildMarketSelection(){

        try {
            return MessageFactory.buildMarketSelection(tray,trayNumber,"Selection of a row or a column from the market.");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }

    /**
     * @return a String which is the message action marble
     */
    public String buildActionMarble(){

        String marbles = marblesActions.length()==0 ? "" : marblesActions.substring(0, marblesActions.length()-1);
        try {
            return MessageFactory.buildActionMarble(marbles, "Marbles managing");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }

    /**
     * @return a String which is the message swap
     */
    public String buildSwap(){
        try {
            return MessageFactory.buildSwap(source, target, "Swapping two shelves of the warehouse");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }

    /**
     * @return a String which is the message buy card
     */
    public String buildBuyCard(){

        ReducedGameBoard model = GUIHandler.getGUIReference().getModelReference();
        String id = model.getDecks().get(position-1);

        DevelopmentCard card = model.getConfiguration().getDevelopmentCard(id);

        String warehouse = getStringWarehouse();
        String strongbox = this.strongbox.length()==0 ? "" : this.strongbox.substring(0, this.strongbox.length()-1);

        try {
           return MessageFactory.buildBuyCard(card.getCardColor().getColor(),
                    card.getCardLevel(), slot, id, "Buy card", warehouse, strongbox);

        }catch(MalformedMessageException e){
            //exit from client
            return "";
        }
    }

    /**
     * @return a String which is the message do production
     */
    public String buildDoProduction(){
        ReducedGameBoard model = GUIHandler.getGUIReference().getModelReference();
        String leaderCards = this.leaderCards.length()==0 ? "" : this.leaderCards.substring(0, this.leaderCards.length()-1);
        String developmentCards = this.developmentCards.length()==0 ? "" : this.developmentCards.substring(0,this.developmentCards.length()-1);
        String leaderMessage = helpCards(model.getBoard(model.getCurrentPlayer()).getLeadersID(),leaderCards);
        String devMessage = helpCards(model.getBoard(model.getCurrentPlayer()).getSlots(),developmentCards);
        //String warehouse =  this.warehouse.length()==0 ? "" : this.warehouse.substring(0, this.warehouse.length()-1);
        String warehouse = getStringWarehouse();
        String strongbox = this.strongbox.length()==0 ? "" : this.strongbox.substring(0, this.strongbox.length()-1);
        String chosenProducts = this.chosenProducts.length()==0 ? "" : this.chosenProducts.substring(0, this.chosenProducts.length()-1);
        String chosenMaterials = this.chosenMaterials.length()==0 ? "" : this.chosenMaterials.substring(0, this.chosenMaterials.length()-1);

        try {
            return MessageFactory.BuildDoProduction(personalProduction,devMessage,leaderMessage,chosenMaterials,chosenProducts,warehouse,strongbox,"Do production");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }

    }

    /**
     * This method associates the ID to the correspondent card
     * @param cards map(position-id)
     * @param action
     * @return a String which represents the correct content for the message
     */
    private String helpCards(Map<Integer, String> cards, String action){

        String[] selection;
        StringBuilder sequence = new StringBuilder();

        selection = action.split(":");

        if(selection.length == 0) return "";
        if(action.isEmpty()) return action;
        for(String s : selection){
            int position = Integer.parseInt(s);
            sequence.append(position).append(":").append(cards.get(position)).append(":");
        }
        return sequence.equals("")? "":sequence.substring(0, sequence.length()-1);
    }

    /**
     * @return a String which is the message leader action
     */
    public String buildLeaderAction() {
        ReducedGameBoard model = GUIHandler.getGUIReference().getModelReference();
        String player = model.getCurrentPlayer();
        Map<Integer, String> leadersID = model.getBoard(player).getLeadersID();
        try {
            return MessageFactory.buildLeaderAction(leadersID.get(this.leaderCard), this.leaderCard, action, "Action on leader");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }
}
