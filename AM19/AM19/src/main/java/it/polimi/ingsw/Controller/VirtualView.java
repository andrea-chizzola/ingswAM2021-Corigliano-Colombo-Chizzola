package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Enumerations.*;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Boards.TurnType;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.View.Update;

import java.util.*;

public class VirtualView implements Update {

    /**
     * handler of the match
     */
    private final Game game;

    public VirtualView(Game game) {
        this.game = game;
    }


    /**
     * this method is used to show a message
     * @param answer represents the type of message
     * @param body   is the content of the message
     */
    @Override
    public void reply(boolean answer, String body, String nickName) {

        try {
            String message = MessageFactory.buildReply(answer,body,nickName);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this method is used to show a message
     * @param body is the content of the message
     * @param nickName represents the nickname of involved player
     * @param state represents the current state of the game
     */
    @Override
    public void showGameStatus(String body, String nickName, TurnType state){
        try {
            String message = MessageFactory.buildGameStatus(body,nickName,state);
            game.send(message,nickName);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }

    /**
     * this method is used to show an update of the marketBoard
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
            String message = MessageFactory.buildLeaderUpdate(cards,status,nickName, "update leader cards");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }


    /**
     * this method is used to show an update of one's FaithTrack
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
     * @param action is the ID of the top token
     */
    @Override
    public void showTopToken(Optional<String> action) {
        try {
            String message = MessageFactory.buildUpdateLorenzo(action.orElse(""),"token");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to show the points achieved at the end of the game
     * @param players contains the name of the players and the points obtained
     * @param winner contains the name of the winner
     */
    @Override
    public void showEndGame(Map<String, Integer> players, String winner) {
        try {
            String message = MessageFactory.buildEndGame(players, winner, "game ended");
            game.sendAll(message);
        }catch (MalformedMessageException e){e.printStackTrace();}
    }

    /**
     * this method is used to show the disconnection of a player
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
     * this method is used to catch the player's selected turn
     * @param turns  is the list of available turns
     * @param player is the nickname of the current player
     */
    @Override
    public void showAvailableTurns(List<String> turns, String player) {
       try {
            String message = MessageFactory.buildCurrentPlayer(player,turns,
                    "The current player is: " + player);
            game.sendAll(message);
        }catch (MalformedMessageException e){ e.printStackTrace();}
    }
}
