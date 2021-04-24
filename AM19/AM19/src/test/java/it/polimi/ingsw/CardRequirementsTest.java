package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Cards.Colors.Blue;
import it.polimi.ingsw.Model.Cards.Colors.Green;
import it.polimi.ingsw.Model.Cards.Colors.Purple;
import it.polimi.ingsw.Model.Cards.Colors.Yellow;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CardRequirementsTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";
    private LeaderCard card1;
    private LeaderCard card2;


    @BeforeEach
    public void setUp() {

        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);



        //Requirements coin:1, servant:2, shield:3
        LinkedList<CardQuantity> requirements1 = new LinkedList<>();
        requirements1.add(new CardQuantity(new Green(), 2, 1));
        requirements1.add(new CardQuantity(new Green(), 1, 3));

        //leader card 1
        Requirements req1 = new CardRequirements(requirements1);
        LinkedList<ResQuantity> materials1 = new LinkedList<>();
        LinkedList<ResQuantity> products1 = new LinkedList<>();
        Production production1 = new Production(materials1,products1);

        card1 = new LeaderCard(3, production1, req1);

        //Requirements coin:1, servant:2, shield:3
        LinkedList<CardQuantity> requirements2 = new LinkedList<>();
        requirements2.add(new CardQuantity(new Green(), 2, 1));
        requirements2.add(new CardQuantity(new Yellow(), 1, 3));

        //leader card 2
        Requirements req2 = new CardRequirements(requirements2);
        LinkedList<ResQuantity> materials2 = new LinkedList<>();
        LinkedList<ResQuantity> products2 = new LinkedList<>();
        Production production2 = new Production(materials2,products2);

        card2 = new LeaderCard(3, production2, req2);




        DevelopmentCard card3 = new DevelopmentCard(3,production1,req1,new Green(), 1);
        DevelopmentCard card4 = new DevelopmentCard(2,production1,req1,new Blue(), 2);
        DevelopmentCard card5 = new DevelopmentCard(2,production1,req1,new Purple(), 3);
        DevelopmentCard card6 = new DevelopmentCard(2,production1,req1,new Green(), 1);
        DevelopmentCard card7 = new DevelopmentCard(2,production1,req1,new Green(), 2);
        DevelopmentCard card8 = new DevelopmentCard(2,production1,req1,new Green(), 3);

        try {
            board.getSlot(1).insertCard(card3);
            board.getSlot(1).insertCard(card4);
            board.getSlot(1).insertCard(card5);
            board.getSlot(2).insertCard(card6);
            board.getSlot(2).insertCard(card7);
            board.getSlot(2).insertCard(card8);
        }
        catch (IllegalSlotException e){fail();}

    }

    @Test
    public void cardRequirementsTest(){
        try {
            ArrayList<Integer> shelves = new ArrayList<>();
            ArrayList<Integer> quantity = new ArrayList<>();
            ArrayList<ResQuantity> strongBox = new ArrayList<>();

            card1.checkReq(board, shelves, quantity, strongBox);
        }
        catch (InvalidActionException e){fail();}
    }

    @Test
    public void notEnoughCardsTest(){
        ArrayList<Integer> shelves = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        assertThrows(InvalidActionException.class, () -> card2.checkReq(board, shelves, quantity, strongBox));
    }
}