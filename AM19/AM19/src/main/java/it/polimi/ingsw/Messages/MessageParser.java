package it.polimi.ingsw.Messages;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MessageParser {

    /**
     * This method returns the root of a XML file passed as a string
     * @param message XML file which represents the message
     * @return the root of the file
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws IllegalArgumentException
     */
    protected static Element getRoot(String message) throws ParserConfigurationException, SAXException, IOException, IllegalArgumentException{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        dBuilder.setErrorHandler(new DefaultHandler());
        Document document = dBuilder.parse(new InputSource(new StringReader(message)));

        return document.getDocumentElement();
    }

    /**
     * This method gets the the string present in the XML file at the specified tag
     * @param file XML file which represents the message
     * @param tagName the name of the tag of interest
     * @return a String which is the value present in the file at the specified tag
     * @throws MalformedMessageException if there is a failure during the parsing, which means that the file is not well formed
     */
    public static String getMessageTag(String file, String tagName) throws MalformedMessageException{
        Element element = null;
        String result = null;
        try {
            element = getRoot(file);
            NodeList nodeList = element.getElementsByTagName(tagName);
            result = nodeList.item(0).getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException | NullPointerException | DOMException | IllegalArgumentException e) {
            throw new MalformedMessageException("Parsing failure!");
        }

        return result;
    }


}
