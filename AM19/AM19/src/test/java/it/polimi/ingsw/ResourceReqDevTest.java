package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceReqDevTest {

    private Board board;
    private final String file = "defaultConfiguration.xml";
    private DevelopmentCard card;


    @BeforeEach
    public void setUp() {

        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

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
        strongBox.addResource(new Coin(), 10);
        strongBox.addResource(new Servant(), 1);
        strongBox.addResource(new Shield(), 5);
        strongBox.addResource(new Stone(), 3);

        //Requirements coin:2, servant:1
        LinkedList<ResQuantity> requirements = new LinkedList<>();
        requirements.add(new ResQuantity(new Coin(), 2));
        requirements.add(new ResQuantity(new Servant(), 1));
        Requirements req = new ResourceReqDev(requirements);

        LinkedList<ResQuantity> materials = new LinkedList<>();
        LinkedList<ResQuantity> products = new LinkedList<>();
        Production production = new Production(materials,products, 1, 0);

        card = new DevelopmentCard(3,production,req, new Green(), 1, "1", "test1");
    }

    @Test
    public void resourceReqDevTest(){

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(2);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 2));
        strongBox.add(new ResQuantity(new Stone(), 0));
        strongBox.add(new ResQuantity(new Shield(), 0));

        try {
            assertTrue(card.checkReq(board, shelves,quantity,strongBox));
        }catch (InvalidActionException e){fail();}

    }

    @Test
    public void resourceReqDevTestTooManyResources(){

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(2);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 3));
        strongBox.add(new ResQuantity(new Stone(), 0));
        strongBox.add(new ResQuantity(new Shield(), 0));


        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> card.checkReq(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Too many resources selected!");
    }

    @Test
    public void resourceReqDevTestDiscount(){

        board.getModifications().addDiscount(new Servant(),1);
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(2);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(0);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 2));
        strongBox.add(new ResQuantity(new Stone(), 0));
        strongBox.add(new ResQuantity(new Shield(), 0));

        try {
            assertTrue(card.checkReq(board, shelves,quantity,strongBox));
        }catch (InvalidActionException e){fail();}

    }


    @Test
    public void resourceReqDevTestEmptyReq(){

        LinkedList<ResQuantity> requirements = new LinkedList<>();

        Requirements req = new ResourceReqDev(requirements);

        LinkedList<ResQuantity> materials = new LinkedList<>();
        LinkedList<ResQuantity> products = new LinkedList<>();
        Production production = new Production(materials,products, 1, 2);

        card = new DevelopmentCard(3,production,req, new Green(), 1, "2", "test2");

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(2);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(0);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 0));
        strongBox.add(new ResQuantity(new Stone(), 0));
        strongBox.add(new ResQuantity(new Shield(), 0));

        try {
            assertTrue(card.checkReq(board, shelves,quantity,strongBox));
        }catch (InvalidActionException e){fail();}

    }

    @Test
    public void resourceReqDevTestOnlyStrongbox(){


        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 2));
        strongBox.add(new ResQuantity(new Stone(), 0));
        strongBox.add(new ResQuantity(new Servant(), 1));

        try {
            assertTrue(card.checkReq(board, shelves,quantity,strongBox));
        }catch (InvalidActionException e){fail();}

    }

    @Test
    public void resourceReqDevTestEmptySelectedResources(){


        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 0));
        strongBox.add(new ResQuantity(new Stone(), 0));
        strongBox.add(new ResQuantity(new Servant(), 1));

        assertThrows(InvalidActionException.class, () -> card.checkReq(board, shelves, quantity, strongBox));

    }

    @Test
    public void getCardsRequirementTest(){
        assertTrue(card.getCardRequirements().isEmpty());
    }

}
