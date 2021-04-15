package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exceptions of the Market
 */
public class MarbleWhiteException extends Exception {

    /**
     * constructor without parameters
     */
    public MarbleWhiteException() {
        super("The Marble is white!");
    }

    /**
     * constructor
     * @param message is the string returned as a message by the exception
     */
    public MarbleWhiteException(String message) {
        super(message);
    }
}
