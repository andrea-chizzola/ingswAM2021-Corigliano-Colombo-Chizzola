package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MessageFactory {
    private static final String splitter = ":";

    public static String buildConnection(String body, String nickname, Boolean gameHost, Integer playersNumber) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.CONNECTION.toString());
        map.put("nickname", nickname);
        map.put("gameHost", gameHost.toString());
        map.put("playersNumber", playersNumber.toString());
        return MessageParser.createMessage(map);
    }

    public static String buildDisconnection(String body, String nickname) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.DISCONNECTION.toString());
        map.put("nickname", nickname);
        return MessageParser.createMessage(map);
    }

    public static String buildReconnection(String body, String nickname) throws MalformedMessageException{
        Map<String, String > map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.RECONNECTION.toString());
        map.put("nickname", nickname);
        return MessageParser.createMessage(map);
    }

    public static String buildReply(Boolean type, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.REPLY.toString());
        map.put("correct", type.toString());
        return MessageParser.createMessage(map);
    }

    public static String buildCurrentPlayer(String playerID, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.GAME_STATUS.toString());
        map.put("currentPlayer", playerID);
        return MessageParser.createMessage(map);
    }

    public static String buildEndGame(Map<String, Integer> points, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        StringBuilder content = new StringBuilder();
        for(String s : points.keySet()){
            content.append(s).append(splitter).append(points.get(s)).append(splitter);
        }
        String string = content.substring(0, content.length()-1);
        map.put("body", body);
        map.put("messageType", Message.MessageType.END_GAME.toString());
        map.put("points", string);
        return MessageParser.createMessage(map);
    }

    public static String buildBoxUpdate(List<ResQuantity> warehouse, List<ResQuantity> strongbox, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.BOX_UPDATE.toString());
        map.put("warehouse", buildBoxString(warehouse));
        map.put("strongbox", buildBoxString(strongbox));
        return MessageParser.createMessage(map);
    }

    private static String buildBoxString(List<ResQuantity> box){
        StringBuilder content = new StringBuilder();
        for(int i=0; i<box.size(); i++){
            content.append(box.get(i).getResource().toString().toLowerCase())
                    .append(splitter).append(box.get(i).getQuantity()).append(splitter);
        }
        return content.substring(0, content.length()-1);
    }

    public static String buildSlotsUpdate(Map<Integer, String> devCards, String body) throws MalformedMessageException {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SLOTS_UPDATE.toString());
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
        return content.substring(0, content.length()-1);
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
        return content.substring(0, content.length()-1);
    }

    public static String buildSelectedMarbles(List<Marble> selection, List<Marble> candidates, String body)
            throws MalformedMessageException{
        String selectionString = buildMarbleList(selection), candidateString = buildMarbleList(candidates);
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("messageType", Message.MessageType.SELECTED_MARBLES.toString());
        map.put("marbles", selectionString);
        map.put("candidates", candidateString);
        return MessageParser.createMessage(map);
    }

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
                    .append(splitter);
        }
        map.put("faith", faithString.substring(0, faithString.length()-1));

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
            content.append(items.toString())
                    .append(splitter);
        }
        return content.substring(0, content.length()-1);
    }
}
