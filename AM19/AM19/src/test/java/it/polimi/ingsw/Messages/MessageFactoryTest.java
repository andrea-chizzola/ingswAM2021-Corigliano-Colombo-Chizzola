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

    @Test
    void buildConnection() throws MalformedMessageException {
        assertEquals(messageFactory.buildConnection("Connection request.", "nickname", true, 4), "<Message><messageType>CONNECTION</messageType><nickname>nickname</nickname><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost></Message>");
    }

    @Test
    void buildDisconnection() throws MalformedMessageException {
        assertEquals(messageFactory.buildDisconnection("Disconnection request.", "nickname"), "<Message><messageType>DISCONNECTION</messageType><nickname>nickname</nickname><body>Disconnection request.</body></Message>");
    }

    @Test
    void buildReconnection() throws MalformedMessageException {
        assertEquals(messageFactory.buildReconnection("Reconnection request.", "nickname"), "<Message><messageType>RECONNECTION</messageType><nickname>nickname</nickname><body>Reconnection request.</body></Message>");
    }

    @Test
    void buildReply() throws MalformedMessageException {
        assertEquals(messageFactory.buildReply(true, "Reply message."), "<Message><messageType>REPLY</messageType><correct>true</correct><body>Reply message.</body></Message>");
    }

    @Test
    void buildCurrentPlayer() throws MalformedMessageException {
        assertEquals(messageFactory.buildCurrentPlayer("playerID", "nickname"), "<Message><messageType>GAME_STATUS</messageType><currentPlayer>playerID</currentPlayer><body>nickname</body></Message>");
    }

    @Test
    void buildEndGame() throws MalformedMessageException {
        Map<String, Integer> map = new HashMap<>();
        map.put("player1", 25);
        map.put("player2", 20);
        assertEquals(messageFactory.buildEndGame(map, "Winner message."), "<Message><messageType>END_GAME</messageType><body>Winner message.</body><points>player1:25:player2:20</points></Message>");
    }

    @Test
    void buildBoxUpdate() throws MalformedMessageException {
        ResQuantity res1 = new ResQuantity(new Coin(), 5);
        ResQuantity res2 = new ResQuantity(new Stone(), 9);
        List<ResQuantity> list = new ArrayList<>();
        list.add(res1);
        list.add(res2);
        assertEquals(messageFactory.buildBoxUpdate(list, list, "Boxes update."), "<Message><strongbox>coin:5:stone:9</strongbox><messageType>BOX_UPDATE</messageType><body>Boxes update.</body><warehouse>coin:5:stone:9</warehouse></Message>");

    }

    @Test
    void buildSlotsUpdate() throws MalformedMessageException {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "id1");
        map.put(2, "id2");
        assertEquals(messageFactory.buildSlotsUpdate(map, "Slots update."), "<Message><messageType>SLOTS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body>Slots update.</body></Message>");
    }

    @Test
    void buildDecksUpdate() throws MalformedMessageException {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "id1");
        map.put(2, "id2");
        assertEquals(messageFactory.buildDecksUpdate(map, "Decks update."),"<Message><messageType>DECKS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body>Decks update.</body></Message>");
    }

    @Test
    void buildUpdateLorenzo() throws MalformedMessageException {
        assertEquals(messageFactory.buildUpdateLorenzo("tokenID", "Update top token.") ,"<Message><messageType>TOKEN_UPDATE</messageType><body>Update top token.</body><token>tokenID</token></Message>");
    }

    @Test
    void buildUpdateMarket() throws MalformedMessageException {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        list.add(new MarbleGray());
        assertEquals(messageFactory.buildUpdateMarket(list, "Update market"),"<Message><market>MarbleBlue:MarbleGray</market><messageType>MARKET_UPDATE</messageType><body>Update market</body></Message>");
    }

    @Test
    void buildSelectedMarbles() throws MalformedMessageException {
        List<Marble> list = new ArrayList<>();
        list.add(new MarbleBlue());
        list.add(new MarbleGray());
        List<Marble> list1 = new ArrayList<>();
        list1.add(new MarblePurple());
        list1.add(new MarbleRed());
        assertEquals(messageFactory.buildSelectedMarbles(list, list1, "Marbles selected by the user."), "<Message><candidates>MarblePurple:MarbleRed</candidates><messageType>SELECTED_MARBLES</messageType><marbles>MarbleBlue:MarbleGray</marbles><body>Marbles selected by the user.</body></Message>");
    }

}