package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResQuantityTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp() {

        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

        //initialization of Warehouse and StrongBox
        //Warehouse: Quantity:Shelf:Resource
        // 1:1:coin, 1:2:servant, 2:3:stones, 1:4:shield, 0:5:coin
        Warehouse warehouse = board.getWarehouse();
        try{
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Servant());
            warehouse.addResource(3, new Stone());
            warehouse.addResource(3, new Stone());
            warehouse.addShelf(new ResQuantity(new Shield(), 2));
            warehouse.addResource(4, new Shield());
            warehouse.addShelf(new ResQuantity(new Coin(), 2));
        }
        catch(IllegalShelfException e){
            fail();
        }

        //StrongBox: Quantity:Resource
        //10:coin, 1:Servant, 5:Shield, 3:Stone
        StrongBox strongBox = board.getStrongBox();
        strongBox.addResource(new Coin(), 10);
        strongBox.addResource(new Servant(), 1);
        strongBox.addResource(new Shield(), 5);
        strongBox.addResource(new Stone(), 3);
    }


    @Test
    public void createReqMapTest(){
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        shelves.add(4);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        quantity.add(1);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        try{
            Map<Resource, Integer> map = ResQuantity.createReqMap(board,shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 11);
            copy.put(new Stone(), 3);
            copy.put(new Shield(), 2);

            for(Resource r : map.keySet()){
                assertEquals(map.get(r), copy.get(r));
            }
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void createReqMapTestEmptyShelvesQuantity(){
        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        try{
            Map<Resource, Integer> map = ResQuantity.createReqMap(board,shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 10);
            copy.put(new Stone(), 1);
            copy.put(new Shield(), 1);

            for(Resource r : map.keySet()){
                assertEquals(map.get(r), copy.get(r));
            }
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void createReqMapTestEmptyWarehouse(){
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        try{
            Map<Resource, Integer> map = ResQuantity.createReqMap(board,shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 1);
            copy.put(new Stone(), 2);


            for(Resource r : map.keySet()){
                assertEquals(map.get(r), copy.get(r));
            }
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void createReqMapTestQuantity0(){
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(4);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        try{
            Map<Resource, Integer> map = ResQuantity.createReqMap(board,shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 11);
            copy.put(new Stone(), 1);
            copy.put(new Shield(), 1);

            for(Resource r : map.keySet()){
                assertEquals(map.get(r), copy.get(r));
            }
        }
        catch(InvalidActionException e){
            fail();
        }
    }


    @Test
    public void createReqMapTestNotEnoughWarehouse() {
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(3);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> ResQuantity.createReqMap(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Selected more resources than are present!");
    }

    @Test
    public void createReqMapTestNotEnoughWarehouse2() {
        ArrayList<Integer> shelves = new ArrayList<>();
        //not existing shelf
        shelves.add(6);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> ResQuantity.createReqMap(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Invalid Shelf!");
    }

    @Test
    public void createReqMapTestNotEnoughWarehouse3() {
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        //selected more resources than are present
        quantity.add(2);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> ResQuantity.createReqMap(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Selected more resources than are present!");
    }

    @Test
    public void createReqMapTestNotEnoughWarehouse4() {
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(5);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        //selected more resources than are present
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> ResQuantity.createReqMap(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Selected more resources than are present!");
    }

    @Test
    public void createReqMapTestNotEnoughStrongbox() {
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 11));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> ResQuantity.createReqMap(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Selected more resources than are present!");
    }

    @Test
    public void createReqMapTestStrongboxWith0(){
        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(4);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 0));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        try{
            Map<Resource, Integer> map = ResQuantity.createReqMap(board,shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 1);
            copy.put(new Stone(), 1);
            copy.put(new Shield(), 1);

            for(Resource r : map.keySet()){
                assertEquals(map.get(r), copy.get(r));
            }
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void useResourcesTest(){

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(2);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        try{
            ResQuantity.useResources(board, shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 0);
            copy.put(new Stone(), 2);
            copy.put(new Shield(), 4);
            copy.put(new Servant() ,1);

            for(Resource r : board.getStrongBox().getResources().keySet()){
                assertEquals(board.getStrongBox().getResources().get(r), copy.get(r));
            }

            Map<Resource, Integer> copy1 = new HashMap<>();
            copy1.put(new Coin(), 0);
            copy1.put(new Stone(), 0);
            copy1.put(new Shield(), 1);
            copy1.put(new Servant() ,1);

            for(Resource r : board.getWarehouse().getAllResources().keySet()){
                assertEquals(board.getWarehouse().getAllResources().get(r), copy1.get(r));
            }
        }
        catch(InvalidActionException e){
            fail();
        }

    }

    @Test
    public void useResourcesTest1() {

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        //not existing shelf with quantity 0
        shelves.add(6);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(0);
        quantity.add(2);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 0));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        strongBox.add(new ResQuantity(new Servant(), 1));
        try {
            ResQuantity.useResources(board, shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 10);
            copy.put(new Stone(), 2);
            copy.put(new Shield(), 4);
            copy.put(new Servant(), 0);

            for (Resource r : board.getStrongBox().getResources().keySet()) {
                assertEquals(board.getStrongBox().getResources().get(r), copy.get(r));
            }

            Map<Resource, Integer> copy1 = new HashMap<>();
            copy1.put(new Coin(), 1);
            copy1.put(new Stone(), 0);
            copy1.put(new Shield(), 1);
            copy1.put(new Servant(), 1);

            for (Resource r : board.getWarehouse().getAllResources().keySet()) {
                assertEquals(board.getWarehouse().getAllResources().get(r), copy1.get(r));
            }
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void useResourcesTest2UseAll() {

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(2);
        shelves.add(3);
        shelves.add(4);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(1);
        quantity.add(1);
        quantity.add(2);
        quantity.add(1);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 10));
        strongBox.add(new ResQuantity(new Stone(), 3));
        strongBox.add(new ResQuantity(new Shield(), 5));
        strongBox.add(new ResQuantity(new Servant(), 1));
        try {
            ResQuantity.useResources(board, shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 0);
            copy.put(new Stone(), 0);
            copy.put(new Shield(), 0);
            copy.put(new Servant(), 0);

            for (Resource r : board.getStrongBox().getResources().keySet()) {
                assertEquals(board.getStrongBox().getResources().get(r), copy.get(r));
            }

            Map<Resource, Integer> copy1 = new HashMap<>();
            copy1.put(new Coin(), 0);
            copy1.put(new Stone(), 0);
            copy1.put(new Shield(), 0);
            copy1.put(new Servant(), 0);

            for (Resource r : board.getWarehouse().getAllResources().keySet()) {
                assertEquals(board.getWarehouse().getAllResources().get(r), copy1.get(r));
            }
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void useResourcesTest2() {

        ArrayList<Integer> shelves = new ArrayList<>();
        shelves.add(1);
        shelves.add(3);
        //not existing shelf with quantity 0
        shelves.add(6);
        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(0);
        quantity.add(3);
        quantity.add(0);
        ArrayList<ResQuantity> strongBox = new ArrayList<>();
        strongBox.add(new ResQuantity(new Coin(), 0));
        strongBox.add(new ResQuantity(new Stone(), 1));
        strongBox.add(new ResQuantity(new Shield(), 1));
        strongBox.add(new ResQuantity(new Servant(), 1));

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> ResQuantity.useResources(board, shelves, quantity, strongBox));
        assertEquals(exception.getMessage(), "Requirements not met! Error!");
    }


    @Test
    public void useResourcesTestEmptyInputArrays() {

        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        try {
            ResQuantity.useResources(board, shelves, quantity, strongBox);
            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 10);
            copy.put(new Stone(), 3);
            copy.put(new Shield(), 5);
            copy.put(new Servant(), 1);

            for (Resource r : board.getStrongBox().getResources().keySet()) {
                assertEquals(board.getStrongBox().getResources().get(r), copy.get(r));
            }

            Map<Resource, Integer> copy1 = new HashMap<>();
            copy1.put(new Coin(), 1);
            copy1.put(new Stone(), 2);
            copy1.put(new Shield(), 1);
            copy1.put(new Servant(), 1);

            for (Resource r : board.getWarehouse().getAllResources().keySet()) {
                assertEquals(board.getWarehouse().getAllResources().get(r), copy1.get(r));
            }
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void checkDevelopmentTest() {

        Map<Resource,Integer>resourceStatus = new HashMap<>();
        resourceStatus.put(new Coin(), 15);
        resourceStatus.put(new Servant(), 5);

        try {
            new ResQuantity(new Coin(), 5).checkDevelopment(resourceStatus,board);

            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 10);
            copy.put(new Stone(), 0);
            copy.put(new Shield(), 0);
            copy.put(new Servant(), 5);

            for (Resource r : resourceStatus.keySet()) {
                assertEquals(resourceStatus.get(r), copy.get(r));
            }

        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void checkDevelopmentTestMoreDiscountThanNecessary() {

        Map<Resource,Integer>resourceStatus = new HashMap<>();
        resourceStatus.put(new Coin(), 15);
        resourceStatus.put(new Servant(), 5);
        board.getModifications().addDiscount(new Coin(), 5);

        try {
            new ResQuantity(new Coin(), 2).checkDevelopment(resourceStatus,board);

            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 15);
            copy.put(new Stone(), 0);
            copy.put(new Shield(), 0);
            copy.put(new Servant(), 5);

            for (Resource r : resourceStatus.keySet()) {
                assertEquals(resourceStatus.get(r), copy.get(r));
            }

        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void checkDevelopmentTestExactDiscount() {

        Map<Resource,Integer>resourceStatus = new HashMap<>();
        resourceStatus.put(new Coin(), 0);
        resourceStatus.put(new Servant(), 5);
        board.getModifications().addDiscount(new Coin(), 1);

        try {
            new ResQuantity(new Coin(), 1).checkDevelopment(resourceStatus,board);

            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 0);
            copy.put(new Stone(), 0);
            copy.put(new Shield(), 0);
            copy.put(new Servant(), 5);

            for (Resource r : resourceStatus.keySet()) {
                assertEquals(resourceStatus.get(r), copy.get(r));
            }

        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void checkDevelopmentTestNotEnoughRes() {

        Map<Resource,Integer>resourceStatus = new HashMap<>();
        resourceStatus.put(new Coin(), 5);
        resourceStatus.put(new Servant(), 5);
        board.getModifications().addDiscount(new Coin(), 1);


        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> new ResQuantity(new Coin(), 7).checkDevelopment(resourceStatus,board));
        assertEquals(exception.getMessage(), "Insufficient resources!");

    }

    @Test
    public void checkProductionTest() {

        Map<Resource,Integer>resourceStatus = new HashMap<>();
        resourceStatus.put(new Coin(), 12);
        resourceStatus.put(new Servant(), 5);

        try {
            new ResQuantity(new Coin(), 1).checkDevelopment(resourceStatus,board);

            Map<Resource, Integer> copy = new HashMap<>();
            copy.put(new Coin(), 11);
            copy.put(new Stone(), 0);
            copy.put(new Shield(), 0);
            copy.put(new Servant(), 5);

            for (Resource r : resourceStatus.keySet()) {
                assertEquals(resourceStatus.get(r), copy.get(r));
            }

        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    public void checkProductionTestNotEnoughRes() {

        Map<Resource,Integer>resourceStatus = new HashMap<>();
        resourceStatus.put(new Coin(), 5);
        resourceStatus.put(new Servant(), 5);


        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> new ResQuantity(new Coin(), 7).checkDevelopment(resourceStatus,board));
        assertEquals(exception.getMessage(), "Insufficient resources!");

    }



}