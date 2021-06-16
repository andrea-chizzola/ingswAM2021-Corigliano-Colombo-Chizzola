package it.polimi.ingsw;

import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.ActionTokens.Discard;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.SinglePlayer;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTest {

    private Discard discardGreen = new Discard(new Green(), 2, "2", "test");
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
    void doAction(){

        assertEquals(discardGreen.toString(), "Action Token: \n" + "Discard: \n" + "2 C=GREEN");
        assertEquals(discardGreen.getId(), "2");
        assertEquals(discardGreen.getImage(), "test");

        assertEquals(6, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(0, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 1));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 2));
        assertEquals(4, singlePlayer.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3));

        while(!singlePlayer.getActionTokenDeck().getTop().equals(discardGreen)){
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        assertEquals(singlePlayer.getActionTokenDeck().getTop().hashCode(), discardGreen.hashCode());

        singlePlayer.endTurnAction(gameBoard);
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

    @Test
    void actionTest(){
        Action action = new Discard(new Green(), 2, "2", "test");
        assertTrue(action.equals(discardGreen));
        assertEquals(action.hashCode(), discardGreen.hashCode());
    }
}