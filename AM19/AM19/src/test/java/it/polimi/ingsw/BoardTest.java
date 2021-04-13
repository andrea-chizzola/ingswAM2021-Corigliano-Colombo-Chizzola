package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

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
     * tests the correct activation of the vatican report
     */
    @Test
    @DisplayName("Add faith activation")
    void addFaithActivation() throws PlayerWonException {

        gameBoard.getPlayers().get(0).addFaith(7); //1>7
        gameBoard.getPlayers().get(1).addFaith(6); //2>6
        gameBoard.getPlayers().get(2).addFaith(4); //3>4

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        gameBoard.getPlayers().get(0).addFaith(1); //1>8

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        gameBoard.getPlayers().get(0).addFaith(7); //1>15
        gameBoard.getPlayers().get(1).addFaith(6); //2>12
        gameBoard.getPlayers().get(2).addFaith(4); //3>8

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(!gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

        gameBoard.getPlayers().get(0).addFaith(1); //1>16

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

        gameBoard.getPlayers().get(2).addFaith(9); //3>17

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

        assertEquals(14, gameBoard.getPlayers().get(0).getTotalPoints());
        assertEquals(11, gameBoard.getPlayers().get(1).getTotalPoints());
        assertEquals(9, gameBoard.getPlayers().get(2).getTotalPoints());

    }

    /**
     * tests the correct behavior of the addFaith method in case a player passes more than one section
     */
    @Test
    @DisplayName("Multiple activation")
    void addFaithMultipleActivation() throws PlayerWonException {

        gameBoard.getPlayers().get(0).addFaith(7); //1>7
        gameBoard.getPlayers().get(1).addFaith(6); //2>6
        gameBoard.getPlayers().get(2).addFaith(4); //3>4

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        gameBoard.getPlayers().get(0).addFaith(9); //1>16

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

        gameBoard.getPlayers().get(2).addFaith(13); //3>17

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
        assertTrue(!gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

        assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(2).getStatus());
        assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(2).getStatus());

    }

    /**
     * tests the game ends correctly, throwing a PlayerWon exception
     * @throws PlayerWonException
     */
    @Test
    @DisplayName("Instant end")
    void addFaithEndGame() throws PlayerWonException {

        try {
            gameBoard.getPlayers().get(0).addFaith(24);
        }catch(PlayerWonException e){

            assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
            assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
            assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

            assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
            assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
            assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());

            assertTrue(!gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).isDiscarded() && gameBoard.getPlayers().get(0).getFaithTrack().getSection(1).getStatus());
            assertTrue(gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(1).getFaithTrack().getSection(1).getStatus());
            assertTrue(gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).isDiscarded() && !gameBoard.getPlayers().get(2).getFaithTrack().getSection(1).getStatus());


            System.out.println(e.getMessage());
        }




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

    /**
     * tests the correct initialization of the board
     */
    @Test
    void getNickname() {

        assertEquals("firstPlayer", gameBoard.getPlayers().get(0).getNickname());

    }

    /**
     * tests the correct change of nickname
     */
    @Test
    void setNickname() {

        gameBoard.getPlayers().get(0).setNickname("newNickname");
        assertEquals("newNickname", gameBoard.getPlayers().get(0).getNickname());

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent slot
     */
    @Test
    void getDevelopmentCardBoundsException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {gameBoard.getPlayers().get(0).getDevelopmentCard(4);});
        assertEquals(exception.getMessage(), "This slot doesn't exist");

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent Leader card
     */
    @Test
    void getLeaderCardException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {gameBoard.getPlayers().get(0).getLeaderCard(4);});
        assertEquals(exception.getMessage(), "Nonexistent Leader card");

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent Leader card
     */
    @Test
    void removeLeaderCardException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {gameBoard.getPlayers().get(0).removeLeaderCard(4);});
        assertEquals(exception.getMessage(), "Nonexistent Leader card");

    }

    /**
     * tests the PlayerWon exception is thrown when the game is over
     */
    @Test
    void playerWonException(){

        Exception exception;
        exception = assertThrows(PlayerWonException.class, () -> {gameBoard.getPlayers().get(0).addFaith(24);});
        assertEquals(exception.getMessage(), "A player won. The game is over");

    }

}