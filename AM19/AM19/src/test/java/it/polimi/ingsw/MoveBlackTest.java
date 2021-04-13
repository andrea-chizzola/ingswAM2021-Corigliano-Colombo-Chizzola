package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveBlackTest {

    private SinglePlayer singlePlayer;
    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {

        ArrayList<Integer> trackPoints = new ArrayList<Integer>(25);
        for(int i = 0; i < 25; i++){
            trackPoints.add(i, 0);
            if(i % 3 == 0){
                if(i <= 6){
                    trackPoints.set(i, i / 3);
                }
                else if(i <= 12){
                    trackPoints.set(i, i / 2);
                }else if(i <= 18){
                    trackPoints.set(i, i - 6);
                }else if(i == 21){
                    trackPoints.set(i, 16);
                }else {
                    trackPoints.set(i, 20);
                }
            }
        }

        ArrayList<VaticanReportSection> sections1 = new ArrayList<VaticanReportSection>(3);
        ArrayList<VaticanReportSection> sections2 = new ArrayList<VaticanReportSection>(3);

        sections1.add(0, new VaticanReportSection(5, 8, 2));
        sections1.add(1, new VaticanReportSection(12, 16, 3));
        sections1.add(2, new VaticanReportSection(19, 24, 4));

        sections2.add(0, new VaticanReportSection(5, 8, 2));
        sections2.add(1, new VaticanReportSection(12, 16, 3));
        sections2.add(2, new VaticanReportSection(19, 24, 4));

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");

        ArrayList<ArrayList<VaticanReportSection>> allSections = new ArrayList<ArrayList<VaticanReportSection>>();

        allSections.add(sections1);
        allSections.add(sections2);

        MoveBlack moveBlack = new MoveBlack(2);
        MoveBlack moveBlack1 = new MoveBlack(2);

        ArrayList<Action> actions1 = new ArrayList<>();
        actions1.add(moveBlack);
        actions1.add(moveBlack1);

        gameBoard = new GameBoard(names, trackPoints, allSections, actions1);

        singlePlayer = new SinglePlayer(gameBoard, trackPoints, allSections.get(1), actions1 );

    }

    @AfterEach
    void tearDown() {

        singlePlayer = null;
        gameBoard = null;

    }

    @Test
    @DisplayName("Do action test with only MoveBlack tokens")
    void doAction() throws LorenzoWonException {

        assertEquals(0, singlePlayer.getLorenzoTrack().getPosition());
        assertEquals(2, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(0, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());

        singlePlayer.endTurnAction(gameBoard); //2

        assertEquals(2, singlePlayer.getLorenzoTrack().getPosition());
        assertEquals(1, singlePlayer.getActionTokenDeck().getUnusedActionTokens().size());
        assertEquals(1, singlePlayer.getActionTokenDeck().getUsedActionTokens().size());

        for(int i = 0; i < 10; i++){
            singlePlayer.endTurnAction(gameBoard); //22
        }

        try{

            singlePlayer.endTurnAction(gameBoard); //24

        }catch(LorenzoWonException e){

            assertTrue(singlePlayer.getGameBoard().getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded());
            assertTrue(singlePlayer.getGameBoard().getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded());
            assertTrue(singlePlayer.getGameBoard().getPlayers().get(0).getFaithTrack().getSection(3).isDiscarded());

            System.out.println(e.getMessage());

        }

    }

    @Test
    @DisplayName("LorenzoWonException is thrown")
    void doActionException(){

        Exception exception;
        exception = assertThrows(LorenzoWonException.class, () -> { for(int i = 0; i < 12; i++){ singlePlayer.endTurnAction(gameBoard);} });
        assertEquals(exception.getMessage(), "End of the game. Lorenzo won.");

    }

}