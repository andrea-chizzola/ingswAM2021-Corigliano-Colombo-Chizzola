package it.polimi.ingsw;

import it.polimi.ingsw.Model.Decks.ActionTokenDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTokenDeckTest {

    private ActionTokenDeck actionTokenDeck1;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    void setUp() {

        actionTokenDeck1 = new ActionTokenDeck(file);

    }

    @AfterEach
    void tearDown() {

        actionTokenDeck1 = null;

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

        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();
        actionTokenDeck1.changeList();

        assertEquals(6, actionTokenDeck1.getUnusedActionTokens().size());
        assertEquals(0, actionTokenDeck1.getUsedActionTokens().size());

    }

    @Test
    @DisplayName("Get top test")
    void getTop() {

        assertSame(actionTokenDeck1.getTop(), actionTokenDeck1.getUnusedActionTokens().readTop());

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