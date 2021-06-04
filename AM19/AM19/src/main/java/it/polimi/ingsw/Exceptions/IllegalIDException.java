package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exceptions for the ID of the cards
 */
public class IllegalIDException extends Exception{

    /**
     * constructor without parameters
     */
    public IllegalIDException() {
        super("Not existent ID!");
    }

    /**
     * constructor
     * @param message
     */
    public IllegalIDException(String message) {
        super(message);
    }
}
