package it.polimi.ingsw.Messages;

public class MessageCurrentPlayer extends  Message{

    /**
     * represents the progressive number associated to the current player
     */
    private int currentPlayer;

    /**
     * creates a new message indicating who is the current player
     * @param nickName represents the nickname of the current player
     * @param currentPlayer represents the progressive number associated to the current player
     */
    MessageCurrentPlayer(String nickName, int currentPlayer){

        super(nickName, MessageType.GAME_STATUS);
        this.currentPlayer = currentPlayer;

    }

    /**
     * @return returns the progressive number associated to the current player
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "MessageCurrentPlayer{" +
                "currentPlayer=" + currentPlayer +
                "\n" +
                super.toString() +
                '}';
    }
}
