package it.polimi.ingsw;

import java.util.ArrayList;

/**
 * This class provides methods needed for managing leader cards.
 */
public class ManageLeader extends Turn{

    /**
     * This method allows to activate the leader card in position with number 'position'.
     * @param board the board of the player
     * @param position the position of the leader card
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void activateCard(Board board, int position) throws InvalidActionException {

        LeaderCard card;
        ArrayList<Integer> array = new ArrayList<>();
        ArrayList<ResQuantity> array1 = new ArrayList<>();
        try {
            card = board.getLeaderCard(position);
        }
        catch (IndexOutOfBoundsException e){throw new InvalidActionException(e.getMessage());}

        //if status == true no actions have to be done
        if(card.getStatus() == false){
           try {
               card.checkReq(board,array,array,array1);
           }
           catch (InvalidActionException e){throw e;}

           card.getSpecialEffect().applyEffect(board);
           card.setStatus(true);
       }
    }

    /**
     * This method allows to remove the leader card in position with number 'position'.
     * @param board the board of the player
     * @param position the position of the leader card
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void removeCard(Board board, int position) throws InvalidActionException, PlayerWonException {

        try{
            board.removeLeaderCard(position);
        }
        catch (IndexOutOfBoundsException e){throw new InvalidActionException(e.getMessage());}
    }
}
