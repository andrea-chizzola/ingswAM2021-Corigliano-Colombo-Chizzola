package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.ActionTokenParser;
import it.polimi.ingsw.xmlParser.CardParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

public class ActionTokenParserTest {

    private ActionTokenParser parser;
    String file = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\ActionTokensTest.xml";

    @BeforeEach
    public void setUp(){
        parser = ActionTokenParser.instance();
    }

    @Test
    public void buildActionTokensTest(){
        LinkedList<Action> actionTokens = parser.buildActionTokens(file);

        //creation of Action Tokens using constructors
        LinkedList<Action> copy = new LinkedList<>();
        copy.add(new Discard(new Purple(),2));
        copy.add(new Discard(new Yellow(),2));
        copy.add(new MoveBlack(2));
        copy.add(new MoveAndShuffle(1));

        System.out.println("Debug");
        assertEquals(actionTokens.get(0), copy.get(0));
        assertEquals(actionTokens.get(1), copy.get(1));
        assertEquals(actionTokens.get(2), copy.get(2));
        assertEquals(actionTokens.get(3), copy.get(3));

    }
}
