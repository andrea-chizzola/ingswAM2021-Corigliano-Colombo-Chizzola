package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    public void emptyTestGetResource1(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(1);});
        assertEquals(exception.getMessage(), "Empty shelf!");
    }

    @Test
    public void emptyTestGetResource2(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(0);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetResource3(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(-6);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetResource4(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(4);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetResource5(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(3);});
        assertEquals(exception.getMessage(), "Empty shelf!");
    }

    @Test
    public void emptyTestGetQuantity1(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getQuantity(1);});
        assertEquals(exception.getMessage(), "Empty shelf!");
    }
    @Test
    public void emptyTestGetQuantity2(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getQuantity(3);});
        assertEquals(exception.getMessage(), "Empty shelf!");
    }

    @Test
    public void emptyTestGetQuantity3(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getQuantity(0);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetQuantity4(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getQuantity(4);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void TestGetCapacity1(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
       try {
           i = warehouse.getCapacity(1);
       }
       catch (IllegalShelfException e){
           System.out.println(e.getMessage());

       }
       assertTrue(i == 1);
    }
    @Test
    public void testGetCapacity2(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        try {
            i = warehouse.getCapacity(2);
        }
        catch (IllegalShelfException e){
            System.out.println(e.getMessage());
        }
        assertTrue(i == 2);
    }

    @Test
    public void testGetCapacity3(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        try {
            i = warehouse.getCapacity(3);
        }
        catch (IllegalShelfException e){
            System.out.println(e.getMessage());

        }
        assertTrue(i == 3);
    }
    @Test
    public void emptyTestGetCapacity1(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getCapacity(0);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }
    @Test
    public void emptyTestGetCapacity2(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getCapacity(4);});
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void testAddResource(){
        Warehouse warehouse = new Warehouse();
        int i=0;
        Resource resource = null;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getQuantity(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource = warehouse.getResource(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertTrue(i==1);
        assertEquals(resource, new Coin());
    }

    @Test
    public void test2AddShelf1(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.addResource(1,new Coin());});
        assertEquals(exception.getMessage(),"Full shelf!");
    }

    @Test
    public void testAddDifferentResource(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.addResource(1,new Stone());});
        assertEquals(exception.getMessage(),"Wrong resource!");
    }

    @Test
    public void testAddSameResourceTwoShelves(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.addResource(2,new Coin());});
        assertEquals(exception.getMessage(),"Already exists a shelf with this resource!");
    }

    @Test
    public void testAddIllegalShelf(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.addResource(0,new Coin());});
        assertEquals(exception.getMessage(),"This shelf does not exist!");
    }

    @Test
    public void testSubtractIllegalShelf(){
        Warehouse warehouse = new Warehouse();
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.subtract(0);});
        assertEquals(exception.getMessage(),"This shelf does not exist!");
    }

    @Test
    public void testSubctract1to0(){
        Warehouse warehouse = new Warehouse();
        int i = 1;
        Resource resource = null;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.subtract(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getQuantity(0);});
        Exception exception1;
        exception1 = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(0);});
    }

    @Test
    public void testSubctract2to1(){
        Warehouse warehouse = new Warehouse();

        int i = 0;
        Resource resource = null;
        try {
            warehouse.addResource(2, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(2, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.subtract(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getQuantity(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertTrue(i==1);
        assertEquals(resource, new Coin());
    }

    @Test
    public void testSwap(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource = null;
        Resource resource1 = null;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(2, new Stone());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.swap(1,2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource1 = warehouse.getResource(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Stone());
        assertEquals(resource, new Coin());
    }

    @Test
    public void testSwapWithInvertedParameters(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource = null;
        Resource resource1 = null;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(2, new Stone());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.swap(2,1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource1 = warehouse.getResource(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Stone());
        assertEquals(resource, new Coin());
    }

    @Test
    public void testSwapWithEmpty(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource = null;
        Resource resource1 = null;

        try {
            warehouse.addResource(2, new Stone());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.swap(1,2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource1 = warehouse.getResource(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getQuantity(1);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Stone());
        assertEquals(i,1 );
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.getQuantity(2);});
        Exception exception1;
        exception1 = assertThrows(IllegalShelfException.class, () -> {warehouse.getResource(2);});
    }

    @Test
    public void testSwapNoExistingShelves(){
        Warehouse warehouse = new Warehouse();

        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> {warehouse.swap(2,5);});
        assertEquals(exception.getMessage(),"Illegal swap!");
    }

    @Test
    public void testSwapBothEmpty(){
        Warehouse warehouse = new Warehouse();
        try{
            warehouse.swap(1,3);
            System.out.println("Swap ok!");
        }
        catch (IllegalShelfException e){System.out.println(e.getMessage());}
    }

    @Test
    public void testSwapCapacityIllegal(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource1 = null;

        try {
            warehouse.addResource(2, new Stone());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(2, new Stone());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource1 = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getQuantity(2);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Stone());
        assertEquals(i,2 );
    }

    @Test
    public void testAddShelf(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource1 = null;


            warehouse.addShelf(new ResQuantity(new Coin(), 2));

        try {
            warehouse.addResource(4, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(4, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource1 = warehouse.getResource(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getQuantity(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Coin());
        assertEquals(i,2 );
    }

    @Test
    public void testAdd2Shelves(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource1 = null;

        warehouse.addShelf(new ResQuantity(new Coin(), 2));
        warehouse.addShelf(new ResQuantity(new Stone(),5));
        try {
            resource1 = warehouse.getResource(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getCapacity(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Coin());
        assertEquals(i,2 );

        try {
            resource1 = warehouse.getResource(5);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getCapacity(5);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertEquals(resource1, new Stone());
        assertEquals(i,5 );
    }

    @Test
    public void testAddShelfZeroCapacity(){
        Warehouse warehouse = new Warehouse();
        int i = 0;
        Resource resource1 = null;


        warehouse.addShelf(new ResQuantity(new Coin(), 0));
        Exception exception1;
        exception1 = assertThrows(IllegalShelfException.class, () -> {warehouse.addResource(4, new Coin());});
        assertEquals("Full shelf!", exception1.getMessage());

    }

    @Test
    public void testExtraShelfSubctract2to1(){
        Warehouse warehouse = new Warehouse();

        int i = 0;
        Resource resource = null;
        warehouse.addShelf(new ResQuantity(new Coin(),2));
        try {
            warehouse.addResource(4, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.addResource(4, new Coin());
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            warehouse.subtract(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            i = warehouse.getQuantity(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        try {
            resource = warehouse.getResource(4);
        }
        catch(IllegalShelfException e){System.out.println(e.getMessage());}
        assertTrue(i==1);
        assertEquals(resource, new Coin());
    }

}