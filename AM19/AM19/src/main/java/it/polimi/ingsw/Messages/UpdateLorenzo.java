package it.polimi.ingsw.Messages;

public class UpdateLorenzo extends UpdateGame{

    /**
     * this attribute represents the faith obtained by Lorenzo
     */
    private int faith;

    /**
     * this attribute represents the ID of the top ActionToken on the GameBoard
     */
    private String token;

    /**
     * this method is the constructor of the class
     * @param faith is the faith of Lorenzo
     * @param token is the ID of the top token on the GameBoard
     */
    public UpdateLorenzo(int faith, String token) {
        super("Update of the top ActionToken and of Lorenzo's faith", UpdateGameType.LORENZO);

        this.faith = faith;
        this.token = token;
    }

    /**
     * @return the faith obtained by Lorenzo
     */
    public int getLorenzoFaith(){
        return faith;
    }

    /**
     * @return the ID of the top action token in the GameBoard
     */
    public String getTopToken(){
        return token;
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        return "Message{" +
                "body='" + getBody() + '\'' +
                "LorenzoFaith" + faith + '\'' +
                "TopToken='" + token + '\'' +
                ", messageType=" + getMessageType() +
                '}';
    }
}
