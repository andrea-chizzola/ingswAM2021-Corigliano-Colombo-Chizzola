package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exceptions of the Market
 */
public class ResourcesExpectedException extends Exception {

    /**
     * constructor without parameters
     */
    public ResourcesExpectedException() {
        super("The card has to check the status of the StrongBox and the Warehouse!");
    }

    /**
     * constructor
     * @param message
     */
    public ResourcesExpectedException(String message) {
        super(message);
    }
}