package it.polimi.ingsw.NetworkTest;
import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Messages.NetworkBuffer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class NetworkBufferTest {
    private static NetworkBuffer buffer;

    @BeforeAll
    public static void setUp(){
        buffer = new NetworkBuffer();
    }

    @Test
    public void appendTest1() throws Exception, EmptyBufferException {
        buffer.append("<message>test</message>");
        assertEquals("<message>test</message>", buffer.get());
    }

    @Test
    public void appendTest2() throws Exception, EmptyBufferException {
        buffer.append("<ping/>");
        assertEquals("<ping/>", buffer.get());
    }

    @Test
    public void appendTest3() throws Exception, EmptyBufferException {
        buffer.append("<pong/>");
        assertEquals("<pong/>", buffer.get());
    }

    @Test
    public void appendTest4() throws Exception, EmptyBufferException {
        assertThrows(Exception.class, () -> buffer.append("pong/>"));
    }

    @Test
    public void appendTest5() throws Exception, EmptyBufferException {
        buffer.append("<message>test");
        buffer.append("</message>c");
        assertEquals("<message>test</message>", buffer.get());
        assertThrows(Exception.class, () -> buffer.append("<pong/>"));
    }

    @Test
    public void getException(){
        assertThrows(EmptyBufferException.class, () -> buffer.get());
    }
}
