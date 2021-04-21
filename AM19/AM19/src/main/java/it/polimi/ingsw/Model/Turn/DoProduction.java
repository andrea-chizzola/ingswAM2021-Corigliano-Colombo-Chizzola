package it.polimi.ingsw.Model.Turn;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class provides methods needed for Activating the Production.
 */
public class DoProduction extends Turn{

    /**
     * This method allows to activate the production of all the productions passed as parameters.
     * @param board the board of the player
     * @param productions ArrayList of Production which represents all the productions the player wants to activate
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @throws InvalidActionException if the action is invalid
     */
    @Override
    public void doProduction(Board board, ArrayList<Production> productions, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException{

        Map<Resource,Integer> resourceStatus;
        try {
            resourceStatus = ResQuantity.createReqMap(board,shelves,quantity,strongbox);

            for(Production production : productions)
                production.checkProduction(resourceStatus);
        }
        catch (InvalidActionException e){throw e;}

        //It checks if the player has selected more resources than required, it is important because all the resources selected will be subtracted from the deposits
        if(resourceStatus.values().stream().mapToInt(Integer::intValue).sum() != 0)
            throw new InvalidActionException("Too many resources selected");

        ResQuantity.useResources(board, shelves, quantity, strongbox);

        for(Production production : productions)
            production.addProducts(board);
    }
}
