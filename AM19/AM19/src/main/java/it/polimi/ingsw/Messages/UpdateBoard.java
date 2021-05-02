package it.polimi.ingsw.Messages;

public abstract class UpdateBoard extends Message {

    /**
     * represents the possible update messages of shared items
     */
    public enum UpdateBoardType{BOX_UPDATE, LEADER, CARD_UPDATE}

    private UpdateBoardType updateBoardType;

    public UpdateBoard(String body, UpdateBoardType updateBoardType) {
        super(body, MessageType.UPDATE_BOARD);
        this.updateBoardType = updateBoardType;
    }
}
