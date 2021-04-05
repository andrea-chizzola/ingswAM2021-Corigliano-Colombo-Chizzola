package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {

    private FaithTrack faithTrack;
    private FaithTrack faithTrack1;

    @BeforeEach
    void setUp(){
        faithTrack = new FaithTrack();
        faithTrack1 = new FaithTrack();
    }

    @AfterEach
    void tearDown(){
        faithTrack = null;
        faithTrack1 = null;
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
     * tests the correct initialization of the faith track sections
     */
    @Test
    @DisplayName("Get section")
    void getSection() {

        assertEquals(0, faithTrack.getSection());

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
        faithTrack1.addFaith(6); //6
        faithTrack1.activateFavor(1);

        assertEquals(2, faithTrack.calculatePoints());
        assertEquals(4, faithTrack1.calculatePoints());

        faithTrack.addFaith(1); //7
        faithTrack1.addFaith(1); //7

        assertEquals(2, faithTrack.calculatePoints());
        assertEquals(4, faithTrack1.calculatePoints());

        faithTrack.addFaith(2); //9
        faithTrack1.addFaith(2); //9

        assertEquals(4, faithTrack.calculatePoints());
        assertEquals(6, faithTrack1.calculatePoints());

        faithTrack.addFaith(1); //10
        faithTrack1.addFaith(1); //10

        assertEquals(4, faithTrack.calculatePoints());
        assertEquals(6, faithTrack1.calculatePoints());

        faithTrack.addFaith(2); //12
        faithTrack1.addFaith(2); //12
        faithTrack1.activateFavor(2);

        assertEquals(6, faithTrack.calculatePoints());
        assertEquals(11, faithTrack1.calculatePoints());

        faithTrack.addFaith(1); //13
        faithTrack1.addFaith(1); //13

        assertEquals(6, faithTrack.calculatePoints());
        assertEquals(11, faithTrack1.calculatePoints());

        faithTrack.addFaith(2); //15
        faithTrack1.addFaith(2); //15

        assertEquals(9, faithTrack.calculatePoints());
        assertEquals(14, faithTrack1.calculatePoints());

        faithTrack.addFaith(1); //16
        faithTrack1.addFaith(1); //16

        assertEquals(9, faithTrack.calculatePoints());
        assertEquals(14, faithTrack1.calculatePoints());

        faithTrack.addFaith(2); //18
        faithTrack1.addFaith(2); //18

        assertEquals(12, faithTrack.calculatePoints());
        assertEquals(17, faithTrack1.calculatePoints());

        faithTrack.addFaith(1); //19
        faithTrack1.addFaith(1); //19
        faithTrack1.activateFavor(3);

        assertEquals(12, faithTrack.calculatePoints());
        assertEquals(21, faithTrack1.calculatePoints());

        faithTrack.addFaith(2); //21
        faithTrack1.addFaith(2); //21

        assertEquals(16, faithTrack.calculatePoints());
        assertEquals(25, faithTrack1.calculatePoints());

        faithTrack.addFaith(1); //22
        faithTrack1.addFaith(1); //22

        assertEquals(16, faithTrack.calculatePoints());
        assertEquals(25, faithTrack1.calculatePoints());

        faithTrack.addFaith(2); //24
        faithTrack1.addFaith(2); //24

        assertEquals(20, faithTrack.calculatePoints());
        assertEquals(29, faithTrack1.calculatePoints());

    }

    /**
     * tests the correct behavior of the addFaith method at the borders of each section
     */
    @Test
    @DisplayName("Add faith borders")
    void addFaith() {

        assertEquals(0, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSection());

        faithTrack.addFaith(4); //4

        assertEquals(4, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSection());

        faithTrack.addFaith(1); //5

        assertEquals(5, faithTrack.getPosition());
        assertEquals(1, faithTrack.getSection());

        faithTrack.addFaith(3); //8

        assertEquals(8, faithTrack.getPosition());
        assertEquals(1, faithTrack.getSection());


        faithTrack.addFaith(1); //9

        assertEquals(9, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSection());


        faithTrack.addFaith(2); //11

        assertEquals(11, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSection());


        faithTrack.addFaith(1); //12

        assertEquals(12, faithTrack.getPosition());
        assertEquals(2, faithTrack.getSection());


        faithTrack.addFaith(4); //16

        assertEquals(16, faithTrack.getPosition());
        assertEquals(2, faithTrack.getSection());


        faithTrack.addFaith(1); //17

        assertEquals(17, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSection());


        faithTrack.addFaith(1); //18

        assertEquals(18, faithTrack.getPosition());
        assertEquals(0, faithTrack.getSection());


        faithTrack.addFaith(1); //19

        assertEquals(19, faithTrack.getPosition());
        assertEquals(3, faithTrack.getSection());


        faithTrack.addFaith(5); //24

        assertEquals(24, faithTrack.getPosition());
        assertEquals(3, faithTrack.getSection());

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