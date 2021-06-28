package it.polimi.ingsw.xmlParser;

import it.polimi.ingsw.Model.MarketBoard.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.Integer.parseInt;

/**
 * This class is responsible of the creation of the MarketBoard from a given XML file.
 * In order to build the right type of Marble, the class leverage two maps.
 * This class uses the Singleton design pattern
 */
public class MarketBoardParser{
    /**
     * this attribute represents the XML element containing the columns of the MarketBoard
     */
    private static final String c = "columns";
    /**
     * this attribute represents the XML element containing the rows of the MarketBoard
     */
    private static final String r = "rows";
    /**
     * this attribute is a map of the available Marbles
     */
    private final Map<String, Supplier<Marble>> marbleMap;
    /**
     * this attribute is the instance of the Singleton pattern
     */
    private static MarketBoardParser instance;

    /**
     * the constructor of MarketBoard is private and initializes the maps
     */
    private MarketBoardParser(){
        marbleMap = new HashMap<>();
        marbleMap.put("WHITE", MarbleWhite:: new);
        marbleMap.put("RED", MarbleRed:: new);
        marbleMap.put("BLUE", MarbleBlue:: new);
        marbleMap.put("YELLOW", MarbleYellow:: new);
        marbleMap.put("PURPLE", MarblePurple::new);
        marbleMap.put("GRAY", MarbleGray::new);
    }

    /**
     * this method is used to get an instance of MarketBoardParser.
     *
     * @return a new instance of MarketBoardParser if instance == null. If a MarketBoardParser
     * has already been created, the method return instance.
     */
    public static MarketBoardParser instance(){
        if (instance == null) {
            instance = new MarketBoardParser();
        }
        return instance;
    }

    /**
     * this method return a dimension of the MarketBoard
     * @param file is the XML file that contains the MarketBoard characteristics
     * @param d is the type of dimension that the method will return
     * @return is a MarketBoard dimension
     */
    private int getMarketDimensions(String file, String d){
        int nrows = 0;
        try {
            return parseInt(ConfigurationParser
                    .getRoot(file)
                    .getAttribute(d));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            ConfigurationParser.notifyParsingError();
        }
        return nrows;
    }

    /**
     * this method returns the number of rows of the MarketBoard
     * @param file is the XML file that contains the MarketBoard characteristics
     * @return the number of rows of the MarketBoard
     */
    public int getMarketRows(String file){
        return getMarketDimensions(file, r);
    }

    /**
     * this method returns the number of columns of the MarketBoard
     * @param file is the XML file that contains the MarketBoard characteristics
     * @return the number of columns of the MarketBoard
     */
    public int getMarketColumns(String file){
        return getMarketDimensions(file, c);
    }

    /**
     *
     * @param marble is an element of the XML file
     * @return the Marble corresponding to that element
     */
    private Marble buildMarble(Element marble){
        return marbleMap.get(marble.getAttribute("color")).get();
    }

    /**
     * this method return the Tray of a MarketBoard
     * @param file is the file that contains the characteristics of a MarketBoard
     * @return an ArrayList of Marbles that represents the tray of the MarketBoard
     */
    public ArrayList<Marble> buildMarketTray(String file){
        ArrayList<Marble> tray = new ArrayList<>();
        try {
            NodeList marbles = ConfigurationParser
                    .getRoot(file)
                    .getChildNodes();
            for (int i = 0; i < marbles.getLength(); i++) {
                Node marble = marbles.item(i);
                if (marble.getNodeType() == Node.ELEMENT_NODE) {
                    tray.add(buildMarble((Element) marble));
                }
            }
        if(tray.size()!=getMarketRows(file)*getMarketColumns(file)+1) throw new IOException("Incoherent MarketBoard");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            ConfigurationParser.notifyParsingError();
        }
        return tray;

    }
}
