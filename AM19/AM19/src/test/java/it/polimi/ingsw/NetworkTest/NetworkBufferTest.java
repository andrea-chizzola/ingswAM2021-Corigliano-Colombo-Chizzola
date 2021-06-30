package it.polimi.ingsw.NetworkTest;
import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.NetworkBuffer;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class NetworkBufferTest {
    private static NetworkBuffer buffer;

    @BeforeAll
    public static void setUp(){
        buffer = new NetworkBuffer();
    }

    @Ignore
    @Test
    public void appendTest1() throws MalformedMessageException, EmptyBufferException {
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message>test</Message>");
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message>test</Message>", buffer.get());
    }

    @Test
    public void appendTest2() throws MalformedMessageException, EmptyBufferException {
        buffer.append("<ping/>");
        assertEquals("<ping/>", buffer.get());
    }

    @Test
    public void appendTest3() throws MalformedMessageException, EmptyBufferException {
        buffer.append("<pong/>");
        assertEquals("<pong/>", buffer.get());
    }

    @Test
    public void appendTest4() {
        assertThrows(Exception.class, () -> buffer.append("pong/>"));
    }

    @Test
    public void appendTest5() throws MalformedMessageException, EmptyBufferException {
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message>test");
        buffer.append("</Message>c");
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Message>test</Message>", buffer.get());
        assertThrows(MalformedMessageException.class, () -> buffer.append("<pong/>"));
    }

    @Test
    public void getException(){
        assertThrows(EmptyBufferException.class, () -> buffer.get());
    }

    @Test
    public void getPingTest() throws MalformedMessageException {
        assertFalse(buffer.getPing());
        buffer.append("<ping/>");
        assertTrue(buffer.getPing());
    }

    @Test
    public void getPongTest() throws MalformedMessageException {
        assertFalse(buffer.getPong());
        buffer.append("<pong/>");
        assertTrue(buffer.getPong());
    }
}
