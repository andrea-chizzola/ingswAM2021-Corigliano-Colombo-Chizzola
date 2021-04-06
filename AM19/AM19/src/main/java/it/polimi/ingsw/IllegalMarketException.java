package it.polimi.ingsw;

/**
 * This class extends Exception and represents the exceptions of the Market
 */
public class IllegalMarketException extends Exception {

    /**
     * constructor without parameters
     */
    public IllegalMarketException() {
        super("Market problem!");
    }

    /**
     * constructor
     * @param message
     */
    public IllegalMarketException(String message) {
        super(message);
    }
}
