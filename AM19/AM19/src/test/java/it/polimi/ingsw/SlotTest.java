package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Boards.Slot;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Cards.Colors.Yellow;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;

class SlotTest {

    private Slot slot;
    private final SpecialEffect specialEffect = new Production(new LinkedList<>(),new LinkedList<>(), 0, 0);
    private final Requirements requirements = new ResourceReqDev(new LinkedList<>());
    private final CardColor color = new Yellow();

    @BeforeEach
    public void setUp(){
        slot = new Slot();
    }

    @Test
    public void testAddLevel1WithEmptySlot(){
        DevelopmentCard dev = new DevelopmentCard(3,specialEffect,requirements, color, 1, "1", "test1");
        try {
            slot.insertCard(dev);
        }
        catch (IllegalSlotException e){fail();}
        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel1WithLevel1In(){
        DevelopmentCard dev = new DevelopmentCard(3,specialEffect,requirements, color, 1, "2", "test2");
        try {
            slot.insertCard(dev);
        }
        catch (IllegalSlotException e){fail();}
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> slot.insertCard(dev));
        assertEquals("This card can't be inserted!", exception1.getMessage());
        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel2WithLevel1In(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "3", "test3");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "4", "test4");
        try {
            slot.insertCard(dev1);

            slot.insertCard(dev2);
        }
        catch (IllegalSlotException e){fail();}

        assertEquals(2,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel2WithEmpty(){

        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "5", "test5");
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> slot.insertCard(dev2));
        assertEquals("This card can't be inserted!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel3WithLevel2In(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1,"6", "test6");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "7", "test7");
        DevelopmentCard dev3 = new DevelopmentCard(3,specialEffect,requirements, color, 3, "8", "test8");
        try {
            slot.insertCard(dev1);

            slot.insertCard(dev2);

            slot.insertCard(dev3);
        }
        catch (IllegalSlotException e){fail();}
        assertEquals(3,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel3WithLevel1In(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "9", "test9");

        DevelopmentCard dev3 = new DevelopmentCard(3,specialEffect,requirements, color, 3, "10", "test10");
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){fail();}
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> slot.insertCard(dev3));
        assertEquals("This card can't be inserted!", exception1.getMessage());

        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testAddIllegalLevel(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, -1, "11", "test11");

        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> slot.insertCard(dev1));
        assertEquals("This card can't be inserted!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testGetTopEmpty(){

        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> slot.getTop());
        assertEquals("Empty slot!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testGetTop(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "12", "test12");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "13", "test13");
        DevelopmentCard dev3 = new DevelopmentCard(3,specialEffect,requirements, color, 3, "14", "test14");
        try {
            slot.insertCard(dev1);

            slot.insertCard(dev2);

            slot.insertCard(dev3);

            dev1 = slot.getTop();
        }
        catch (IllegalSlotException e){fail();}

        assertEquals(3,slot.getNumberOfCards());
        assertEquals(dev1.getCardLevel(),3);
    }

    @Test
    public void testCountPoints(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "15", "test15");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "16", "test16");
        DevelopmentCard dev3 = new DevelopmentCard(1,specialEffect,requirements, color, 3, "17", "test17");
        try {
            slot.insertCard(dev1);

            slot.insertCard(dev2);

            slot.insertCard(dev3);
        }
        catch (IllegalSlotException e){fail();}


        assertEquals(3,slot.getNumberOfCards());
        assertEquals(slot.countPoints(),6);
    }

    @Test
    public void testCountPointsEmpty(){
        assertEquals(0,slot.getNumberOfCards());
        assertEquals(slot.countPoints(),0);
    }

    @Test
    public void testGetCardEmpty(){

        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> slot.getCard(0));
        assertEquals("This slot does not exist!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testGet0(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "18", "test18");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "19", "test19");

        try {
            slot.insertCard(dev1);

           dev2 = slot.getCard(0);
        }
        catch (IllegalSlotException e){fail();}


        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testGetCard2(){
        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "20", "test20");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "21", "test21");
        DevelopmentCard dev3 = new DevelopmentCard(3,specialEffect,requirements, color, 3, "22", "test22");
        try {
            slot.insertCard(dev1);

            slot.insertCard(dev2);

            slot.insertCard(dev3);

            dev1 = slot.getCard(2);
        }
        catch (IllegalSlotException e){fail();}

        assertEquals(3,slot.getNumberOfCards());
    }

    @Test
    public void getCardsTest(){

        DevelopmentCard dev1 = new DevelopmentCard(3,specialEffect,requirements, color, 1, "23", "test23");
        DevelopmentCard dev2 = new DevelopmentCard(2,specialEffect,requirements,color,2, "24", "test24");
        DevelopmentCard dev3 = new DevelopmentCard(3,specialEffect,requirements, color, 3, "25", "test25");
        ArrayList<DevelopmentCard> copy = new ArrayList<>();
        copy.add(dev1);
        copy.add(dev2);
        copy.add(dev3);

        try {
            slot.insertCard(dev1);
            slot.insertCard(dev2);
            slot.insertCard(dev3);
        }
        catch(IllegalSlotException e){
            fail();
        }
        assertEquals(slot.getCards(), copy);
    }

    @Test
    public void getCardsEmptySlotTest(){
        assertEquals(slot.getCards(), new ArrayList<DevelopmentCard>());
    }


}