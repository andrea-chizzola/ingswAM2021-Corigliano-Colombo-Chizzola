package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.VirtualView;
import it.polimi.ingsw.Model.Boards.GameBoardHandler;
import it.polimi.ingsw.Controller.StateHandler;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.View.View;

import java.util.*;

/**
 * public class used to handle the creation of a match
 */
public class Game {

    /**
     * server
     */
    private Server server;

    /**
     * configuration file path
     */
    private final String file = "defaultConfiguration.xml";

    /**
     * It manages the game
     */
    private StateHandler stateHandler;

    /**
     * keeps track of the players part of the match
     */
    private Map<String, String> players;

    /**
     * represents a unique id associated to the match
     */
    private final String id;

    /**
     * represents the number of players associated to the match
     */
    private final int playersNumber;

    /**
     * indicates if the game can start (game's size = players number)
     */
    private boolean start;

    public Game(Server server, String nickname, String socketID, int playersNumber, String id){

        this.server = server;
        players = new HashMap<>();
        players.put(nickname, socketID);
        this.id = id;
        this.playersNumber = playersNumber;
        start = (playersNumber == 1);

    }

    private String getNickName(String socketID){
        for(String nickName : players.keySet()){
            if(players.get(nickName).equals(socketID))
                return nickName;
        }
        return null;
    }

    public void onReceivedMessage(String message, String socketID){
        //if(getNickName(socketID) != null)
            //stateHandler.stateHandler(message, getNickName(socketID));
    }

    /**
     * sends a message to the selected player
     * @param message represents the message to send
     * @param nickname represents the message receiver
     */
    public void send(String message, String nickname){
        server.send(players.get(nickname),message);
    }

    /**
     * sends a message to all the players
     * @param message represents the message to send
     */
    public void sendAll(String message){
        for(Map.Entry<String, String> entry : players.entrySet()){
            server.send(entry.getValue(),message);
        }
    }

    /**
     * adds a new player to the game
     * @param nickname represents the nickname of the added player
     * @param socketID represents the player's socket connection
     */
    public void addPlayer(String nickname, String socketID){

        /*if(start)
            stateHandler.reconnection(nickname);
        players.put(nickname, socketID);
        if(playersNumber == players.size()) start = true;*/

    }


    /**
     * removes a player from the game
     * @param nickname represents the nickname of the removed player
     */
    public void removePlayer(String nickname){
        /*if(start){
            stateHandler.disconnection(nickname);
        }
        players.remove(nickname);*/ }

    /**
     * @param nickname represents the name of the player
     * @return returns true if the player is part of the match
     */
    public boolean containsPlayer(String nickname){
        return players.containsKey(nickname);
    }


    public boolean containsSocketID(String socketID){
        return players.containsValue(socketID);
    }

    /**
     *
     * @return returns the match id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return returns the number of players associated to the match
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     *
     * @return returns the number of players currently connected
     */
    public int getActualPlayers(){ return players.size(); }

    /**
     *
     * @return returns true if the game can start
     */
    public boolean canStart(){ return getActualPlayers() == playersNumber; }

    /**
     * sets up the game creating all the necessary components
     */
    public void setUpGame(){

        /*System.out.println("[SERVER] Creating a new game...");

        ArrayList<String> names = new ArrayList<>(players.keySet());
        GameBoardHandler gameBoard = new GameBoard(names, file);
        gameBoard.giveLeaderCards(file);
        View virtualView = new VirtualView(this,gameBoard);
        gameBoard.attachView(virtualView);
        this.stateHandler = new StateHandler(gameBoard, virtualView);

        System.out.println("[SERVER] Game is ready to start");

        return;*/

    }

}
