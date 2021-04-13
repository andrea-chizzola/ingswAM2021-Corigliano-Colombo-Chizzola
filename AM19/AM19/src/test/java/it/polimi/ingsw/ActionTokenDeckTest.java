package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActionTokenDeckTest {

    private ActionTokenDeck actionTokenDeck1;
    private ArrayList<Action> actions1;


    @BeforeEach
    void setUp() {

        MoveBlack moveBlack = new MoveBlack(2);
        MoveAndShuffle moveAndShuffle = new MoveAndShuffle(1);
        Discard discardBlue = new Discard(new Blue(), 2);
        Discard discardGreen = new Discard(new Green(), 2);
        Discard discardPurple = new Discard(new Purple(), 2);
        Discard discardYellow = new Discard(new Yellow(), 2);

        actions1 = new ArrayList<>();
        actions1.add(moveBlack);
        actions1.add(moveAndShuffle);
        actions1.add(discardBlue);
        actions1.add(discardPurple);
        actions1.add(discardGreen);
        actions1.add(discardYellow);

        actionTokenDeck1 = new ActionTokenDeck(actions1);

    }

    @AfterEach
    void tearDown() {

        actionTokenDeck1 = null;
        actions1 = null;

    }

    @Test
    @DisplayName("Merge and shuffle test")
    void mergeAndShuffle() {

        actionTokenDeck1.mergeAndShuffle();

        assertEquals(6, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(0, actionTokenDeck1.getUsedActionTokens().size());

        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();

        actionTokenDeck1.mergeAndShuffle();

        assertEquals(6, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(0, actionTokenDeck1.getUsedActionTokens().size());

        actionTokenDeck1.mergeAndShuffle();
        actionTokenDeck1.mergeAndShuffle();
        actionTokenDeck1.mergeAndShuffle();
        actionTokenDeck1.mergeAndShuffle();
        actionTokenDeck1.mergeAndShuffle();
        actionTokenDeck1.mergeAndShuffle();

        actionTokenDeck1.mergeAndShuffle();

        assertEquals(6, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(0, actionTokenDeck1.getUsedActionTokens().size());

    }

    @Test
    @DisplayName("Get top test")
    void getTop() {

        assertSame(actionTokenDeck1.getTop(), actions1.get(0));

    }

    @Test
    @DisplayName("Change list test")
    void changeList() {

        assertEquals(6, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(0, actionTokenDeck1.getUsedActionTokens().size());

        actionTokenDeck1.changeList();

        assertEquals(5, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(1, actionTokenDeck1.getUsedActionTokens().size());
        assertNotEquals(actionTokenDeck1.getUnusedActionTokens().readTop(), actionTokenDeck1.getUsedActionTokens().get(0));

        actionTokenDeck1.changeList();

        assertEquals(4, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(2, actionTokenDeck1.getUsedActionTokens().size());
        assertNotEquals(actionTokenDeck1.getUnusedActionTokens().readTop(), actionTokenDeck1.getUsedActionTokens().get(0));

        actionTokenDeck1.changeList();

        assertEquals(3, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(3, actionTokenDeck1.getUsedActionTokens().size());
        assertNotEquals(actionTokenDeck1.getUnusedActionTokens().readTop(), actionTokenDeck1.getUsedActionTokens().get(0));

        actionTokenDeck1.changeList();

        assertEquals(2, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(4, actionTokenDeck1.getUsedActionTokens().size());
        assertNotEquals(actionTokenDeck1.getUnusedActionTokens().readTop(), actionTokenDeck1.getUsedActionTokens().get(0));

        actionTokenDeck1.changeList();

        assertEquals(1, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(5, actionTokenDeck1.getUsedActionTokens().size());
        assertNotEquals(actionTokenDeck1.getUnusedActionTokens().readTop(), actionTokenDeck1.getUsedActionTokens().get(0));

        actionTokenDeck1.changeList();

        assertEquals(6, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(0, actionTokenDeck1.getUsedActionTokens().size());

    }
}