package it.polimi.ingsw.Exceptions;

/**
 * This class extends Exception and represents the exceptions of the NetworkBuffer
 */
public class EmptyBufferException extends Exception {

    /**
     * constructor without parameters
     */
    public EmptyBufferException() {
        super("The Buffer is Empty!");
    }

    /**
     * constructor with a parameter message
     * @param message is the message attribute of the exception
     */
    public EmptyBufferException(String message) {
        super(message);
    }
}
