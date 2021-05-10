package it.polimi.ingsw.Messages;

public class PongMessage extends Message{

    /**
     * creates a new pong message
     */
    public PongMessage() {
        super("pong", MessageType.PONG);
    }

    @Override
    public String toString() {
        return "PongMessage:" + "\n" +
                super.toString();
    }
}
