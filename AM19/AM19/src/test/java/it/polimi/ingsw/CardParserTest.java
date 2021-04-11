package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

public class CardParserTest {
    String DevTest = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\DevCardsTest.xml";
    String LeaderTest = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\LeaderCardsTest.xml";

    CardParser parser;

    @BeforeEach
    public void setUp(){
        parser = CardParser.instance();
    }

    @Test
    public void devCardCreationTest(){
        List<DevelopmentCard> cards = parser.parseDevelopmentCard(DevTest);
        DevelopmentCard card = cards.get(0);

        //construction of card production
        LinkedList<ResQuantity> materials = new LinkedList<>();
        materials.add(new ResQuantity(new Coin(), 1));

        LinkedList<ResQuantity> products = new LinkedList<>();
        products.add(new ResQuantity(new Faith(), 1));

        SpecialEffect effect = new Production(materials, products);

        //construction of card requirements
        LinkedList<ResQuantity> req = new LinkedList<>();
        req.add(new ResQuantity(new Shield(), 2));
        Requirements requirements = new ResourceReqDev(req);

        //test of the content of the two cards
        assertEquals(card, new DevelopmentCard(1,effect, requirements ,new Green(), 1 ));
    }

    @Test
    public void leaderDiscountCreationTest(){
        List<LeaderCard> cards = parser.parseLeaderCard(LeaderTest);
        LeaderCard card = cards.get(0);

        //construction of card special effect
        SpecialEffect effect = new Discount(new ResQuantity(new Servant(),1));

        //construction of card requirements
        LinkedList<CardQuantity> req = new LinkedList<>();
        req.add(new CardQuantity(new Yellow(), 1, 1));
        req.add(new CardQuantity(new Green(), 1, 1));
        Requirements requirements = new CardRequirements(req);

        //test of the content of the two cards
        assertEquals(card, new LeaderCard(2,effect, requirements));
    }

    @Test
    public void leaderExtraSlotCreationTest(){
        List<LeaderCard> cards = parser.parseLeaderCard(LeaderTest);
        LeaderCard card = cards.get(1);

        //construction of card special effect
        SpecialEffect effect = new ExtraSlot(new ResQuantity( new Stone(), 2));

        //construction of card requirements
        LinkedList<ResQuantity> req = new LinkedList<>();
        req.add(new ResQuantity(new Coin(), 5));
        Requirements requirements = new ResourceReqLeader(req);

        //test of the content of the two cards
        assertEquals(card, new LeaderCard(3,effect, requirements));
    }

    @Test
    public void leaderWhiteMarbleCreationTest(){
        List<LeaderCard> cards = parser.parseLeaderCard(LeaderTest);
        LeaderCard card = cards.get(2);

        //construction of card special effect
        SpecialEffect effect = new WhiteMarble(new Servant());

        //construction of card requirements
        LinkedList<CardQuantity> req = new LinkedList<>();
        req.add(new CardQuantity(new Yellow(), 2, 1));
        req.add(new CardQuantity(new Blue(), 1, 1));
        Requirements requirements = new CardRequirements(req);

        //test of the content of the two cards
        assertEquals(card, new LeaderCard(5,effect, requirements));
    }

    @Test
    public void leaderProductionCreationTest(){
        List<LeaderCard> cards = parser.parseLeaderCard(LeaderTest);
        LeaderCard card = cards.get(3);

        //construction of card special effect
        LinkedList<ResQuantity> materials = new LinkedList<>();
        materials.add(new ResQuantity(new Shield(), 1));

        LinkedList<ResQuantity> products = new LinkedList<>();
        products.add(new ResQuantity(new Faith(), 1));

        SpecialEffect effect = new Production(materials, products);

        //construction of card requirements
        LinkedList<CardQuantity> req = new LinkedList<>();
        req.add(new CardQuantity(new Yellow(), 1, 2));
        Requirements requirements = new CardRequirements(req);

        //test of the content of the two cards
        assertEquals(card, new LeaderCard(4,effect, requirements));
    }
}
