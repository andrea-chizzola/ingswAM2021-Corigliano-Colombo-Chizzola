package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.Model.Turn.DoProduction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;


import static org.junit.jupiter.api.Assertions.*;

class DoProductionTest {
    private Production production1;
    private  Production production2;
    private Board board;
    private  ArrayList<Production> productions;
    private  final String file = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp(){
        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

        productions = new ArrayList<>();

        //creation of a production without faith in products
        LinkedList<ResQuantity> materials1 = new LinkedList<>();
        materials1.add(new ResQuantity(new Coin(),2));
        materials1.add(new ResQuantity(new Stone(), 1));

        LinkedList<ResQuantity> products1 = new LinkedList<>();
        products1.add(new ResQuantity(new Servant(),3));
        products1.add(new ResQuantity(new Shield(),1));

        production1 = new Production(materials1, products1, 0, 0);

        productions.add(production1);

        //creation of a production with faith in products
        LinkedList<ResQuantity> materials2 = new LinkedList<>();
        materials2.add(new ResQuantity(new Coin(),2));
        materials2.add(new ResQuantity(new Stone(), 3));

        LinkedList<ResQuantity> products2 = new LinkedList<>();
        products2.add(new ResQuantity(new Faith(),3));
        products2.add(new ResQuantity(new Stone(),1));

        production2 = new Production(materials2, products2, 0, 0);

        productions.add(production2);

        //initialization of Warehouse and StrongBox
        //Warehouse: Quantity:Shelf:Resource
        // 1:1:coin, 1:2:servant, 2:3:stones, 1:4:shield, 0:5:coin
        Warehouse warehouse = board.getWarehouse();
        try{
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Servant());
            warehouse.addResource(3, new Stone());
            warehouse.addResource(3, new Stone());
            warehouse.addShelf(new ResQuantity(new Shield(), 2));
            warehouse.addResource(4, new Shield());
            warehouse.addShelf(new ResQuantity(new Coin(), 2));
        }
        catch(IllegalShelfException e){
            fail();
        }
        //StrongBox: Quantity:Resource
        //10:coin, 1:Servant, 5:Shield, 3:Stone
        StrongBox strongBox = board.getStrongBox();
        strongBox.addResource(new Coin(), 10);
        strongBox.addResource(new Servant(), 1);
        strongBox.addResource(new Shield(), 5);
        strongBox.addResource(new Stone(), 3);

    }


    @Test
    public void testDoProduction(){
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 3));
        strongBox.add(new ResQuantity(new Stone(), 2));
        strongBox.add(new ResQuantity(new Shield(), 0));
        DoProduction action = new DoProduction();
        try {

            action.doProduction(board,productions,shelves,quantity,strongBox);
        }catch (InvalidActionException e){fail();}

        try {
            assertEquals(board.getWarehouse().getQuantity(2), 1);
            assertEquals(board.getWarehouse().getResource(2), new Servant());
            assertEquals(board.getWarehouse().getQuantity(4), 1);
            assertEquals(board.getWarehouse().getResource(4), new Shield());
            assertEquals(board.getWarehouse().getQuantity(5), 0);
            assertEquals(board.getWarehouse().getResource(5), new Coin());

        }catch (IllegalShelfException e){fail();}


        assertThrows(IllegalShelfException.class, () -> board.getWarehouse().getQuantity(1));
        assertThrows(IllegalShelfException.class, () -> board.getWarehouse().getQuantity(3));
        assertThrows(IllegalShelfException.class, () -> board.getWarehouse().getQuantity(6));

        assertEquals(board.getStrongBox().getQuantity(new Coin()), 7);
        assertEquals(board.getStrongBox().getQuantity(new Shield()), 6);
        assertEquals(board.getStrongBox().getQuantity(new Stone()), 2);
        assertEquals(board.getStrongBox().getQuantity(new Servant()), 4);
        assertEquals(board.getFaithTrack().getPosition(), 3);

    }

    @Test
    public void DoProductionTestTooManyResources() {
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 3));
        strongBox.add(new ResQuantity(new Stone(), 2));
        strongBox.add(new ResQuantity(new Shield(), 1));
        DoProduction action = new DoProduction();
        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> action.doProduction(board, productions, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Too many resources selected!");
    }

    @Test
    public void DoProductionTestNotEnoughResources() {
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 0));
        strongBox.add(new ResQuantity(new Stone(), 2));
        strongBox.add(new ResQuantity(new Shield(), 1));
        DoProduction action = new DoProduction();
        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> action.doProduction(board, productions, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Insufficient resources!");
    }
}