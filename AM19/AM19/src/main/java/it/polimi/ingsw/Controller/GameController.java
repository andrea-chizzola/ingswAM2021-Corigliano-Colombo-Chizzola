package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageUtilities;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Server.ConnectionListener;
import it.polimi.ingsw.Server.GamesHandler;
import it.polimi.ingsw.Server.Server;
import it.polimi.ingsw.View.View;


public class GameController implements ConnectionListener {


    Server server;
    GamesHandler gamesHandler;

    public GameController(Server server) {
        this.server = server;
        this.gamesHandler = new GamesHandler(server);

    }

    @Override
    public synchronized void onReceivedMessage(String message, String socketID) {
        gamesHandler.onReceivedMessage(message,socketID);
    }

    @Override
    public synchronized void onMissingPong(String socketID) {
        gamesHandler.manageDisconnection(socketID);
    }
}
