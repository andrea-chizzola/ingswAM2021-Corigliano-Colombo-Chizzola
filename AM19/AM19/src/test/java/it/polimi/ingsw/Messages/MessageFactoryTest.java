package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.Coin;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Stone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {

    private MessageFactory messageFactory;

    @BeforeEach
    void setUp() {
        messageFactory = new MessageFactory();
    }

    @AfterEach
    void tearDown() {
        messageFactory = null;
    }
/*

    @Test
    void buildReply() throws MalformedMessageException {
        assertEquals(messageFactory.buildReply(true, "Reply message."), "<Message><messageType>REPLY</messageType><correct>true</correct><body>Reply message.</body></Message>");
    }

*/
    @Test
    void buildCurrentPlayerTest(){
        String playerID = "pippo";
        String body = "no body";
        String actualMessage = "";
        List<String> turns = new LinkedList<>();
        turns.add("TAKE_RESOURCES");
        turns.add("MANAGE_MARBLE");
        turns.add("MANAGE_LEADER");

        String expectedMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>GAME_STATUS</messageType><correct>true</correct><state>TURN_SELECTION</state><body>no body</body><turns>TAKE_RESOURCES:MANAGE_MARBLE:MANAGE_LEADER</turns><player>pippo</player></Message>";
        try {
             actualMessage = MessageFactory.buildCurrentPlayer(playerID,turns,body);
        } catch (MalformedMessageException e) {
            fail();
        }

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void buildCurrentPlayerTestEmptyList(){
        String playerID = "pippo";
        String body = "no body";
        String actualMessage = "";
        List<String> turns = new LinkedList<>();

        String expectedMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>GAME_STATUS</messageType><correct>true</correct><state>TURN_SELECTION</state><body>no body</body><turns/><player>pippo</player></Message>";
        try {
            actualMessage = MessageFactory.buildCurrentPlayer(playerID,turns,body);
        } catch (MalformedMessageException e) {
            fail();
        }

        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void buildConnection()  {
        try {
            assertEquals(messageFactory.buildConnection("Connection request.", "nickname", true, 4),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>CONNECTION</messageType><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost><player>nickname</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDisconnection()  {
        try {
            assertEquals(messageFactory.buildDisconnection("Disconnection request.", "nickname"),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DISCONNECTION</messageType><body>Disconnection request.</body><player>nickname</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildReconnection()  {
        try {
            assertEquals(messageFactory.buildReconnection("Reconnection request.", "nickname"),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>RECONNECTION</messageType><body>Reconnection request.</body><player>nickname</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildEndGame() {
        Map<String, Integer> map = new HashMap<>();
        map.put("player1", 25);
        map.put("player2", 20);
        try {
            assertEquals(messageFactory.buildEndGame(map, "Winner message."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>END_GAME</messageType><body>Winner message.</body><points>player1:25:player2:20</points></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildEndGameEmptyMap()  {
        Map<String, Integer> map = new HashMap<>();
        try {
            assertEquals(messageFactory.buildEndGame(map, "Winner message."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>END_GAME</messageType><body>Winner message.</body><points/></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildBoxUpdate()  {
        ResQuantity res1 = new ResQuantity(new Coin(), 5);
        ResQuantity res2 = new ResQuantity(new Stone(), 9);
        List<ResQuantity> list = new ArrayList<>();
        list.add(res1);
        list.add(res2);
        try {
            assertEquals(messageFactory.buildBoxUpdate(list, list, "pippo","Boxes update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><strongbox>coin:5:stone:9</strongbox><messageType>BOX_UPDATE</messageType><body>Boxes update.</body><warehouse>coin:5:stone:9</warehouse><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }

    }

    @Test
    void buildBoxUpdateEmpty()  {

        List<ResQuantity> list = new ArrayList<>();

        try {
            assertEquals(messageFactory.buildBoxUpdate(list, list, "pippo","Boxes update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><strongbox/><messageType>BOX_UPDATE</messageType><body>Boxes update.</body><warehouse/><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }

    }

    @Test
    void buildSlotsUpdate()  {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "id1");
        map.put(2, "id2");
        try {
            assertEquals(messageFactory.buildSlotsUpdate(map, "pippo","Slots update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>SLOTS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body>Slots update.</body><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildSlotsUpdateEmptyMap()  {
        Map<Integer, String> map = new HashMap<>();

        try {
            assertEquals(messageFactory.buildSlotsUpdate(map, "pippo","Slots update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>SLOTS_UPDATE</messageType><devCards/><body>Slots update.</body><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDecksUpdate()  {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "id1");
        map.put(2, "id2");
        try {
            assertEquals(messageFactory.buildDecksUpdate(map, "Decks update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DECKS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body>Decks update.</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDecksUpdateEmptyBody()  {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "id1");
        map.put(2, "id2");
        try {
            assertEquals(messageFactory.buildDecksUpdate(map, ""),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DECKS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body/></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDecksUpdateEmptyMap() {
        Map<Integer, String> map = new HashMap<>();

        try {
            assertEquals(messageFactory.buildDecksUpdate(map, "Decks update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DECKS_UPDATE</messageType><devCards/><body>Decks update.</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }


    @Test
    void buildUpdateLorenzo()  {
        try {
            assertEquals(messageFactory.buildUpdateLorenzo("tokenID", "Update top token.")
                    ,"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>TOKEN_UPDATE</messageType><body>Update top token.</body><token>tokenID</token></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildUpdateMarket() {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        list.add(new MarbleGray());
        try {
            assertEquals(messageFactory.buildUpdateMarket(list, "Update market"),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><market>MarbleBlue:MarbleGray</market><messageType>MARKET_UPDATE</messageType><body>Update market</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildUpdateMarketEmpty()  {
        List<Marble> list = new ArrayList<>();
        try {
            assertEquals(messageFactory.buildUpdateMarket(list, "Update market"),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><market/><messageType>MARKET_UPDATE</messageType><body>Update market</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildSelectedMarbles()  {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        list.add(new MarbleGray());
        List<Marble> list1 = new ArrayList<>();
        list1.add(new MarblePurple());
        list1.add(new MarbleRed());
        try {
            assertEquals(messageFactory.buildSelectedMarbles(list, list1, "pippo", "Marbles selected by the user."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><candidates>MarblePurple:MarbleRed</candidates><messageType>GAME_STATUS</messageType><correct>true</correct><marbles>MarbleBlue:MarbleGray</marbles><state>MANAGE_MARBLE</state><body>Marbles selected by the user.</body><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildSelectedMarblesEmptyLists()  {
        List<Marble> list = new ArrayList<>();
        List<Marble> list1 = new ArrayList<>();

        try {
            assertEquals(messageFactory.buildSelectedMarbles(list, list1, "pippo", "Marbles selected by the user."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><candidates/><messageType>GAME_STATUS</messageType><correct>true</correct><marbles/><state>MANAGE_MARBLE</state><body>Marbles selected by the user.</body><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }


    }
}