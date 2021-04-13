package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerTest {

    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {

        ArrayList<Action> actions = new ArrayList<>();

        MoveBlack moveBlack = new MoveBlack(2);
        MoveAndShuffle moveAndShuffle = new MoveAndShuffle(1);
        Discard discardBlue = new Discard(new Blue(), 2);
        Discard discardGreen = new Discard(new Green(), 2);
        Discard discardPurple = new Discard(new Purple(), 2);
        Discard discardYellow = new Discard(new Yellow(), 2);

        actions.add(moveBlack);
        actions.add(moveAndShuffle);
        actions.add(discardBlue);
        actions.add(discardPurple);
        actions.add(discardGreen);
        actions.add(discardYellow);

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

        gameBoard = new GameBoard(names, trackPoints, allSections, actions);


    }

    @AfterEach
    void tearDown() {

        gameBoard = null;

    }

    @Test
    void EndTurnAction() {
    }

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

            System.out.println(e.getMessage());
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