package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.*;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.Model.Turn.BuyCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BuyCardTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";
    private DevelopmentCard dev1;
    private DevelopmentCard dev2;
    private DevelopmentCard dev3;
    private Coin coin = new Coin();
    private Shield shield = new Shield();
    private Servant servant = new Servant();
    private Stone stone = new Stone();


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
        strongBox.addResource(new Coin(), 20);
        strongBox.addResource(new Servant(), 20);
        strongBox.addResource(new Shield(), 20);
        strongBox.addResource(new Stone(), 20);

        //creation of a DevelopmentCard
        Requirements requirements1 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect1 = new Production(new LinkedList<>(), new LinkedList<>(), 0, 0);
        dev1 = new DevelopmentCard(0, effect1, requirements1, new Green(), 1, "1");

        //creation of a DevelopmentCard
        Requirements requirements2 = new ResourceReqDev(new LinkedList<>());
        SpecialEffect effect2 = new Production(new LinkedList<>(), new LinkedList<>(), 0 , 0);
        dev2 = new DevelopmentCard(0, effect2, requirements2, new Green(), 1, "2");

        //creation of a DevelopmentCard
        Requirements requirements3 = new ResourceReqDev(new LinkedList<>());
        
        dev3 = new DevelopmentCard(0, effect2, requirements3, new Purple(), 2, "3");

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
    public void testBuyCardBasic() {

        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        DevelopmentCard card = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1);
        int coins = board.getStrongBox().getQuantity(coin);
        int shields = board.getStrongBox().getQuantity(shield);
        int stones = board.getStrongBox().getQuantity(stone);
        int servants = board.getStrongBox().getQuantity(servant);
        int deckSize = board.getGameBoard().getDevelopmentDeck().getNumber(new Blue(), 1);

        if (map.containsKey(coin)) {
            shelves.add(1);
            quantity.add(1);
        }

        if (map.containsKey(stone)) {
            shelves.add(3);
            quantity.add(1);
        }

        if (map.containsKey(shield)) {
            shelves.add(4);
            quantity.add(1);
        }


        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(servant.getColor()))
                 strongBox.add(new ResQuantity(resource, map.get(resource)));

        }

        BuyCard buyCard = new BuyCard();

        try {
            buyCard.buyCard(board, 3, new Blue(), 1, shelves, quantity, strongBox);
        } catch (InvalidActionException e) {
            fail();
        }


        try {
            if (map.containsKey(coin)) {
                assertThrows(IllegalShelfException.class, () -> board.getWarehouse().getResource(1));
            }

            if (map.containsKey(stone)) {
                assertEquals(board.getWarehouse().getQuantity(3), 1);
                assertEquals(board.getWarehouse().getResource(3).getColor(), stone.getColor());
            }

            if (map.containsKey(shield)) {
                assertEquals(board.getWarehouse().getQuantity(4),0);
                assertEquals(board.getWarehouse().getResource(4).getColor(), shield.getColor());
            }
        }catch (IllegalShelfException e){fail();}

        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), coins-(map.get(resource)-1));
            if (resource.getColor().equals(stone.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), stones-(map.get(resource)-1));
            if (resource.getColor().equals(shield.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), shields-(map.get(resource)-1));
            if(resource.getColor().equals(servant.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), servants-(map.get(resource)));
        }

        assertEquals(board.getGameBoard().getDevelopmentDeck().getNumber(new Blue(), 1), deckSize - 1);
        try {
            assertEquals(board.getSlot(3).getCards().getFirst(), card );
        } catch (IllegalSlotException e) {
            fail();
        }


    }


    @Test
    public void testIllegalCardLevel(){
        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();


        if (map.containsKey(coin)) {
            shelves.add(1);
            quantity.add(1);
        }

        if (map.containsKey(stone)) {
            shelves.add(3);
            quantity.add(1);
        }

        if (map.containsKey(shield)) {
            shelves.add(4);
            quantity.add(1);
        }


        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(servant.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

        }

        BuyCard buyCard = new BuyCard();
        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> buyCard.buyCard(board,3, new Blue(), 5, shelves,quantity,strongBox));
        assertEquals("The deck does not exist",exception.getMessage());
    }

    @Test
    public void testIllegalSlotLevel(){
        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();


        if (map.containsKey(coin)) {
            shelves.add(1);
            quantity.add(1);
        }

        if (map.containsKey(stone)) {
            shelves.add(3);
            quantity.add(1);
        }

        if (map.containsKey(shield)) {
            shelves.add(4);
            quantity.add(1);
        }


        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(servant.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

        }

        BuyCard buyCard = new BuyCard();
        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> buyCard.buyCard(board,2, new Blue(), 1, shelves,quantity,strongBox));
        assertEquals("This card can't be inserted!",exception.getMessage());
    }

    @Test
    public void testIllegalSlotNumber(){
        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();


        if (map.containsKey(coin)) {
            shelves.add(1);
            quantity.add(1);
        }

        if (map.containsKey(stone)) {
            shelves.add(3);
            quantity.add(1);
        }

        if (map.containsKey(shield)) {
            shelves.add(4);
            quantity.add(1);
        }


        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(servant.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

        }

        BuyCard buyCard = new BuyCard();
        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> buyCard.buyCard(board,4, new Blue(), 1, shelves,quantity,strongBox));
        assertEquals("This slot doesn't exist",exception.getMessage());
    }


    @Test
    public void testBuyCardOnlyStrongbox() {

        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        DevelopmentCard card = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1);
        int coins = board.getStrongBox().getQuantity(coin);
        int shields = board.getStrongBox().getQuantity(shield);
        int stones = board.getStrongBox().getQuantity(stone);
        int servants = board.getStrongBox().getQuantity(servant);
        int deckSize = board.getGameBoard().getDevelopmentDeck().getNumber(new Blue(), 1);



        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) ));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

            if (resource.getColor().equals(servant.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

        }


        BuyCard buyCard = new BuyCard();

        try {
            buyCard.buyCard(board, 3, new Blue(), 1, shelves, quantity, strongBox);
        } catch (InvalidActionException e) {

            fail();
        }


        try {
            if (map.containsKey(coin)) {
                assertEquals(board.getWarehouse().getQuantity(1), 1);
                assertEquals(board.getWarehouse().getResource(1).getColor(), coin.getColor());
            }

            if (map.containsKey(stone)) {
                assertEquals(board.getWarehouse().getQuantity(3), 2);
                assertEquals(board.getWarehouse().getResource(3).getColor(), stone.getColor());
            }

            if (map.containsKey(shield)) {
                assertEquals(board.getWarehouse().getQuantity(4),1);
                assertEquals(board.getWarehouse().getResource(4).getColor(), shield.getColor());
            }
        }catch (IllegalShelfException e){fail();}

        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), coins-(map.get(resource)));
            if (resource.getColor().equals(stone.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), stones-(map.get(resource)));
            if (resource.getColor().equals(shield.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), shields-(map.get(resource)));
            if(resource.getColor().equals(servant.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), servants-(map.get(resource)));
        }

        assertEquals(board.getGameBoard().getDevelopmentDeck().getNumber(new Blue(), 1), deckSize - 1);
        try {
            assertEquals(board.getSlot(3).getCards().getFirst(), card );
        } catch (IllegalSlotException e) {
            fail();
        }


    }


    @Test
    public void testBuyCardNotEnoughResources() {



        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();




        BuyCard buyCard = new BuyCard();

        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> buyCard.buyCard(board,3, new Blue(), 1, shelves,quantity,strongBox));
        assertEquals("Insufficient resources!",exception.getMessage());

    }

    @Test
    public void testBuyCardTooManyResources() {

        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Blue(), 1).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();



        if (map.containsKey(coin)) {
            shelves.add(1);
            quantity.add(1);
        }

        if (map.containsKey(stone)) {
            shelves.add(3);
            quantity.add(1);
        }

        if (map.containsKey(shield)) {
            shelves.add(4);
            quantity.add(1);
        }


        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) ));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) ));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) ));

            if (resource.getColor().equals(servant.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

        }


        BuyCard buyCard = new BuyCard();
        Exception exception;
        exception = assertThrows(InvalidActionException.class, () -> buyCard.buyCard(board,3, new Blue(), 1, shelves,quantity,strongBox));
        assertEquals("Too many resources selected!",exception.getMessage());

    }

    @Test
    public void testBuyCardLevel3() {

        HashMap<Resource, Integer> map = board.getGameBoard().getDevelopmentDeck().readTop(new Green(), 3).getRequirements();

        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        DevelopmentCard card = board.getGameBoard().getDevelopmentDeck().readTop(new Green(), 3);
        int coins = board.getStrongBox().getQuantity(coin);
        int shields = board.getStrongBox().getQuantity(shield);
        int stones = board.getStrongBox().getQuantity(stone);
        int servants = board.getStrongBox().getQuantity(servant);
        int deckSize = board.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3);

        if (map.containsKey(coin)) {
            shelves.add(1);
            quantity.add(1);
        }

        if (map.containsKey(stone)) {
            shelves.add(3);
            quantity.add(1);
        }

        if (map.containsKey(shield)) {
            shelves.add(4);
            quantity.add(1);
        }


        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(stone.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(shield.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource) - 1));

            if (resource.getColor().equals(servant.getColor()))
                strongBox.add(new ResQuantity(resource, map.get(resource)));

        }

        BuyCard buyCard = new BuyCard();

        try {
            buyCard.buyCard(board, 1, new Green(), 3, shelves, quantity, strongBox);
        } catch (InvalidActionException e) {

            fail();
        }


        try {
            if (map.containsKey(coin)) {
                 assertThrows(IllegalShelfException.class, () -> board.getWarehouse().getResource(1));
            }

            if (map.containsKey(stone)) {
                assertEquals(board.getWarehouse().getQuantity(3), 1);
                assertEquals(board.getWarehouse().getResource(3).getColor(), stone.getColor());
            }

            if (map.containsKey(shield)) {
                assertEquals(board.getWarehouse().getQuantity(4),0);
                assertEquals(board.getWarehouse().getResource(4).getColor(), shield.getColor());
            }
        }catch (IllegalShelfException e){fail();}

        for (Resource resource : map.keySet()) {
            if (resource.getColor().equals(coin.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), coins-(map.get(resource)-1));
            if (resource.getColor().equals(stone.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), stones-(map.get(resource)-1));
            if (resource.getColor().equals(shield.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), shields-(map.get(resource)-1));
            if(resource.getColor().equals(servant.getColor()))
                assertEquals(board.getStrongBox().getQuantity(resource), servants-(map.get(resource)));
        }

        assertEquals(board.getGameBoard().getDevelopmentDeck().getNumber(new Green(), 3), deckSize - 1);
        try {
            assertEquals(board.getSlot(1).getCards().getLast(), card );
        } catch (IllegalSlotException e) {
            fail();
        }

    }


}