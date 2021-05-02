package it.polimi.ingsw.Messages;

public abstract class UpdateGame extends Message{

    /**
     * represents the possible update messages of shared items
     */
    public enum UpdateGameType {MARKET, FAITH, LORENZO, DECK}

    private UpdateGameType updateGameType;

    public UpdateGame(String body, UpdateGameType updateGameType) {
        super(body, MessageType.UPDATE_GAME);
        this.updateGameType = updateGameType;
    }
}
