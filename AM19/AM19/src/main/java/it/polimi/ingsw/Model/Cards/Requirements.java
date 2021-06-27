package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.MissingResourcesException;
import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * interface containing the necessary methods for managing the methods to buy a card
 */
public interface Requirements {

    /**
     * This method checks if the requirements of the card are met, otherwise an InvalidActionException is thrown.
     * @param board is the board on which we are checking the requisites
     * @return true if the player's board meets the requirements of the card
     * @throws InvalidActionException if the requirements are not met
     * @throws MissingResourcesException if the card needs more information to check the requirement.
     */
    boolean checkReq(Board board) throws InvalidActionException, MissingResourcesException;

    /**
     * This method checks if the requirements of the card are met, otherwise an InvalidActionException is thrown.
     * @param board is the board on which we are checking the requisites
     * @param shelves ArrayList of Integer which represents all the shelves selected
     * @param quantity ArrayList of Integer which represents the amount of resources selected for each shelf
     * @param strongbox ArrayList of ResQuantity which represents all the resources selected from the strongBox
     *                  It is important that each resource is present at most once
     * @return true if the player's board meets the requirements of the card
     * @throws InvalidActionException if the requirements are not satisfied
     */
    boolean checkReq(Board board, ArrayList<Integer> shelves, ArrayList<Integer> quantity, ArrayList<ResQuantity> strongbox) throws InvalidActionException;

    /**
     * @return HasMap(Resource,Integer) with all the resources present in the requirements
     */
    HashMap<Resource,Integer> getRequirements();

    /**
     * @return LinkedList of CardQuantity with all the cards present in the requirements
     */
    LinkedList<CardQuantity> getCardRequirements();
}
