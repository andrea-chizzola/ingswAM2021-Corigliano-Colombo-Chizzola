package it.polimi.ingsw.xmlParser;

import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * This class is responsible of the creation of a FaithTrack from a given XML file.
 */

public class FaithTrackParser{

    /**
     * this method creates a list of VaticanReportSections from a XML file
     * @param file is the XML file
     * @return a LinkedList containing all the VaticanReportSections
     */
    protected static ArrayList<VaticanReportSection> buildReportSection(String file){
        ArrayList<VaticanReportSection> result = new ArrayList<>();
        try {
            NodeList sections = ConfigurationParser.getRoot(file)
                    .getElementsByTagName("vaticanReport")
                    .item(0).getChildNodes();
            for (int j = 0; j < sections.getLength(); j++) {
                Node el = sections.item(j);
                if (el.getNodeType() == Node.ELEMENT_NODE) {
                    Element section = (Element) el;
                    int start = parseInt(section.getAttribute("start"));
                    int end = parseInt(section.getAttribute("end"));
                    int points = parseInt(section.getAttribute("value"));
                    result.add(new VaticanReportSection(start, end, points));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            ConfigurationParser.notifyParsingError();
        }
        return result;
    }

    /**
     * This method builds the path of a given FaithTrack
     * @param file is the XML file containing the characteristics of the given track
     * @return the requested track
     */
    protected static ArrayList<Integer> buildTrack(String file){
        ArrayList<Integer> result = new ArrayList<>();
        try {
            NodeList positions = ConfigurationParser.getRoot(file)
                    .getElementsByTagName("track")
                    .item(0).getChildNodes();
            for (int j = 0; j < positions.getLength(); j++) {
                Node el = positions.item(j);
                if (el.getNodeType() == Node.ELEMENT_NODE) {
                    Element p = (Element) el;
                    result.add(parseInt(p.getAttribute("points")));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            ConfigurationParser.notifyParsingError();
        }
        return result;
    }

    /**
     * this method creates a new FaithTrack from a given file (full path is needed)
     * @param file is the XML file that contains the characteristics of the FaithTrack
     * @return the requested FaithTrack
     */
    public static FaithTrack buildFaithTrack(String file){
        return new FaithTrack(buildTrack(file), buildReportSection(file));
    }

    /**
     * This method return the length of the FaithTrack
     * @param file is the name of the configuration file
     * @return the length of the FaithTrack
     */
    protected static int getTrackLength(String file){
        int length = 0;
        try {
            return parseInt(ConfigurationParser
                    .getRoot(file)
                    .getAttribute("length"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            ConfigurationParser.notifyParsingError();
        }
        return length;
    }
}
