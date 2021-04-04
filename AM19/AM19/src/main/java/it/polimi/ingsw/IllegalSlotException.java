package it.polimi.ingsw;

/**
 * This class extends Exception and represents the exceptions of development card slots
 */
public class IllegalSlotException extends Exception {

    /**
     * constructor without parameters
     */
    public IllegalSlotException() {
        super("Slot problem!");
    }

    /**
     * constructor
     * @param message
     */
    public IllegalSlotException(String message) {
        super(message);
    }
}
