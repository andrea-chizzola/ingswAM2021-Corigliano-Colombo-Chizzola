package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationParserTest {

    private final String file = "defaultConfiguration.xml";
    private final String path = "src\\main\\resources\\XML\\";

    @Test
    public void getTrackPathTest(){
        assertEquals(path + "defaultFaithTrack.xml", ConfigurationParser.getTrackPath(file));
    }

    @Test
    public void getDevCardsPathTest(){
        assertEquals(path + "defaultDevCards.xml", ConfigurationParser.getDevCardsPath(file));
    }

    @Test
    public void getLeaderCardsPathTest(){
        assertEquals(path + "defaultLeaderCards.xml", ConfigurationParser.getLeaderCardsPath(file));
    }

    @Test
    public void getActionTokensPathTest(){
        assertEquals(path + "defaultActionTokens.xml", ConfigurationParser.getActionTokensPath(file));
    }

    @Test
    public void getMaxLevelCardsPathTest(){
        assertEquals(3, ConfigurationParser.getMaxLevel(file));
    }

    @Test
    public void getMarketBoardPathTest(){
        assertEquals(path + "defaultMarketBoard.xml", ConfigurationParser.getMarketBoardPath(file));
    }

    @Test
    public void getNumLeader(){
        assertEquals(2, ConfigurationParser.getNumLeader(file));
    }
}
