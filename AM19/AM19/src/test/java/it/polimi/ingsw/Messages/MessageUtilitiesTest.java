package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleBlue;
import it.polimi.ingsw.Model.MarketBoard.MarbleYellow;
import it.polimi.ingsw.Model.Resources.Coin;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Stone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageUtilitiesTest {

    MessageUtilities instance;

    @BeforeEach
    void setUp() {
        instance = MessageUtilities.instance();
    }

    @AfterEach
    void tearDown() {
        instance = null;
    }

    @Test
    void getType() throws MalformedMessageException {
        assertEquals(Message.MessageType.CONNECTION, instance.getType("<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>"));
    }

    @Test
    void getTray() throws MalformedMessageException {
        assertEquals("COLUMN", instance.getTray("<Message><messageType>CONNECTION</messageType><tray>COLUMN</tray><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>", "tray"));
    }

    @Test
    void getBody() throws MalformedMessageException {
        assertEquals("Connection request.", instance.getBody("<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>"));
    }

    @Test
    void getShelves() throws MalformedMessageException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        assertEquals(list, instance.getShelves("<Message><body>Buy card managing.</body><messageType>BUY_CARD</messageType><color>blue</color><level>1</level><slot>1</slot><ID>ciao</ID><warehouse>1:1:2:1</warehouse><strongBox>coins:2:servants:3</strongBox></Message>", "warehouse"));
    }

    @Test
    void getQuantity() throws MalformedMessageException {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(9);
        assertEquals(list, instance.getQuantity("<Message><strongbox>coin:5:stone:9</strongbox><messageType>BOX_UPDATE</messageType><body>Boxes update.</body><warehouse>coin:5:stone:9</warehouse></Message>", "warehouse"));
    }

    @Test
    void getResQuantityList() throws MalformedMessageException {
        List<ResQuantity> list = new ArrayList<>();
        list.add(new ResQuantity(new Coin(), 5));
        list.add(new ResQuantity(new Stone(), 9));
        assertEquals(list, instance.getResQuantityList("<Message><strongbox>coins:5:stones:9</strongbox><messageType>BOX_UPDATE</messageType><body>Boxes update.</body><warehouse>coins:5:stones:9</warehouse></Message>", "warehouse"));
    }

    @Test
    void getMarbleFromAction() throws MalformedMessageException {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        //assertEquals(list, instance.getMarbleFromAction("<Message><body>Marbles managing.</body><messageType>ACTION_MARBLE</messageType><marblesActions>MarbleBlue:INSERT:2</marblesActions></Message>", "marblesActions"));
    }

    @Test
    void getMarbleList() throws MalformedMessageException {
        System.out.println(instance.getMarbleList("<Message><marbles></marbles>Red:Blue</Message>", "marbles"));
    }

    @Test
    void getActions() throws MalformedMessageException {
        List<PlayerAction> list = new ArrayList<>();
        list.add(PlayerAction.INSERT);
        list.add(PlayerAction.DISCARD);
        list.add(PlayerAction.INSERT);
        assertEquals(list, instance.getActions("<Message><body>Marbles managing.</body><messageType>ACTION</messageType><marblesActions>MarbleBlue:INSERT:2:MarbleYellow:DISCARD:0:MarbleBlue:INSERT:2</marblesActions></Message>", "marblesActions"));

    }

    @Test
    void getStatusList() throws MalformedMessageException {
        List<ItemStatus> list = new ArrayList<>();
        list.add(ItemStatus.ACTIVE);
        list.add(ItemStatus.INACTIVE);
        assertEquals(list, instance.getStatusList("<Message><status>ACTIVE:INACTIVE</status></Message>", "status"));
    }

    @Test
    void getShelvesActions() throws MalformedMessageException {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(6);
        assertEquals(list, instance.getShelvesActions("<Message><shelves>test:test:3:test:test:6</shelves></Message>", "shelves"));
    }

    @Test
    void getResources() {
    }

    @Test
    void getCardColor() throws MalformedMessageException {
        CardColor color = new Blue();
        assertEquals(color.getColor(), instance.getCardColor("<Message><body>Buy card managing.</body><messageType>BUY_CARD</messageType><color>blue</color><level>1</level><slot>1</slot><ID>ciao</ID><warehouse>1:1:2:1</warehouse><strongBox>coins:2:servants:3</strongBox></Message>", "color").getColor());
    }

    @Test
    void getInteger() throws MalformedMessageException {
        assertEquals(2, instance.getInteger("<Message><body>Connection request.</body><messageType>CONNECTION</messageType><nickname>nickname</nickname><gameHost>true</gameHost><playersNumber>2</playersNumber></Message>", "playersNumber"));
    }

    @Test
    void getString() throws MalformedMessageException {
        assertEquals("nickname",instance.getString("<Message><body>Reconnection request.</body><messageType>RECONNECTION</messageType><nickname>nickname</nickname></Message>", "nickname"));
    }

    @Test
    void getBoolean() throws MalformedMessageException {
        assertTrue(instance.getBoolean("<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>", "gameHost"));
    }
}