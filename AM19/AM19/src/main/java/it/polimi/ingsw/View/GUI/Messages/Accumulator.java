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

    //init
    private StringBuilder initLeaders = new StringBuilder();
    private StringBuilder initResources = new StringBuilder();

    //market
    private String tray;
    private int trayNumber;
    private StringBuilder marblesActions = new StringBuilder();
    private int source;
    private int target;

    //
    private StringBuilder warehouse = new StringBuilder();
    private StringBuilder strongbox = new StringBuilder();

    //buy_card
    private int position;
    private int slot;

    //do_production
    private StringBuilder developmentCards = new StringBuilder();
    private StringBuilder leaderCards = new StringBuilder();
    private StringBuilder chosenProducts = new StringBuilder();
    private StringBuilder chosenMaterials = new StringBuilder();
    private boolean personalProduction;

    //manage_leader
    private int leaderCard;
    private String action;



    public void setInitLeaders(String cardNumber){
        initLeaders.append(cardNumber).append(splitter);
    }

    public void setInitResources(String resource){
        initResources.append(resource).append(splitter);
    }

    public void setInitResourcesShelf(String shelf){
        initResources.append(shelf).append(splitter);
    }

    public void setMarketTray(String tray){ this.tray = tray;}

    public void setMarketNumber(int trayNumber){
        this.trayNumber = trayNumber;
    }

    public void setMarblesActions(String string){
        marblesActions.append(string).append(splitter);
    }

    public void setSwapSource(int source){
        this.source = source;
    }

    public void setSwapTarget(int target){
        this.target = target;
    }

    public void setWarehouse(String warehouse){
        this.warehouse.append(warehouse).append(splitter);
    }

    public void setStrongbox(String strongbox){
        this.strongbox.append(strongbox).append(splitter);
    }

    public void setDevelopmentCards(String card){
        developmentCards.append(card).append(splitter);
    }

    public void setLeaderCards(String card){
        developmentCards.append(card).append(splitter);
    }

    public void setChosenProducts(String string){
        chosenProducts.append(string).append(splitter);
    }

    public void setChosenMaterials(String string){
        chosenMaterials.append(string).append(splitter);
    }

    public void setLeaderCard(int position){
        leaderCard = position;
    }

    public void setAction(String action){
        this.action = action;
    }

    public String buildSelectedResources(){
        String resources = initResources.length()==0 ? "" : initResources.substring(0, initResources.length()-1);
        try {
            return MessageFactory.buildSelectedResources(resources,"Selection of resources during initialization");
        } catch (MalformedMessageException e) {
            return "";
            //exit
        }
    }

    public String buildLeaderUpdate(){

        String input = initLeaders.length()==0 ? "" : initLeaders.substring(0, initLeaders.length()-1);
        String[] selections = input.split(splitter);
        ReducedGameBoard model = GUIHandler.instance().getModel();
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

    public String buildMarketSelection(){

        try {
            return MessageFactory.buildMarketSelection(tray,trayNumber,"Selection of a row or a column from the market.");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }

    public String buildActionMarble(){

        String marbles = marblesActions.length()==0 ? "" : marblesActions.substring(0, marblesActions.length()-1);
        try {
            return MessageFactory.buildActionMarble(marbles, "Marbles managing");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }

    public String buildSwap(){
        try {
            return MessageFactory.buildSwap(source, target, "Swapping two shelves of the warehouse");
        } catch (MalformedMessageException e) {
            //exit
            return "";
        }
    }

    public String buildBuyCard(){

        ReducedGameBoard model = GUIHandler.instance().getModel();
        String id = model.getDecks().get(position-1);
        //System.out.println("Debug");
        DevelopmentCard card = model.getConfiguration().getDevelopmentCard(id);

        String warehouse =  this.warehouse.length()==0 ? "" : this.warehouse.substring(0, this.warehouse.length()-1);
        String strongbox = this.strongbox.length()==0 ? "" : this.strongbox.substring(0, this.strongbox.length()-1);

        try {
           return MessageFactory.buildBuyCard(card.getCardColor().getColor(),
                    card.getCardLevel(), slot, id, "Buy card", warehouse, strongbox);

        }catch(MalformedMessageException e){
            //exit from client
            return "";
        }
    }

    public String buildDoProduction(){
        ReducedGameBoard model = GUIHandler.instance().getModel();
        String leaderCards = this.leaderCards.length()==0 ? "" : this.leaderCards.substring(0, this.leaderCards.length()-1);
        String developmentCards = this.developmentCards.length()==0 ? "" : this.developmentCards.substring(0,this.developmentCards.length()-1);
        String leaderMessage = helpCards(model.getBoard(model.getCurrentPlayer()).getLeadersID(),leaderCards);
        String devMessage = helpCards(model.getBoard(model.getCurrentPlayer()).getSlots(),developmentCards);
        String warehouse =  this.warehouse.length()==0 ? "" : this.warehouse.substring(0, this.warehouse.length()-1);
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

    public String buildLeaderAction() {
        ReducedGameBoard model = GUIHandler.instance().getModel();
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
