package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DevelopmentDeckTest {
    private DevelopmentDeck deck;
    private final String file = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\DevCardsTest.xml";

    @BeforeEach
    public void setUp(){
        deck = new DevelopmentDeck(file);
    }

    @Test
    public void extractCardsTest() {
        try {
            LinkedList<DevelopmentCard> cardsDeck = deck.extract(4, new Green());
            CardParser parser = CardParser.instance();

            List<DevelopmentCard> parsed = parser.parseDevelopmentCard(file);
            Map<Integer, DevelopmentCard> firstFour = new HashMap<>();
            firstFour.put(parsed.get(0).getVictoryPoint(), parsed.get(0));
            firstFour.put(parsed.get(1).getVictoryPoint(), parsed.get(1));
            firstFour.put(parsed.get(2).getVictoryPoint(), parsed.get(2));
            firstFour.put(parsed.get(3).getVictoryPoint(), parsed.get(3));

            //the two lists should contain the same cards
            assertEquals(cardsDeck.get(0),firstFour.get(cardsDeck.get(0).getVictoryPoint()));
            assertEquals(cardsDeck.get(1),firstFour.get(cardsDeck.get(1).getVictoryPoint()));
            assertEquals(cardsDeck.get(2),firstFour.get(cardsDeck.get(2).getVictoryPoint()));
            assertEquals(cardsDeck.get(3),firstFour.get(cardsDeck.get(3).getVictoryPoint()));
        }
        catch (LorenzoWonException e) {
            assertFalse(true);
        }
    }

    @Test
    public void LorenzoWonExactExtraction() {
        assertThrows(LorenzoWonException.class, () -> deck.extract(12, new Green()));
    }

    @Test
    public void LorenzoWonBigExtraction() {
        assertThrows(LorenzoWonException.class, () -> deck.extract(50, new Green()));
    }

    @Test
    public void extractNonExistentDeck(){
        assertThrows(IllegalArgumentException.class, () -> deck.extract(50, new Purple()));
    }


    @Test
    public void getCardTest(){
        DevelopmentCard card = deck.getTop(new Green(), 1);
        CardParser parser = CardParser.instance();
        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(file);
        assertTrue(card.equals(cardsParser.get(0))||
                card.equals(cardsParser.get(1))||
                card.equals(cardsParser.get(2))||
                card.equals(cardsParser.get(3)));
    }

    @Test
    public void getNonExistentDeck(){
        assertThrows(IllegalArgumentException.class, () -> deck.getTop(new Purple(),1));
    }

    @Test
    public void getCardExceptionTest(){
        try{
            deck.extract(20, new Green());
        }
        catch(LorenzoWonException ignored){ }
        assertThrows(IllegalArgumentException.class, () -> deck.getTop(new Green(), 1));
    }

    @Test
    public void readCardTest(){
        DevelopmentCard card = deck.readTop(new Green(),1);
        CardParser parser = CardParser.instance();
        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(file);
        assertTrue(card.equals(cardsParser.get(0))||
                card.equals(cardsParser.get(1))||
                card.equals(cardsParser.get(2))||
                card.equals(cardsParser.get(3)));
    }

    @Test
    public void readNonExistentDeck(){
        assertThrows(IllegalArgumentException.class, () -> deck.readTop(new Purple(),1));
    }

    @Test
    public void readCardExceptionTest(){
        try{
            deck.extract(20, new Green());
        }
        catch(LorenzoWonException ignored){
        }
        assertThrows(IllegalArgumentException.class, () -> deck.readTop(new Green(), 1));
    }

}
