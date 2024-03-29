package it.polimi.ingsw.xmlParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.xml.sax.SAXException;

import static java.lang.Integer.parseInt;

/**
 * this class gives the methods used to parse the configuration file
 * and to extract the main features of a game. This class works on files that
 * are contained in the Resource directory of the project
 */
public class ConfigurationParser{
    /**
     * this attribute represents the path of a generic XML file in the project
     */
    private static final String path = "/XML/";
    /**
     * this attribute represents the element faitTrack in the XML file
     */
    private static final String faithTrack = "faithTrack";
    /**
     * this attribute represents the element developmentCards in the XML file
     */
    private static final String developmentCards = "developmentCards";
    /**
     * this attribute represents the element leaderCards in the XML file
     */
    private static final String leaderCards = "leaderCards";

    /**
     * this attribute represents the element leaderCards in the XML file
     */
    private static final String actionTokens = "actionTokens";
    /**
     * this attribute represents the element leaderCards in the XML file
     */
    private static final String marketBoard = "marketBoard";

    /**
     *
     * this method opens a file and creates the root containing the elements that we want to parse.
     * @param file is the name of the file that contains the cards
     * @return the root of the file
     * @throws ParserConfigurationException if a DocumentBuild cannot be created
     * @throws SAXException if any parse errors occur
     * @throws IOException if any IO errors occur
     */

    protected static Element getRoot(String file) throws ParserConfigurationException, SAXException, IOException {

        InputStream inputStream = ConfigurationParser.class.getResourceAsStream(file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(inputStream);
        return document.getDocumentElement();
    }

    /**
     * this method returns the ID of an XML element
     * @param el is the target element
     * @return is the value of the id
     */
    protected static String getIDvalue(Element el){
        return el.getAttribute("id");
    }

    /**
     * this method returns the image path of an object from a XML file
     * @param el is the element containing the path
     * @return a String that represents the path
     */
    protected static String getImagePath(Element el){
        return el.getAttribute("image");
    }

    /**
     * This method extract the path of a configuration file from another XML file
     * @param file is the name of the XML that contains the configuration files
     * @param argument is the type of the target configuration file
     * @return the full path of the configuration file
     */
    private static String getPath(String file, String argument){
        file = path + file;
        String fullPath = path;
        try {
            Element name = (Element) getRoot(file).getElementsByTagName(argument).item(0);
            fullPath = fullPath + name.getAttribute("file");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return fullPath;
    }

    /**
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the configuration file that contains the FaithTrack characteristics
     */
    private static String getTrackPath(String file){
        return getPath(file, faithTrack);
    }

    /**
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the configuration file that contains the DevelopmentCards characteristics
     */
    private static String getDevCardsPath(String file){
        return getPath(file, developmentCards);
    }

    /**
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the configuration file that contains the LeaderCards characteristics
     */
    private static String getLeaderCardsPath(String file){
        return getPath(file, leaderCards);
    }

    /**
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the configuration file that contains the ActionTokens characteristics
     */
    private static String getActionTokensPath(String file){
        return getPath(file, actionTokens);
    }

    /**
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the configuration file that contains the MarketBoard characteristics
     */
    private static String getMarketBoardPath(String file){
        return getPath(file, marketBoard);
    }

    /**
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the maximum level of DevelopmentCards
     */
    public static int getMaxLevel(String file){
        int num=0;
        try {
            Element max = (Element) getRoot(path + file).getElementsByTagName("cardsLevel").item(0);
            num = parseInt(max.getAttribute("max"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return num;
    }

    /**
     * this method return the number of LeaderCards assigned to each player
     * @param file is the name of the XML that contains the configuration files
     * @return the number of slots
     */
    public static int getNumLeader(String file){
        int num=0;
        try {
            Element max = (Element) getRoot(path + file).getElementsByTagName("leader").item(0);
            num = parseInt(max.getAttribute("num"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return num;
    }

    /**
     * this method returns the number of Warehouse slots
     * @param file is the name of the XML that contains the configuration files
     * @return the number of slots
     */
    public static int getNumSlots(String file){
        int num=0;
        try {
            Element max = (Element) getRoot(path + file).getElementsByTagName("slot").item(0);
            num = parseInt(max.getAttribute("num"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return num;
    }

    /**
     * this method returns the capacity of Warehouse slots
     * @param file is the name of the XML that contains the configuration files
     * @return a LinkedList that contains the capacity of each default slot
     */

    public static LinkedList<Integer> getCapacityWarehouse(String file){
        file = path + file;
        LinkedList<Integer> result = new LinkedList<>();
        try {
            Element warehouse = (Element) getRoot(file).getElementsByTagName("warehouse").item(0);
            NodeList shelves = warehouse.getChildNodes();
            for (int i = 0; i < shelves.getLength(); i++) {
                Node shelf = shelves.item(i);
                if (shelf.getNodeType() == Node.ELEMENT_NODE) {
                    Element capacity = (Element) shelf;
                    result.add(parseInt(capacity.getAttribute("capacity")));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return result;
    }

    /**
     * this method returns the number of resources for the initialization of each player
     * @param file is the name of the XML that contains the configuration files
     * @return a LinkedList that contains the number of resources for the initialization of each player
     */
    public static LinkedList<Integer> getInitializationResources(String file){
        file = path + file;
        LinkedList<Integer> result = new LinkedList<>();
        try {
            Element initializationResources = (Element) getRoot(file).getElementsByTagName("initializationResources").item(0);
            NodeList players = initializationResources.getChildNodes();
            for (int i = 0; i < players.getLength(); i++) {
                Node player = players.item(i);
                if (player.getNodeType() == Node.ELEMENT_NODE) {
                    Element number = (Element) player;
                    result.add(parseInt(number.getAttribute("resources")));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return result;
    }

    /**
     * this method returns the number of faith points that have to be added to each player's faithTrack during initialization
     * @param file is the name of the XML that contains the configuration files
     * @return a LinkedList that contains the number of faith points that have to be added to each player's faithTrack during initialization
     */
    public static LinkedList<Integer> getInitializationFaith(String file){
        file = path + file;
        LinkedList<Integer> result = new LinkedList<>();
        try {
            Element initializationResources = (Element) getRoot(file).getElementsByTagName("initializationResources").item(0);
            NodeList players = initializationResources.getChildNodes();
            for (int i = 0; i < players.getLength(); i++) {
                Node player = players.item(i);
                if (player.getNodeType() == Node.ELEMENT_NODE) {
                    Element number = (Element) player;
                    result.add(parseInt(number.getAttribute("faith")));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return result;
    }


    /**
     * this function is used to create a set of DevelopmentCards from a given file name (full path is not needed).
     *
     * @param file iis the name of the XML that contains the configuration files
     * @return the LinkedList of DevelopmentCards created using the file
     */

    public static List<DevelopmentCard> parseDevelopmentCard(String file) {
        CardParser parser = CardParser.instance();
        file = getDevCardsPath(file);
        return parser.parseDevelopmentCard(file);
    }

    /**
     * this function is used to create a set of LeaderCards from a given file name (full path is not needed)
     *
     * @param file is the name of the XML that contains the configuration files
     * @return the LinkedList of LeaderCards created using the file
     */
    public static List<LeaderCard> parseLeaderCard(String file) {
        CardParser parser = CardParser.instance();
        file = getLeaderCardsPath(file);
        return parser.parseLeaderCard(file);
    }

    /**
     * this method creates a new FaithTrack from a given file name (full path is not needed)
     * @param file is the name of the XML that contains the configuration files
     * @return the requested FaithTrack
     */
    public static FaithTrack parseFaithTrack(String file){
        file = getTrackPath(file);
        return FaithTrackParser.buildFaithTrack(file);
    }

    /**
     * This method return the length of the FaithTrack
     * @param file is the name of the configuration file
     * @return the length of the FaithTrack
     */
    public static int parseTrackLength(String file){
        file = getTrackPath(file);
        return FaithTrackParser.getTrackLength(file);
    }

    /**
     * This method builds the path of a given FaithTrack
     * @param file is the XML file containing the characteristics of the given track
     * @return the requested track
     */
    public static ArrayList<Integer> parseTrack(String file){
        file = getTrackPath(file);
        return FaithTrackParser.buildTrack(file);
    }

    /**
     * this method returns all the Action Tokens contained in a XML file (full path is not required)
     * @param file is the configuration file
     * @return a LinkedList that contains all the tokens
     */
    public static LinkedList<Action> parseActionTokens(String file){
        ActionTokenParser parser = ActionTokenParser.instance();
        file = getActionTokensPath(file);
        return parser.buildActionTokens(file);
    }

    /**
     * this method return the number of rows of the market board
     * @param file is the name of the XML that contains the configuration files
     * @return the number of rows of the MarketBoard
     */
    public static int parseMarketRows(String file){
        MarketBoardParser parser = MarketBoardParser.instance();
        file = getMarketBoardPath(file);
        return parser.getMarketRows(file);
    }

    /**
     * this method return the number of rows of the market board
     * @param file is the name of the XML that contains the configuration files
     * @return the number of rows of the MarketBoard
     */
    public static int parseMarketColumns(String file){
        MarketBoardParser parser = MarketBoardParser.instance();
        file = getMarketBoardPath(file);
        return parser.getMarketColumns(file);
    }

    /**
     * this method return the Tray of the MarketBoard
     * @param file is the name of the XML that contains the configuration files
     * @return an ArrayList of Marbles that represent the Tray of the MarketBoard
     */
    public static ArrayList<Marble> parseMarketTray(String file){
        MarketBoardParser parser = MarketBoardParser.instance();
        file = getMarketBoardPath(file);
        return parser.buildMarketTray(file);
    }

    /**
     * this method creates a list of VaticanReportSections from a XML file
     * @param file is the XML file
     * @return a LinkedList containing all the VaticanReportSections
     */
    public static ArrayList<VaticanReportSection> parseReportSection(String file){
        file = getTrackPath(file);
        return FaithTrackParser.buildReportSection(file);
    }

    public static Production parsePersonalProduction(String file){
        file = path + file;
        CardParser parser = CardParser.instance();
        Production p = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        try {
            Element production = (Element) getRoot(file).getElementsByTagName("production").item(0);
            p = parser.buildProduction(production);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            notifyParsingError();
        }
        return p;
    }

    /**
     * this helper method is used to notify a parsing error and close the server.
     */
    protected static void notifyParsingError(){
        System.out.println("Cannot load the configurations of the game");
        System.exit(1);
    }

}

