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

    public List<Integer> getShelves(String warehouse) throws MalformedMessageException {

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


    public List<Integer> getQuantity(String warehouse) throws MalformedMessageException{

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


    public List<ResQuantity> getResQuantityList(String messageString) throws MalformedMessageException{

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

    public List<Marble> getMarbles(String messageString) throws MalformedMessageException{

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

    public List<MessageAction.Action> getActions(String messageString) throws MalformedMessageException{

        List<MessageAction.Action> list = new ArrayList<>();
        String[] string = messageString.split(splitter);
        MessageAction.Action action;

        if(string.length % 3 != 0 )
            throw new MalformedMessageException();

        for(int i=0; i<string.length/3; i++){

            try {
                action = MessageAction.Action.valueOf(string[(i*3)+1].toUpperCase());
            }catch (IllegalArgumentException e){
                throw new MalformedMessageException(e.getMessage());
            }

            list.add(action);
        }
        return list;
    }

    public List<Integer> getShelvesActions(String messageString) throws MalformedMessageException{

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

    public List<Resource> getResources(String messageString) throws MalformedMessageException{

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


    public CardColor getCardColor(String color) throws MalformedMessageException{

        if(!cardColor.containsKey(color.toUpperCase()))
            throw new MalformedMessageException("Wrong card color name!");

        return this.cardColor.get(color.toUpperCase()).get();
    }

    public Map<Integer,String> getMapIntegerString(String messageString) throws MalformedMessageException{

        Map<Integer,String> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        int key;
        String value;

        if(string.length%2 != 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){

            try {
                key = parseInt(string[i]);
                value = string[i+1];
                map.put(key,value);
            }
            catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return map;
    }

    public Map<Integer,Boolean> getMapIntegerBoolean(String messageString) throws MalformedMessageException{

        Map<Integer,Boolean> map = new HashMap<>();
        String[] string = messageString.split(splitter);
        int key;
        Boolean value;

        if(string.length%2 != 0)
            throw new MalformedMessageException();

        for(int i=0; i<string.length/2; i++){

            try {
                key = parseInt(string[i]);
                value = Boolean.parseBoolean(string[i+1]);
                map.put(key,value);
            }catch (NumberFormatException e){throw new MalformedMessageException("ParseInt fail!");}
        }
        return map;
    }


}
