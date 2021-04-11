package it.polimi.ingsw.xmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * this abstract class represent a XmlParser. It gives method that are common to all Parsers
 */
public abstract class Parser {

    /**
     *
     * this method opens a file and creates a nodeList containing the elements that we want to parse.
     * @param file is the name of the file that contains the cards
     * @return the NodeList of cards
     * @throws ParserConfigurationException if a DocumentBuild cannot be created
     * @throws SAXException if any parse errors occur
     * @throws IOException if any IO errors occur
     */
    protected static NodeList getNodes(String file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(new File(file));
        Element root = document.getDocumentElement();
        return root.getChildNodes();
    }

}
