package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Boards.Slot;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class SlotTest {

    private Slot slot;

    @BeforeEach
    public void setUp(){
        slot = new Slot();
    }

    @Test
    public void testAddLevel1WithEmptySlot(){
        DevelopmentCard dev = new DevelopmentCard(3,null,null, null, 1);
        try {
            slot.insertCard(dev);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel1WithLevel1In(){
        DevelopmentCard dev = new DevelopmentCard(3,null,null, null, 1);
        try {
            slot.insertCard(dev);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> {slot.insertCard(dev);});
        assertEquals("This card can't be inserted!", exception1.getMessage());
        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel2WithLevel1In(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev2);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}

        assertEquals(2,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel2WithEmpty(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> {slot.insertCard(dev2);});
        assertEquals("This card can't be inserted!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel3WithLevel2In(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(3,null,null, null, 3);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev2);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev3);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        assertEquals(3,slot.getNumberOfCards());
    }

    @Test
    public void testAddLevel3WithLevel1In(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(3,null,null, null, 3);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> {slot.insertCard(dev3);});
        assertEquals("This card can't be inserted!", exception1.getMessage());

        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testAddIllegalLevel(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, -1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> {slot.insertCard(dev1);});
        assertEquals("This card can't be inserted!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testGetTopEmpty(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, -1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> {slot.getTop();});
        assertEquals("Empty slot!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testGetTop(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(3,null,null, null, 3);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev2);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev3);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            dev1 = slot.getTop();
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}

        assertEquals(3,slot.getNumberOfCards());
        assertEquals(dev1.getCardLevel(),3);
    }

    @Test
    public void testCountPoints(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(1,null,null, null, 3);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev2);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev3);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}


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
        DevelopmentCard dev1 = null;
        Exception exception1;
        exception1 = assertThrows(IllegalSlotException.class, () -> {slot.getCard(0);});
        assertEquals("This slot does not exist!", exception1.getMessage());

        assertEquals(0,slot.getNumberOfCards());
    }

    @Test
    public void testGet0(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(1,null,null, null, 3);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}

        try {
           dev2 = slot.getCard(0);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}


        assertEquals(1,slot.getNumberOfCards());
    }

    @Test
    public void testGetCard2(){
        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(3,null,null, null, 3);
        try {
            slot.insertCard(dev1);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev2);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            slot.insertCard(dev3);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}
        try {
            dev1 = slot.getCard(2);
        }
        catch (IllegalSlotException e){System.out.println(e.getMessage());}

        assertEquals(3,slot.getNumberOfCards());
    }

    @Test
    public void getCardsTest(){

        DevelopmentCard dev1 = new DevelopmentCard(3,null,null, null, 1);
        DevelopmentCard dev2 = new DevelopmentCard(2,null,null,null,2);
        DevelopmentCard dev3 = new DevelopmentCard(3,null,null, null, 3);
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