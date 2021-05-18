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
     * configuration file path
     */
    private final String file = "defaultConfiguration.xml";

    /**
     * It manages the game
     */
    private StateHandler stateHandler;

    /**
     * keeps track of the players part of the match (ID - nickname)
     */
    private Map<String, String> players;

    /**
     * keeps track of the connections associated to each player currently in the game
     */
    private Map<String, ClientConnection> connections;

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

    public Game(String nickname, String socketId, ClientConnection connection, int playersNumber, String id){

        players = new HashMap<>();
        connections = new HashMap<>();
        players.put(socketId, nickname);
        connections.put(nickname, connection);
        this.id = id;
        this.playersNumber = playersNumber;
        start = (playersNumber == 1);

    }

    //TODO: ELIMINARE GETTER E LE RELATIVE PRINT
    public Map<String, String> getPlayers() {
        return new HashMap<>(players);
    }

    /**
     *
     * @param socketId represents the socket's id
     * @return returns the nickname associated to the socket id
     */
    public String getNickname(String socketId){
        return players.get(socketId);
    }

    /**
     * sends the received message to the stateHandler
     * @param message represents the message to send
     * @param socketID represents the sender's socket id
     */
    public void onReceivedMessage(String message, String socketID){
        stateHandler.stateHandler(message, getNickname(socketID));
    }

    /**
     * sends a message to the selected player
     * @param message represents the message to send
     * @param nickname represents the message receiver
     */
    public void send(String message, String nickname){
        connections.get(nickname).send(message);
    }

    /**
     * sends a message to all the players
     * @param message represents the message to send
     */
    public void sendAll(String message){
        for(String player : connections.keySet()){
            connections.get(player).send(message);
        }
    }

    /**
     * adds a new player to the game
     * @param nickname represents the nickname of the added player
     * @param socketId represents the player's socket id
     * @param connection represents the player's socket connection
     */
    public void addPlayer(String nickname, String socketId, ClientConnection connection){
        /*
        if(start)
            stateHandler.reconnection(nickname);*/
        players.put(socketId, nickname);
        connections.put(nickname, connection);
        if(playersNumber == players.size()) start = true;

    }

    /**
     * removes a player from the game
     * @param socketId represents the removed player's socket id
     */
    public void removePlayer(String socketId){
        /*
        if(start){
            stateHandler.disconnection(players.get(socketId));
        }*/
        String nickname = players.get(socketId);

        connections.remove(nickname);
        players.remove(socketId);

    }

    /**
     *
     * @param socketId represents the id associated to the socket connection
     * @param nickname represents the player's nickname
     * @return returns true if the id corresponds to to the selected nickname
     */
    public boolean isCorresponding(String socketId, String nickname){
        return getNickname(socketId).equals(nickname);
    }

    /**
     * @param nickname represents the name of the player
     * @return returns true if the player is part of the match
     */
    public boolean containsPlayer(String nickname){
        return connections.containsKey(nickname);
    }

    /**
     *
     * @param socketId represents the socket's id
     * @return returns true if the player associated to the selected id is part of the match
     */
    public boolean containsID(String socketId){ return players.containsKey(socketId); }


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
     * ends the game sending each player the final message containing the final results
     * @param message represents the end game message
     */
    public void endGame(String message){

        System.out.println("[SERVER] Closing game...");
        sendAll(message);
        for(String nickname : connections.keySet()){
            connections.get(nickname).closeConnection();
        }
        System.out.println("[SERVER] Game closed correctly");

    }

    /**
     * sets up the game creating all the necessary components
     */
    public void setUpGame(){

        System.out.println("[SERVER] Creating a new game...");

        ArrayList<String> names = new ArrayList<>(players.keySet());
        GameBoardHandler gameBoard = new GameBoard(names, file);
        gameBoard.giveLeaderCards(file);
        View virtualView = new VirtualView(this,gameBoard);
        //gameBoard.attachView(virtualView);
        //this.stateHandler = new StateHandler(gameBoard, virtualView);

        System.out.println("[SERVER] Game is ready to start");

    }

}
