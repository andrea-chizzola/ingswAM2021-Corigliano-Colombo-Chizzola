package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VaticanReportSectionTest {

    private VaticanReportSection vaticanReportSection2;
    private VaticanReportSection vaticanReportSection3;
    private VaticanReportSection vaticanReportSection4;

    /**
     * initializes the attributes before each test
     */
    @BeforeEach
    void setUp(){

        vaticanReportSection2 = new VaticanReportSection(5, 8, 2);
        vaticanReportSection3 = new VaticanReportSection(12, 16, 3);
        vaticanReportSection4 = new VaticanReportSection(19, 24, 4);

    }

    /**
     * resets the attributes after each test
     */
    @AfterEach
    void tearDown(){

        vaticanReportSection2 = null;
        vaticanReportSection3 = null;
        vaticanReportSection4 = null;

    }

    /**
     * tests the correct initialization of the vatican report sections
     */
    @Test
    @DisplayName("Start initialization")
    void getStart() {

        assertEquals(5, vaticanReportSection2.getStart());
        assertEquals(12, vaticanReportSection3.getStart());
        assertEquals(19, vaticanReportSection4.getStart());

    }

    /**
     * tests the correct initialization of the vatican report sections
     */
    @Test
    @DisplayName("End initialization")
    void getEnd() {

        assertEquals(8, vaticanReportSection2.getEnd());
        assertEquals(16, vaticanReportSection3.getEnd());
        assertEquals(24, vaticanReportSection4.getEnd());

    }

    /**
     * tests the correct initialization of the attribute discarded
     */
    @Test
    @DisplayName("Discarded initialization")
    void isDiscarded(){

        assertFalse(vaticanReportSection2.isDiscarded());
        assertFalse(vaticanReportSection3.isDiscarded());
        assertFalse(vaticanReportSection4.isDiscarded());

    }

    /**
     * tests the correct behavior of the discard method
     */
    @Test
    @DisplayName("Discarded test")
    void discard() {

        vaticanReportSection3.discard();
        vaticanReportSection4.discard();
        vaticanReportSection4.activateFavor();

        assertFalse(vaticanReportSection2.isDiscarded());
        assertTrue(vaticanReportSection3.isDiscarded());
        assertTrue(vaticanReportSection4.isDiscarded());

    }

    /**
     * tests the correct activation/inactivation of the pope's favor tile
     */
    @Test
    @DisplayName("Activate favor test")
    void activateFavor() {

        vaticanReportSection2.activateFavor();
        vaticanReportSection3.discard();
        vaticanReportSection3.activateFavor();
        vaticanReportSection4.activateFavor();
        vaticanReportSection4.discard();

        assertFalse(vaticanReportSection2.isDiscarded());
        assertTrue(vaticanReportSection3.isDiscarded());
        assertTrue(vaticanReportSection4.isDiscarded());

    }

    /**
     * tests the correct update of pope's favor tile's status
     */
    @Test
    @DisplayName("Pope's favor tiles status test")
    void getStatus() {

        vaticanReportSection2.activateFavor();
        vaticanReportSection3.discard();
        vaticanReportSection3.activateFavor();
        vaticanReportSection4.activateFavor();
        vaticanReportSection4.discard();

        assertTrue(vaticanReportSection2.getStatus());
        assertFalse(vaticanReportSection3.getStatus());
        assertFalse(vaticanReportSection4.getStatus());

    }

    /**
     * tests the correct inhibition of the victory points in case the pope's favor tile is discarded or inactive
     */
    @Test
    @DisplayName("Victory points test")
    void getPopeFavorVictoryPoints() {

        assertEquals(0, vaticanReportSection2.getPopeFavorVictoryPoints());

        vaticanReportSection2.activateFavor();
        vaticanReportSection3.discard();
        vaticanReportSection3.activateFavor();
        vaticanReportSection4.activateFavor();
        vaticanReportSection4.discard();

        assertEquals(2, vaticanReportSection2.getPopeFavorVictoryPoints());
        assertEquals(0, vaticanReportSection3.getPopeFavorVictoryPoints());
        assertEquals(0, vaticanReportSection4.getPopeFavorVictoryPoints());

    }
}