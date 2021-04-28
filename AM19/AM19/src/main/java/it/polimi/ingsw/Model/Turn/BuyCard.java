package it.polimi.ingsw.Model.Turn;

import it.polimi.ingsw.Exceptions.IllegalSlotException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.ResourcesExpectedException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Boards.Slot;
import it.polimi.ingsw.Model.Cards.Colors.CardColor;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.ArrayList;

/**
 * This class provides methods needed for Buying one Development Card.
 */
public class BuyCard extends Turn{


    /**
     * This method allows to buy the development card with color 'color' and level 'level' on the top of the deck
     * and it allows to add that card in the slot with number 'slot'
     * @param board the board of the player
     * @param slot the number of the slot in which the player wants to add the card
     * @param color the color of the card the player wants to add
     * @param level the level of the card the player wants to add
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void buyCard(Board board, int slot, CardColor color, int level, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException {
        Slot slot1;
        DevelopmentCard card;
        try {
            card = board.getGameBoard().getDevelopmentDeck().readTop(color,level);
            card.checkReq(board,shelves,quantity,strongbox);
        }catch (IllegalArgumentException e){throw new InvalidActionException(e.getMessage());}

        try {
            slot1 = board.getSlot(slot);
        }
        catch (IllegalSlotException e){throw new InvalidActionException(e.getMessage());}
        try {
            slot1.insertCard(card);
            board.checkDevCard(card.getCardColor());
        }
        catch (IllegalSlotException e){throw new InvalidActionException(e.getMessage());}
        try {
            board.getGameBoard().getDevelopmentDeck().getTop(color, level);
        }catch (IllegalArgumentException e){throw new InvalidActionException("Problem!");}

        ResQuantity.useResources(board,shelves,quantity,strongbox);

    }
}
