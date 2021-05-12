package it.polimi.ingsw.Messages;


import it.polimi.ingsw.Exceptions.MalformedMessageException;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MessageParserTest {

    @Test
    void test1 (){
        String file = "<Message><body>Selection of the type of turn.</body><messageType>SELECTED_TURN</messageType><turnType>DO_PRODUCTION</turnType></Message>";
        String result = null;
        try {
            result = MessageParser.getMessageTag(file, "messageType");
        } catch (MalformedMessageException e) {
            fail();
        }
        assertEquals(result,"SELECTED_TURN");
    }

    @Test
    void test3 (){
        String file = "<Message><body>Selection of the type of turn.</body><messageType>SELECTED_TURN</messageType><turnType>DO_PRODUCTION</turnType></Message>";
        String result = null;
        try {
            result = MessageParser.getMessageTag(file, "body");
        } catch (MalformedMessageException e) {
            fail();
        }
        assertEquals(result,"Selection of the type of turn.");
    }

    @Test
    void test4 (){
        String file = "<Message><body>Selection of the type of turn.</body><messageType>SELECTED_TURN</messageType><turnType>DO_PRODUCTION</turnType></Message>";
        String result = null;


            Exception exception1;
            exception1 = assertThrows(MalformedMessageException.class, () -> MessageParser.getMessageTag(file,"vsnso"));
            assertEquals("Parsing failure!", exception1.getMessage());
    }

    @Test
    void test5 (){
        String file = null;
        String result = null;

        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> MessageParser.getMessageTag(file,"vsnso"));
        assertEquals("Parsing failure!", exception1.getMessage());
    }

    @Test
    void test6 (){
        String file = "osfnvsfjdvpjnvpwvn";
        String result = null;

        Exception exception1;
        exception1 = assertThrows(MalformedMessageException.class, () -> MessageParser.getMessageTag(file,"vsnso"));
        assertEquals("Parsing failure!", exception1.getMessage());
    }


}