package it.polimi.ingsw;
import java.util.ArrayList;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ModificationsTest {
    private Board board;

    @BeforeEach
    public void setUp(){
        board = new Board("test",
                new GameBoard(),
                new ArrayList<>(),
                new ArrayList<>()
                );
    }

    @Test
    public void discountTest(){
        Discount effect = new Discount(new ResQuantity(new Coin(), 2));
        effect.applyEffect(board);

        assertEquals(board.getModifications().getDiscount(new Coin()), 2);
    }
    @Test
    public void whiteMarbleTest(){
        WhiteMarble effect = new WhiteMarble(new Coin());
        effect.applyEffect(board);

        assertTrue(board.getModifications().marbleTo(new Coin()));
    }
    @Test
    public void extraSlotTest(){
        ExtraSlot effect = new ExtraSlot(new ResQuantity(new Coin(), 2));
        effect.applyEffect(board);

    try{
            assertEquals(board.getWarehouse().getCapacity(4), 2);
            assertEquals(board.getWarehouse().getResource(4),new Coin());
    }
    catch(IllegalShelfException e){
        assertTrue(false);
        }
    }

}
