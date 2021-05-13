package it.polimi.ingsw.Controller;

public interface ConnectionListener {

    public  void onReceivedMessage(String message, String nickname);

    public  void onMissingPong(String nickname);

}
