package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationParserTest {

    private final String file = "defaultConfiguration.xml";
    private final String path = "src/main/resources/XML/";

    @Test
    public void getMaxLevelCardsPathTest(){
        assertEquals(3, ConfigurationParser.getMaxLevel(file));
    }


    @Test
    public void getNumLeader(){
        assertEquals(4, ConfigurationParser.getNumLeader(file));
    }

    @Test
    public void getInitializationResourcesTest(){
        LinkedList<Integer> expected = new LinkedList<>();
        LinkedList<Integer> actual = new LinkedList<>();
        expected.add(0);
        expected.add(1);
        expected.add(1);
        expected.add(2);

        actual = ConfigurationParser.getInitializationResources(file);
        for(int i=0; i<expected.size(); i++){
            assertEquals(expected.get(i),actual.get(i));
        }

    }

    @Test
    public void getInitializationFaithTest(){
        LinkedList<Integer> expected = new LinkedList<>();
        LinkedList<Integer> actual = new LinkedList<>();
        expected.add(0);
        expected.add(0);
        expected.add(1);
        expected.add(1);

        actual = ConfigurationParser.getInitializationFaith(file);
        for(int i=0; i<expected.size(); i++){
            assertEquals(expected.get(i),actual.get(i));
        }

    }

}
