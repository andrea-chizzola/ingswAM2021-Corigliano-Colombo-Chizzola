package it.polimi.ingsw;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ModificationsTest {
    private Board board;

    @BeforeEach
    public void setUp(){

        ArrayList<String> names = new ArrayList<>();
        names.add("firstPlayer");

        ArrayList<VaticanReportSection> section1 = new ArrayList<>();
        section1.add(0, new VaticanReportSection(5, 8, 2));
        section1.add(1, new VaticanReportSection(12, 16, 3));
        section1.add(2, new VaticanReportSection(19, 24, 4));

        ArrayList<ArrayList<VaticanReportSection>> allSections = new ArrayList<>();
        allSections.add(section1);

        GameBoard gameBoard = new GameBoard(names, new ArrayList<>(), allSections);

        board = gameBoard.getPlayers().get(0);
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
