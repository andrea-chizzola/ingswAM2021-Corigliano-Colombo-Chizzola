package it.polimi.ingsw;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";

    @BeforeEach
    public void setUp() {

        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

    }

    @Test
    public void getColorServantTest(){
        assertEquals(new Servant().getColor(), ResourceColor.PURPLE);
    }

    @Test
    public void addResourceServantTest(){
        StrongBox strongbox = board.getStrongBox();
        assertEquals(strongbox.getResources().getOrDefault(new Servant(), 0),0);

        new Servant().addResourceStrongbox(board, 2);
        assertEquals(strongbox.getResources().get(new Servant()),2);
    }

    @Test
    public void addNoResourceTest(){
        StrongBox strongbox = board.getStrongBox();
        assertEquals(strongbox.getResources().getOrDefault(new Servant(), 0),0);

        new Servant().addResourceStrongbox(board, 0);
        assertEquals(strongbox.getResources().get(new Servant()),0);
    }

    @Test
    public void getColorShieldTest(){
        assertEquals(new Shield().getColor(), ResourceColor.BLUE);
    }

    @Test
    public void addResourceShieldTest(){
        StrongBox strongbox = board.getStrongBox();
        assertEquals(strongbox.getResources().getOrDefault(new Shield(), 0),0);

        new Shield().addResourceStrongbox(board, 8);
        assertEquals(strongbox.getResources().get(new Shield()),8);
    }

    @Test
    public void getColorStoneTest(){
        assertEquals(new Stone().getColor(), ResourceColor.GRAY);
    }

    @Test
    public void addResourceStoneTest(){
        StrongBox strongbox = board.getStrongBox();
        assertEquals(strongbox.getResources().getOrDefault(new Stone(), 0),0);

        new Stone().addResourceStrongbox(board, 8);
        assertEquals(strongbox.getResources().get(new Stone()),8);
    }

    @Test
    public void getColorCoinTest(){
        assertEquals(new Coin().getColor(), ResourceColor.YELLOW);
    }

    @Test
    public void addResourceCoinTest(){
        StrongBox strongbox = board.getStrongBox();
        assertEquals(strongbox.getResources().getOrDefault(new Coin(), 0),0);

        new Coin().addResourceStrongbox(board, 8);
        assertEquals(strongbox.getResources().get(new Coin()),8);
    }

    @Test
    public void getColorFaithTest(){
        assertEquals(new Faith().getColor(), ResourceColor.RED);
    }

    @Test
    public void addResourceFaithTest(){
        StrongBox strongbox = board.getStrongBox();
        assertEquals(strongbox.getResources().getOrDefault(new Faith(), 0),0);

        new Faith().addResourceStrongbox(board, 8);
        assertEquals(strongbox.getResources().getOrDefault(new Faith(), 0),0);
        FaithTrack track = board.getFaithTrack();
        assertEquals(track.getPosition(), 8);
    }
}
