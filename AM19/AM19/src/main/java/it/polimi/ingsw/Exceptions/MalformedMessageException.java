package it.polimi.ingsw.Exceptions;

public class MalformedMessageException extends Exception{

    public MalformedMessageException() {
        super("Malformed Message!");
    }

    public MalformedMessageException(String message) {
        super(message);
    }
}
