package it.polimi.ingsw.Messages;

public class PingMessage extends Message{

    /**
     * creates a new ping message
     */
    public PingMessage() {
        super("ping", MessageType.PING);
    }

    @Override
    public String toString() {
        return "PingMessage:" + "\n" +
                super.toString();
    }
}
