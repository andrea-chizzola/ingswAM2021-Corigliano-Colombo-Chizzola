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
    public String findWinnerMessage(ArrayList<Board> boards) {

        String result = new String("Final Leaderboard: \n");

        Collections.sort(boards, (Board b1, Board b2) -> {
            if(b1.getTotalPoints() < b2.getTotalPoints()) return -1;
            else if(b1.getTotalPoints() > b2.getTotalPoints()) return 1;
            else if(b1.getTotalPoints() == b2.getTotalPoints() && b1.getTotalResources() < b2.getTotalResources()) return -1;
            else if(b1.getTotalPoints() == b2.getTotalPoints() && b1.getTotalResources() > b2.getTotalResources()) return 1;
            else return 0;
        });

        for(int i = boards.size() - 1; i >= 0; i--){

            result = result + (boards.size() - i) + " " + boards.get(i).getNickname() + " " + boards.get(i).getTotalPoints() + "\n";

        }

        return result;

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
