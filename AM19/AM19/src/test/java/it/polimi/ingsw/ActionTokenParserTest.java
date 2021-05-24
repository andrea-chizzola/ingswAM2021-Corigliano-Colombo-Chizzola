package it.polimi.ingsw;

import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.ActionTokens.Discard;
import it.polimi.ingsw.Model.ActionTokens.MoveAndShuffle;
import it.polimi.ingsw.Model.ActionTokens.MoveBlack;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.Cards.Colors.Yellow;
import it.polimi.ingsw.xmlParser.ActionTokenParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

public class ActionTokenParserTest {

    private ActionTokenParser parser;
    String file = "src/test/java/it/polimi/ingsw/XMLSourcesTest/ActionTokensTest.xml";

    @BeforeEach
    public void setUp(){
        parser = ActionTokenParser.instance();
    }

    @Test
    public void buildActionTokensTest(){
        LinkedList<Action> actionTokens = parser.buildActionTokens(file);

        //creation of Action Tokens using constructors
        LinkedList<Action> copy = new LinkedList<>();
        copy.add(new Discard(new Purple(),2, "1", "src/main/resources/Images/punchboard/discardPurple.png"));
        copy.add(new Discard(new Yellow(),2, "2", "src/main/resources/Images/punchboard/discardYellow.png"));
        copy.add(new MoveBlack(2, "3", "src/main/resources/Images/punchboard/move2.png"));
        copy.add(new MoveAndShuffle(1, "4", "src/main/resources/Images/punchboard/moveAndShuffle.png"));

        assertEquals(actionTokens.get(0), copy.get(0));
        assertEquals(actionTokens.get(1), copy.get(1));
        assertEquals(actionTokens.get(2), copy.get(2));
        assertEquals(actionTokens.get(3), copy.get(3));

    }
}
