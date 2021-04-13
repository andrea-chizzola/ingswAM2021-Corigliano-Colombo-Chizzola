package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.ActionTokenParser;
import it.polimi.ingsw.xmlParser.FaithTrackParser;

import java.util.ArrayList;

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

    /**
     * creates all the necessary attributes for a single player match
     * @param gameBoard represents the game board
     * @param faithTrackFile is the file that contains the characteristics of Lorenzo's FaithTrack
     * @param actionsFile is the file that contains Lorenzo's ActionTokens
     */
    public SinglePlayer(GameBoard gameBoard, String faithTrackFile, String actionsFile) {

        lorenzoTrack = FaithTrackParser.buildFaithTrack(faithTrackFile);
        actionTokenDeck = new ActionTokenDeck(actionsFile);
        this.gameBoard = gameBoard;

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
    public void moveBlackCross(int amount) throws LorenzoWonException{

        lorenzoTrack.addFaith(amount);

        gameBoard.checkStartVaticanReport(lorenzoTrack);

        if(lorenzoTrack.isEndTrack()){

            throw new LorenzoWonException();

        }

    }


    /**
     * manages Lorenzo's actions associated to the picked token
     * @param gameBoard represents the game board
     */
    @Override
    public void endTurnAction(GameBoard gameBoard) throws LorenzoWonException {

        try {

            actionTokenDeck.getTop().doAction(this);

        }catch(LorenzoWonException e){

            throw new LorenzoWonException(e.getMessage());

        }

    }

    /**
     * adds faith to Lorenzo when a resource is discarded
     * @param amount    indicates how many faith points have to be added
     * @param gameBoard represents the game board
     */
    @Override
    public void addFaithToOthers(int amount, GameBoard gameBoard) throws LorenzoWonException {

        try {

            moveBlackCross(amount);

        }catch(LorenzoWonException e){

            throw new LorenzoWonException(e.getMessage());

        }

    }

}
