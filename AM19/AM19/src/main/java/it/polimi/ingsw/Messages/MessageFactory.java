package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.Cards.Colors.DevColor;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MessageFactory {
    private static final String splitter = ":";


    public static String buildExit(String body) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.EXIT.toString());
        return MessageParser.createMessage(map);
    }

    public static String buildConnection(String body, String nickname, Boolean gameHost, Integer playersNumber) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.CONNECTION.toString());
        map.put("player", nickname);
        map.put("gameHost", gameHost.toString());
        map.put("playersNumber", playersNumber.toString());
        return MessageParser.createMessage(map);
    }

    public static String buildDisconnection(String body, String nickname) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.DISCONNECTION.toString());
        map.put("player", nickname);
        return MessageParser.createMessage(map);
    }

    public static String buildReconnection(String body, String nickname) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.RECONNECTION.toString());
        map.put("player", nickname);
        return MessageParser.createMessage(map);
    }

    //--------------------------------------CORRECTION
    //------------------------------------------------
    //------------------------------------------------
    public static String buildGameStatus(Boolean type, String body, String nickName, TurnType state) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.GAME_STATUS.toString());
        map.put("correct", type.toString());
        map.put("player", nickName);
        map.put("state", state.name());
        return MessageParser.createMessage(map);
    }

    //--------------------------------------CORRECTION
    //------------------------------------------------
    //------------------------------------------------
    public static String buildCurrentPlayer(String playerID, List<String> turns, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        StringBuilder content = new StringBuilder();
        for(int i=0; i<turns.size(); i++){
            content.append(turns.get(i))
                    .append(splitter);
        }

        map.put("body", body);
        map.put("messageType", Message.MessageType.GAME_STATUS.toString());
        map.put("state", TurnType.TURN_SELECTION.toString());
        map.put("correct", Boolean.toString(true));
        map.put("turns", content.length()==0 ? "" : content.substring(0, content.length()-1));
        map.put("player", playerID);
        return MessageParser.createMessage(map);
    }


    public static String buildReply(Boolean type, String body, String nickName) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.REPLY.toString());
        map.put("correct", type.toString());
        map.put("player", nickName);
        return MessageParser.createMessage(map);
    }

    public static String buildStartGame(String body, List<String> nicknames) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        StringBuilder content = new StringBuilder();

        for(int i=0; i<nicknames.size(); i++){
            content.append(nicknames.get(i)).append(":");
        }

        map.put("body", body);
        map.put("messageType", Message.MessageType.START_GAME.toString());
        map.put("activePlayers", ((content.length()>0)?content.substring(0, content.length()-1):""));
        return MessageParser.createMessage(map);
    }



    //--------------------------------------CORRECTION
    //------------------------------------------------
    //------------------------------------------------
    /*public static String buildSelectedMarbles(List<Marble> selection, List<Marble> candidates, String player, String body)
            throws MalformedMessageException{
        String selectionString = buildMarbleList(selection), candidateString = buildMarbleList(candidates);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.GAME_STATUS.toString());
        map.put("state", TurnType.MANAGE_MARBLE.toString());
        map.put("correct", Boolean.toString(true));
        map.put("player",player);
        map.put("marbles", selectionString);
        map.put("candidates", candidateString);
        return MessageParser.createMessage(map);
    }*/

    //--------------------------------------CORRECTION
    //------------------------------------------------
    //------------------------------------------------
    public static String buildSelectedMarbles(List<Marble> selection, List<Marble> candidates, String player, String body)
            throws MalformedMessageException{
        String selectionString = buildMarbleList(selection), candidateString = buildMarbleList(candidates);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.GAME_STATUS.toString());
        map.put("state", TurnType.MANAGE_MARBLE.toString());
        map.put("correct", Boolean.toString(true));
        map.put("player",player);
        map.put("marbles", selectionString);
        map.put("candidates", candidateString);
        return MessageParser.createMessage(map);
    }

    public static String buildEndGame(Map<String, Integer> points, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        StringBuilder content = new StringBuilder();
        for(String s : points.keySet()){
            content.append(s).append(splitter).append(points.get(s)).append(splitter);
        }
        String string = content.length()==0 ? "" : content.substring(0, content.length()-1);
        map.put("body", body);
        map.put("messageType", Message.MessageType.END_GAME.toString());
        map.put("points", string);
        return MessageParser.createMessage(map);
    }

    public static String buildBoxUpdate(List<ResQuantity> warehouse, List<ResQuantity> strongbox, String player, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.BOX_UPDATE.toString());
        map.put("player", player);
        map.put("warehouse", buildResQuantity(warehouse));
        map.put("strongbox", buildResQuantity(strongbox));
        return MessageParser.createMessage(map);
    }

    private static String buildResQuantity(List<ResQuantity> box){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<box.size(); i++){
            content.append(box.get(i).getResource().toString().toLowerCase())
                    .append(splitter).append(box.get(i).getQuantity()).append(splitter);
        }
        if(content.length()==0) return content.toString();
        return content.substring(0, content.length()-1);
    }

    public static String buildSlotsUpdate(Map<Integer, String> devCards, String player, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SLOTS_UPDATE.toString());
        map.put("player", player);
        map.put("devCards", buildIDStringMap(devCards));
        return MessageParser.createMessage(map);
    }

    public static String buildDecksUpdate(Map<Integer, String> devCards, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.DECKS_UPDATE.toString());
        map.put("devCards", buildIDStringMap(devCards));
        return MessageParser.createMessage(map);
    }

    private static String buildIDStringMap(Map<Integer, String> target){
        StringBuilder content = new StringBuilder();
        for(int i : target.keySet()){
            content.append(i)
                    .append(splitter)
                    .append(target.get(i)).append(splitter);
        }
        return content.length()==0 ? "" : content.substring(0, content.length()-1);
    }

    public static String buildUpdateLorenzo(String tokenID, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.TOKEN_UPDATE.toString());
        map.put("token", tokenID);
        return MessageParser.createMessage(map);
    }

    public static String buildUpdateMarket(List<Marble> tray, String body) throws MalformedMessageException {
        String content = buildMarbleList(tray);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.MARKET_UPDATE.toString());
        map.put("market", content);
        return MessageParser.createMessage(map);
    }

    private static String buildMarbleList(List<Marble> target){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<target.size(); i++){
            content.append(target.get(i).toString())
                    .append(splitter);
        }
        if(content.length()==0) return content.toString();
        return content.substring(0, content.length()-1);
    }

    public static String buildFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections
            , Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.FAITH_UPDATE.toString());

        String itemString;
        //se la mappa è vuota ci può essere qualche errore, anche se non dovrebbe mai capitare
        for(String name : sections.keySet()){
            itemString = buildStatusList(sections.get(name));
            map.put(name+"Sections", itemString);
        }
        StringBuilder faithString = new StringBuilder();
        for(String name : faith.keySet()){
            faithString.append(name)
                    .append(splitter)
                    .append(faith.get(name))
                    .append(splitter);
        }
        map.put("faith", faithString.length()==0 ? "" : faithString.substring(0, faithString.length()-1));

        faithLorenzo.ifPresent(integer -> map.put("LorenzoFaith", integer.toString()));

        if(sectionsLorenzo.isPresent()){
            itemString = buildStatusList(sectionsLorenzo.get());
            map.put("LorenzoSections", itemString);
        }
        return MessageParser.createMessage(map);
    }


    private static String buildStatusList(List<ItemStatus> items){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<items.size(); i++){
            content.append(items.get(i).toString())
                    .append(splitter);
        }
        return content.length()==0 ? "" : content.substring(0, content.length()-1);
    }

    public static String buildLeaderUpdate(Map<Integer, String> cards, Map<Integer, ItemStatus> status, String player, String body)
            throws MalformedMessageException {
        String cardString = buildIDStringMap(cards), statusString = buildIDItemStatusMap(status);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.UPDATE_LEADER_CARDS.toString());
        map.put("player", player);
        map.put("leaderStatus", statusString);
        map.put("leaderCards", cardString);
        return MessageParser.createMessage(map);
    }

    private static String buildIDItemStatusMap(Map<Integer, ItemStatus> target){
        StringBuilder content = new StringBuilder();
        for(int i : target.keySet()){
            content.append(i)
                    .append(splitter)
                    .append(target.get(i).toString())
                    .append(splitter);
        }
        return content.length()==0 ? "" : content.substring(0, content.length()-1);
    }

    /*public static String buildSelectedResources(List<Resource> resources, List<Integer> shelves, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        StringBuilder content = new StringBuilder();
        for(int i=0; i<resources.size(); i++){
            content.append(shelves.get(i))
                    .append(splitter)
                    .append(resources.get(i).toString().toLowerCase())
                    .append(splitter);
        }
        map.put("body", body);
        map.put("messageType", Message.MessageType.RESOURCE.toString());
        map.put("resources", content.substring(0, content.length()-1));
        return MessageParser.createMessage(map);
    }*/

    public static String buildSelectedResources(String content, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.RESOURCE.toString());
        map.put("resources", content);
        return MessageParser.createMessage(map);
    }

    public static String buildSelectedTurn(String turn, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SELECTED_TURN.toString());
        map.put("turnType", turn);
        return MessageParser.createMessage(map);
    }

    /*public static String buildBuyCard(DevColor color, int level, int slot, String id, String body
            , String shelves, String quantity, String strongbox) throws MalformedMessageException {
        String strongboxString, warehouseString;

        warehouseString = buildStringIntInt(shelves, quantity);

        strongboxString = buildResQuantity(strongbox);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.BUY_CARD.toString());
        map.put("color", color.toString().toLowerCase());
        map.put("level", Integer.toString(level));
        map.put("slot", Integer.toString(slot));
        map.put("ID", id);
        map.put("warehouse", warehouseString);
        map.put("strongBox", strongboxString);
        return MessageParser.createMessage(map);

    }*/

    public static String buildBuyCard(DevColor color, int level, int slot, String id, String body
            , String warehouse, String strongbox) throws MalformedMessageException {

        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.BUY_CARD.toString());
        map.put("color", color.toString().toLowerCase());
        map.put("level", Integer.toString(level));
        map.put("slot", Integer.toString(slot));
        map.put("ID", id);
        map.put("warehouse", warehouse);
        map.put("strongBox", strongbox);
        return MessageParser.createMessage(map);

    }

    private static String buildStringIntInt(List<Integer> target1, List<Integer> target2){
        StringBuilder content = new StringBuilder();

        for(int i=0; i<target1.size(); i++){
            content.append(target1.get(i))
                    .append(splitter)
                    .append(target2)
                    .append(splitter);
        }
        return content.length()==0 ? "" : content.substring(0, content.length()-1);

    }
    public static String BuildDoProduction(boolean personal, String developments
            , String leaders, String customMaterials, String customProducts
            , String warehouse,String strongbox, String body)
            throws MalformedMessageException{

        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.DO_PRODUCTION.toString());
        map.put("personalProduction", Boolean.toString(personal));
        map.put("developmentCards", developments);
        map.put("leaderCards", leaders);
        map.put("chosenProducts", customProducts);
        map.put("chosenMaterials", customMaterials);
        map.put("warehouse", warehouse);
        map.put("strongBox", strongbox);
        return MessageParser.createMessage(map);
    }

    public static String buildLeaderAction(String id, int position, String action, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.LEADER_ACTION.toString());
        map.put("leaderCards", position+":"+id);
        map.put("action", action);
        return MessageParser.createMessage(map);
    }

    public static String buildSwap(int source, int target, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SWAP.toString());
        map.put("source", Integer.toString(source));
        map.put("target", Integer.toString(target));
        return MessageParser.createMessage(map);
    }

    public static String buildMarketSelection(String traySelection, int position, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.MARKET_SELECTION.toString());
        map.put("tray", traySelection);
        map.put("number", Integer.toString(position));
        return MessageParser.createMessage(map);
    }

    /*public static String buildActionMarble(List<Marble> marbles, List<Integer> position, List<String> action, String body)
            throws MalformedMessageException {
        StringBuilder content = new StringBuilder();
        for(int i=0; i<marbles.size(); i++){
            content.append(marbles.get(i).toString())
                    .append(splitter)
                    .append(position.get(i).toString())
                    .append(splitter)
                    .append(action)
                    .append(splitter);

        }
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.ACTION_MARBLE.toString());
        map.put("marblesActions", content.toString());
        return MessageParser.createMessage(map);
    }*/

    public static String buildActionMarble(String action, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.ACTION_MARBLE.toString());
        map.put("marblesActions", action);
        return MessageParser.createMessage(map);
    }

}
