package it.polimi.ingsw;

import java.util.ArrayList;

/**
 * interface containing the necessary methods for managing the methods to buy a card
 */
public interface Requirements {

    /**
     * This method checks if the requirements of the card are met, otherwise an InvalidActionException is thrown.
     * @param board is the board on which we are checking the requisites
     * @return true if the player's board satisfy the requirements of the card
     * @throws InvalidActionException if the requirements are not satisfied
     * @throws ResourcesExpectedException if the card needs more information to check the requirement.
     */
    boolean checkReq(Board board) throws InvalidActionException, ResourcesExpectedException;


    boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox)
            throws InvalidActionException;
}
