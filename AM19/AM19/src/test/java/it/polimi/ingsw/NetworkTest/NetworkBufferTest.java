package it.polimi.ingsw.NetworkTest;
import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.NetworkBuffer;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class NetworkBufferTest {
    private static NetworkBuffer buffer;
/*
    @BeforeAll
    public static void setUp(){
        buffer = new NetworkBuffer();
    }

    @Ignore
    @Test
    public void appendTest1() throws Exception, EmptyBufferException {
        buffer.append("<Message>test</Message>");
        assertEquals("<Message>test</Message>", buffer.get());
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

    @Ignore
    @Test
    public void appendTest5() throws Exception, EmptyBufferException {
        buffer.append("<Message>test");
        buffer.append("</Message>c");
        assertEquals("<Message>test</Message>", buffer.get());
        assertThrows(MalformedMessageException.class, () -> buffer.append("<pong/>"));
    }

    @Test
    public void getException(){
        assertThrows(EmptyBufferException.class, () -> buffer.get());
    }*/
}
