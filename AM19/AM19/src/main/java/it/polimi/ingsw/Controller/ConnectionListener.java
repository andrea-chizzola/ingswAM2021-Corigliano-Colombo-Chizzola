package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Messages.Message;

public interface ConnectionListener {

    void onReceivedMessage(String message, String nickname);
}
