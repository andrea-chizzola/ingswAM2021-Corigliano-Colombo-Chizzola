package it.polimi.ingsw.xmlParser;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.ActionTokens.Discard;
import it.polimi.ingsw.Model.ActionTokens.MoveAndShuffle;
import it.polimi.ingsw.Model.ActionTokens.MoveBlack;
import it.polimi.ingsw.Model.Cards.Colors.*;
import org.w3c.dom.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import org.xml.sax.SAXException;

import static java.lang.Integer.parseInt;

/**
 * This class is responsible of the creation of ActionTokens from a given XML file.
 * In order to build the right type of  cardColor, the class leverage a maps.
 * This class uses the Singleton design pattern
 */
public class ActionTokenParser{

    private static Map<String, Supplier<CardColor>> cardColor;
    /**
     * this attribute is the instance of the Singleton pattern
     */
    private static ActionTokenParser instance;

    /**
     * the constructor of ActionTokenParser is private and initializes the maps
     */
    private ActionTokenParser() {
        cardColor = new HashMap<>();
        cardColor.put("GREEN", Green::new);
        cardColor.put("BLUE", Blue::new);
        cardColor.put("YELLOW", Yellow::new);
        cardColor.put("PURPLE", Purple::new);
    }
    /**
     * this method is used to get an instance of ActionTokenParser.
     *
     * @return a new instance of CardParser if instance == null. If a CardParser has already been created, the method
     * return instance.
     */
    public static ActionTokenParser instance() {
        if (instance == null) {
            instance = new ActionTokenParser();
        }
        return instance;
    }

    /**
     * this method creates all the Discard tokens contained in the XML file
     * @param tokens is the NodeList containing all the tokens
     * @return a LinkedList that contains all the tokens
     */
    private LinkedList<Action> buildDiscard(NodeList tokens){
        LinkedList<Action> result = new LinkedList<>();
        for (int i = 0; i < tokens.getLength(); i++) {
            Node token = tokens.item(i);
            if (token.getNodeType() == Node.ELEMENT_NODE) {
                Element action = (Element) token;
                String id = ConfigurationParser.getIDvalue(action);
                String image = ConfigurationParser.getImagePath(action);
                result.add(new Discard(getCardColor(action), getAmount(action), id, image));
            }
        }
        return result;
    }

    /**
     * this method creates all the Move tokens contained in the XML file
     * @param tokens is the NodeList containing all the tokens
     * @return a LinkedList that contains all the tokens
     */
    private LinkedList<Action> buildMove(NodeList tokens){
        LinkedList<Action> result = new LinkedList<>();
        for (int i = 0; i < tokens.getLength(); i++) {
            Node token = tokens.item(i);
            if (token.getNodeType() == Node.ELEMENT_NODE) {
                Element action = (Element) token;
                String id = ConfigurationParser.getIDvalue(action);
                String image = ConfigurationParser.getImagePath(action);
                result.add(new MoveBlack(getAmount(action), id, image));
            }
        }
        return result;
    }

    /**
     * this method creates all the MoveAndShuffle tokens contained in the XML file
     * @param tokens is the NodeList containing all the tokens
     * @return a LinkedList that contains all the tokens
     */
    private LinkedList<Action> buildMoveAndShuffle(NodeList tokens){
        LinkedList<Action> result = new LinkedList<>();
        for (int i = 0; i < tokens.getLength(); i++) {
            Node token = tokens.item(i);
            if (token.getNodeType() == Node.ELEMENT_NODE) {
                Element action = (Element) token;
                String id = ConfigurationParser.getIDvalue(action);
                String image = ConfigurationParser.getImagePath(action);
                result.add(new MoveAndShuffle(getAmount(action), id, image));
            }
        }
        return result;
    }

    /**
     * this method returns all the Action Tokens contained in a XML file (full path is required)
     * @param file is the name of the file that contains the characteristics of the tokens
     * @return a LinkedList that contains all the tokens
     */
    public LinkedList<Action> buildActionTokens(String file){
        LinkedList<Action> result = new LinkedList<>();

        try{
            Element root = ConfigurationParser.getRoot(file);
            result.addAll(buildDiscard(root.getElementsByTagName("discard").item(0).getChildNodes()));
            result.addAll(buildMove(root.getElementsByTagName("move").item(0).getChildNodes()));
            result.addAll(buildMoveAndShuffle(root.getElementsByTagName("moveAndShuffle").item(0).getChildNodes()));

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
       return result;
    }

    /**
     *
     * @param token an XML element that contains the color of a card
     * @return an instance of CardColor of the given color
     */
    private CardColor getCardColor(Element token) {
        return cardColor.get(token.getAttribute("color")).get();
    }

    /**
     *
     * @param token an XML element that contains an amount
     * @return an the amount
     */
    private int getAmount(Element token) {
        return parseInt(token.getAttribute("amount"));
    }


}
