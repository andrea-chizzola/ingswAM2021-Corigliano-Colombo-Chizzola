package it.polimi.ingsw;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

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
        assertEquals(5, strongBox.getResources().get(servant));
        assertEquals(0, strongBox.getQuantity(shield));
        assertEquals(0, strongBox.getResources().get(shield));

    }

    /**
     * tests the correct behavior of two consecutive calls to addResource
     */
    @Test
    @DisplayName("Consecutive adds")
    void addResourceConsecutive(){

        strongBox.addResource(stone, 5);
        strongBox.addResource(stone, 9);

        assertEquals(14, strongBox.getQuantity(stone));
        assertEquals(14, strongBox.getResources().get(stone));

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
        assertEquals(5, strongBox.getResources().get(shield));

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
        assertEquals(10, strongBox.getResources().get(shield));

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
        assertEquals(12, strongBox.getResources().get(stone));
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
        assertEquals(3, strongBox.getResources().get(shield));
        assertEquals(0, strongBox.getQuantity(coin));
        assertEquals(0, strongBox.getResources().get(coin));
        assertEquals(0, strongBox.getQuantity(stone));
        assertEquals(0, strongBox.getResources().get(stone));
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

    /**
     * tests the correct sum of the resources left in the strongbox
     */
    @Test
    void calculateTotalResources(){

        assertEquals(0, strongBox.calculateTotalResources());

        strongBox.addResource(coin, 2);
        strongBox.addResource(servant, 2);

        assertEquals(4, strongBox.calculateTotalResources());

        strongBox.addResource(shield, 1);

        assertEquals(5, strongBox.calculateTotalResources());

        strongBox.takeResource(coin, 2);
        strongBox.takeResource(servant, 2);
        strongBox.takeResource(shield, 1);

        assertEquals(0, strongBox.calculateTotalResources());

        strongBox.addResource(stone, 10);

        assertEquals(10, strongBox.calculateTotalResources());

    }

    @Test
    void getResourcesEmpty(){
        assertEquals(strongBox.getResources().size(), 0);
    }

    @Test
    void getResources(){
        strongBox.addResource(coin, 3);
        strongBox.addResource(coin, 1);
        strongBox.addResource(shield,0);
        strongBox.addResource(stone,5);
        Map<Resource,Integer> map = strongBox.getResources();

        Map<Resource, Integer> copy = new HashMap<>();
        copy.put(coin, 4);
        copy.put(shield,0);
        copy.put(stone,5);

        for(Resource r : map.keySet()){
            assertEquals(map.get(r), copy.get(r));
        }

    }
}