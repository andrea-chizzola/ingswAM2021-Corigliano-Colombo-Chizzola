package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.PlayerAction;
import it.polimi.ingsw.Messages.Enumerations.TraySelection;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

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
    void getTypeEmpty()  {

        String message = "<Message><messageType></messageType><body>Connection request.</body></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getType(message));
    }

    @Test
    void getTypeWrongMessage()  {

        String message = "<Messag messageType><body>Connection reques body></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getType(message));
    }

    @Test
    void getTray() {
        try {
            assertEquals(TraySelection.COLUMN, instance.getTray("<Message><messageType>CONNECTION</messageType><tray>COLUMN</tray><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>", "tray"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getTrayEmpty()  {

        String message = "<Message><messageType></messageType><tray></tray><body>Connection request.</body></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getTray(message,"tray"));
    }

    @Test
    void getTrayWrongMessage()  {

        String message = "<Messag messageType><body>Connection reques body></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getTray(message,"tray"));
    }

    @Test
    void getTrayWrongMessage1()  {

        String message = "<Message><messageType></messageType><tray>ciao</tray><body>Connection request.</body></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getTray(message,"tray"));
    }

    @Test
    void getBody() throws MalformedMessageException {
        assertEquals("Connection request.", instance.getBody("<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>"));
    }

    @Test
    void getBodyEmpty()  {
        try {
            assertEquals("", instance.getBody("<Message><body></body><gameHost>true</gameHost></Message>"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getBodyWrong()  {

        String message = "<Message><messageType></messageType><tray></tray><body>Connection request.body></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getBody(message));
    }

    @Test
    void getShelves() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        try {
            assertEquals(list, instance.getShelves("<Message><warehouse>1:1:2:1</warehouse><strongBox>coins:2:servants:3</strongBox></Message>", "warehouse"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getShelvesEmpty() {
        List<Integer> list = new ArrayList<>();

        try {
            assertEquals(list, instance.getShelves("<Message><warehouse></warehouse><strongBox>coins:2:servants:3</strongBox></Message>", "warehouse"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getQuantity() {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(9);
        try {
            assertEquals(list, instance.getQuantity("<Message><strongbox>coin:5:stone:9</strongbox><warehouse>1:5:2:9</warehouse></Message>", "warehouse"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getQuantityEmpty() {
        List<Integer> list = new ArrayList<>();

        try {
            assertEquals(list, instance.getQuantity("<Message><strongbox>coin:5:stone:9</strongbox><warehouse></warehouse></Message>", "warehouse"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getResQuantityList() {
        List<ResQuantity> list = new ArrayList<>();
        list.add(new ResQuantity(new Coin(), 5));
        list.add(new ResQuantity(new Stone(), 9));
        try {
            assertEquals(list, instance.getResQuantityList("<Message><strongbox>coin:5:stone:9</strongbox><warehouse>1:5:2:9</warehouse></Message>", "strongbox"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getResQuantityListEmpty() {
        List<ResQuantity> list = new ArrayList<>();
        try {
            assertEquals(list, instance.getResQuantityList("<Message><strongbox></strongbox><warehouse>1:5:2:9</warehouse></Message>", "strongbox"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getMarbleFromAction() {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        list.add(new MarbleWhite());
        try {
            List<Marble> messageList = instance.getMarbleFromAction("<Message><marblesActions>MarbleBlue:INSERT:2:marblewhite:insert:0</marblesActions></Message>", "marblesActions");
            for (int i=0; i<list.size(); i++) {
                assertEquals(list.get(i).toString(),messageList.get(i).toString() );
            }
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getMarbleActionMalformadMessage()  {

        String message = "<Message><marblesActions>::::</marblesActions></Message>";
        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getMarbleFromAction(message,"marblesActions"));
    }

    @Test
    void getMarbleFromActionEmpty() {
        List<Marble> list = new ArrayList<>();

        try {
            List<Marble> messageList = instance.getMarbleFromAction("<Message><marblesActions></marblesActions></Message>", "marblesActions");
            assertEquals(list,messageList);

        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getMarbleList()  {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        list.add(new MarbleWhite());
        list.add(new MarbleRed());
        try {
            List<Marble> messageList = instance.getMarbleList("<Message><marblesActions>MarbleBlue:marblewhite:maRblerEd</marblesActions></Message>", "marblesActions");
            for (int i=0; i<list.size(); i++) {
                assertEquals(list.get(i).toString(),messageList.get(i).toString() );
            }
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getMarbleListEmpty()  {
        List<Marble> list = new ArrayList<>();

        try {
            List<Marble> messageList = instance.getMarbleList("<Message><marblesActions></marblesActions></Message>", "marblesActions");
            assertEquals(list,messageList);

        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getActions()  {
        List<PlayerAction> list = new ArrayList<>();
        list.add(PlayerAction.INSERT);
        list.add(PlayerAction.DISCARD);
        list.add(PlayerAction.INSERT);
        try {
            assertEquals(list, instance.getActions("<Message><body>Marbles managing.</body><messageType>ACTION</messageType><marblesActions>MarbleBlue:InSERT:2:MarbleYellow:DIScARD:0:MarbleBlue:INSERT:2</marblesActions></Message>", "marblesActions"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getActionsEmpty()  {
        List<PlayerAction> list = new ArrayList<>();
        try {
            assertEquals(list, instance.getActions("<Message><body>Marbles managing.</body><messageType>ACTION</messageType><marblesActions></marblesActions></Message>", "marblesActions"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getStatusList()  {
        List<ItemStatus> list = new ArrayList<>();
        list.add(ItemStatus.ACTIVE);
        list.add(ItemStatus.INACTIVE);
        try {
            assertEquals(list, instance.getStatusList("<Message><status>ACTIVE:INACTIVE</status></Message>", "status"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getStatusListEmpty()  {
        List<ItemStatus> list = new ArrayList<>();
        try {
            assertEquals(list, instance.getStatusList("<Message><status></status></Message>", "status"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getShelvesActions()  {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(6);
        try {
            assertEquals(list, instance.getShelvesActions("<Message><shelves>test:test:3:test:test:6</shelves></Message>", "shelves"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getShelvesActionsEmpty()  {
        List<Integer> list = new ArrayList<>();
        try {
            assertEquals(list, instance.getShelvesActions("<Message><shelves></shelves></Message>", "shelves"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getResources() {
        List<Resource> list = new ArrayList<>();
        list.add(new Coin());
        list.add(new Faith());
        try {
            List<Resource> listMessage = instance.getResources("<Message><resources>1:coin:2:faith</resources></Message>", "resources");
            for (int i=0; i<list.size(); i++) {
                assertEquals(list.get(i).getColor(), listMessage.get(i).getColor());
            }
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getResourcesEmpty() {
        List<Resource> list = new ArrayList<>();

        try {
            List<Resource> listMessage = instance.getResources("<Message><resources></resources></Message>", "resources");

            assertEquals(list, listMessage);

        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getCardColor()  {
        CardColor color = new Blue();
        try {
            assertEquals(color.getColor(), instance.getCardColor("<Message><body>Buy card managing.</body><messageType>BUY_CARD</messageType><color>blue</color><level>1</level><slot>1</slot><ID>ciao</ID><warehouse>1:1:2:1</warehouse><strongBox>coins:2:servants:3</strongBox></Message>", "color").getColor());
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getCardColorEmpty()  {

        String message ="<Message><body>Buy card managing.</body><messageType>BUY_CARD</messageType><color></color><level>1</level><slot>1</slot><ID>ciao</ID><warehouse>1:1:2:1</warehouse><strongBox>coins:2:servants:3</strongBox></Message>";

        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getCardColor(message,"color"));
    }

    @Test
    void getInteger() {
        try {
            assertEquals(2, instance.getInteger("<Message><body>Connection request.</body><messageType>CONNECTION</messageType><nickname>nickname</nickname><gameHost>true</gameHost><playersNumber>2</playersNumber></Message>", "playersNumber"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getIntegerEmpty()  {

        String message = "<Message><body>Connection request.</body><messageType>CONNECTION</messageType><nickname>nickname</nickname><gameHost>true</gameHost><playersNumber></playersNumber></Message>";

        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> instance.getInteger(message,"playersNumber"));
    }

    @Test
    void getString()  {
        try {
            assertEquals("nickname",instance.getString("<Message><body>Reconnection request.</body><messageType>RECONNECTION</messageType><nickname>nickname</nickname></Message>", "nickname"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getStringEmpty() {
        try {
            assertEquals("",instance.getString("<Message><body>Reconnection request.</body><messageType>RECONNECTION</messageType><nickname></nickname></Message>", "nickname"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void getBoolean() {
        try {
            assertTrue(instance.getBoolean("<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>", "gameHost"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }
    @Test
    void getBooleanFalse() {
        try {
            assertFalse(instance.getBoolean("<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost></gameHost></Message>", "gameHost"));
        } catch (MalformedMessageException e) {
            fail();
        }
    }


}