package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

public class LeaderDeckTest {
    private LeaderDeck deck;
    private final String file = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\LeaderCardsTest.xml";

    @BeforeEach
    public void setUp(){
        deck = new LeaderDeck(file);
    }

    @Test
    public void extractTest(){
       LinkedList<LeaderCard> cardsDeck = deck.extract(4);
       CardParser parser = CardParser.instance();

        List<LeaderCard> cardsParser = parser.parseLeaderCard(file);
        //test of the content of the two cards
        assertTrue(cardsDeck.containsAll(cardsParser));
        assertTrue(cardsParser.containsAll(cardsDeck));
    }

    @Test
    public void extractExceptionTest(){
        deck.extract(20);
        assertThrows(IllegalArgumentException.class, () -> deck.extract(1));
    }
}
