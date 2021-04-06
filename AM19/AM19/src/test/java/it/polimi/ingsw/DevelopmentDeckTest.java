package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

public class DevelopmentDeckTest {
    private DevelopmentDeck deck;
    private final String file = "src\\test\\java\\it\\polimi\\ingsw\\DevCardsTest.xml";

    @BeforeEach
    public void setUp(){
        deck = new DevelopmentDeck(file);
    }

    @Test
    public void extractCardsTest(){
        LinkedList<DevelopmentCard> cardsDeck = deck.extract(4);
        CardParser parser = CardParser.instance();

        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(file);
        //test of the content of the two cards
        assertTrue(cardsDeck.containsAll(cardsParser));
        assertTrue(cardsParser.containsAll(cardsDeck));
    }

    @Test
    public void extractExceptionTest(){
        deck.extract(20);
        assertThrows(IllegalArgumentException.class, () -> deck.extract(1));
    }

    @Test
    public void getCardTest(){
        DevelopmentCard card = deck.getTop();
        CardParser parser = CardParser.instance();
        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(file);
        assertEquals(cardsParser.get(0), card);
    }

    @Test
    public void getCardExceptionTest(){
        deck.extract(20);
        assertThrows(IllegalArgumentException.class, () -> deck.getTop());
    }

    @Test
    public void readCardTest(){
        DevelopmentCard card = deck.readTop();
        CardParser parser = CardParser.instance();
        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(file);
        assertEquals(cardsParser.get(0), card);
    }

    @Test
    public void readCardExceptionTest(){
        deck.extract(20);
        assertThrows(IllegalArgumentException.class, () -> deck.readTop());
    }

}
