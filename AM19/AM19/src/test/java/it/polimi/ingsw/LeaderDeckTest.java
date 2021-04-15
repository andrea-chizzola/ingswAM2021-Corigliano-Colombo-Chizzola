package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeaderDeckTest {
    private LeaderDeck deck;
    private final String configurationFile = "defaultConfiguration.xml";
    private final String fullPathFile = "src\\main\\resources\\XML\\defaultLeaderCards.xml";

    @BeforeEach
    public void setUp(){
        deck = new LeaderDeck(configurationFile);
    }

    @Test
    public void extractTest(){
       LinkedList<LeaderCard> cardsDeck = deck.extract(16);
       CardParser parser = CardParser.instance();

        List<LeaderCard> cardsParser = parser.parseLeaderCard(fullPathFile);
        //test of the content of the two cards

        assertTrue(cardsDeck.containsAll(cardsParser));
        assertTrue(cardsParser.containsAll(cardsDeck));
    }

    @Test
    public void extractExceptionTest(){
        deck.extract(50);
        assertThrows(IllegalArgumentException.class, () -> deck.extract(1));
    }
}
