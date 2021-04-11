package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import it.polimi.ingsw.xmlParser.PopeFavorParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PopeFavorParserTest {
    String favorTest = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\PopeFavorTest.xml";

    @Test
    public void parseFirstTest(){
        PopeFavor favor = PopeFavorParser.parseFirst(favorTest);
        assertEquals(PopeFavorParser.parseFirst(favorTest), new PopeFavor(4));
    }
    @Test
    public void parseSecondTest(){
        assertEquals(PopeFavorParser.parseSecond(favorTest), new PopeFavor(5));
    }
    @Test
    public void parseThirdTest(){
        assertEquals(PopeFavorParser.parseThird(favorTest), new PopeFavor(6));
    }
}
