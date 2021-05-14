package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    private Warehouse warehouse;
    private String configurationFile = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp(){
        warehouse = new Warehouse(configurationFile);
    }

    @Test
    public void emptyTestGetResource1(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getResource(1));
        assertEquals(exception.getMessage(), "Empty shelf!");
    }

    @Test
    public void emptyTestGetResource2(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getResource(0));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetResource3(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getResource(-6));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetResource4(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getResource(4));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetResource5(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getResource(3));
        assertEquals(exception.getMessage(), "Empty shelf!");
    }

    @Test
    public void emptyTestGetQuantity1(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getQuantity(1));
        assertEquals(exception.getMessage(), "Empty shelf!");
    }
    @Test
    public void emptyTestGetQuantity2(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getQuantity(3));
        assertEquals(exception.getMessage(), "Empty shelf!");
    }

    @Test
    public void emptyTestGetQuantity3(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getQuantity(0));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void emptyTestGetQuantity4(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getQuantity(4));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void TestGetCapacity1(){
        int i = 0;
       try {
           i = warehouse.getCapacity(1);
       }
       catch (IllegalShelfException e){
          fail();

       }
        assertEquals(i, 1);
    }
    @Test
    public void testGetCapacity2(){
        int i = 0;
        try {
            i = warehouse.getCapacity(2);
        }
        catch (IllegalShelfException e){
            fail();
        }
        assertEquals(i, 2);
    }

    @Test
    public void testGetCapacity3(){
        int i = 0;
        try {
            i = warehouse.getCapacity(3);
        }
        catch (IllegalShelfException e){
            fail();

        }
        assertEquals(i, 3);
    }
    @Test
    public void emptyTestGetCapacity1(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getCapacity(0));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }
    @Test
    public void emptyTestGetCapacity2(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.getCapacity(4));
        assertEquals(exception.getMessage(), "This shelf does not exist!");
    }

    @Test
    public void testAddResource(){
        int i=0;
        Resource resource = null;
        try {
            warehouse.addResource(1, new Coin());

            i = warehouse.getQuantity(1);

            resource = warehouse.getResource(1);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(i, 1);
        assertEquals(resource, new Coin());
    }

    @Test
    public void test2AddShelf1(){
        Exception exception;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){fail();}
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.addResource(1,new Coin()));
        assertEquals(exception.getMessage(),"Full shelf!");
    }

    @Test
    public void testAddDifferentResource(){
        Exception exception;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){fail();}
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.addResource(1,new Stone()));
        assertEquals(exception.getMessage(),"Wrong resource!");
    }

    @Test
    public void testAddSameResourceTwoShelves(){
        Exception exception;
        try {
            warehouse.addResource(1, new Coin());
        }
        catch(IllegalShelfException e){fail();}
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.addResource(2,new Coin()));
        assertEquals(exception.getMessage(),"Already exists a shelf with this resource!");
    }

    @Test
    public void testAddIllegalShelf(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.addResource(0,new Coin()));
        assertEquals(exception.getMessage(),"This shelf does not exist!");
    }

    @Test
    public void testSubtractIllegalShelf(){
        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.subtract(0));
        assertEquals(exception.getMessage(),"This shelf does not exist!");
    }

    @Test
    public void testSubctract1to0(){


        try {
            warehouse.addResource(1, new Coin());

            warehouse.subtract(1);
        }
        catch(IllegalShelfException e){fail();}
        assertThrows(IllegalShelfException.class, () -> warehouse.getQuantity(0));
        assertThrows(IllegalShelfException.class, () -> warehouse.getResource(0));
    }

    @Test
    public void testSubctractIllegalExtra(){


            warehouse.addShelf(new ResQuantity(new Coin(),2));

        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.subtract(4));
        assertEquals(exception.getMessage(),"Empty extra shelf!");



    }

    @Test
    public void testSubtract2to1(){

        int i = 0;
        Resource resource = null;
        try {
            warehouse.addResource(2, new Coin());

            warehouse.addResource(2, new Coin());

            warehouse.subtract(2);

            i = warehouse.getQuantity(2);

            resource = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(i, 1);
        assertEquals(resource, new Coin());
    }

    @Test
    public void testSwap(){

        Resource resource = null;
        Resource resource1 = null;
        try {
            warehouse.addResource(1, new Coin());

            warehouse.addResource(2, new Stone());

            warehouse.swap(1,2);

            resource1 = warehouse.getResource(1);

            resource = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Stone());
        assertEquals(resource, new Coin());
    }

    @Test
    public void testSwapSameShelf(){
        int i = 0;

        Resource resource1 = null;
        try {
            warehouse.addResource(1, new Coin());

            warehouse.addResource(2, new Stone());

            warehouse.swap(1,1);

            resource1 = warehouse.getResource(1);
            i = warehouse.getQuantity(1);
        }
        catch(IllegalShelfException e){fail();}

        assertEquals(resource1, new Coin());
        assertEquals(i,1);

    }

    @Test
    public void testSwapWithInvertedParameters(){

        Resource resource = null;
        Resource resource1 = null;
        try {
            warehouse.addResource(1, new Coin());

            warehouse.addResource(2, new Stone());

            warehouse.swap(2,1);

            resource1 = warehouse.getResource(1);

            resource = warehouse.getResource(2);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Stone());
        assertEquals(resource, new Coin());
    }

    @Test
    public void testSwapSourceQuantityTooBig(){
        int i1 = 0;
        int i2 = 0;


        Resource resource1 = null;
        Resource resource2 = null;

        try {
            warehouse.addResource(1, new Coin());

            warehouse.addResource(2, new Stone());
            warehouse.addResource(2,new Stone());
        }
        catch(IllegalShelfException e){fail();}

        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () ->  warehouse.swap(2,1));
        assertEquals(exception.getMessage(),"Illegal swap!");

        try {
            resource1 = warehouse.getResource(1);
            resource2 = warehouse.getResource(2);
            i1= warehouse.getQuantity(1);
            i2= warehouse.getQuantity(2);
        }catch (IllegalShelfException e){fail();}
        assertEquals(resource2, new Stone());
        assertEquals(resource1, new Coin());
        assertEquals(i1, 1);
        assertEquals(i2, 2);

    }

    @Test
    public void testSwapSourceQuantityTooBig2(){
        int i1 = 0;
        int i2 = 0;


        Resource resource1 = null;
        Resource resource2 = null;

        try {
            warehouse.addResource(1, new Coin());

            warehouse.addResource(2, new Stone());
            warehouse.addResource(2,new Stone());
        }
        catch(IllegalShelfException e){fail();}

        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () ->  warehouse.swap(1,2));
        assertEquals(exception.getMessage(),"Illegal swap!");

        try {
            resource1 = warehouse.getResource(1);
            resource2 = warehouse.getResource(2);
            i1= warehouse.getQuantity(1);
            i2= warehouse.getQuantity(2);
        }catch (IllegalShelfException e){fail();}
        assertEquals(resource2, new Stone());
        assertEquals(resource1, new Coin());
        assertEquals(i1, 1);
        assertEquals(i2, 2);

    }

    @Test
    public void testSwapWithEmpty(){
        int i = 0;

        Resource resource1 = null;

        try {
            warehouse.addResource(2, new Stone());

            warehouse.swap(1,2);

            resource1 = warehouse.getResource(1);

            i = warehouse.getQuantity(1);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Stone());
        assertEquals(i,1 );
        assertThrows(IllegalShelfException.class, () -> warehouse.getQuantity(2));
        assertThrows(IllegalShelfException.class, () -> warehouse.getResource(2));
    }

    @Test
    public void testSwapNoExistingShelves(){

        Exception exception;
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.swap(2,5));
        assertEquals(exception.getMessage(),"Illegal swap!");
    }

    @Test
    public void testSwapBothEmpty(){
        try{
            warehouse.swap(1,3);
        }
        catch (IllegalShelfException e){fail();}
    }

    @Test
    public void testSwapCapacityIllegal(){
        int i = 0;
        Resource resource1 = null;

        try {
            warehouse.addResource(2, new Stone());

            warehouse.addResource(2, new Stone());

            warehouse.addResource(1, new Coin());

            resource1 = warehouse.getResource(2);

            i = warehouse.getQuantity(2);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Stone());
        assertEquals(i,2 );
    }

    @Test
    public void testAddShelf(){
        int i = 0;
        Resource resource1 = null;


            warehouse.addShelf(new ResQuantity(new Coin(), 2));

        try {
            warehouse.addResource(4, new Coin());

            warehouse.addResource(4, new Coin());

            resource1 = warehouse.getResource(4);

            i = warehouse.getQuantity(4);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Coin());
        assertEquals(i,2 );
    }

    @Test
    public void testAdd2Shelves(){
        int i = 0;
        Resource resource1 = null;

        warehouse.addShelf(new ResQuantity(new Coin(), 2));
        warehouse.addShelf(new ResQuantity(new Stone(),5));
        try {
            resource1 = warehouse.getResource(4);

            i = warehouse.getCapacity(4);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Coin());
        assertEquals(i,2 );

        try {
            resource1 = warehouse.getResource(5);

            i = warehouse.getCapacity(5);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(resource1, new Stone());
        assertEquals(i,5 );
    }

    @Test
    public void testAddShelfZeroCapacity(){



        warehouse.addShelf(new ResQuantity(new Coin(), 0));
        Exception exception1;
        exception1 = assertThrows(IllegalShelfException.class, () -> warehouse.addResource(4, new Coin()));
        assertEquals("Full shelf!", exception1.getMessage());

    }

    @Test
    public void testExtraShelfSubtract2to1(){

        int i = 0;
        Resource resource = null;
        warehouse.addShelf(new ResQuantity(new Coin(),2));
        try {
            warehouse.addResource(4, new Coin());

            warehouse.addResource(4, new Coin());

            warehouse.subtract(4);

            i = warehouse.getQuantity(4);

            resource = warehouse.getResource(4);
        }
        catch(IllegalShelfException e){fail();}
        assertEquals(i, 1);
        assertEquals(resource, new Coin());
    }

    @Test
    void calculateTotalResources(){
        System.out.println(warehouse.calculateTotalResources());
    }

    @Test

    public void getAllResourcesTest(){
        try{
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Stone());
            warehouse.addResource(3, new Servant());
            warehouse.addResource(3, new Servant());
        }
        catch (IllegalShelfException e){
            fail();
        }

        Map<Resource, Integer> map = warehouse.getAllResources();
        Map<Resource, Integer> copy = new HashMap<>();
        copy.put(new Coin(), 1);
        copy.put(new Stone(), 1);
        copy.put(new Servant(), 2);

        for(Resource r : map.keySet()){
            assertEquals(map.get(r), copy.get(r));
        }

    }

    @Test
    public void testAddDifferentResourceExtra(){
        Exception exception;
        try {
            warehouse.addShelf(new ResQuantity(new Coin(),2));
            warehouse.addResource(4, new Coin());
        }
        catch(IllegalShelfException e){fail();}
        exception = assertThrows(IllegalShelfException.class, () -> warehouse.addResource(4,new Stone()));
        assertEquals(exception.getMessage(),"Wrong resource!");
    }

    @Test
    public void testCheckInsertMultipleResources(){
        try{
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Stone());
            warehouse.addResource(3, new Servant());

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(1);

        assertFalse(warehouse.checkInsertMultipleRes(resources,shelves));

    }

    @Test
    public void testCheckInsertMultipleResources1(){
        try{
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Stone());
            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Stone());
        shelves.add(2);
        resources.add(new Servant());
        shelves.add(3);
        resources.add(new Servant());
        shelves.add(3);


        assertTrue(warehouse.checkInsertMultipleRes(resources,shelves));

    }

    @Test
    public void testCheckInsertMultipleResources2(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(1);
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Stone());
        shelves.add(2);
        resources.add(new Servant());
        shelves.add(3);
        resources.add(new Servant());
        shelves.add(3);


        assertTrue(warehouse.checkInsertMultipleRes(resources,shelves));

    }

    @Test
    public void testCheckInsertMultipleResources3(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(1);
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Coin());
        shelves.add(2);

        resources.add(new Servant());
        shelves.add(3);
        resources.add(new Servant());
        shelves.add(3);


        assertFalse(warehouse.checkInsertMultipleRes(resources,shelves));

    }

    @Test
    public void testCheckInsertMultipleResources4(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(2);
        resources.add(new Coin());
        shelves.add(4);
        resources.add(new Servant());
        shelves.add(2);

        resources.add(new Servant());
        shelves.add(3);
        resources.add(new Servant());
        shelves.add(3);

        assertFalse(warehouse.checkInsertMultipleRes(resources,shelves));
    }

    @Test
    public void testCheckInsertMultipleResources5(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Servant());
        shelves.add(4);


        assertFalse(warehouse.checkInsertMultipleRes(resources,shelves));
    }

    @Test
    public void testCheckInsertMultipleResources6(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Servant());
        shelves.add(1);


        assertFalse(warehouse.checkInsertMultipleRes(resources,shelves));
    }

    @Test
    public void testCheckInsertMultipleResources7(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(1);
        resources.add(new Stone());
        shelves.add(2);
        resources.add(new Stone());
        shelves.add(2);

        assertTrue(warehouse.checkInsertMultipleRes(resources,shelves));
    }

    @Test
    public void testCheckInsertMultipleResources8(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();
        resources.add(new Coin());
        shelves.add(1);
        resources.add(new Stone());
        shelves.add(2);
        resources.add(new Stone());
        shelves.add(2);
        resources.add(new Faith());
        shelves.add(2);
        resources.add(new Faith());
        shelves.add(1);
        resources.add(new Faith());
        shelves.add(7);

        assertTrue(warehouse.checkInsertMultipleRes(resources,shelves));
    }

    @Test
    public void testCheckInsertMultipleResources9(){

        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();


        assertTrue(warehouse.checkInsertMultipleRes(resources,shelves));
    }

    @Test
    public void testCheckInsertMultipleResources10(){
        try{

            warehouse.addResource(3, new Servant());
            warehouse.addShelf(new ResQuantity(new Coin(), 3));

        }
        catch (IllegalShelfException e){
            fail();
        }
        List<Resource> resources = new ArrayList<>();
        List<Integer> shelves = new ArrayList<>();

        resources.add(new Servant());
        shelves.add(3);
        resources.add(new Servant());
        shelves.add(3);
        resources.add(new Servant());
        shelves.add(3);

        assertFalse(warehouse.checkInsertMultipleRes(resources,shelves));
    }
}