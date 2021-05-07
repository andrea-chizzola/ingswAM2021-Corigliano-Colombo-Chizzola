package it.polimi.ingsw.Messages;

public class MessageConnection extends Message{

    /**
     * represents the nickname chosen by the player
     */
    private String nickname;

    /**
     * Indicates if the player wants to create a new game or join an existing one
     */
    private boolean gameHost;

    /**
     * indicates the number of players required for the new game
     */
    private int playersNumber;

    /**
     * creates a new message to create/join a game
     * @param nickName represents the name of the player who wants to create/join a game
     * @param gameHost indicates if the player wants to create a new game or join an existing one
     * @param playersNumber indicates the number of players required for the new game
     */
    public MessageConnection(String nickName, boolean gameHost, int playersNumber){

        super("Connection request", MessageType.CONNECTION);

        this.nickname = nickName;
        this.gameHost = gameHost;
        this.playersNumber = playersNumber;

    }

    /**
     * @return returns the nickname chosen by the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return returns true if the player wants to create a new game, false otherwise
     */
    public boolean isGameHost() {
        return gameHost;
    }

    /**
     * @return returns the number of players required for the new game
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString() {
        return "MessageConnection{" +
                "nickname='" + nickname + '\'' +
                ", gameHost=" + gameHost +
                ", playersNumber=" + playersNumber +
                "\n" +
                super.toString() +
                '}';
    }
}
