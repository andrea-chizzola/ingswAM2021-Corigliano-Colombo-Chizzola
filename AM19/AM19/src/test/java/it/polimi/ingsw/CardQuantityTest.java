package it.polimi.ingsw;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CardQuantityTest {
    private CardQuantity quantity;

    @BeforeEach
    public void setUp(){
        quantity = new CardQuantity(new Purple(), 3, 1);
    }

    @Test
    public void getCardColorTest(){
        assertEquals(quantity.getCardColor(), new Purple());
    }

    @Test
    public void getLevelTest(){
        assertEquals(quantity.getLevel(), 1);
    }

    @Test
    public void getQuantityTest(){
        assertEquals(quantity.getQuantity(), 3);
    }

}
