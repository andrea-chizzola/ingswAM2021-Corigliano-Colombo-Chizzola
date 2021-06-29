package it.polimi.ingsw;
import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Cards.WhiteMarble;
import it.polimi.ingsw.Model.MarketBoard.*;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        catch(IllegalShelfException e){
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
        catch(IllegalShelfException e){
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
        catch(IllegalShelfException e){
            fail();
        }
    }


    @Test
    public void addMarbleRed(){

        new MarbleRed().addResource(board,1);

        assertEquals(board.getFaithTrack().getPosition(),1);
    }



    @Test
    public void testWhite (){
        board.getModifications().addMarbleTo(new Coin());
        MarbleWhite marbleWhite = new MarbleWhite();
        assertTrue(marbleWhite.checkMarble(new MarbleYellow(), board));
    }

    @Test
    public void getWhiteTransformations1Test(){
        Marble marble;

        marble = new MarbleBlue();
        assertEquals(0, marble.whiteTransformations(board).size());
        marble = new MarbleYellow();
        assertEquals(0, marble.whiteTransformations(board).size());
        marble = new MarbleRed();
        assertEquals(0, marble.whiteTransformations(board).size());
        marble = new MarblePurple();
        assertEquals(0, marble.whiteTransformations(board).size());
        marble = new MarbleGray();
        assertEquals(0, marble.whiteTransformations(board).size());
    }

    @Test
    public void getWhiteTransformations2Test(){
        Marble marble;

        marble = new MarbleWhite();
        assertEquals(0, marble.whiteTransformations(board).size());
    }

    @Test
    public void getWhiteTransformations3Test(){
        Marble marble;
        List<Resource> transformations = new LinkedList<>();
        List<Resource> result;

        transformations.add(new Coin());
        transformations.add(new Servant());

        marble = new MarbleWhite();
        board.getModifications().addMarbleTo(new Coin());
        board.getModifications().addMarbleTo(new Servant());
        result = marble.whiteTransformations(board)
                .stream()
                .map(Marble::getResourceAssociated)
                .collect(Collectors.toList());
        assertEquals(transformations, result);

    }

    @Test
    public void getAssociatedResourcesTest(){
        Marble marble;

        marble = new MarbleBlue();
        assertEquals(new Shield(), marble.getResourceAssociated());
        marble = new MarbleYellow();
        assertEquals(new Coin(), marble.getResourceAssociated());
        marble = new MarbleRed();
        assertEquals(new Faith(), marble.getResourceAssociated());
        marble = new MarblePurple();
        assertEquals(new Servant(), marble.getResourceAssociated());
        marble = new MarbleGray();
        assertEquals(new Stone(), marble.getResourceAssociated());
        marble = new MarbleWhite();
        assertEquals(new White(), marble.getResourceAssociated());
    }

    @Test
    public void isWhiteTest(){
        Marble marble;

        marble = new MarbleBlue();
        assertFalse(marble.isWhite());
        marble = new MarbleYellow();
        assertFalse(marble.isWhite());
        marble = new MarbleRed();
        assertFalse(marble.isWhite());
        marble = new MarblePurple();
        assertFalse(marble.isWhite());
        marble = new MarbleGray();
        assertFalse(marble.isWhite());
        marble = new MarbleWhite();
        assertTrue(marble.isWhite());
    }

    @Test
    public void getImageTest(){
        Marble marble;

        marble = new MarbleBlue();
        assertEquals("MarbleBlue.PNG", marble.getImage());
        marble = new MarbleYellow();
        assertEquals("MarbleYellow.PNG", marble.getImage());
        marble = new MarbleRed();
        assertEquals("MarbleRed.PNG", marble.getImage());
        marble = new MarblePurple();
        assertEquals("MarblePurple.PNG", marble.getImage());
        marble = new MarbleGray();
        assertEquals("MarbleGray.PNG", marble.getImage());
        marble = new MarbleWhite();
        assertEquals("MarbleWhite.PNG", marble.getImage());
    }
}
