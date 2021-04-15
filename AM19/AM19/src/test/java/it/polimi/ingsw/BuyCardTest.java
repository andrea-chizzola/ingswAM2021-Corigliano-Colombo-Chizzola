package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BuyCardTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";
    private DevelopmentCard dev1;
    private DevelopmentCard dev2;
    private DevelopmentCard dev3;


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
        try {
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Servant());
            warehouse.addResource(3, new Stone());
            warehouse.addResource(3, new Stone());
            warehouse.addShelf(new ResQuantity(new Shield(), 2));
            warehouse.addResource(4, new Shield());
            warehouse.addShelf(new ResQuantity(new Coin(), 2));
        } catch (IllegalShelfException e) {
            fail();
        }
        //StrongBox: Quantity:Resource
        //10:coin, 1:Servant, 5:Shield, 3:Stone
        StrongBox strongBox = board.getStrongBox();
        strongBox.addResource(new Coin(), 10);
        strongBox.addResource(new Servant(), 1);
        strongBox.addResource(new Shield(), 5);
        strongBox.addResource(new Stone(), 3);

        //creation of a DevelopmentCard
        Requirements requirements1 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect1 = new Production(new LinkedList<>(), new LinkedList<>());
        dev1 = new DevelopmentCard(0, effect1, requirements1, new Green(), 1);

        //creation of a DevelopmentCard
        Requirements requirements2 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect2 = new Production(new LinkedList<>(), new LinkedList<>());
        dev2 = new DevelopmentCard(0, effect2, requirements2, new Green(), 1);

        //creation of a DevelopmentCard
        Requirements requirements3 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect3 = new Production(new LinkedList<>(), new LinkedList<>());
        dev3 = new DevelopmentCard(0, effect2, requirements3, new Purple(), 2);

        //Initialization of board slots of firstPlayer
        try {
            Slot slot1 = board.getSlot(1);
            slot1.insertCard(dev1);
            slot1.insertCard(dev3);
            Slot slot2 = board.getSlot(2);
            slot2.insertCard(dev2);
        } catch (IllegalSlotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){

        }


}