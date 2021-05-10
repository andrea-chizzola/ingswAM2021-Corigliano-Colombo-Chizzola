package it.polimi.ingsw.Messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuyCardTest {

    @Test
    void test1(){
        SerializeMessage.serializeToXML();
    }
@Test
    void test2(){
        SerializeMessage.deserialize();
}
    @Test
    void test3(){
        SerializeMessage.serializeToXML1();
    }

    @Test
    void test4(){
        SerializeMessage.deserialize2();
    }
}