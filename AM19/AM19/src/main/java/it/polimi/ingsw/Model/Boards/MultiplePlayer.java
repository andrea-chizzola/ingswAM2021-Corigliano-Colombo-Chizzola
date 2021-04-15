package it.polimi.ingsw.Model.Boards;

import it.polimi.ingsw.Exceptions.PlayerWonException;

/**
 * public class used to implement the endTurnAction method in case of a multiplayer match
 */
public class MultiplePlayer implements CustomMode{

    /**
     * sets the new current player
     * @param gameBoard represents the game board
     */
    @Override
    public void endTurnAction(GameBoard gameBoard) {

        Board currentPlayer = gameBoard.getCurrentPlayer();

        if(gameBoard.getPlayers().indexOf(currentPlayer) == gameBoard.getPlayers().size() - 1){
            gameBoard.setCurrentPlayer(gameBoard.getPlayers().get(0));
        }else gameBoard.setCurrentPlayer(gameBoard.getPlayers().get(gameBoard.getPlayers().indexOf(currentPlayer) + 1));

    }

    /**
     * adds faith to the other players when a resource is discarded
     * @param amount    indicates how many faith points have to be added
     * @param gameBoard represents the game board
     */
    @Override
    public void addFaithToOthers(int amount, GameBoard gameBoard) throws PlayerWonException {

        for(Board board : gameBoard.getPlayers()){

            if(board != gameBoard.getCurrentPlayer()){

                board.addFaith(amount);

            }

        }

    }


}
