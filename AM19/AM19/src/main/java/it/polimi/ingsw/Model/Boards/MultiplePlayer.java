package it.polimi.ingsw.Model.Boards;


import it.polimi.ingsw.Messages.Enumerations.*;

import java.util.*;

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
            if(gameBoard.isEndGameStarted())
               gameBoard.setGameEnded();
        }else gameBoard.setCurrentPlayer(gameBoard.getPlayers().get(gameBoard.getPlayers().indexOf(currentPlayer) + 1));

    }

    /**
     * adds faith to the other players when a resource is discarded
     * @param amount    indicates how many faith points have to be added
     * @param gameBoard represents the game board
     */
    @Override
    public void addFaithToOthers(int amount, GameBoard gameBoard){

        for(Board board : gameBoard.getPlayers()){

            if(board != gameBoard.getCurrentPlayer()){

                board.addFaith(amount);

            }

        }

    }

    /**
     * @param boards contains the board of each player
     * @return returns the message showed to the players when the match is over
     */
    @Override
    public Map<String,Integer> findWinnerMessage(ArrayList<Board> boards) {

        Map<String,Integer> map = new HashMap<>();

        for(Board board : boards){
            map.put(board.getNickname(),board.getTotalPoints());
        }

        return map;
    }

    @Override
    public Optional<Integer> showFaithLorenzo() {
        return Optional.empty();
    }

    @Override
    public Optional<List<ItemStatus>> showSectionsLorenzo() {
        return Optional.empty();
    }

    @Override
    public Optional<String> showTopToken() {
        return Optional.empty();
    }
}
