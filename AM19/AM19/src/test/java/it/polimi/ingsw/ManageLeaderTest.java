package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.Model.Turn.ManageLeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ManageLeaderTest {


    private Board board;

    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp() {
        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");

        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

        gameBoard.giveLeaderCards(file);


        //initialization of Warehouse and StrongBox
        //Warehouse: Quantity:Shelf:Resource
        // 1:1:coin, 1:2:servant, 2:3:stones, 1:4:shield, 0:5:coin
        Warehouse warehouse = board.getWarehouse();
        try {
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Servant());
            warehouse.addResource(3, new Stone());
            warehouse.addResource(3, new Stone());
            warehouse.addShelf(new ResQuantity(new Shield(), 2));
            warehouse.addResource(4, new Shield());
            warehouse.addShelf(new ResQuantity(new Coin(), 2));
        } catch (IllegalShelfException e) {
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
    public void test1() {


        ManageLeader action = new ManageLeader();
        try {
            action.removeCard(board, 1);
        } catch (InvalidActionException e) {
            System.out.println(e.getMessage());
            fail();
            assertEquals(board.getFaithTrack().getPosition(), 1);

        }

    }
}