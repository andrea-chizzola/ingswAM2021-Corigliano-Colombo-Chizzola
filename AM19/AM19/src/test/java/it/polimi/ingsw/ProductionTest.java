package it.polimi.ingsw;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class ProductionTest {
    private static Production production1;
    private static Production production2;
    private static Production production3;
    private static Production production4;
    private static Board board;
    private static Map<Resource,Integer> resources;
    private static final String file = "defaultConfiguration.xml";

    @BeforeAll
    public static void setUp(){
        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

        //creation of a production without faith in products
        LinkedList<ResQuantity> materials1 = new LinkedList<>();
        materials1.add(new ResQuantity(new Coin(),2));
        materials1.add(new ResQuantity(new Stone(), 1));

        LinkedList<ResQuantity> products1 = new LinkedList<>();
        products1.add(new ResQuantity(new Servant(),3));
        products1.add(new ResQuantity(new Shield(),1));

        production1 = new Production(materials1, products1);

        //creation of a production with faith in products
        LinkedList<ResQuantity> materials2 = new LinkedList<>();
        materials2.add(new ResQuantity(new Coin(),2));
        materials2.add(new ResQuantity(new Stone(), 3));

        LinkedList<ResQuantity> products2 = new LinkedList<>();
        products2.add(new ResQuantity(new Faith(),3));
        products2.add(new ResQuantity(new Stone(),1));

        production2 = new Production(materials2, products2);

        //creation of a production with faith in materials
        LinkedList<ResQuantity> materials3 = new LinkedList<>();
        materials3.add(new ResQuantity(new Faith(),2));
        materials3.add(new ResQuantity(new Stone(), 1));

        LinkedList<ResQuantity> products3 = new LinkedList<>();
        products3.add(new ResQuantity(new Servant(),3));
        products3.add(new ResQuantity(new Shield(),1));

        production3 = new Production(materials3, products3);

        //creation of a production whose requirements contains exactly the same resources of the map
        LinkedList<ResQuantity> materials4 = new LinkedList<>();
        materials4.add(new ResQuantity(new Coin(),3));
        materials4.add(new ResQuantity(new Stone(), 1));

        LinkedList<ResQuantity> products4 = new LinkedList<>();
        products4.add(new ResQuantity(new Servant(),3));
        products4.add(new ResQuantity(new Shield(),1));

        production4 = new Production(materials4, products4);
    }

    @BeforeEach
    public void setUpMap() {
        //creation of a map of resources
        resources = new HashMap<>();
        resources.put(new Stone(),1);
        resources.put(new Coin(), 3);
    }

    @Test
    public void getProduction(){
        assertEquals(production1, production1.getProduction());
    }

    @Test
    public void addProductsTest(){
        production1.addProducts(board);
        StrongBox strongBox = board.getStrongBox();
        assertEquals(strongBox.getQuantity( new Servant()), 3 );
        assertEquals(strongBox.getQuantity( new Shield()), 1 );

    }

    @Test
    public void addProductsFaithTest(){
        production2.addProducts(board);
        FaithTrack track = board.getFaithTrack();
        StrongBox strongBox = board.getStrongBox();

        assertEquals(track.getPosition(), 3 );
        assertEquals(strongBox.getQuantity( new Stone()), 1 );

    }

    @Test
    public void checkProductionTest1(){
        //the map contains more resources than the ones required
        try{
            assertTrue(production1.checkProduction(resources));
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void checkProductionTest2(){
        //the map contains exactly the required resources
        try{
            assertTrue(production4.checkProduction(resources));
        }
        catch(InvalidActionException e){
            fail();
        }
    }

    @Test
    public void checkProductionFailureTest1(){
        //the map does not contains enough resources
        System.out.println("Prova");
        assertThrows(InvalidActionException.class, () -> production2.checkProduction(resources));
    }

    @Test
    public void checkProductionFailureTest2(){
        //the production contains Faith in the resources, which can't be inserted in StrongBox or Wharehouse
        assertThrows(InvalidActionException.class, () -> production3.checkProduction(resources));
    }
}
