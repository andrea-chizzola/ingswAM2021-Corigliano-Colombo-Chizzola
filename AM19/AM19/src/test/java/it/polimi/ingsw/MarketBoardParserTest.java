package it.polimi.ingsw;

import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.xmlParser.MarketBoardParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MarketBoardParserTest {
    private final String file = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\MarketBoardTest.xml";
    MarketBoardParser parser;

    @BeforeEach
    public void setUp(){
        parser = MarketBoardParser.instance();
    }

    @Test
    public void getMarketRowsTest(){
        assertEquals(parser.getMarketRows(file), 4);
    }

    @Test
    public void getMarketColumnsTest(){
        assertEquals(parser.getMarketColumns(file), 2);
    }

    @Test
    public void buildMarketTrayTest(){
        ArrayList<Marble> tray = parser.buildMarketTray(file);
        assertEquals(tray.get(0).getClass(), MarbleWhite.class);
        assertEquals(tray.get(1).getClass(), MarbleWhite.class);
        assertEquals(tray.get(2).getClass(), MarblePurple.class);
        assertEquals(tray.get(3).getClass(), MarbleBlue.class);
        assertEquals(tray.get(4).getClass(), MarbleBlue.class);
        assertEquals(tray.get(5).getClass(), MarblePurple.class);
        assertEquals(tray.get(6).getClass(), MarbleYellow.class);
        assertEquals(tray.get(7).getClass(), MarbleYellow.class);
        assertEquals(tray.get(8).getClass(), MarbleYellow.class);
    }
}
