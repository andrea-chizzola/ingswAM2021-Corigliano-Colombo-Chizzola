package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Model.Cards.Colors.*;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.Integer.parseInt;

public class MessageUtilities {

    private static Map<String, Supplier<Resource>> resources = new HashMap<>();
    private static Map<String, Supplier<Marble>> marbles = new HashMap<>();
    private static Map<String, Supplier<CardColor>> cardColor;
    private static MessageUtilities instance;
    private static final String splitter = ":";

    private MessageUtilities() {
        resources.put("COINS", Coin::new);
        resources.put("SERVANTS", Servant::new);
        resources.put("STONES", Stone::new);
        resources.put("FAITH", Faith::new);
        resources.put("SHIELDS", Shield::new);

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

    public TurnType getTurnType(String message,String tag) throws MalformedMessageException{
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

    public Tray getTray(String message,String tag) throws MalformedMessageException{
        String type = MessageParser.getMessageTag(message,tag);
        Tray tray;
        try {
            tray = Tray.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new MalformedMessageException();
        }
        return tray;
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
        String[] string = warehouse.split(splitter);

        if(string.length%2 != 0)
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

        if(string.length%2 != 0)
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

        if(string.length%2 != 0)
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

    public List<Marble> getMarbles(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<Marble> list = new ArrayList<>();
        String[] string = messageString.split(splitter);

        if(string.length % 3 != 0 )
            throw new MalformedMessageException();

        for(int i=0; i<string.length/3; i++){

            if(!this.marbles.containsKey(string[i*3].toUpperCase()))
                throw new MalformedMessageException("Wrong marble name!");

            list.add(this.marbles.get(string[i*3].toUpperCase()).get());
        }
        return list;
    }


    public Action getAction(String message, String tag) throws MalformedMessageException{
        String messageString = MessageParser.getMessageTag(message,tag);
        Action action;
        try {
            action = Action.valueOf(messageString.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new MalformedMessageException(e.getMessage());
        }
        return action;
    }

    public List<Action> getActions(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<Action> list = new ArrayList<>();
        String[] string = messageString.split(splitter);
        Action action;

        if(string.length % 3 != 0 )
            throw new MalformedMessageException();

        for(int i=0; i<string.length/3; i++){

            try {
                action = Action.valueOf(string[(i*3)+1].toUpperCase());
            }catch (IllegalArgumentException e){
                throw new MalformedMessageException(e.getMessage());
            }

            list.add(action);
        }
        return list;
    }

    public List<Integer> getShelvesActions(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        List<Integer> list = new ArrayList<>();
        String[] string = messageString.split(splitter);

        if(string.length % 3 != 0 )
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
        String[] string = messageString.split(splitter);
        String key;

        if(string.length%2 != 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){
            key = string[(i*2)+1].toUpperCase();
            if(!resources.containsKey(key))
                throw new MalformedMessageException("Wrong resource name!");
            list.add(this.resources.get(key).get());
        }
        return list;
    }


    public CardColor getCardColor(String message, String tag) throws MalformedMessageException{

        String color = MessageParser.getMessageTag(message,tag);

        if(!cardColor.containsKey(color.toUpperCase()))
            throw new MalformedMessageException("Wrong card color name!");

        return this.cardColor.get(color.toUpperCase()).get();
    }

    public Map<Integer,String> getMapIntegerString(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        Map<Integer,String> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        int key;
        String value;

        if(string.length%2 != 0)
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

    public Map<Integer,Boolean> getMapIntegerBoolean(String message, String tag) throws MalformedMessageException{

        String messageString = MessageParser.getMessageTag(message,tag);

        Map<Integer,Boolean> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        int key;
        Boolean value;

        if(string.length%2 != 0)
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

    //MARKET_SELECTION
    public Tray getTrayMARKETSELECTION(String message) throws MalformedMessageException{
        return getTray(message,"tray");
    }
    public int getNumberMARKETSELECTION(String message) throws MalformedMessageException {
        return getInteger(message,"number");
    }

    //ACTION
    public List<Action> getActionsACTION(String message) throws MalformedMessageException{
        return getActions(message,"marblesActions");
    }
    public List<Marble> getMarblesACTION(String message) throws MalformedMessageException{
        return getMarbles(message, "marblesActions");
    }
    public List<Integer> getShelvesACTION(String message) throws MalformedMessageException{
        return getShelvesActions(message,"marblesActions");
    }

    //BUY_CARD
    public int getSlotBUYCARD(String message) throws MalformedMessageException{
        return getInteger(message,"slot");
    }
    public int getLevelBUYCARD(String message) throws MalformedMessageException{
        return getInteger(message,"level");
    }
    public CardColor getColorBUYCARD(String message) throws MalformedMessageException {
        return getCardColor(message,"color");
    }
    public String getIDBUYCARD(String message) throws MalformedMessageException {
        return getString(message,"ID");
    }
    public List<Integer> getShelvesBUYCARD(String message) throws MalformedMessageException {
        return getShelves(message,"warehouse");
    }
    public List<Integer> getQuantityBUYCARD(String message) throws MalformedMessageException {
        return getQuantity(message,"warehouse");
    }
    public List<ResQuantity> getStrongBoxBUYCARD(String message) throws MalformedMessageException {
        return getResQuantityList(message,"strongBox");
    }

    //DO_PRODUCTION
    public boolean isPersonalProductionDOPRODUCTION(String message) throws MalformedMessageException {
        return getBoolean(message,"personalProduction");
    }
    public Map<Integer, String> getDevelopmentCardsDOPRODUCTION(String message) throws MalformedMessageException{
        return getMapIntegerString(message, "developmentCards");
    }
    public Map<Integer, String> getLeaderCardsDOPRODUCTION(String message) throws MalformedMessageException{
        return getMapIntegerString(message, "leaderCards");
    }
    public List<ResQuantity> getChosenProductsDOPRODUCTION(String message) throws MalformedMessageException {
        return getResQuantityList(message,"chosenProducts");
    }
    public List<ResQuantity> getChosenMaterialsDOPRODUCTION(String message) throws MalformedMessageException {
        return getResQuantityList(message,"chosenMaterials");
    }
    public List<Integer> getShelvesDOPRODUCTION(String message) throws MalformedMessageException {
        return getShelves(message,"warehouse");
    }
    public List<Integer> getQuantityDOPRODUCTION(String message) throws MalformedMessageException {
        return getQuantity(message,"warehouse");
    }
    public List<ResQuantity> getStrongBoxDOPRODUCTION(String message) throws MalformedMessageException {
        return getResQuantityList(message,"strongBox");
    }


    //LEADER_ACTION
    public Map<Integer, String> getLeaderCardsLEADERACTION(String message) throws MalformedMessageException{
        return getMapIntegerString(message, "leaderCards");
    }
    public Action getActionLEADERACTION(String message) throws MalformedMessageException{
        return getAction(message,"action");
    }

    //RESOURCE
    public List<Resource> getResourcesRESOURCE(String message) throws MalformedMessageException {
        return getResources(message,"resources");
    }
    public List<Integer> getShelvesRESOURCES(String message) throws MalformedMessageException {
        return getShelves(message,"resources");
    }

    //SELECTED_TURN
    public TurnType getTurnTypeSELECTEDTURN(String message) throws MalformedMessageException {
        return getTurnType(message,"turnType");
    }

    //SWAP
    public int getSourceSWAP(String message) throws MalformedMessageException {
        return getInteger(message,"source");
    }
    public int getTargetSWAP(String message) throws MalformedMessageException {
        return getInteger(message,"target");
    }

    //UPDATE_LEADER_CARDS
    public Map<Integer, Boolean> getLeaderStatusUPDATELEADERCARDS(String message) throws MalformedMessageException {
        return getMapIntegerBoolean(message,"leaderStatus");
    }
    public Map<Integer, String> getLeaderCardsUPDATELEADERCARDS(String message) throws MalformedMessageException{
        return getMapIntegerString(message, "leaderCards");
    }

}
