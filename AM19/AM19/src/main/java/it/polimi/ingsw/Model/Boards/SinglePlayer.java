package it.polimi.ingsw.Model.Boards;
import it.polimi.ingsw.Messages.Enumerations.*;
import it.polimi.ingsw.Model.Decks.ActionTokenDeck;
import it.polimi.ingsw.Model.Boards.FaithTrack.FaithTrack;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.*;

/**
 * public class used to implement the endTurnAction method along with the necessary methods in case of a single player match
 */
public class SinglePlayer implements CustomMode{

    /**
     * represents Lorenzo's faith track
     */
    private FaithTrack lorenzoTrack;

    /**
     * represents the game board
     */
    private GameBoard gameBoard;

    /**
     * represents the deck containing all the action tokens
     */
    private ActionTokenDeck actionTokenDeck;

    private boolean initialization;

    /**
     * creates all the necessary attributes for a single player match
     * @param gameBoard represents the game board
     * @param file is the file that contains the configurations
     */
    public SinglePlayer(GameBoard gameBoard, String file) {

        lorenzoTrack = ConfigurationParser.parseFaithTrack(file);
        actionTokenDeck = new ActionTokenDeck(file);
        this.gameBoard = gameBoard;
        initialization = true;
    }

    /**
     * @return returns Lorenzo's faith track
     */
    public FaithTrack getLorenzoTrack() {
        return lorenzoTrack;
    }

    /**
     * @return returns the game board
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * @return returns the action token deck
     */
    public ActionTokenDeck getActionTokenDeck() {
        return actionTokenDeck;
    }

    /**
     * moves Lorenzo's black cross ahead in the faith track
     * @param amount indicates how many positions the black cross has to be moved ahead
     */
    public void moveBlackCross(int amount){

        lorenzoTrack.addFaith(amount);

        gameBoard.checkStartVaticanReport(lorenzoTrack);

        if(lorenzoTrack.isEndTrack()){

            gameBoard.setEndGameStarted(true);

        }

    }


    /**
     * manages Lorenzo's actions associated to the picked token
     * @param gameBoard represents the game board
     */
    @Override
    public void endTurnAction(GameBoard gameBoard){

        if(!initialization)
            actionTokenDeck.getTop().doAction(this);
        initialization = false;
        if(gameBoard.isEndGameStarted())
           gameBoard.setGameEnded();
    }

    /**
     * adds faith to Lorenzo when a resource is discarded
     * @param amount    indicates how many faith points have to be added
     * @param gameBoard represents the game board
     */
    @Override
    public void addFaithToOthers(int amount, GameBoard gameBoard){

        moveBlackCross(amount);

    }

    /**
     * @param boards contains the boards related to the single player
     * @return returns the message showed to the player when the match is over
     */
    @Override
    public Map<String,Integer> findWinnerMessage(ArrayList<Board> boards) {

        Map<String,Integer> map = new HashMap<>();
        map.put(boards.get(0).getNickname(),boards.get(0).getTotalPoints());

        int cardNumber = 0;

        for(Slot slot : boards.get(0).getSlots()){
            cardNumber = cardNumber + slot.getNumberOfCards();
        }

        if(cardNumber >= 7 || boards.get(0).getFaithTrack().isEndTrack()) {
            map.put("Lorenzo", 0);
            return map;
        }

        map.put("Lorenzo", 9999);
        return map;
    }


    @Override
    public Optional<Integer> showFaithLorenzo() {
        return Optional.of(lorenzoTrack.getPosition());
    }

    @Override
    public Optional<List<ItemStatus>> showSectionsLorenzo() {
        return Optional.of(gameBoard.createListItems(lorenzoTrack.getSections()));
    }

    @Override
    public Optional<String> showTopToken() {
        return Optional.of(actionTokenDeck.getTop().getId());
    }
}
