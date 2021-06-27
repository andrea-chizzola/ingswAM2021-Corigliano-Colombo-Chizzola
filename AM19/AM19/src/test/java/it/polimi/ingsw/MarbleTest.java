package it.polimi.ingsw;
import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.Coin;
import it.polimi.ingsw.Model.Resources.Servant;
import it.polimi.ingsw.Model.Resources.Shield;
import it.polimi.ingsw.Model.Resources.Stone;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MarbleTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp() {

        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

    }


    @Test
    public void addMarbleBlue(){
        try{
            new MarbleBlue().addResource(board,1);
            assertEquals(board.getWarehouse().getResource(1), new Shield());
            assertEquals(board.getWarehouse().getQuantity(1), 1);
        }
        catch(IllegalShelfException e){
            fail();
        }
    }

    @Test
    public void testWhiteMarble(){
        board.getModifications().addMarbleTo(new Coin());
        board.getModifications().addMarbleTo((new Stone()));

        Marble marble = new MarbleWhite();

        assertEquals(marble.whiteTransformations(board).size(),2);
        assertTrue(marble.whiteTransformations(board).stream().anyMatch(marble1 -> marble1.toString().equals("MarbleYellow")));
        assertTrue(marble.whiteTransformations(board).stream().anyMatch(marble1 -> marble1.toString().equals("MarbleGray")));

    }

    @Test
    public void addMarbleGray(){
        try{
            new MarbleGray().addResource(board,2);
            assertEquals(board.getWarehouse().getResource(2), new Stone());
            assertEquals(board.getWarehouse().getQuantity(2), 1);
        }
        catch(/*InvalidActionException |*/ IllegalShelfException e){
            fail();
        }
    }

    @Test
    public void addMarblePurple(){
        try{
            new MarblePurple().addResource(board,1);
            assertEquals(board.getWarehouse().getResource(1), new Servant());
            assertEquals(board.getWarehouse().getQuantity(1), 1);
        }
        catch(/*InvalidActionException |*/ IllegalShelfException e){
            fail();
        }
    }

    @Test
    public void addMarbleYellow(){
        try{
            new MarbleYellow().addResource(board,1);
            assertEquals(board.getWarehouse().getResource(1), new Coin());
            assertEquals(board.getWarehouse().getQuantity(1), 1);
        }
        catch(/*InvalidActionException |*/ IllegalShelfException e){
            fail();
        }
    }


    @Test
    public void addMarbleRed(){

        new MarbleRed().addResource(board,1);

        assertEquals(board.getFaithTrack().getPosition(),1);
    }



    @Test
    void testWhite (){
        board.getModifications().addMarbleTo(new Coin());
        MarbleWhite marbleWhite = new MarbleWhite();
        assertTrue(marbleWhite.checkMarble(new MarbleYellow(), board));
    }

}
