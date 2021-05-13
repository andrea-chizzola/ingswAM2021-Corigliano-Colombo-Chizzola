package it.polimi.ingsw.Server;

import java.util.*;

/**
 * public class used to handle the creation of a match
 */
public class Game {

    /**
     * keeps track of the players part of the match
     */
    private Map<String, ClientConnection> players;

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

    public Game(String nickname, ClientConnection connection, int playersNumber, String id){

        players = new HashMap<>();
        players.put(nickname, connection);
        this.id = id;
        this.playersNumber = playersNumber;
        start = playersNumber == 1;

    }

    /**
     * sends a message to the selected player
     * @param message represents the message to send
     * @param nickname represents the message receiver
     */
    public void send(String message, String nickname){
        players.get(nickname).send(message);
    }

    /**
     * sends a message to all the players
     * @param message represents the message to send
     */
    public void sendAll(String message){
        for(Map.Entry<String, ClientConnection> entry : players.entrySet()){
            entry.getValue().send(message);
        }
    }

    /**
     * adds a new player to the game
     * @param nickname represents the nickname of the added player
     * @param connection represents the player's socket connection
     */
    public void addPlayer(String nickname, ClientConnection connection){

        players.put(nickname, connection);
        if(playersNumber == players.size()) start = true;

    }

    /**
     *
     * @param nickname represents the player's nickname
     * @return returns the socket connection related to the selected player
     */
    public ClientConnection getPlayerConnection(String nickname){
        return players.get(nickname);
    }

    /**
     * removes a player from the game
     * @param nickname represents the nickname of the removed player
     */
    public void removePlayer(String nickname){ players.remove(nickname); }

    /**
     * @param nickname represents the name of the player
     * @return returns true if the player is part of the match
     */
    public boolean containsPlayer(String nickname){
        return players.containsKey(nickname);
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

        System.out.println("[SERVER] Creating a new game...");

        List<String> names = new ArrayList<>(players.keySet());
        //GameBoard gameBoard = new GameBoard(names, file);       message connection ha bisogno di un file?
        //Controller controller = new Controller();
        //View view = new View();
        List<ClientConnection> connections = new ArrayList<>(players.values());

        for(int i = 0; i < connections.size(); i++){

            //creo una virtual view per giocatore?
            //connections.get(i).addListener(view);
            //views.add(view);  solo se view ha metodo per creare un giocatore
            //gameBoard.addListener(view);
            //view.addListener(controller);

        }

        //ciclo for che per ogni view fa qualcosa per giocatore

        System.out.println("[SERVER] Game is ready to start");

        //sendAll(new UpdateGame());
        //sendAll(new UpdateLeaderCards());
        //message currentplayer a tutti o ne creo uno apposta per ciascuno? o dice il controller cosa mandare?

    }

}
