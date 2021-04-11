package it.polimi.ingsw.xmlParser;

import it.polimi.ingsw.PopeFavor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Integer.parseInt;

/**
 * This class is responsible of the creation of PopeFavors from a given XML file.
 * The class build three types of Favors
 */

public class PopeFavorParser extends Parser{

    /**
     * this method creates a list of PopeFavors from a XML file
     * @param file is the XML file
     * @return a LinkedList containing all the PopeFavors
     */
    private static LinkedList<PopeFavor> buildFavor(String file){
        LinkedList<PopeFavor> result = new LinkedList<>();
        try {
            NodeList favors = getNodes(file);
            for (int j = 0; j < favors.getLength(); j++) {
                Node el = favors.item(j);
                if (el.getNodeType() == Node.ELEMENT_NODE) {
                    Element favor = (Element) el;
                    result.add(new PopeFavor(parseInt(favor.getAttribute("value"))));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method returns the first PopeFavor of a FaithTrack
     * @param file is the XML file that contains the PopeFavor value
     * @return the first PopeFavor
     */
    public static PopeFavor parseFirst(String file){
        return buildFavor(file).get(0);
    }

    /**
     * this method returns the second PopeFavor of a FaithTrack
     * @param file is the XML file that contains the PopeFavor value
     * @return the second PopeFavor
     */
    public static PopeFavor parseSecond(String file){
        return buildFavor(file).get(1);
    }

    /**
     * this method returns the third PopeFavor of a FaithTrack
     * @param file is the XML file that contains the PopeFavor value
     * @return the third PopeFavor
     */
    public static PopeFavor parseThird(String file){
        return buildFavor(file).get(2);
    }
}
