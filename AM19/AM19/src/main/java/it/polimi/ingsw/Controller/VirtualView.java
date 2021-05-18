package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Boards.GameBoardHandler;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.*;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Server.Game;
import it.polimi.ingsw.View.View;

import java.util.*;

public class VirtualView implements View {

    private Game game;
    private GameBoardHandler gameBoardHandler;

    public VirtualView(Game game, GameBoardHandler gameBoardHandler) {
        this.game = game;
        this.gameBoardHandler = gameBoardHandler;
    }

    /**
     * this method is used to initialize the state of the view
     */
    @Override
    public void initialize() {

    }

    /**
     * this method is used to show a message
     *
     * @param answer represents the type of message
     * @param body   is the content of the message
     *//*
    @Override
    public void showAnswer(boolean answer, String body, String nickName) {

        try {
            String message = MessageFactory.buildReply(answer,body,nickName);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}

    }*/

    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body is the content of the message
     * @param nickName represents the nickname of involved player
     * @param nickName represents the current state of the game
     */
    public void showGameStatus(boolean answer, String body, String nickName, TurnType state){
        try {
            String message = MessageFactory.buildGameStatus(answer,body,nickName,state);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this method is used to show an update of the marketBoard
     *
     * @param tray is the current state of the market tray
     */
    @Override
    public void showMarketUpdate(List<Marble> tray) {

        try{
            String message = MessageFactory.buildUpdateMarket(tray,"update market");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    @Override
    public void showMarblesUpdate(List<Marble> marblesTray, List<Marble> whiteModifications, String nickName) {
        try {
            String message = MessageFactory.buildSelectedMarbles(marblesTray,whiteModifications,nickName,"update selected marbles");
            game.send(message,nickName);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to show an update of the shared decks on the GameBoard
     *
     * @param decks contains the top cards of each deck
     */
    @Override
    public void showDecksUpdate(Map<Integer, String> decks) {
        try {
            String message = MessageFactory.buildDecksUpdate(decks,"update decks");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }


    @Override
    public void showBoxes(List<ResQuantity> warehouse, List<ResQuantity> strongBox, String nickName) {
        try {
            String message = MessageFactory.buildBoxUpdate(warehouse,strongBox,nickName,"update boxes");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to show an update of one's card slots
     *
     * @param slots represent the current state of one's card slots
     */
    @Override
    public void showSlotsUpdate(Map<Integer, String> slots, String nickName) {
        try {
            String message = MessageFactory.buildSlotsUpdate(slots,nickName,"update slots");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }


    /**
     * this method is used to show an update of one's LeaderCards
     *
     * @param cards represent the current state of one's leader cards
     */
    @Override
    public void showLeaderCards(Map<Integer, String> cards, Map<Integer, ItemStatus> status,  String nickName) {
        try {
            //aggiungere nickname al factory
            String message = MessageFactory.buildLeaderUpdate(cards,status,nickName, "update leader cards");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }


    /**
     * this method is used to show an update of one's FaithTrack
     *
     * @param faith           is the amount of faith obtained by the players
     * @param sections        is the state of the players' sections
     * @param faithLorenzo    is the faith obtained by Lorenzo
     * @param sectionsLorenzo is the status of Lorenzo's sections
     */
    @Override
    public void showFaithUpdate(Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, Optional<Integer> faithLorenzo, Optional<List<ItemStatus>> sectionsLorenzo) {
        try {
            String message = MessageFactory.buildFaithUpdate(faith,sections,faithLorenzo,sectionsLorenzo,"update faith");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to show
     *
     * @param action is the ID of the top token
     */
    @Override
    public void showTopToken(Optional<String> action) {
        try {
            String message = MessageFactory.buildUpdateLorenzo(action.orElse(""),"token");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();
        System.out.println(e.getMessage());
        }
    }

    /**
     * this method is used to show the points achieved at the end of the game
     *
     * @param players contains the name of the players and the points obtained
     */
    @Override
    public void showEndGame(Map<String, Integer> players) {
        try {
            String message = MessageFactory.buildEndGame(players,"game ended");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to show the disconnection of a player
     *
     * @param nickname is the name of the disconnected player
     */
    @Override
    public void showDisconnection(String nickname) {
        try {
            String message = MessageFactory.buildDisconnection("disconnection", nickname);
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to add a player to the view
     */
    @Override
    public void newPlayer() {

    }

    /**
     * this method is used to catch the player's selected turn
     *
     * @param turns  is the list of available turns
     * @param player is the nickname of the current player
     */
    @Override
    public String selectTurnAction(List<String> turns, String player) {
        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildCurrentPlayer(nickName,turns,"current player");
            //game.send(message,nickName);
            game.sendAll(message);
        }catch (MalformedMessageException e){ e.printStackTrace();}
        return "";
    }

    /**
     * this method is used to catch the LeaderCards selected by a player
     */
    @Override
    public void selectLeaderAction() {

        try {
            String message = MessageFactory.buildGameStatus(true,"Initialize cards",gameBoardHandler.currentPlayerNickname(),TurnType.INITIALIZATION_LEADERS);
            game.send(message,gameBoardHandler.currentPlayerNickname());
        }catch (MalformedMessageException e){ e.printStackTrace();}
        /*
        try {
            Map<Integer,ItemStatus> map = new HashMap<>();
            for(Integer i : cards.keySet()){
                map.put(i,ItemStatus.INACTIVE);
            }
            String message = MessageFactory.buildLeaderUpdate(cards,map, gameBoardHandler.currentPlayer(), "leader cards to choose");
            game.send(message,gameBoardHandler.currentPlayer());
        }catch (MalformedMessageException e){ e.printStackTrace();}*/
    }

    /**
     * this method is used to catch the player's selected row or column of the MarketBoard
     */
    @Override
    public void selectMarketAction() {

        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildGameStatus(true,"select market", nickName, TurnType.TAKE_RESOURCES);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }


    /**
     * this method is used to catch the action of a player on a LeaderCard
     */
    @Override
    public void leaderAction() {
        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildGameStatus(true,"leader action", nickName,TurnType.MANAGE_LEADER);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this method is used to catch the action of a player of a shared DevelopmentCard
     */
    @Override
    public void buyCardAction() {
        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildGameStatus(true,"buy card", nickName, TurnType.BUY_CARD);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this method is used to catch the action of a player on their productions
     */
    @Override
    public void doProductionsAction() {
        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildGameStatus(true,"do production", nickName, TurnType.DO_PRODUCTION);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this action is used to catch the resources chosen by a player
     */
    @Override
    public void getResourcesAction() {
        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildGameStatus(true,"get resources", nickName,TurnType.INITIALIZATION_RESOURCE);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this method is used to catch a swap in the warehouse
     */
    @Override
    public boolean swapAction() {
        try {
            String nickName = gameBoardHandler.currentPlayerNickname();
            String message = MessageFactory.buildGameStatus(true,"swap action", nickName,TurnType.SWAP);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
        return true;
    }



    /**
     * this method show the player's personal production.
     */
    @Override
    public void showPersonalProduction() {

    }
}
