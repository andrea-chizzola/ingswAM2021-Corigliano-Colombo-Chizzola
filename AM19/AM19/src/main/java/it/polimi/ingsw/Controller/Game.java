package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageFactory;
import it.polimi.ingsw.Model.Boards.GameBoardHandler;
import it.polimi.ingsw.Model.Boards.GameBoard;
import it.polimi.ingsw.Server.ClientConnectionHandler;
import it.polimi.ingsw.View.Update;

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
     * represents the game handler
     */
    private final GamesHandler gamesHandler;

    /**
     * It manages the game
     */
    private MessageHandler messageHandler;

    /**
     * keeps track of the players part of the match (ID - nickname)
     */
    private final Map<String, String> activePlayers;

    /**
     * keeps track of the connections associated to each player currently in the game
     */
    private final Map<String, ClientConnectionHandler> connections;

    /**
     * contains all the players associated to the game
     */
    private final ArrayList<String> nicknames;

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

    /**
     * creates a new game
     * @param gamesHandler represents the games handler
     * @param nickname represents the nickname of the player who created the game
     * @param socketId represents the id related to the socket connection of the player who created the game
     * @param connection represents the connection to the client who created the game
     * @param playersNumber indicates the number of player associated to the game
     * @param id represents the game's id
     */
    public Game(GamesHandler gamesHandler, String nickname, String socketId, ClientConnectionHandler connection, int playersNumber, String id){

        this.gamesHandler = gamesHandler;
        activePlayers = new HashMap<>();
        connections = new HashMap<>();
        activePlayers.put(socketId, nickname);
        connections.put(nickname, connection);
        nicknames = new ArrayList<>();
        this.id = id;
        this.playersNumber = playersNumber;
        start = (playersNumber == 1);

    }

    /**
     *
     * @return a map containing the players currently in the game and their related ID
     */
    public Map<String, String> getPlayers() {
        return new HashMap<>(activePlayers);
    }

    /**
     *
     * @param socketId represents the socket's id
     * @return returns the nickname associated to the socket id
     */
    public String getNickname(String socketId){
        return activePlayers.get(socketId);
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
    public void addPlayer(String nickname, String socketId, ClientConnectionHandler connection){

        activePlayers.put(socketId, nickname);
        connections.put(nickname, connection);
        if(start) {
            try {
                send(MessageFactory.buildStartGame("Game is starting", nicknames), nickname);
            } catch (MalformedMessageException e) {
                System.out.println("[SERVER] Cannot create the XML message using DOM library");
                closeGame();
            }
            messageHandler.reconnection(nickname);
        }
        if(playersNumber == activePlayers.size()) start = true;

    }

    /**
     * removes a player from the game
     * @param socketId represents the removed player's socket id
     */
    public void removePlayer(String socketId){

        String nickname = activePlayers.get(socketId);

        connections.remove(nickname);
        activePlayers.remove(socketId);

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
    public boolean containsID(String socketId){ return activePlayers.containsKey(socketId); }

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
    public int getActualPlayers(){ return activePlayers.size(); }

    /**
     *
     * @return returns true if the game can start
     */
    public boolean canStart(){ return getActualPlayers() == playersNumber; }

    /**
     * sets up the game creating all the necessary components
     */
    public void setUpGame(Boolean cheat){

        System.out.println("[SERVER] Creating a new game...");

        nicknames.addAll(activePlayers.values());
        try {
            sendAll(MessageFactory.buildStartGame("Game is starting", nicknames));
        } catch (MalformedMessageException e) {
            System.out.println("[SERVER] Cannot create the XML message using DOM library");
            closeGame();
        }

        GameBoardHandler gameBoard = new GameBoard(nicknames, file);

        Update virtualView = new VirtualView(this);

        gameBoard.attachView(virtualView);
        gameBoard.initializeGame(file);

        if(cheat) gameBoard.cheatStrongbox();

        this.messageHandler = new MessageHandler(gameBoard, virtualView);

        System.out.println("[SERVER] Game is ready to start");

    }

    /**
     * closes the game
     */
    private void closeGame(){

        for(String player : connections.keySet()){
            connections.get(player).closeConnection();
        }
        gamesHandler.removeGame(getId(), new ArrayList<>(activePlayers.keySet()));

    }

    /**
     * Notifies a parsing error of a received message
     * @param socketId represents the id associated to the connection
     */
    public void notifyParsingError(String socketId){
        String name = activePlayers.get(socketId);
        try {
            send(MessageFactory.buildReply(false, "You've sent a malformed XML message", name), name);
        } catch (MalformedMessageException e) {
            System.out.println("[SERVER] Cannot create the XML message using DOM library");
            closeGame();
        }
    }

}
