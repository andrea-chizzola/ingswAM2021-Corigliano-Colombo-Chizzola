package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exception thrown if a player does an invalid action.
 */
public class InvalidActionException extends Exception {

    /**
     * constructor without parameters
     */
    public InvalidActionException() {
        super("Invalid Action!");
    }

    /**
     * constructor
     * @param message
     */
    public InvalidActionException(String message) {
        super(message);
    }
}
