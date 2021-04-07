package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

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

        ArrayList<VaticanReportSection> sections = new ArrayList<VaticanReportSection>(3);

        sections.add(0, new VaticanReportSection(5, 8, 2));
        sections.add(1, new VaticanReportSection(12, 16, 3));
        sections.add(2, new VaticanReportSection(19, 24, 4));

        GameBoard gameBoard = new GameBoard();

        board = new Board("firstPlayer", gameBoard, trackPoints, sections);

    }

    @AfterEach
    void tearDown() {

        board = null;

    }

    /**
     * tests the correct initialization of the board
     */
    @Test
    void getNickname() {

        assertEquals("firstPlayer", board.getNickname());

    }

    /**
     * tests the correct change of nickname
     */
    @Test
    void setNickname() {

        board.setNickname("newNickname");
        assertEquals("newNickname", board.getNickname());

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent slot
     */
    @Test
    void getDevelopmentCardBoundsException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {board.getDevelopmentCard(4);});
        assertEquals(exception.getMessage(), "This slot doesn't exist");

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent Leader card
     */
    @Test
    void getLeaderCardException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {board.getLeaderCard(4);});
        assertEquals(exception.getMessage(), "Nonexistent Leader card");

    }

    /**
     * tests the IndexOutOfBounds exception is thrown in case of nonexistent Leader card
     */
    @Test
    void removeLeaderCardException() {

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {board.removeLeaderCard(4);});
        assertEquals(exception.getMessage(), "Nonexistent Leader card");

    }

}