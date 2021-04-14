package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerTest {

    private GameBoard gameBoard;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    void setUp() {

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");


        gameBoard = new GameBoard(names, file);

    }

    @AfterEach
    void tearDown() {

        gameBoard = null;

    }

    //endTurnAction and getters tested inside the Action Tokens tests

    @Test
    @DisplayName("MoveBlackCross and addFaithToOthers test")
    void MoveBlackCrossAndAddFaithToOthers() throws LorenzoWonException, PlayerWonException {

        gameBoard.getPlayers().get(0).addFaith(5); //5
        gameBoard.addFaithToOthers(8); //8

        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());

        gameBoard.getPlayers().get(0).addFaith(4); //9
        gameBoard.addFaithToOthers(8); //16

        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());

        gameBoard.getPlayers().get(0).addFaith(7); //16
        gameBoard.addFaithToOthers(2); //18

        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());

    }

    @Test
    @DisplayName("addFaithToOthers multiple activation")
    void multipleActivation() throws LorenzoWonException ,PlayerWonException {

        gameBoard.getPlayers().get(0).addFaith(5); //5
        gameBoard.addFaithToOthers(5); //5

        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

        gameBoard.getPlayers().get(0).addFaith(12); //17
        gameBoard.addFaithToOthers(1); //6

        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

        gameBoard.getPlayers().get(0).addFaith(1); //18
        gameBoard.addFaithToOthers(10); //16

        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
        assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).getStatus());
        assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

    }

    @Test
    @DisplayName("Lorenzo won the game")
    void lorenzoWon() throws LorenzoWonException, PlayerWonException {

        gameBoard.getPlayers().get(0).addFaith(5); //5
        gameBoard.addFaithToOthers(5); //5

        gameBoard.getPlayers().get(0).addFaith(12); //17
        gameBoard.addFaithToOthers(1); //6

        gameBoard.getPlayers().get(0).addFaith(1); //18
        gameBoard.addFaithToOthers(10); //16

        try {

            gameBoard.addFaithToOthers(15); //24

        }catch(LorenzoWonException e){

            assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
            assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
            assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
            assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
            assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).getStatus());
            assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

        }

    }

    @Test
    @DisplayName("LorenzoWonException is thrown")
    void lorenzoWonException(){

        Exception exception;
        exception = assertThrows(LorenzoWonException.class, () -> {gameBoard.addFaithToOthers(24);});
        assertEquals(exception.getMessage(), "End of the game. Lorenzo won.");

    }

    @Test
    @DisplayName("The player won the game")
    void playerWon() throws LorenzoWonException, PlayerWonException {

        gameBoard.getPlayers().get(0).addFaith(5); //5
        gameBoard.addFaithToOthers(8); //8

        gameBoard.getPlayers().get(0).addFaith(4); //9
        gameBoard.addFaithToOthers(8); //16

        gameBoard.getPlayers().get(0).addFaith(7); //16
        gameBoard.addFaithToOthers(2); //18

        try{

            gameBoard.getPlayers().get(0).addFaith(15); //16

        }catch(PlayerWonException e){

            assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
            assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
            assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
            assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
            assertTrue(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).getStatus());
            assertFalse(gameBoard.getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

            System.out.println(e.getMessage());

        }
    }

    @Test
    @DisplayName("PlayerWonException is thrown")
    void playerWonException(){

        Exception exception;
        exception = assertThrows(PlayerWonException.class, () -> {gameBoard.getPlayers().get(0).addFaith(24);});
        assertEquals(exception.getMessage(), "A player won. The game is over");

    }

}