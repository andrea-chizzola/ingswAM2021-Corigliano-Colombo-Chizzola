package it.polimi.ingsw;

import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.ActionTokens.MoveAndShuffle;
import it.polimi.ingsw.Model.Boards.SinglePlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveAndShuffleTest {

    private MoveAndShuffle moveAndShuffle = new MoveAndShuffle(1, "2");
    private SinglePlayer singlePlayer;
    private GameBoard gameBoard;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    void setUp() {

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");

        gameBoard = new GameBoard(names, file);

        singlePlayer = new SinglePlayer(gameBoard, file);
        singlePlayer.endTurnAction(gameBoard);

    }

    @AfterEach
    void tearDown() {

        singlePlayer = null;
        gameBoard = null;

    }

    @Test
    @DisplayName("Do action test with only MoveAndShuffle tokens")
    void doAction(){

        assertEquals(0, singlePlayer.getLorenzoTrack().getPosition());
        assertEquals(6, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(0, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());

        for(int i = 0; i < 3; i++) {

            while (!singlePlayer.getActionTokenDeck().getTop().equals(moveAndShuffle)) {
                singlePlayer.getActionTokenDeck().mergeAndShuffle();
            }

            singlePlayer.endTurnAction(gameBoard); //3

        }

        assertEquals(3, singlePlayer.getLorenzoTrack().getPosition());
        assertEquals(6, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(0, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());

        for(int i = 0; i < 20; i++){

            while (!singlePlayer.getActionTokenDeck().getTop().equals(moveAndShuffle)) {
                singlePlayer.getActionTokenDeck().mergeAndShuffle();
            }

            singlePlayer.endTurnAction(gameBoard); //23

        }

        while (!singlePlayer.getActionTokenDeck().getTop().equals(moveAndShuffle)) {
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        assertFalse(singlePlayer.getGameBoard().isEndGameStarted());

        singlePlayer.endTurnAction(gameBoard); //24

        assertTrue(singlePlayer.getGameBoard().getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
        assertTrue(singlePlayer.getGameBoard().getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
        assertTrue(singlePlayer.getGameBoard().getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

        assertTrue(singlePlayer.getGameBoard().isEndGameStarted());

    }

}