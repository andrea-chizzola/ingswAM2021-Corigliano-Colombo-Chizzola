package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.MessageHandler;
import it.polimi.ingsw.Controller.VirtualView;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Boards.GameBoardHandler;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.View.View;

import java.util.*;
import java.util.stream.Collectors;

/**
 * public class used to handle the creation of a match
 */
public class Game {


    /**
     * configuration file path
     */
    private final String file = "defaultConfiguration.xml";

    private GamesHandler gamesHandler;

    /**
     * It manages the game
     */
    private MessageHandler messageHandler;

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

    public Game(GamesHandler gamesHandler, String nickname, String socketId, ClientConnection connection, int playersNumber, String id){

        this.gamesHandler = gamesHandler;
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
        messageHandler.messageHandler(message, getNickname(socketID));
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

        players.put(socketId, nickname);
        connections.put(nickname, connection);
        if(start) {
            try {
                send(MessageFactory.buildStartGame("Game is starting", new ArrayList<>(players.values())),nickname);
            } catch (MalformedMessageException e) {
                //TERMINA IL GAME
            }
            messageHandler.reconnection(nickname);
        }
        if(playersNumber == players.size()) start = true;

    }

    /**
     * removes a player from the game
     * @param socketId represents the removed player's socket id
     */
    public void removePlayer(String socketId){

        String nickname = players.get(socketId);

        connections.remove(nickname);
        players.remove(socketId);

        if(start){
            messageHandler.disconnection(nickname);
        }

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
     * ends the game closing the socket connections and removing it from the GamesHandler
     */
    public void endGame(){

        System.out.println("[SERVER] Closing game...");
        for(String nickname : connections.keySet()){
            connections.get(nickname).closeConnection();
        }
        gamesHandler.removeGame(getId(), new ArrayList<>(players.keySet()));
        System.out.println("[SERVER] Game " + getId() + " closed correctly");

    }

    /**
     * sets up the game creating all the necessary components
     */
    public void setUpGame(){

        System.out.println("[SERVER] Creating a new game...");

        ArrayList<String> names = new ArrayList<>(players.values());
        try {
            sendAll(MessageFactory.buildStartGame("Game is starting", names));
        } catch (MalformedMessageException e) {
            //TERMINA IL GAME
        }

        GameBoardHandler gameBoard = new GameBoard(names, file);

        View virtualView = new VirtualView(this);

        gameBoard.attachView(virtualView);
        gameBoard.initializeGame(file);

        this.messageHandler = new MessageHandler(gameBoard, virtualView);

        System.out.println("[SERVER] Game is ready to start");

    }

}
