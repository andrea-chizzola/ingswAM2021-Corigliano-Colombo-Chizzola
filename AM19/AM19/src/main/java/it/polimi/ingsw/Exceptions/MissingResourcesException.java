package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception.
 * It represents an exception that is thrown by the market
 * when resources are missing
 */
public class MissingResourcesException extends Exception {

    /**
     * constructor without parameters
     */
    public MissingResourcesException() {
        super("The card has to check the status of the StrongBox and the Warehouse!");
    }

    /**
     * constructor
     * @param message
     */
    public MissingResourcesException(String message) {
        super(message);
    }
}