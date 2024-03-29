package it.polimi.ingsw;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Yellow;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

public class CardParserTest {
    String DevTest = "/DevCardsTest.xml";
    String LeaderTest = "/LeaderCardsTest.xml";

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

        SpecialEffect effect = new Production(materials, products,1,2);

        //construction of card requirements
        LinkedList<ResQuantity> req = new LinkedList<>();
        req.add(new ResQuantity(new Shield(), 2));
        Requirements requirements = new ResourceReqDev(req);

        //test of the content of the two cards
        assertEquals(card, new DevelopmentCard(1,effect, requirements ,new Green(), 1 , "1", "src/main/resources/Images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
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
        assertEquals(card, new LeaderCard(2,effect, requirements, "1", "src/main/resources/Images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-49-1.png"));
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
        assertEquals(card, new LeaderCard(3,effect, requirements, "2", "src/main/resources/Images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-53-1.png"));
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
        assertEquals(card, new LeaderCard(5,effect, requirements, "3", "src/main/resources/Images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-57-1.png"));
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

        SpecialEffect effect = new Production(materials, products, 0 , 0);

        //construction of card requirements
        LinkedList<CardQuantity> req = new LinkedList<>();
        req.add(new CardQuantity(new Yellow(), 1, 2));
        Requirements requirements = new CardRequirements(req);

        //test of the content of the two cards
        assertEquals(card, new LeaderCard(4,effect, requirements, "4", "src/main/resources/Images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-61-1.png"));
    }
}
