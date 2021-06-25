package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.ResourcesExpectedException;
import it.polimi.ingsw.Model.Boards.*;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.Cards.Colors.Yellow;
import it.polimi.ingsw.Model.Resources.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManageLeaderTest {
    private Board board;
    private GameBoard gameBoard;
    private final String file = "defaultConfiguration.xml";
    private DevelopmentCard dev1;
    private DevelopmentCard dev2;
    private DevelopmentCard dev3;
    private DevelopmentCard dev4;
    private DevelopmentCard dev5;
    private Coin coin = new Coin();
    private Shield shield = new Shield();
    private Servant servant = new Servant();
    private Stone stone = new Stone();




    @BeforeEach
    public void setUp() {

        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);
        gameBoard.giveLeaderCards(file);
        gameBoard.setStartTurns();
        board.setResourcesInitialized();
        gameBoard.attachView(new ViewForTest());

        Map<Integer,Boolean> map = new HashMap<>();
        map.put(1,true);
        map.put(2,true);
        map.put(3,false);
        map.put(4,false);

        try {
            gameBoard.initializeLeaderCard(map);
        } catch (InvalidActionException e) {
            fail();
        }

        //initialization of Warehouse and StrongBox
        //Warehouse: Quantity:Shelf:Resource
        // 1:1:coin, 1:2:servant, 2:3:stones, 1:4:shield, 0:5:coin
        Warehouse warehouse = board.getWarehouse();

        warehouse.insertResource(1, new Coin());
        warehouse.insertResource(2, new Servant());
        warehouse.insertResource(3, new Stone());
        warehouse.insertResource(3, new Stone());
        warehouse.addShelf(new ResQuantity(new Shield(), 2));
        warehouse.insertResource(4, new Shield());
        warehouse.addShelf(new ResQuantity(new Coin(), 2));

        //StrongBox: Quantity:Resource
        //10:coin, 1:Servant, 5:Shield, 3:Stone
        StrongBox strongBox = board.getStrongBox();
        strongBox.addResource(new Coin(), 20);
        strongBox.addResource(new Servant(), 20);
        strongBox.addResource(new Shield(), 20);
        strongBox.addResource(new Stone(), 20);

        //creation of a DevelopmentCard
        Requirements requirements1 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect1 = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        dev1 = new DevelopmentCard(0, effect1, requirements1, new Green(), 1, "1", "test1");

        //creation of a DevelopmentCard
        Requirements requirements2 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect2 = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        dev2 = new DevelopmentCard(0, effect2, requirements2, new Yellow(), 1, "2", "test2" );

        //creation of a DevelopmentCard
        Requirements requirements3 = new ResourceReqDev(new LinkedList<>());
        dev3 = new DevelopmentCard(0, effect2, requirements3, new Purple(), 2, "3", "test3");

        //creation of a DevelopmentCard
        dev4 = new DevelopmentCard(0, effect2, requirements3, new Blue(), 1, "4", "test4");

        //creation of a DevelopmentCard
        dev5 = new DevelopmentCard(0, effect2, requirements3, new Yellow(), 2, "5", "test5");

        //Initialization of board slots of firstPlayer
        try {
            Slot slot1 = board.getSlot(1);
            slot1.insertCard(dev1);
            slot1.insertCard(dev3);
            Slot slot2 = board.getSlot(2);
            slot2.insertCard(dev2);
            slot2.insertCard(dev5);
            Slot slot3 = board.getSlot(3);
            slot3.insertCard(dev4);
        } catch (IllegalSlotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemove() {

        try {
            gameBoard.removeCard(2);
        } catch (InvalidActionException e) {
            fail();}

        assertEquals(board.getFaithTrack().getPosition(), 1);
        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> board.getLeaderCard(5));
        //If a leader card is removed the arraylist with the leadercards will decrease its size
        assertEquals("Nonexistent Leader card",exception.getMessage());

    }

    @RepeatedTest(50)
    public void testActivateLeader(){

        LeaderCard card = board.getLeaderCard(1);
        boolean check = true;


        int coins = board.getStrongBox().getQuantity(coin);
        int shields = board.getStrongBox().getQuantity(shield);
        int stones = board.getStrongBox().getQuantity(stone);
        int servants = board.getStrongBox().getQuantity(servant);


        try {
            card.checkReq(board);
        }
        catch (InvalidActionException e){
            check = false;
        }
        catch (ResourcesExpectedException e){
            fail();
        }

        if(check){
            try {
                gameBoard.activateCard(1);
            }
            catch (InvalidActionException e){fail();}

            if(card.getSpecialEffect().isExtraShelf()){
                try {
                    assertEquals(board.getWarehouse().getResource(6), card.getSpecialEffect().getSpecialEffect().getResource());
                    assertEquals(board.getWarehouse().getCapacity(6), card.getSpecialEffect().getSpecialEffect().getQuantity());
                }catch (IllegalShelfException e){fail();}
            }

            if(card.getSpecialEffect().isDiscount()){
                ResQuantity resQuantity = card.getSpecialEffect().getSpecialEffect();
                assertEquals(board.getModifications().getDiscount(resQuantity.getResource()), resQuantity.getQuantity());
            }

            if(card.getSpecialEffect().isWhiteMarble()){
                ResQuantity resQuantity = card.getSpecialEffect().getSpecialEffect();
                assertTrue(board.getModifications().marbleTo(resQuantity.getResource()));
            }

            assertTrue(board.getLeaderCard(1).getStatus());

        }
        else{
            assertThrows(InvalidActionException.class, () -> gameBoard.activateCard(1));
            assertFalse(board.getLeaderCard(1).getStatus());
        }

        try {
            assertEquals(board.getWarehouse().getQuantity(1), 1);
            assertEquals(board.getWarehouse().getResource(1).getColor(), coin.getColor());
            assertEquals(board.getWarehouse().getQuantity(2), 1);
            assertEquals(board.getWarehouse().getResource(2).getColor(), servant.getColor());
            assertEquals(board.getWarehouse().getQuantity(3), 2);
            assertEquals(board.getWarehouse().getResource(3).getColor(), stone.getColor());
            assertEquals(board.getWarehouse().getQuantity(4), 1);
            assertEquals(board.getWarehouse().getResource(4).getColor(), shield.getColor());
            assertEquals(board.getWarehouse().getQuantity(5), 0);
            assertEquals(board.getWarehouse().getResource(5).getColor(), coin.getColor());
        }catch (IllegalShelfException e){fail();}


        assertEquals(board.getStrongBox().getQuantity(coin), coins);
        assertEquals(board.getStrongBox().getQuantity(servant), servants);
        assertEquals(board.getStrongBox().getQuantity(stone), stones);
        assertEquals(board.getStrongBox().getQuantity(shield), shields);

    }


    @Test
    public void testActivateLeaderWrongPosition(){



        int coins = board.getStrongBox().getQuantity(coin);
        int shields = board.getStrongBox().getQuantity(shield);
        int stones = board.getStrongBox().getQuantity(stone);
        int servants = board.getStrongBox().getQuantity(servant);




        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> gameBoard.activateCard(5));
        assertEquals("Nonexistent Leader card",exception.getMessage());


        try {
            assertEquals(board.getWarehouse().getQuantity(1), 1);
            assertEquals(board.getWarehouse().getResource(1).getColor(), coin.getColor());
            assertEquals(board.getWarehouse().getQuantity(2), 1);
            assertEquals(board.getWarehouse().getResource(2).getColor(), servant.getColor());
            assertEquals(board.getWarehouse().getQuantity(3), 2);
            assertEquals(board.getWarehouse().getResource(3).getColor(), stone.getColor());
            assertEquals(board.getWarehouse().getQuantity(4), 1);
            assertEquals(board.getWarehouse().getResource(4).getColor(), shield.getColor());
            assertEquals(board.getWarehouse().getQuantity(5), 0);
            assertEquals(board.getWarehouse().getResource(5).getColor(), coin.getColor());
        }catch (IllegalShelfException e){fail();}


        assertEquals(board.getStrongBox().getQuantity(coin), coins);
        assertEquals(board.getStrongBox().getQuantity(servant), servants);
        assertEquals(board.getStrongBox().getQuantity(stone), stones);
        assertEquals(board.getStrongBox().getQuantity(shield), shields);

    }


}