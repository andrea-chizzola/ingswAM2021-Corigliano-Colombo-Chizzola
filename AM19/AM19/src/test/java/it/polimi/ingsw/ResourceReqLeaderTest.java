package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Model.Boards.StrongBox;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.Cards.Requirements;
import it.polimi.ingsw.Model.Cards.ResourceReqLeader;
import it.polimi.ingsw.Model.Resources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceReqLeaderTest {
    private Board board;
    private final String file = "defaultConfiguration.xml";
    private LeaderCard card;


    @BeforeEach
    public void setUp() {

        //creation of GameBoard and Board
        ArrayList<String> names = new ArrayList<>();
        names.add("test");
        GameBoard gameBoard = new GameBoard(names, file);
        board = gameBoard.getPlayers().get(0);

        //initialization of Warehouse and StrongBox
        //Warehouse: Quantity:Shelf:Resource
        // 1:1:coin, 1:2:servant, 2:3:stones, 1:4:shield, 0:5:coin
        Warehouse warehouse = board.getWarehouse();
        try{
            warehouse.addResource(1, new Coin());
            warehouse.addResource(2, new Servant());
            warehouse.addResource(3, new Stone());
            warehouse.addResource(3, new Stone());
            warehouse.addShelf(new ResQuantity(new Shield(), 2));
            warehouse.addResource(4, new Shield());
            warehouse.addShelf(new ResQuantity(new Coin(), 2));
        }
        catch(IllegalShelfException e){
            fail();
        }
        //StrongBox: Quantity:Resource
        //10:coin, 1:Servant, 5:Shield, 3:Stone
        StrongBox strongBox = board.getStrongBox();
        strongBox.addResource(new Coin(), 10);
        strongBox.addResource(new Servant(), 1);
        strongBox.addResource(new Shield(), 5);
        strongBox.addResource(new Stone(), 3);

        //Requirements coin:1, servant:2, shield:3
        LinkedList<ResQuantity> requirements = new LinkedList<>();
        requirements.add(new ResQuantity(new Coin(), 2));
        requirements.add(new ResQuantity(new Servant(), 2));
        requirements.add(new ResQuantity(new Shield(), 3));
        Requirements req = new ResourceReqLeader(requirements);

        LinkedList<ResQuantity> materials = new LinkedList<>();
        LinkedList<ResQuantity> products = new LinkedList<>();
        Production production = new Production(materials,products, 0, 0);

       card = new LeaderCard(3, production, req, "1");
    }

    @Test
    public void ResourceReqLeader(){

        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        try {
            card.checkReq(board, shelves, quantity, strongBox);
        }
        catch (InvalidActionException e){fail();}
    }

    @Test
    public void ResourceReqLeaderAllResourcesNeeded(){

        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        //Requirements coin:1, servant:2, shield:3
        LinkedList<ResQuantity> requirements = new LinkedList<>();
        requirements.add(new ResQuantity(new Coin(), 11));
        requirements.add(new ResQuantity(new Servant(), 2));
        requirements.add(new ResQuantity(new Shield(), 6));
        requirements.add(new ResQuantity(new Stone(), 5));
        Requirements req = new ResourceReqLeader(requirements);

        LinkedList<ResQuantity> materials = new LinkedList<>();
        LinkedList<ResQuantity> products = new LinkedList<>();
        Production production = new Production(materials,products, 0, 0);

        card = new LeaderCard(3, production, req, "2");

        try {
            card.checkReq(board, shelves, quantity, strongBox);
        }
        catch (InvalidActionException e){fail();}
    }


    @Test
    public void ResourceReqLeaderNotEnoughRes(){

        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        //Requirements coin:1, servant:2, shield:3
        LinkedList<ResQuantity> requirements = new LinkedList<>();
        requirements.add(new ResQuantity(new Coin(), 11));
        requirements.add(new ResQuantity(new Servant(), 3));
        requirements.add(new ResQuantity(new Shield(), 6));
        requirements.add(new ResQuantity(new Stone(), 5));
        Requirements req = new ResourceReqLeader(requirements);

        LinkedList<ResQuantity> materials = new LinkedList<>();
        LinkedList<ResQuantity> products = new LinkedList<>();
        Production production = new Production(materials,products, 0, 0);

        card = new LeaderCard(3, production, req, "3");


        assertThrows(InvalidActionException.class, () -> card.checkReq(board, shelves, quantity, strongBox));
    }

    @Test
    public void ResourceReqLeaderZeroReq(){

        ArrayList<Integer> shelves = new ArrayList<>();

        ArrayList<Integer> quantity = new ArrayList<>();

        ArrayList<ResQuantity> strongBox = new ArrayList<>();

        //Requirements coin:1, servant:2, shield:3
        LinkedList<ResQuantity> requirements = new LinkedList<>();

        Requirements req = new ResourceReqLeader(requirements);

        LinkedList<ResQuantity> materials = new LinkedList<>();
        LinkedList<ResQuantity> products = new LinkedList<>();
        Production production = new Production(materials,products,0,0);

        card = new LeaderCard(3, production, req, "3");

        try {
            card.checkReq(board, shelves, quantity, strongBox);
        }
        catch (InvalidActionException e){fail();}
    }



}