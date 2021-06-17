package it.polimi.ingsw;

import it.polimi.ingsw.Model.Boards.FaithTrack.PopeFavor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopeFavorTest {

    private PopeFavor popeFavor2;
    private PopeFavor popeFavor3;
    private PopeFavor popeFavor4;

    /**
     * initializes the attributes before each test
     */
    @BeforeEach
    void setUp(){

        popeFavor2 = new PopeFavor(2);
        popeFavor3 = new PopeFavor(3);
        popeFavor4 = new PopeFavor(4);

    }

    /**
     * resets the attributes after each test
     */
    @AfterEach
    void tearDown(){

        popeFavor2 = null;
        popeFavor3 = null;
        popeFavor4 = null;

    }

    /**
     * tests the correct behavior of the constructor
     */
    @Test
    @DisplayName("Constructor test")
    void getVictoryPointsAndStatus() {

        assertEquals(2, popeFavor2.getVictoryPoints());
        assertEquals(3, popeFavor3.getVictoryPoints());
        assertEquals(4, popeFavor4.getVictoryPoints());

        assertFalse(popeFavor2.isActive());
        assertFalse(popeFavor3.isActive());
        assertFalse(popeFavor4.isActive());

    }

    /**
     * tests the correct behavior of the setter, even after consecutive calls
     */
    @Test
    @DisplayName("Set status")
    void setStatus() {

        popeFavor2.setStatus(true);
        popeFavor2.setStatus(false);

        popeFavor3.setStatus(true);
        popeFavor3.setStatus(false);
        popeFavor3.setStatus(true);

        popeFavor4.setStatus(false);

        assertFalse(popeFavor2.isActive());
        assertTrue(popeFavor3.isActive());
        assertFalse(popeFavor4.isActive());

    }

    @Test
    void favorTest(){

        assertEquals(popeFavor2.hashCode(), new PopeFavor(2).hashCode());

    }

}