package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Messages.Enumerations.TraySelection;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.Cards.Colors.*;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.*;

import java.util.*;
import java.util.function.Supplier;

import static java.lang.Integer.parseInt;

public class MessageUtilities {

    private static Map<String, Supplier<Resource>> resources = new HashMap<>();
    private static Map<String, Supplier<Marble>> marbles = new HashMap<>();
    private static Map<String, Supplier<CardColor>> cardColor;
    private static MessageUtilities instance;
    private static final String splitter = ":";

    private MessageUtilities() {
        resources.put("COIN", Coin::new);
        resources.put("SERVANT", Servant::new);
        resources.put("STONE", Stone::new);
        resources.put("FAITH", Faith::new);
        resources.put("SHIELD", Shield::new);

        marbles.put("MARBLEBLUE", MarbleBlue::new);
        marbles.put("MARBLEGRAY", MarbleGray::new);
        marbles.put("MARBLEPURPLE", MarblePurple::new);
        marbles.put("MARBLERED", MarbleRed::new);
        marbles.put("MARBLEWHITE", MarbleWhite::new);
        marbles.put("MARBLEYELLOW", MarbleYellow::new);

        cardColor = new HashMap<>();
        cardColor.put("GREEN", Green::new);
        cardColor.put("BLUE", Blue::new);
        cardColor.put("YELLOW", Yellow::new);
        cardColor.put("PURPLE", Purple::new);

    }

    public static MessageUtilities instance() {
        if (instance == null) {
            instance = new MessageUtilities();
        }
        return instance;
    }

    public Message.MessageType getType(String message) throws MalformedMessageException{
        String type = MessageParser.getMessageTag(message,"messageType");
        Message.MessageType messageType;
        try {
             messageType = Message.MessageType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new MalformedMessageException();
        }
        return messageType;
    }

    public TurnType getTurnType(String message, String tag) throws MalformedMessageException{
        String type = MessageParser.getMessageTag(message,tag);
        TurnType turnType;
        try {
            turnType = TurnType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new MalformedMessageException();
        }
        return turnType;
    }

    public TraySelection getTray(String message, String tag) throws MalformedMessageException{
        String type = MessageParser.getMessageTag(message,tag);
        TraySelection traySelection;
        try {
            traySelection = TraySelection.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new MalformedMessageException();
        }
        return traySelection;
    }

    public String getBody(String message)  throws MalformedMessageException{
        String body = MessageParser.getMessageTag(message,"body");
        if(body == null)
             throw new MalformedMessageException();
        return body;
    }

    public List<Integer> getShelves(String message, String tag) throws MalformedMessageException {

        String warehouse = MessageParser.getMessageTag(message,tag);

        List<Integer> shelves = new ArrayList<>();
        if(warehouse.length() == 0) return shelves;
        String[] string = warehouse.split(splitter);

        if(warehouse.equals("")) return shelves;

        if(string.length%2 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){
            try {
                shelves.add(parseInt(string[i*2]));
            }
            catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return shelves;
    }


    public List<Integer> getQuantity(String message, String tag) throws MalformedMessageException{

        String warehouse = MessageParser.getMessageTag(message,tag);

        List<Integer> quantity = new ArrayList<>();
        String[] string = warehouse.split(splitter);

        if(warehouse.equals("")){
            return quantity;
        }
        if(string.length%2 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){
            try {
                quantity.add(parseInt(string[(i * 2) + 1]));
            }
            catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return quantity;
    }


    public List<ResQuantity> getResQuantityList(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<ResQuantity> list = new ArrayList<>();
        String[] string = messageString.split(splitter);
        String key;
        int value;
        ResQuantity resQuantity;

        if(messageString.equals("")){
            return list;
        }

        if(string.length%2 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){

            key = string[i*2].toUpperCase();
            try {
                value = parseInt(string[(i*2)+1]);
            }catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}

            if(!resources.containsKey(key))
                throw new MalformedMessageException("Wrong resource name!");

            resQuantity = new ResQuantity(resources.get(key).get(), value);
            list.add(resQuantity);
        }
        return list;
    }

    /*public Map<Integer, ResQuantity> getResAndPosition(String message, String tag) throws MalformedMessageException{

        String content = MessageParser.getMessageTag(message, tag);
        Map<Integer, ResQuantity> map = new HashMap<>();
        String[] string = content.split(splitter);
        int position, quantity;
        String key;
        ResQuantity res;

        if(string.length%3 != 0){
            throw new MalformedMessageException();
        }

        for(int i=0; i<string.length/3; i++){
            key = string[i*3];
            try {
                position = parseInt(string[(i*3)+1]);
                quantity = parseInt(string[(i*3)+2]);
            }catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
            res = new ResQuantity(resources.get(key).get(), quantity);
            map.put(position, res);
        }
        return map;
    }*/

    public List<Marble> getMarbleFromAction(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<Marble> list = new ArrayList<>();
        String[] string = messageString.split(splitter);

        if(messageString.equals("")){
            return list;
        }

        if(string.length % 3 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/3; i++){

            if(!marbles.containsKey(string[i*3].toUpperCase()))
                throw new MalformedMessageException("Wrong marble name!");

            list.add(marbles.get(string[i*3].toUpperCase()).get());
        }
        return list;
    }

    public List<Marble> getMarbleList(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);

        List<Marble> list = new ArrayList<>();
        String[] string = messageString.split(splitter);
        System.out.println("Debug");
        if(messageString.equals("")){
            return list;
        }

        if(string.length == 0){
            return list;
        }

        for(int i=0; i<string.length; i++){
            if(!marbles.containsKey(string[i].toUpperCase()))
                throw new MalformedMessageException("Wrong marble name!");

            list.add(marbles.get(string[i].toUpperCase()).get());
        }
        return list;
    }


    public PlayerAction getAction(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);
        PlayerAction action;
        try {
            action = PlayerAction.valueOf(messageString.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new MalformedMessageException(e.getMessage());
        }
        return action;
    }

    public List<PlayerAction> getActions(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<PlayerAction> list = new ArrayList<>();
        String[] string = messageString.split(splitter);
        PlayerAction action;

        if(messageString.equals("")){
            return list;
        }

        if(string.length % 3 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/3; i++){

            try {
                action = PlayerAction.valueOf(string[(i*3)+1].toUpperCase());
            }catch (IllegalArgumentException e){
                throw new MalformedMessageException(e.getMessage());
            }

            list.add(action);
        }
        return list;
    }

    public List<ItemStatus> getStatusList(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message, tag);
        List<ItemStatus> list = new LinkedList<>();
        String[] string = messageString.split(splitter);
        ItemStatus item;

        if(messageString.equals("") || string.length == 0){
            return list;
        }
        for(int i=0; i<string.length; i++){
            try{
                item = ItemStatus.valueOf(string[i].toUpperCase());
            }catch(IllegalArgumentException e){
                throw new MalformedMessageException(e.getMessage());
            }
            list.add(item);
        }
        return list;
    }

    public List<Integer> getShelvesActions(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<Integer> list = new ArrayList<>();
        String[] string = messageString.split(splitter);

        if(messageString.equals("")){
            return list;
        }

        if(string.length % 3 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/3; i++){

            try {
                list.add(parseInt(string[(i*3)+2]));
            }
            catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return list;
    }

    public List<Resource> getResources(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<Resource> list = new ArrayList<>();
        if(messageString.length() == 0) return list;
        String[] string = messageString.split(splitter);
        String key;

        if(messageString.equals("")){
            return list;
        }

        if(string.length%2 != 0 || string.length == 0) {
            throw new MalformedMessageException();
        }

        for(int i=0; i<string.length/2; i++){
            key = string[(i*2)+1].toUpperCase();
            if(!resources.containsKey(key))
                throw new MalformedMessageException("Wrong resource name!");
            list.add(resources.get(key).get());
        }
        return list;
    }


    public CardColor getCardColor(String message, String tag) throws MalformedMessageException{

        String color = MessageParser.getMessageTag(message,tag);

        if(!cardColor.containsKey(color.toUpperCase()))
            throw new MalformedMessageException("Wrong card color name!");

        return cardColor.get(color.toUpperCase()).get();
    }

    public Map<Integer,String> getMapIntegerString(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        Map<Integer,String> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        int key;
        String value;

        if(messageString.equals("")){
            return map;
        }
        if(string.length%2 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){

            try {
                key = parseInt(string[i*2]);
                value = string[(i*2)+1];
                map.put(key,value);
            }
            catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return map;
    }

    public Map<String, Integer> getMapStringInteger(String message, String tag) throws MalformedMessageException {
        String content = MessageParser.getMessageTag(message,tag);
        Map<String, Integer> table = new HashMap<>();
        String[] string = content.split(splitter);

        if(content.equals("")){
            return table;
        }
        if(string.length%2 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){
            table.put(string[i*2], parseInt(string[i*2+1]));
            try {
                table.put(string[i*2], parseInt(string[i*2+1]));
            }
            catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return table;
    }

    public Map<Integer,Boolean> getMapIntegerBoolean(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        Map<Integer,Boolean> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        int key;
        boolean value;

        if(messageString.equals("")){
            return map;
        }

        if(string.length%2 != 0 || string.length == 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){

            try {
                key = parseInt(string[i*2]);
                value = Boolean.parseBoolean(string[(i*2)+1]);
                map.put(key,value);
            }catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return map;
    }

    public Map<Integer, ItemStatus> getMapIntegerItemStatus(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message, tag);
        Map<Integer, ItemStatus> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        ItemStatus item;
        int number;

        if(messageString.equals("")){
            return map;
        }
        if(string.length%2!=0 || string.length == 0) throw new MalformedMessageException();
        for(int i=0; i<string.length/2; i++){

            try{
                number = Integer.parseInt(string[i*2]);
            }catch(NumberFormatException e){
                throw new MalformedMessageException();
            }

            try{
                item = ItemStatus.valueOf(string[(i*2)+1].toUpperCase());
            }catch(IllegalArgumentException e){
                throw new MalformedMessageException(e.getMessage());
            }
            map.put(number, item);
        }
        return map;
    }

    public int getInteger(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);
        int i;
        try {
            i = parseInt(messageString);
        }
        catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        return i;
    }

    public String getString(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);
        if(messageString == null)
            throw new MalformedMessageException("ParseInt fail!");
        return messageString;
    }

    public boolean getBoolean(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);
        return Boolean.parseBoolean(messageString);
    }

    public List<String> getListString(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);

        String[] string = messageString.split(splitter);
        return new ArrayList<>(Arrays.asList(string));
    }
}
