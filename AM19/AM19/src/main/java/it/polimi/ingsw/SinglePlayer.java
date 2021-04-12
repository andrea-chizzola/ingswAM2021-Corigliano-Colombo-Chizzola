package it.polimi.ingsw;

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
     * @param trackPoints represents Lorenzo's faith track points
     * @param sections represents the sections of Lorenzo's faith track
     * @param actions contains the action tokens
     */
    public SinglePlayer(GameBoard gameBoard, ArrayList<Integer> trackPoints, ArrayList<VaticanReportSection> sections, ArrayList<Action> actions) {

        lorenzoTrack = new FaithTrack(trackPoints, sections);
        actionTokenDeck = new ActionTokenDeck(actions);
        this.gameBoard = gameBoard;

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

        if(lorenzoTrack.isEndTrack()){

            throw new LorenzoWonException();

        }

        gameBoard.checkStartVaticanReport(lorenzoTrack);

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
        actionTokenDeck.changeList();

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
