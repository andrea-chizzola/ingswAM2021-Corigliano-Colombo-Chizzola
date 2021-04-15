package it.polimi.ingsw;
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
        catch(InvalidActionException | MarbleWhiteException | IllegalShelfException e){
            fail();
        }
    }

    @Test
    public void addMarbleGray(){
        try{
            new MarbleGray().addResource(board,2);
            assertEquals(board.getWarehouse().getResource(2), new Stone());
            assertEquals(board.getWarehouse().getQuantity(2), 1);
        }
        catch(InvalidActionException | MarbleWhiteException | IllegalShelfException e){
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
        catch(InvalidActionException | MarbleWhiteException | IllegalShelfException e){
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
        catch(InvalidActionException | MarbleWhiteException | IllegalShelfException e){
            fail();
        }
    }

    @Test
    public void addMarbleWhite(){
        assertThrows(MarbleWhiteException.class, () -> new MarbleWhite().addResource(board,1));
    }

    @Test
    public void addMarbleRed(){
        try{
            new MarbleRed().addResource(board,1);
        }
        catch(InvalidActionException | MarbleWhiteException | PlayerWonException e){
            fail();
        }
        assertEquals(board.getFaithTrack().getPosition(),1);
    }

    @Test
    public void IllegalShelfTest(){
        try {
            new MarbleYellow().addResource(board, 1);
        }
        catch(InvalidActionException | MarbleWhiteException e){
                fail();
            }
        assertThrows(InvalidActionException.class, () -> new MarbleBlue().addResource(board,1));

        assertThrows(InvalidActionException.class, () -> new MarbleBlue().addResource(board,50));
    }


}
