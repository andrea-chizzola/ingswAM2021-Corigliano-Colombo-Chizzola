package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiplePlayerTest {

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
        ArrayList<VaticanReportSection> sections3 = new ArrayList<VaticanReportSection>(3);

        sections1.add(0, new VaticanReportSection(5, 8, 2));
        sections1.add(1, new VaticanReportSection(12, 16, 3));
        sections1.add(2, new VaticanReportSection(19, 24, 4));

        sections2.add(0, new VaticanReportSection(5, 8, 2));
        sections2.add(1, new VaticanReportSection(12, 16, 3));
        sections2.add(2, new VaticanReportSection(19, 24, 4));

        sections3.add(0, new VaticanReportSection(5, 8, 2));
        sections3.add(1, new VaticanReportSection(12, 16, 3));
        sections3.add(2, new VaticanReportSection(19, 24, 4));

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");
        names.add("secondPlayer");
        names.add("thirdPlayer");

        ArrayList<ArrayList<VaticanReportSection>> allSections = new ArrayList<ArrayList<VaticanReportSection>>();

        allSections.add(sections1);
        allSections.add(sections2);
        allSections.add(sections3);

        gameBoard = new GameBoard(names, trackPoints, allSections, new ArrayList<Action>());


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
    void endTurnAction() throws LorenzoWonException {

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
    void addFaithToOthers() throws LorenzoWonException, PlayerWonException {

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