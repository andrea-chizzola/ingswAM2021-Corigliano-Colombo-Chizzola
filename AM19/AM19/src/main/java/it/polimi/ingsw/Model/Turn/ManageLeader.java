package it.polimi.ingsw.Model.Turn;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.ResourcesExpectedException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.Resources.ResQuantity;

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

        try {
            card = board.getLeaderCard(position);
        }
        catch (IndexOutOfBoundsException e){throw new InvalidActionException(e.getMessage());}

        //if status == true no actions have to be done
        if(card.getStatus() == false){
           try {
               card.checkReq(board);
           }
           catch (InvalidActionException | ResourcesExpectedException e){throw new InvalidActionException(e.getMessage());}

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
    public void removeCard(Board board, int position) throws InvalidActionException {

        try{
            board.removeLeaderCard(position);
        }
        catch (IndexOutOfBoundsException e){throw new InvalidActionException(e.getMessage());}
    }
}
