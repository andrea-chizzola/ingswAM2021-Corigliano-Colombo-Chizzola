package it.polimi.ingsw.Messages;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

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
        Element element;
        String result;
        try {
            element = getRoot(file);
            NodeList nodeList = element.getElementsByTagName(tagName);
            result = nodeList.item(0).getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException | NullPointerException | DOMException | IllegalArgumentException e) {
            throw new MalformedMessageException("Parsing failure!");
        }

        return result;
    }

    /**
     * this method is used to create a XML document
     * @return the new XML document
     * @throws ParserConfigurationException if the DocumentBuilder was not correctly opened
     */
    protected static Document createNewDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    /**
     * this method is used to create a XML message String
     * @param elements is the map of the tags and the contents to be inserted in the message
     * @return an String that represents the XML message
     * @throws MalformedMessageException if any error occurs during the creation of the xml string.
     */
    protected static String createMessage(Map<String, String> elements) throws MalformedMessageException {
        String message;
        try {
            Document document = createNewDocument();
            Element root = document.createElement("Message");
            Element e;

            for (String s : elements.keySet()) {
                e = document.createElement(s);
                e.appendChild(document.createTextNode(elements.get(s)));
                root.appendChild(e);
            }
            document.appendChild(root);
            message = createXMLString(document);
        }catch(ParserConfigurationException | TransformerException e){
            throw new MalformedMessageException("Error during the creation of the message");
        }
        return message;

    }

    /**
     * this method is used to create a XML String from a XML document
     * @param document is the target document
     * @return return the XML String
     * @throws TransformerException if the Transformer was not correctly instantiated
     */
    protected static String createXMLString(Document document) throws TransformerException {

        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty("omit-xml-declaration", "yes");

        StringWriter buffer = new StringWriter();
        tr.transform(new DOMSource(document), new StreamResult(buffer));
        return buffer.toString();
    }

}
