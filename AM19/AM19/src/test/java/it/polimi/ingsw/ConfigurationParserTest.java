package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.Test;

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
