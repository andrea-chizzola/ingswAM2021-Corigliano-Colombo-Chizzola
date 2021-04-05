package it.polimi.ingsw;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StrongBoxTest {

    private StrongBox strongBox;
    private Resource coin;
    private Resource servant;
    private Resource shield;
    private Resource stone;

    /**
     * initializes the attributes before each test
     */
    @BeforeEach
    void setUp(){

        strongBox = new StrongBox();
        coin = new Coin();
        servant = new Servant();
        shield = new Shield();
        stone = new Stone();

    }

    /**
     * resets the attributes after each test
     */
    @AfterEach
    void tearDown(){

        strongBox = null;
        coin = null;
        servant = null;
        shield = null;
        stone = null;

    }

    /**
     * tests the correct behavior when quantity equals 0
     */
    @Test
    @DisplayName("Add zero")
    void addResourceZero(){

        strongBox.addResource(servant, 5);
        strongBox.addResource(servant, 0);
        strongBox.addResource(shield, 0);

        assertEquals(5, strongBox.getQuantity(servant));
        assertEquals(0, strongBox.getQuantity(shield));

    }

    /**
     * tests the correct behavior of two consecutive calls to addResource
     */
    @Test
    @DisplayName("Consecutive adds")
    void addResourceConsecutive(){

        strongBox.addResource(stone, 5);
        strongBox.addResource(stone, 9);

        assertSame(14, strongBox.getQuantity(stone));

    }

    /**
     * tests the correct behavior in case of an add followed by a take and then followed by a second add
     */
    @Test
    @DisplayName("Add after take")
    void addResourceAfterTake(){

        strongBox.addResource(shield,10);
        strongBox.takeResource(shield, 7);
        strongBox.addResource(shield, 2);

        assertEquals(5, strongBox.getQuantity(shield));

    }

    /**
     * tests the correct behavior when quantity equals 0
     */
    @Test
    @DisplayName("Taking zero resources")
    void takeZeroResource(){

        strongBox.addResource(shield,10);
        strongBox.takeResource(shield, 0);

        assertEquals(10, strongBox.getQuantity(shield));

    }

    /**
     * tests the correct behavior in case quantity equals the exact amount of resources in the strongbox
     */
    @Test
    @DisplayName("Takes the exact amount of resources in the strongbox")
    void takeExactResource(){

        strongBox.addResource(stone, 12);
        strongBox.takeResource(stone, 0);
        strongBox.takeResource(coin, 0);

        assertEquals(12, strongBox.getQuantity(stone));
        assertEquals(0, strongBox.getQuantity(coin));

    }

    /**
     * tests the correct behavior in case of consecutive takes
     */
    @Test
    @DisplayName("Consecutive takes")
    void takeConsecutiveResource(){

        strongBox.addResource(shield, 10);
        strongBox.addResource(coin, 6);
        strongBox.addResource(stone, 9);

        strongBox.takeResource(shield, 4);
        strongBox.takeResource(shield, 3);
        strongBox.takeResource(coin, 4);
        strongBox.takeResource(coin, 2);
        strongBox.takeResource(stone, 9);
        strongBox.takeResource(stone, 0);

        assertEquals(3, strongBox.getQuantity(shield));
        assertEquals(0, strongBox.getQuantity(coin));
        assertEquals(0, strongBox.getQuantity(stone));
    }

    /**
     * tests the correct initialization of th strongbox
     */
    @Test
    @DisplayName("Strongbox initialization")
    void getQuantity() {

        assertEquals(strongBox.getQuantity(coin), 0);
        assertEquals(strongBox.getQuantity(servant), 0);
        assertEquals(strongBox.getQuantity(shield), 0);
        assertEquals(strongBox.getQuantity(stone), 0);

    }

    @Test
    void calculateVictoryPoints(){

        assertEquals(0, strongBox.calculateVictoryPoints());

        strongBox.addResource(coin, 2);
        strongBox.addResource(servant, 2);

        assertEquals(0, strongBox.calculateVictoryPoints());

        strongBox.addResource(shield, 1);

        assertEquals(1, strongBox.calculateVictoryPoints());

        strongBox.takeResource(coin, 2);
        strongBox.takeResource(servant, 2);
        strongBox.takeResource(shield, 1);

        assertEquals(0, strongBox.calculateVictoryPoints());

        strongBox.addResource(stone, 10);

        assertEquals(2, strongBox.calculateVictoryPoints());

    }
}