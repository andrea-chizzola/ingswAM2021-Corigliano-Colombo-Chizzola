package it.polimi.ingsw;

import it.polimi.ingsw.Model.Decks.Container;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

/**
 * this class is used to test the generic class Container<T>
 */
public class ContainerTest {
    private Container<String> container;
    private LinkedList<String> elementsTest;

    @BeforeEach
    public void setUp(){
        elementsTest = new LinkedList<>();
        elementsTest.add("A");
        elementsTest.add("B");
        elementsTest.add("C");
        elementsTest.add("D");
        elementsTest.add("E");
        elementsTest.add("F");
        elementsTest.add("G");
        elementsTest.add("H");
        elementsTest.add("I");

        container = new Container<>();
        container.addAll(elementsTest);
    }

    @Test
    public void correctInsertionTest(){
        assertEquals(elementsTest.size(), container.size());
        LinkedList<String> temp = new LinkedList<>();

        for(int i=0; i<elementsTest.size(); i++){
            temp.add(container.extractTop());
        }
        assertTrue(temp.containsAll(elementsTest));
        assertTrue(elementsTest.containsAll(temp));
    }

    @Test
    public void shuffleTest(){
        assertEquals(elementsTest.size(), container.size());

        container.shuffle();
        LinkedList<String> temp = new LinkedList<>();

        for(int i=0; i<elementsTest.size(); i++){
            temp.add(container.extractTop());
        }
        assertTrue(temp.containsAll(elementsTest));
        assertTrue(elementsTest.containsAll(temp));
    }

    @Test
    public void sizeTest(){
        assertEquals(elementsTest.size(), container.size());
    }

    @Test
    public void readTopTest(){
        assertEquals(elementsTest.size(), container.size());

        for(int i=0; i<elementsTest.size(); i++){
            assertEquals(elementsTest.get(i), container.readTop());
            container.extractTop();
        }
    }
    @Test
    public void extractTopTest(){
        assertEquals(elementsTest.size(), container.size());
        LinkedList<String> temp = new LinkedList<>();

        for(int i=0; i<elementsTest.size(); i++){
            temp.addAll(container.extractTop(1));
            assertTrue(temp.containsAll(elementsTest.subList(0,i+1)));
            assertTrue(elementsTest.subList(0,i+1).containsAll(temp));
        }
    }
    @Test
    public void extractEverythingTest(){
        container.extractTop(20);
        assertEquals(container.size(), 0);
    }

    @Test
    public void extractItemsFromEmpty(){
        container.extractTop(20);
        assertThrows(IllegalArgumentException.class, () -> container.extractTop(1));
    }
    @Test
    public void extractOneItemFromEmpty(){
        container.extractTop(20);
        assertThrows(IllegalArgumentException.class, () -> container.extractTop());
    }
}
