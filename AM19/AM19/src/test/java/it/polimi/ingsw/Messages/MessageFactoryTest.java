package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Messages.Enumerations.TurnType;
import it.polimi.ingsw.Model.Cards.Colors.DevColor;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.Coin;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Stone;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {


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
            assertEquals(MessageFactory.buildConnection("Connection request.", "nickname", true, 4),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>CONNECTION</messageType><playersNumber>4</playersNumber><body>Connection request.</body><gameHost>true</gameHost><player>nickname</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDisconnection()  {
        try {
            assertEquals(MessageFactory.buildDisconnection("Disconnection request.", "nickname"),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DISCONNECTION</messageType><body>Disconnection request.</body><player>nickname</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildReconnection()  {
        try {
            assertEquals(MessageFactory.buildReconnection("Reconnection request.", "nickname"),
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
            assertEquals(MessageFactory.buildEndGame(map, "pippo","Winner message."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><winner>pippo</winner><messageType>END_GAME</messageType><body>Winner message.</body><points>player1:25:player2:20</points></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildExit() {
        try {
            assertEquals(MessageFactory.buildExit("Exit body."), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>EXIT</messageType><body>Exit body.</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildStartGame(){
        List<String> list = new ArrayList<>();
        list.add("player1");
        list.add("player2");
        try {
            assertEquals(MessageFactory.buildStartGame("Game is starting", list),"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>START_GAME</messageType><activePlayers>player1:player2</activePlayers><body>Game is starting</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildLeaderUpdate(){
        Map<Integer,String> map1 = new HashMap<>();
        map1.put(1, "card");
        Map<Integer,ItemStatus> map2 = new HashMap<>();
        map2.put(1, ItemStatus.ACTIVE);
        try {
            assertEquals(MessageFactory.buildLeaderUpdate(map1, map2,"player", "update leader cards"),"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><leaderStatus>1:ACTIVE</leaderStatus><messageType>UPDATE_LEADER_CARDS</messageType><body>update leader cards</body><leaderCards>1:card</leaderCards><player>player</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildSelectedResources(){
        try {
            assertEquals(MessageFactory.buildSelectedResources("selection", "Selection of resources during initialization"),"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>RESOURCE</messageType><resources>selection</resources><body>Selection of resources during initialization</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildBuyCard(){
        try {
            assertEquals(MessageFactory.buildBuyCard(DevColor.BLUE, 2, 1, "id", "Buy card", "warehouse", "strongbox"),"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><strongBox>strongbox</strongBox><messageType>BUY_CARD</messageType><color>blue</color><level>2</level><slot>1</slot><ID>id</ID><body>Buy card</body><warehouse>warehouse</warehouse></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDoProduction(){
        try {
            assertEquals(MessageFactory.BuildDoProduction(true, "developments", "leaders", "custom materials", "custom products", "warehouse", "strongbox", "Do production"),"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><strongBox>strongbox</strongBox><messageType>DO_PRODUCTION</messageType><developmentCards>developments</developmentCards><personalProduction>true</personalProduction><body>Do production</body><chosenProducts>custom products</chosenProducts><warehouse>warehouse</warehouse><leaderCards>leaders</leaderCards><chosenMaterials>custom materials</chosenMaterials></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildLeaderAction(){
        try {
            assertEquals(MessageFactory.buildLeaderAction("id", 1, "action", "Action on leader"), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>LEADER_ACTION</messageType><action>action</action><body>Action on leader</body><leaderCards>1:id</leaderCards></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildSwap(){
        try {
            assertEquals(MessageFactory.buildSwap(1,3, "swap"), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>SWAP</messageType><source>1</source><body>swap</body><target>3</target></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildMarketSelection(){
        try {
            assertEquals(MessageFactory.buildMarketSelection("tray selection", 2, "body"), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><tray>tray selection</tray><number>2</number><messageType>MARKET_SELECTION</messageType><body>body</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildActionMarble(){
        try {
            assertEquals(MessageFactory.buildActionMarble("action", "body"), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><marblesActions>action</marblesActions><messageType>ACTION_MARBLE</messageType><body>body</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildEndGameEmptyMap()  {
        Map<String, Integer> map = new HashMap<>();
        try {
            assertEquals(MessageFactory.buildEndGame(map, "pippo","Winner message."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><winner>pippo</winner><messageType>END_GAME</messageType><body>Winner message.</body><points/></Message>");
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
            assertEquals(MessageFactory.buildBoxUpdate(list, list, "pippo","Boxes update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><strongbox>coin:5:stone:9</strongbox><messageType>BOX_UPDATE</messageType><body>Boxes update.</body><warehouse>coin:5:stone:9</warehouse><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }

    }

    @Test
    void buildFaithUpdate(){
        Map<String,Integer> faith = new HashMap<>();
        Map<String, List<ItemStatus>> sections = new HashMap<>();
        Optional<Integer> faithLorenzo;
        Optional<List<ItemStatus>> sectionsLorenzo;
        String body = "body";
        faith.put("pippo",5);
        faith.put("pluto",6);
        List<ItemStatus> list1 = new ArrayList<>();
        list1.add(ItemStatus.ACTIVE);
        list1.add(ItemStatus.DISCARDED);
        List<ItemStatus> list2 = new ArrayList<>();
        list2.add(ItemStatus.INACTIVE);
        sections.put("pippo",list1);
        sections.put("pluto",list2);
        sectionsLorenzo = Optional.of(list2);
        faithLorenzo = Optional.of(3);

        String messageExpected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><pippoSections>ACTIVE:DISCARDED</pippoSections><LorenzoFaith>3</LorenzoFaith><LorenzoSections>INACTIVE</LorenzoSections><messageType>FAITH_UPDATE</messageType><faith>pluto:6:pippo:5</faith><plutoSections>INACTIVE</plutoSections><body>body</body></Message>";
        String messageActual = "";
        try {
            messageActual = MessageFactory.buildFaithUpdate(faith,sections,faithLorenzo,sectionsLorenzo,body);
        } catch (MalformedMessageException e) {
            fail();
        }

        assertEquals(messageExpected,messageActual);

    }


    @Test
    void buildBoxUpdateEmpty()  {

        List<ResQuantity> list = new ArrayList<>();

        try {
            assertEquals(MessageFactory.buildBoxUpdate(list, list, "pippo","Boxes update."),
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
            assertEquals(MessageFactory.buildSlotsUpdate(map, "pippo","Slots update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>SLOTS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body>Slots update.</body><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildSlotsUpdateEmptyMap()  {
        Map<Integer, String> map = new HashMap<>();

        try {
            assertEquals(MessageFactory.buildSlotsUpdate(map, "pippo","Slots update."),
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
            assertEquals(MessageFactory.buildDecksUpdate(map, "Decks update."),
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
            assertEquals(MessageFactory.buildDecksUpdate(map, ""),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DECKS_UPDATE</messageType><devCards>1:id1:2:id2</devCards><body/></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildDecksUpdateEmptyMap() {
        Map<Integer, String> map = new HashMap<>();

        try {
            assertEquals(MessageFactory.buildDecksUpdate(map, "Decks update."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>DECKS_UPDATE</messageType><devCards/><body>Decks update.</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildGameStatus(){
        try {
            assertEquals(MessageFactory.buildGameStatus(false, "ERROR: Missed pong", "player", TurnType.WRONG_STATE), "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>GAME_STATUS</messageType><correct>false</correct><state>WRONG_STATE</state><body>ERROR: Missed pong</body><player>player</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildReply(){
        try {
            assertEquals(MessageFactory.buildReply(true,"reply","player"),"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><messageType>REPLY</messageType><correct>true</correct><body>reply</body><player>player</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }


    @Test
    void buildUpdateLorenzo()  {
        try {
            assertEquals(MessageFactory.buildUpdateLorenzo("tokenID", "Update top token.")
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
            assertEquals(MessageFactory.buildUpdateMarket(list, "Update market"),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><market>MarbleBlue:MarbleGray</market><messageType>MARKET_UPDATE</messageType><body>Update market</body></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }
    }

    @Test
    void buildUpdateMarketEmpty()  {
        List<Marble> list = new ArrayList<>();
        try {
            assertEquals(MessageFactory.buildUpdateMarket(list, "Update market"),
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
            assertEquals(MessageFactory.buildSelectedMarbles(list, list1, "pippo", "Marbles selected by the user."),
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
            assertEquals(MessageFactory.buildSelectedMarbles(list, list1, "pippo", "Marbles selected by the user."),
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message><candidates/><messageType>GAME_STATUS</messageType><correct>true</correct><marbles/><state>MANAGE_MARBLE</state><body>Marbles selected by the user.</body><player>pippo</player></Message>");
        } catch (MalformedMessageException e) {
            fail();
        }


    }
}