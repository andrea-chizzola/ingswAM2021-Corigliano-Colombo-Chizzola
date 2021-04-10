package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

/**
 * this class is used to test the implemented method of the abstract class Card
 */
public class CardTest {
    String DevTest = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\DevCardsTest.xml";
    String LeaderTest = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\LeaderCardsTest.xml";

    CardParser parser;

    @BeforeEach
    public void setUp(){
        parser = CardParser.instance();
    }

    @Test
    public void getProductionDevTest(){
        //creation of a card
        List<DevelopmentCard> cards = parser.parseDevelopmentCard(DevTest);
        DevelopmentCard card = cards.get(0);

        //construction of card production
        LinkedList<ResQuantity> materials = new LinkedList<>();
        materials.add(new ResQuantity(new Coin(), 1));
        LinkedList<ResQuantity> products = new LinkedList<>();
        products.add(new ResQuantity(new Faith(), 1));

        Production effect = new Production(materials, products);

        assertEquals(card.getSpecialEffect().getProduction(), effect);

    }
    @Test
    public void getProductionLeaderTest(){
        //creation of a card
        List<LeaderCard> cards = parser.parseLeaderCard(LeaderTest);
        LeaderCard card = cards.get(0);

        assertEquals(card.getSpecialEffect().getProduction(), new Production(
                new LinkedList<>(), new LinkedList<>()
        ));

    }

}
