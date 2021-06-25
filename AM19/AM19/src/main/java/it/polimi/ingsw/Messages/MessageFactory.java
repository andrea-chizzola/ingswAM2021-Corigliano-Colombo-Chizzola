package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Boards.TurnType;
import it.polimi.ingsw.Model.Cards.Colors.DevColor;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MessageFactory {
    private static final String splitter = ":";


    /**
     * Creates a new exit message as an XML string
     * @param body represents the body of the message
     * @return a new exit message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildExit(String body) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.EXIT.toString());
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new connection message as an XML string
     * @param body represents the body of the message
     * @param nickname represents the player's chosen nickname
     * @param gameHost indicates if the player wants to create a new game or join an existing one
     * @param playersNumber represents the number of players associated to the new game (considered only when a new game is created)
     * @return a new connection message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildConnection(String body, String nickname, Boolean gameHost, Integer playersNumber) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.CONNECTION.toString());
        map.put("player", nickname);
        map.put("gameHost", gameHost.toString());
        map.put("playersNumber", playersNumber.toString());
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new disconnection message as an XML string
     * @param body represents the body of the message
     * @param nickname represents the disconnected player's nickname
     * @return a new disconnection message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildDisconnection(String body, String nickname) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.DISCONNECTION.toString());
        map.put("player", nickname);
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new reconnection message as an XML string
     * @param body represents the body of the message
     * @param nickname represents the reconnected player's nickname
     * @return a new reconnection message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildReconnection(String body, String nickname) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.RECONNECTION.toString());
        map.put("player", nickname);
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new game status message as an XML string
     * @param body represents the body of the message
     * @param nickName represents the player's nickname
     * @param state represents the turn type
     * @return a new game status message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildGameStatus(String body, String nickName, TurnType state) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.GAME_STATUS.toString());
        map.put("player", nickName);
        map.put("state", state.name());
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new current player message as an XML string
     * @param playerID represent the nickname of the current player
     * @param turns contains the available turns
     * @param body represents the body of the message
     * @return a new current player message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
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


    /**
     * Creates a new reply message as an XML string
     * @param type indicates if the message is a confirm or an error
     * @param body represents the body of the message
     * @param nickName represents the player's nickname
     * @return a new reply message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildReply(Boolean type, String body, String nickName) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.REPLY.toString());
        map.put("correct", type.toString());
        map.put("player", nickName);
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new start game message as an XML string
     * @param body represents the body of the message
     * @param nicknames contains the nicknames associated to the new game
     * @return a new start game message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
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

    /**
     * Creates a new selected marbles message as an XML string
     * @param selection contains the selected marbles
     * @param candidates contains the candidate marbles
     * @param player represents the player's nickname
     * @param body represents the body of the message
     * @return a new selected marbles message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
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

    /**
     * Creates a new end game message as an XML string
     * @param points contains the final score associated to ech player
     * @param body represents the body of the message
     * @param winner is the nickname of the winner
     * @return a new end game message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildEndGame(Map<String, Integer> points, String winner, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        StringBuilder content = new StringBuilder();
        for(String s : points.keySet()){
            content.append(s).append(splitter).append(points.get(s)).append(splitter);
        }
        String string = content.length()==0 ? "" : content.substring(0, content.length()-1);
        map.put("body", body);
        map.put("messageType", Message.MessageType.END_GAME.toString());
        map.put("points", string);
        map.put("winner", winner);
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new box update message as an XML string
     * @param warehouse indicates the resources contained inside the warehouse
     * @param strongbox indicates the resources contained inside the strongbox
     * @param player represents the player's nickname
     * @param body represents the body of the message
     * @return a new box update message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildBoxUpdate(List<ResQuantity> warehouse, List<ResQuantity> strongbox, String player, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.BOX_UPDATE.toString());
        map.put("player", player);
        map.put("warehouse", buildResQuantity(warehouse));
        map.put("strongbox", buildResQuantity(strongbox));
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new ResQuantity update
     * @param box contains the resources associated to either the strongbox or the warehouse
     * @return a new ResQuantity update following the convention used inside the messages
     */
    private static String buildResQuantity(List<ResQuantity> box){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<box.size(); i++){
            content.append(box.get(i).getResource().toString().toLowerCase())
                    .append(splitter).append(box.get(i).getQuantity()).append(splitter);
        }
        if(content.length()==0) return content.toString();
        return content.substring(0, content.length()-1);
    }

    /**
     * Creates a new slots update message as an XML string
     * @param devCards contains the card associated to each slot
     * @param player represents the player's nickname
     * @param body represents the body of the message
     * @return a new slots update message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildSlotsUpdate(Map<Integer, String> devCards, String player, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SLOTS_UPDATE.toString());
        map.put("player", player);
        map.put("devCards", buildIDStringMap(devCards));
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new decks update message as an XML string
     * @param devCards contains the development cards available in the shared deck
     * @param body represents the body of the message
     * @return a new decks update message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildDecksUpdate(Map<Integer, String> devCards, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.DECKS_UPDATE.toString());
        map.put("devCards", buildIDStringMap(devCards));
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new Id - String map
     * @param target associates each card to the selected slot
     * @return a string containing each card to the selected slot
     */
    private static String buildIDStringMap(Map<Integer, String> target){
        StringBuilder content = new StringBuilder();
        for(int i : target.keySet()){
            content.append(i)
                    .append(splitter)
                    .append(target.get(i)).append(splitter);
        }
        return content.length()==0 ? "" : content.substring(0, content.length()-1);
    }

    /**
     * Creates a new update Lorenzo message as an XML string
     * @param tokenID represents the used action token
     * @param body represents the body of the message
     * @return a new update Lorenzo message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildUpdateLorenzo(String tokenID, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.TOKEN_UPDATE.toString());
        map.put("token", tokenID);
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new update market message as an XML string
     * @param tray contains the marbles selected form the market
     * @param body represents the body of the message
     * @return a new update market message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildUpdateMarket(List<Marble> tray, String body) throws MalformedMessageException {
        String content = buildMarbleList(tray);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.MARKET_UPDATE.toString());
        map.put("market", content);
        return MessageParser.createMessage(map);
    }

    /**
     *
     * @param target contains the selected marbles
     * @return a new marble list following the convention used inside the messages
     */
    private static String buildMarbleList(List<Marble> target){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<target.size(); i++){
            content.append(target.get(i).toString())
                    .append(splitter);
        }
        if(content.length()==0) return content.toString();
        return content.substring(0, content.length()-1);
    }

    /**
     * Creates a new faith update message as an XML string
     * @param faith contains the position of each player inside the faith track
     * @param sections indicates the status of each section
     * @param faithLorenzo contains Lorenzo's inside the faith track
     * @param sectionsLorenzo indicates the status of each Lorenzo's section
     * @param body represents the body of the message
     * @return a new faith update message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections
            , Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.FAITH_UPDATE.toString());

        String itemString;

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


    /**
     *
     * @param items represents the status of each section
     * @return a string containing the status of each section
     */
    private static String buildStatusList(List<ItemStatus> items){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<items.size(); i++){
            content.append(items.get(i).toString())
                    .append(splitter);
        }
        return content.length()==0 ? "" : content.substring(0, content.length()-1);
    }

    /**
     * Creates a new Leader update message as an XML string
     * @param cards contains the leader cards associated to the player
     * @param status represents the status of each leader card
     * @param player represents the player's nickname
     * @param body represents the body of the message
     * @return a new Leader update message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
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

    /**
     *
     * @param target represents the status of each leader card
     * @return a string containing the status of each leader card
     */
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

    /**
     * Creates a new selected resources message as an XML string
     * @param content contains the selected resources
     * @param body represents the body of the message
     * @return a new selected resources message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildSelectedResources(String content, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.RESOURCE.toString());
        map.put("resources", content);
        return MessageParser.createMessage(map);
    }


    /**
     * Creates a new buy card message as an XML string
     * @param color represents the selected card's color
     * @param level represents the selected card's level
     * @param slot indicates the slot to insert the selected card
     * @param id represents the selected card's id
     * @param body represents the body of the message
     * @param warehouse contains the resources taken from the warehouse
     * @param strongbox contains the resources taken from the strongbox
     * @return a new buy card message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
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


    /**
     * Creates a new do production message as an XML string
     * @param personal indicates the player's decision
     * @param developments contains the selected leader cards
     * @param leaders contains the selected development cards
     * @param customMaterials contains the custom materials
     * @param customProducts contains the custom products
     * @param warehouse contains the selected resources from the warehouse
     * @param strongbox contains the selected resources from the strongbox
     * @param body represents the body of the message
     * @return a new do production message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
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

    /**
     * Creates a new leader action message as an XML string
     * @param id represents the leader card
     * @param position represents the leader card position
     * @param action represents the performed action
     * @param body represents the body of the message
     * @return a new leader action message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildLeaderAction(String id, int position, String action, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.LEADER_ACTION.toString());
        map.put("leaderCards", position+":"+id);
        map.put("action", action);
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new swap message as an XML string
     * @param source represents the starting position
     * @param target represents the final position
     * @param body represents the body of the message
     * @return a new swap message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildSwap(int source, int target, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SWAP.toString());
        map.put("source", Integer.toString(source));
        map.put("target", Integer.toString(target));
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new market selection message as an XML string
     * @param traySelection contains the selected tray
     * @param position contains the selected position
     * @param body represents the body of the message
     * @return a new market selection message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildMarketSelection(String traySelection, int position, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.MARKET_SELECTION.toString());
        map.put("tray", traySelection);
        map.put("number", Integer.toString(position));
        return MessageParser.createMessage(map);
    }

    /**
     * Creates a new action marble message as an XML string
     * @param action represents the performed action
     * @param body represents the body of the message
     * @return a new action marble message
     * @throws MalformedMessageException if an error occurs during the creation of the message
     */
    public static String buildActionMarble(String action, String body)
            throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.ACTION_MARBLE.toString());
        map.put("marblesActions", action);
        return MessageParser.createMessage(map);
    }

}
