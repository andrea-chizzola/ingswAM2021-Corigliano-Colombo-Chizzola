package it.polimi.ingsw;

import it.polimi.ingsw.Model.ActionTokens.Discard;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.SinglePlayer;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.Cards.Colors.Yellow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTest {

    private Discard discardBlue = new Discard(new Blue(), 2, "1", "test1");
    private Discard discardGreen = new Discard(new Green(), 2, "2", "test2");
    private Discard discardPurple = new Discard(new Purple(), 2, "3", "test3");
    private Discard discardYellow = new Discard(new Yellow(), 2, "4", "test4");
    private SinglePlayer singlePlayer;
    private GameBoard gameBoard;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    void setUp() {

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");

        gameBoard = new GameBoard(names, file);

        singlePlayer = new SinglePlayer(gameBoard, file);

    }

    @AfterEach
    void tearDown() {

        gameBoard = null;
        singlePlayer = null;

    }

    @Test
    @DisplayName("Discard green test")
    void doActionBlue(){

        assertEquals(6, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(0, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 1));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 2));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3));

        while(!singlePlayer.getActionTokenDeck().getTop().equals(discardGreen)){
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        singlePlayer.endTurnAction(gameBoard);

        assertEquals(5, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(1, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());
        assertEquals(2, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 1));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 2));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3));

        singlePlayer.getActionTokenDeck().mergeAndShuffle();

        while(!singlePlayer.getActionTokenDeck().getTop().equals(discardGreen)){
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        singlePlayer.endTurnAction(gameBoard);

        assertEquals(0, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 1));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 2));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3));

        singlePlayer.getActionTokenDeck().mergeAndShuffle();

        for(int i = 0; i < 3; i++){

            while(!singlePlayer.getActionTokenDeck().getTop().equals(discardGreen)){
                singlePlayer.getActionTokenDeck().mergeAndShuffle();
            }

            singlePlayer.endTurnAction(gameBoard);
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        assertEquals(0, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 1));
        assertEquals(0, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 2));
        assertEquals(2, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3));

        while (!singlePlayer.getActionTokenDeck().getTop().equals(discardGreen)) {
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        assertFalse(singlePlayer.getGameBoard().isEndGameStarted());

        singlePlayer.endTurnAction(gameBoard);

        assertEquals(0, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 1));
        assertEquals(0, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 2));
        assertEquals(0, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3));

        assertTrue(singlePlayer.getGameBoard().isEndGameStarted());

    }

}