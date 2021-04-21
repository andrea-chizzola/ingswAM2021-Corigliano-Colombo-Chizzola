package it.polimi.ingsw;


import it.polimi.ingsw.Model.Boards.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiplePlayerTest {

    private GameBoard gameBoard;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    void setUp() {
        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");
        names.add("secondPlayer");
        names.add("thirdPlayer");

        gameBoard = new GameBoard(names, file);


    }

    @AfterEach
    void tearDown() {

        gameBoard = null;

    }

    /**
     * tests the correct change of the current player
     */
    @Test
    @DisplayName("Current player switch")
    void endTurnAction(){

        assertEquals("firstPlayer", gameBoard.getCurrentPlayer().getNickname());

        gameBoard.endTurnMove();  //2

        assertEquals("secondPlayer", gameBoard.getCurrentPlayer().getNickname());

        gameBoard.endTurnMove();  //3

        assertEquals("thirdPlayer", gameBoard.getCurrentPlayer().getNickname());

        gameBoard.endTurnMove();  //1

        assertEquals("firstPlayer", gameBoard.getCurrentPlayer().getNickname());

    }

    /**
     * tests the correct behavior of the addFaithToOthers method
     */
    @Test
    void addFaithToOthers(){

        gameBoard.getPlayers().get(0).addFaithToOthers(1);

        assertEquals(0, gameBoard.getPlayers().get(0).getFaithTrack().getPosition());
        assertEquals(1, gameBoard.getPlayers().get(1).getFaithTrack().getPosition());
        assertEquals(1, gameBoard.getPlayers().get(2).getFaithTrack().getPosition());

        gameBoard.endTurnMove();

        gameBoard.getPlayers().get(1).addFaithToOthers(1);

        assertEquals(1, gameBoard.getPlayers().get(0).getFaithTrack().getPosition());
        assertEquals(1, gameBoard.getPlayers().get(1).getFaithTrack().getPosition());
        assertEquals(2, gameBoard.getPlayers().get(2).getFaithTrack().getPosition());

        gameBoard.endTurnMove();

        gameBoard.getPlayers().get(2).addFaithToOthers(1);

        assertEquals(2, gameBoard.getPlayers().get(0).getFaithTrack().getPosition());
        assertEquals(2, gameBoard.getPlayers().get(1).getFaithTrack().getPosition());
        assertEquals(2, gameBoard.getPlayers().get(2).getFaithTrack().getPosition());

    }

}