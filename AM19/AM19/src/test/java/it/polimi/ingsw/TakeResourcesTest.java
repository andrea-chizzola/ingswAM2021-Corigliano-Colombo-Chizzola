package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.MarketBoard.MarbleYellow;
import it.polimi.ingsw.Model.Resources.Coin;
import it.polimi.ingsw.Model.Turn.TakeResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TakeResourcesTest {
    private Board board;
    private GameBoard gameBoard;
    private  final String file = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp() {
        //creation of GameBoard and Board

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");
        names.add("secondPlayer");
        names.add("thirdPlayer");

        gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

    }

    @Test
    public void insertResourcesTest(){
        TakeResources takeResources = new TakeResources();
        try {
            takeResources.insertResource(board, new MarbleYellow(), 1);
        }catch (InvalidActionException e){fail();}

        try {
            assertEquals(board.getWarehouse().getQuantity(1), 1);
            assertEquals(board.getWarehouse().getResource(1), new Coin());
        } catch (IllegalShelfException e){fail();}

    }


    @Test
    public void discardResourceTest(){
        TakeResources takeResources = new TakeResources();

        takeResources.discardResource(board, new MarbleYellow());

        assertEquals(board.getFaithTrack().getPosition(),0);

        for(Board board1 : gameBoard.getPlayers()){
            if(board1 != board)
                assertEquals(board1.getFaithTrack().getPosition(),1);
        }

    }
}