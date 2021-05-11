package it.polimi.ingsw;

import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.xmlParser.MarketBoardParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MarketBoardParserTest {
    private final String file = "src/main/resources/XML/defaultMarketBoard.xml";
    MarketBoardParser parser;

    @BeforeEach
    public void setUp(){
        parser = MarketBoardParser.instance();
    }

    @Test
    public void getMarketRowsTest(){
        assertEquals(parser.getMarketRows(file), 3);
    }

    @Test
    public void getMarketColumnsTest(){
        assertEquals(parser.getMarketColumns(file), 4);
    }

    @Test
    public void buildMarketTrayTest(){
        ArrayList<Marble> tray = parser.buildMarketTray(file);
        assertEquals(tray.get(0).getClass(), MarbleWhite.class);
        assertEquals(tray.get(1).getClass(), MarbleWhite.class);
        assertEquals(tray.get(2).getClass(), MarbleWhite.class);
        assertEquals(tray.get(3).getClass(), MarbleWhite.class);
        assertEquals(tray.get(4).getClass(), MarbleBlue.class);
        assertEquals(tray.get(5).getClass(), MarbleBlue.class);
        assertEquals(tray.get(6).getClass(), MarblePurple.class);
        assertEquals(tray.get(7).getClass(), MarblePurple.class);
        assertEquals(tray.get(8).getClass(), MarbleRed.class);
        assertEquals(tray.get(9).getClass(), MarbleYellow.class);
        assertEquals(tray.get(10).getClass(), MarbleYellow.class);
        assertEquals(tray.get(11).getClass(), MarbleGray.class);
        assertEquals(tray.get(12).getClass(), MarbleGray.class);
    }
}
