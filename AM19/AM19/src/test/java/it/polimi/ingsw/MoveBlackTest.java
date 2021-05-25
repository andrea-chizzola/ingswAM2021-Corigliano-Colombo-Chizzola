package it.polimi.ingsw;

import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.ActionTokens.MoveBlack;
import it.polimi.ingsw.Model.Boards.SinglePlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveBlackTest {

    private MoveBlack moveBlack = new MoveBlack(2, "2");
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

        singlePlayer = null;
        gameBoard = null;

    }

    @Test
    @DisplayName("Do action test with only MoveBlack tokens")
    void doAction(){

        assertEquals(0, singlePlayer.getLorenzoTrack().getPosition());
        assertEquals(6, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(0, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());

        while(!singlePlayer.getActionTokenDeck().getTop().equals(moveBlack)){
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }

        singlePlayer.endTurnAction(gameBoard);
        singlePlayer.endTurnAction(gameBoard);//2

        assertEquals(2, singlePlayer.getLorenzoTrack().getPosition());
        assertEquals(5, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(1, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());

        singlePlayer.getActionTokenDeck().mergeAndShuffle();

        for(int i = 0; i < 10; i++){

            while(!singlePlayer.getActionTokenDeck().getTop().equals(moveBlack)){
                singlePlayer.getActionTokenDeck().mergeAndShuffle();
            }

            singlePlayer.endTurnAction(gameBoard); //22
            singlePlayer.getActionTokenDeck().mergeAndShuffle();
        }



        while(!singlePlayer.getActionTokenDeck().getTop().equals(moveBlack)){
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