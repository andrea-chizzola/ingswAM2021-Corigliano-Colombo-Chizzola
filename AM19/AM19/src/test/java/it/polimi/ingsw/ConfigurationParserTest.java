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

}
