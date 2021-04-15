package it.polimi.ingsw;

import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Boards.FaithTrack.VaticanReportSection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {

    private FaithTrack faithTrack;

    @BeforeEach
    void setUp(){

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

        faithTrack = new FaithTrack(trackPoints, sections);

    }

    @AfterEach
    void tearDown(){
        faithTrack = null;
    }

    /**
     * tests the correct initialization of the faith track position
     */
    @Test
    @DisplayName("Get position")
    void getPosition() {

        assertEquals(0, faithTrack.getPosition());

    }

    /**
     * tests the correct initialization of the sections
     */
    @Test
    @DisplayName("Get sections test")
    void getSections(){

        for(VaticanReportSection section : faithTrack.getSections()){

            assertSame(section, faithTrack.getSection(faithTrack.getSections().indexOf(section) + 1));

        }

    }

    /**
     * tests the exception is thrown in case of nonexistent section
     */
    @Test
    @DisplayName("Activate Favor exception")
    void activateFavorException(){

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {faithTrack.activateFavor(5);});
        assertEquals(exception.getMessage(), "Nonexistent Vatican Report Section");

    }

    /**
     * tests the exception is thrown in case of nonexistent section
     */
    @Test
    @DisplayName("Discard Favor exception")
    void discardFavorException(){

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {faithTrack.discardFavor(5);});
        assertEquals(exception.getMessage(), "Nonexistent Vatican Report Section");

    }

    /**
     * tests the exception is thrown in case of nonexistent section
     */
    @Test
    @DisplayName("Is inside section exception")
    void isInsideSectionException(){

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {faithTrack.isInsideSection(5);});
        assertEquals(exception.getMessage(), "Nonexistent Vatican Report Section");

    }

    /**
     * tests the exception is thrown in case of nonexistent section
     */
    @Test
    @DisplayName("Is before section exception")
    void isBeforeSectionException(){

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {faithTrack.isBeforeSection(5);});
        assertEquals(exception.getMessage(), "Nonexistent Vatican Report Section");

    }

    /**
     * tests the exception is thrown in case of nonexistent section
     */
    @Test
    @DisplayName("Is end section exception")
    void isEndSectionException(){

        Exception exception;
        exception = assertThrows(IndexOutOfBoundsException.class, () -> {faithTrack.isEndSection(5);});
        assertEquals(exception.getMessage(), "Nonexistent Vatican Report Section");

    }

    /**
     * tests the correct initialization of the faith track sections
     */
    @Test
    @DisplayName("Get section number")
    void getSectionNumber() {

        assertEquals(0, faithTrack.getSectionNumber());

    }

    /**
     * tests the correct behavior of the calculatePoints method when all the pope's favor tile is activated
     */
    @Test
    void calculatePointsActivated(){

        faithTrack.addFaith(6); //6
        faithTrack.activateFavor(1);

        assertEquals(4, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //7

        assertEquals(4, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //9

        assertEquals(6, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //10

        assertEquals(6, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //12
        faithTrack.activateFavor(2);

        assertEquals(11, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //13

        assertEquals(11, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //15

        assertEquals(14, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //16

        assertEquals(14, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //18

        assertEquals(17, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //19
        faithTrack.activateFavor(3);

        assertEquals(21, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //21

        assertEquals(25, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //22

        assertEquals(25, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //24
        assertEquals(29, faithTrack.calculatePoints());

    }


    /**
     * tests the correct behavior of the calculatePoints method applied to the slots containing some victory points and the following slot
     */
    @Test
    @DisplayName("Calculate points borders")
    void calculatePoints() {

        assertEquals(0, faithTrack.calculatePoints());

        faithTrack.addFaith(3); //3

        assertEquals(1, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //4

        assertEquals(1, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //6

        assertEquals(2, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //7

        assertEquals(2, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //9

        assertEquals(4, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //10

        assertEquals(4, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //12

        assertEquals(6, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //13

        assertEquals(6, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //15

        assertEquals(9, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //16

        assertEquals(9, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //18

        assertEquals(12, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //19

        assertEquals(12, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //21

        assertEquals(16, faithTrack.calculatePoints());

        faithTrack.addFaith(1); //22

        assertEquals(16, faithTrack.calculatePoints());

        faithTrack.addFaith(2); //24

        assertEquals(20, faithTrack.calculatePoints());

    }

    /**
     * tests the correct behavior of the addFaith method at the borders of each section
     */
    @Test
    @DisplayName("Add faith borders")
    void addFaith() {

        assertEquals(0, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSectionNumber());

        faithTrack.addFaith(4); //4

        assertEquals(4, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSectionNumber());

        faithTrack.addFaith(1); //5

        assertEquals(5, faithTrack.getPosition());
        assertEquals(1, faithTrack.getSectionNumber());
        assertSame(faithTrack.getSection(1), faithTrack.getSection(faithTrack.getSectionNumber()));

        faithTrack.addFaith(3); //8

        assertEquals(8, faithTrack.getPosition());
        assertEquals(1, faithTrack.getSectionNumber());
        assertSame(faithTrack.getSection(1), faithTrack.getSection(faithTrack.getSectionNumber()));


        faithTrack.addFaith(1); //9

        assertEquals(9, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSectionNumber());


        faithTrack.addFaith(2); //11

        assertEquals(11, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSectionNumber());


        faithTrack.addFaith(1); //12

        assertEquals(12, faithTrack.getPosition());
        assertEquals(2, faithTrack.getSectionNumber());
        assertSame(faithTrack.getSection(2), faithTrack.getSection(faithTrack.getSectionNumber()));


        faithTrack.addFaith(4); //16

        assertEquals(16, faithTrack.getPosition());
        assertEquals(2, faithTrack.getSectionNumber());
        assertSame(faithTrack.getSection(2), faithTrack.getSection(faithTrack.getSectionNumber()));


        faithTrack.addFaith(1); //17

        assertEquals(17, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSectionNumber());


        faithTrack.addFaith(1); //18

        assertEquals(18, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSectionNumber());


        faithTrack.addFaith(1); //19

        assertEquals(19, faithTrack.getPosition());
        assertEquals(3, faithTrack.getSectionNumber());
        assertSame(faithTrack.getSection(3), faithTrack.getSection(faithTrack.getSectionNumber()));


        faithTrack.addFaith(5); //24

        assertEquals(24, faithTrack.getPosition());
        assertEquals(3, faithTrack.getSectionNumber());
        assertSame(faithTrack.getSection(3), faithTrack.getSection(faithTrack.getSectionNumber()));

    }

    /**
     * tests the correct behavior of the isInsideSection method at the borders of each section
     */
    @Test
    @DisplayName("Sections borders")
    void isInsideSection() {

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(4); //4

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(1); //5

        assertTrue(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(3); //8

        assertTrue(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(1); //9

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(2); //11

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(1); //12

        assertFalse(faithTrack.isInsideSection(1));
        assertTrue(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(4); //16

        assertFalse(faithTrack.isInsideSection(1));
        assertTrue(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(1); //17

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(1); //18

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertFalse(faithTrack.isInsideSection(3));

        faithTrack.addFaith(1); //19

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertTrue(faithTrack.isInsideSection(3));

        faithTrack.addFaith(5); //24

        assertFalse(faithTrack.isInsideSection(1));
        assertFalse(faithTrack.isInsideSection(2));
        assertTrue(faithTrack.isInsideSection(3));

    }

    /**
     * tests the correct behavior of the isBeforeSection method at the borders of each section
     */
    @Test
    @DisplayName("Before sections borders")
    void isBeforeSection() {

        assertTrue(faithTrack.isBeforeSection(1));
        assertTrue(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(4); //4

        assertTrue(faithTrack.isBeforeSection(1));
        assertTrue(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(1); //5

        assertFalse(faithTrack.isBeforeSection(1));
        assertTrue(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(3); //8

        assertFalse(faithTrack.isBeforeSection(1));
        assertTrue(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(1); //9

        assertFalse(faithTrack.isBeforeSection(1));
        assertTrue(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(2); //11

        assertFalse(faithTrack.isBeforeSection(1));
        assertTrue(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(1); //12

        assertFalse(faithTrack.isBeforeSection(1));
        assertFalse(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(4); //16

        assertFalse(faithTrack.isBeforeSection(1));
        assertFalse(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(1); //17

        assertFalse(faithTrack.isBeforeSection(1));
        assertFalse(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(1); //18

        assertFalse(faithTrack.isBeforeSection(1));
        assertFalse(faithTrack.isBeforeSection(2));
        assertTrue(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(1); //19

        assertFalse(faithTrack.isBeforeSection(1));
        assertFalse(faithTrack.isBeforeSection(2));
        assertFalse(faithTrack.isBeforeSection(3));

        faithTrack.addFaith(5); //24

        assertFalse(faithTrack.isBeforeSection(1));
        assertFalse(faithTrack.isBeforeSection(2));
        assertFalse(faithTrack.isBeforeSection(3));

    }

    /**
     * tests the correct behavior of the isEndSection method at the borders of each section
     */
    @Test
    @DisplayName("End sections borders")
    void isEndSection() {

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(7); //7

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(1); //8

        assertTrue(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(1); //9

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(6); //15

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(1); //16

        assertFalse(faithTrack.isEndSection(1));
        assertTrue(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(1); //17

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(6); //23

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertFalse(faithTrack.isEndSection(3));

        faithTrack.addFaith(1); //24

        assertFalse(faithTrack.isEndSection(1));
        assertFalse(faithTrack.isEndSection(2));
        assertTrue(faithTrack.isEndSection(3));

    }

    /**
     * tests the correct behavior of the isEndTrack method
     */
    @Test
    @DisplayName("End track test")
    void isEndTrack() {

        assertFalse(faithTrack.isEndTrack());

        faithTrack.addFaith(24);

        assertTrue(faithTrack.isEndTrack());

        faithTrack.addFaith(2);

        assertTrue(faithTrack.isEndTrack());

    }


}