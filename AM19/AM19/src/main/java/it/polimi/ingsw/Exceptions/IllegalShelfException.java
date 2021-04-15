package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exceptions of warehouse
 */
public class IllegalShelfException extends Exception {

    /**
     * constructor without parameters
     */
    public IllegalShelfException() {
        super("Warehouse problem!");
    }

    /**
     * constructor
     * @param message
     */
    public IllegalShelfException(String message) {
        super(message);
    }
}
