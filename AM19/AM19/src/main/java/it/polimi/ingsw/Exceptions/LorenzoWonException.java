package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exceptions of warehouse
 */
public class LorenzoWonException extends Exception {

    /**
     * constructor without parameters
     */
    public LorenzoWonException() {
        super("End of the game. Lorenzo won.");
    }

    /**
     * constructor
     * @param message is the message associated to the exception.
     */
    public LorenzoWonException(String message) {
        super(message);
    }
}
