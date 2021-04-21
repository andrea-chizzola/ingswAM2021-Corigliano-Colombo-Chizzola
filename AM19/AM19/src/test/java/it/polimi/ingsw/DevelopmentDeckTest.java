package it.polimi.ingsw;

import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Cards.Colors.DevColor;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Decks.DevelopmentDeck;
import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DevelopmentDeckTest {
    private DevelopmentDeck deck;
    private final String configurationFile = "defaultConfiguration.xml";
    private final String fullPathFile = "src\\main\\resources\\XML\\defaultDevCards.xml";

    @BeforeEach
    public void setUp(){
        deck = new DevelopmentDeck(configurationFile);
    }
/*
    @Test
    public void extractCardsTest() {
        try {
            LinkedList<DevelopmentCard> cardsDeck = deck.extract(4, new Green());
            CardParser parser = CardParser.instance();

            List<DevelopmentCard> parsed = parser.parseDevelopmentCard(fullPathFile);
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
    public void extractFromMultipleDecksTest() {
        try {
            LinkedList<DevelopmentCard> cardsDeck = deck.extract(5, new Green());
            CardParser parser = CardParser.instance();

            List<DevelopmentCard> parsed = parser.parseDevelopmentCard(fullPathFile);
            Map<Integer, DevelopmentCard> firstEight = new HashMap<>();
            firstEight.put(parsed.get(0).getVictoryPoint(), parsed.get(0));
            firstEight.put(parsed.get(1).getVictoryPoint(), parsed.get(1));
            firstEight.put(parsed.get(2).getVictoryPoint(), parsed.get(2));
            firstEight.put(parsed.get(3).getVictoryPoint(), parsed.get(3));
            firstEight.put(parsed.get(4).getVictoryPoint(), parsed.get(4));
            firstEight.put(parsed.get(5).getVictoryPoint(), parsed.get(5));
            firstEight.put(parsed.get(6).getVictoryPoint(), parsed.get(6));
            firstEight.put(parsed.get(7).getVictoryPoint(), parsed.get(7));


            //the two lists should contain the same cards
            assertEquals(cardsDeck.get(0),firstEight.get(cardsDeck.get(0).getVictoryPoint()));
            assertEquals(cardsDeck.get(1),firstEight.get(cardsDeck.get(1).getVictoryPoint()));
            assertEquals(cardsDeck.get(2),firstEight.get(cardsDeck.get(2).getVictoryPoint()));
            assertEquals(cardsDeck.get(3),firstEight.get(cardsDeck.get(3).getVictoryPoint()));
            assertEquals(cardsDeck.get(4),firstEight.get(cardsDeck.get(4).getVictoryPoint()));
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

    //definition of an unexpected color to test if DevelopmentDeck throws IllegalArgumentException
    private class Unidentified extends CardColor {
        public Unidentified() {
            super(DevColor.PURPLE);
        }
    }

    @Test
    public void extractNonExistentDeck(){
        assertThrows(IllegalArgumentException.class, () -> deck.extract(50, new Unidentified()));
    }

    @Test
    public void getCardTest(){
        DevelopmentCard card = deck.getTop(new Green(), 1);
        CardParser parser = CardParser.instance();
        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(fullPathFile);
        assertTrue(card.equals(cardsParser.get(0))||
                card.equals(cardsParser.get(1))||
                card.equals(cardsParser.get(2))||
                card.equals(cardsParser.get(3)));
    }

    @Test
    public void getNonExistentDeck(){
        assertThrows(IllegalArgumentException.class, () -> deck.getTop(new Unidentified(),1));
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
        List<DevelopmentCard> cardsParser = parser.parseDevelopmentCard(fullPathFile);
        assertTrue(card.equals(cardsParser.get(0))||
                card.equals(cardsParser.get(1))||
                card.equals(cardsParser.get(2))||
                card.equals(cardsParser.get(3)));
    }

    @Test
    public void readNonExistentDeck(){
        assertThrows(IllegalArgumentException.class, () -> deck.readTop(new Unidentified(),1));
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

 */
}
