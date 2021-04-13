package it.polimi.ingsw;

/**
 * Exception thrown when a player has won the game
 */
public class PlayerWonException extends Exception{

    /**
     * constructor without parameters
     */
    public PlayerWonException(){ super("A player won. The game is over"); }

    /**
     * constructor containing a message
     * @param message indicates that a player has won t
     */
    public PlayerWonException(String message){ super(message); }

}
