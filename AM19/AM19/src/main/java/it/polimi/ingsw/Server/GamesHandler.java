package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class GamesHandler implements ConnectionListener{

    /**
     * keeps track of the active connections associating them to a unique ID
     */
    private Map<String, ClientConnection> activeConnections;

    /**
     * keeps track of the games waiting for other players to start
     */
    private List<Game> waitingConnection;

    /**
     * keeps track of the active games
     */
    private List<Game> activeGames;

    /**
     * contains the nicknames of disconnected players and the related game id (nickname - gameId)
     */
    private Map<String, String> inactivePlayers;

    /**
     * keeps track of the single player games that have been suspended as the player disconnected
     */
    private List<Game> suspendedGames;

    /**
     * represents a progressive number associated to a game
     */
    private AtomicLong idCounter;

    /**
     * creates a new server accepting clients to the port selected
     */
    public GamesHandler() {

        activeConnections = new HashMap<>();
        waitingConnection = new ArrayList<>();
        activeGames = new ArrayList<>();
        inactivePlayers = new HashMap<>();
        suspendedGames = new ArrayList<>();
        idCounter = new AtomicLong();

    }

    /**
     *
     * @return returns a new Id associated to the client's connection
     */
    private String createId(){
        return String.valueOf(idCounter.getAndIncrement());
    }

    /**
     *
     * @param gameId represents the game id
     * @return returns the game related to the selected id
     */
    private Game getGameById(String gameId){

        for(Game game : waitingConnection){
            if(game.getId().equals(gameId)){
                return game;
            }
        }
        for(Game game : activeGames){
            if(game.getId().equals(gameId)){
                return game;
            }
        }
        for(Game game : suspendedGames){
            if(game.getId().equals(gameId)){
                return game;
            }
        }
        return null;

    }

    /**
     *
     * @param socketID represents the id associated to the connection
     * @return returns the game containing the player associated to the selected connection id
     */
    private Game getGameBySocketID(String socketID){


        for(Game game : waitingConnection){
            if(game.containsID(socketID)){
                return game;
            }
        }
        for(Game game : activeGames){
            if(game.containsID(socketID)){
                return game;
            }
        }
        return null;

    }


    /**
     * adds the player to the disconnected ones
     * @param nickname represents the player's nickname
     * @param gameId represents the game id
     */
    private void addInactivePlayer(String nickname, String gameId){
        inactivePlayers.put(nickname, gameId);
    }

    /**
     * removes the inactive players related to a deleted match
     * @param gameId represents the deleted match
     */
    private void clearInactivePlayers(String gameId){

        List<String> removed = new ArrayList<>();
        for(String nickname : inactivePlayers.keySet()){
            if(inactivePlayers.get(nickname).equals(gameId)){
                removed.add(nickname);
            }
        }
        for(String nickname : removed){
            inactivePlayers.remove(nickname);
        }

    }

    /**
     * adds a game to the suspended ones
     * @param game represents the game to suspend
     */
    private void addSuspendedGame(Game game){
        suspendedGames.add(game);
    }

    /**
     * removes a game from the suspended ones
     * @param game represents the game to remove
     */
    private void removeSuspendedGame(Game game){
        suspendedGames.remove(game);
    }

    /**
     * adds a new game to the ones waiting for other players to start
     * @param game represents the new game added
     */
    private void addWaitingGame(Game game){
        waitingConnection.add(game);
    }

    /**
     * adds a game to the active ones
     * @param game represents the game added
     */
    private void addActiveGame(Game game){
        activeGames.add(game);
    }

    /**
     * removes the connection associated to the selected id
     * @param socketID represents the id related to the socket connection
     */
    private void removeActiveConnection(String socketID){
        activeConnections.remove(socketID);
    }

    /**
     *
     * @param socketId represents the id related to the socket connection
     * @return returns the connection associated to the selected id
     */
    private ClientConnection getConnection(String socketId){
        return activeConnections.get(socketId);
    }


    /**
     *
     * @param nickname represents the selected nickname
     * @return returns true if the selected nickname is currently available
     */
    private boolean isNicknameAvailable(String nickname){

        if(inactivePlayers.containsKey(nickname)) return false;

        for (Game game : waitingConnection) {
            if (game.containsPlayer(nickname)) {
                return false;
            }
        }

        for (Game activeGame : activeGames) {
            if (activeGame.containsPlayer(nickname)) {
                return false;
            }
        }

        return true;

    }

    /**
     * checks if the selected nickname is an empty string
     * @param nickname represents the selected nickname
     * @throws MalformedMessageException if the nickname equals an empty string
     */
    private void checkNickname(String nickname) throws MalformedMessageException {
        if(nickname.equals("")) throw new MalformedMessageException();
    }

    /**
     * checks if the selected players number is correct
     * @param gameHost indicates if the player wants to create a new game
     * @param playersNumber represents the selected number of players
     * @throws MalformedMessageException if the selected parameters are not correct
     */
    private void checkConnectionParameters(boolean gameHost, int playersNumber) throws MalformedMessageException {
        if(gameHost && !(playersNumber > 0 && playersNumber <= 4)) throw new MalformedMessageException();
    }

    /**
     *
     * @param socketId represents the socket's id
     * @return returns true if the player associated to the selected id is currently in a started game
     */
    private boolean isPlaying(String socketId){

        for(Game game : activeGames){
            if(game.containsID(socketId)) return true;
        }

        return false;
    }

    /**
     *
     * @param socketId represents the socket's id
     * @return returns true if the player associated to the selected id is currently waiting for a game to start
     */
    private boolean isWaiting(String socketId){

        for(Game game : waitingConnection) {
            if (game.containsID(socketId)) return true;
        }

        return false;
    }

    /**
     * removes the game when over
     * @param gameId represents the id of the game to remove
     * @param playersId contains the id of the players in the selected game
     */
    public void removeGame(String gameId, ArrayList<String> playersId){

        clearInactivePlayers(gameId);

        for(String id : playersId){
            removeActiveConnection(id);
        }

        List<Game> removed = new ArrayList<>();
        for(Game game : activeGames){
            if(game.getId().equals(gameId)){
                removed.add(game);
            }
        }
        activeGames.removeAll(removed);

        for(Game game : suspendedGames){
            if(game.getId().equals(gameId)){
                removed.add(game);
            }
        }
        suspendedGames.removeAll(removed);

    }

    /**
     *
     * @param playersNumber indicates the number of player inside the game
     * @return the type of the game created
     */
    private String getGameType(int playersNumber){
        if(playersNumber == 1)
            return "single player";
        else
            return "multiplayer";
    }

    /**
     * adds a new connection to the active ones
     * @param socketId represents the id associated to the connection
     * @param connection represents the client's socket connection
     */
    @Override
    public synchronized void addActiveConnection(String socketId, ClientConnection connection){
        activeConnections.put(socketId, connection);
    }

    /**
     * Notifies the listener that a player did not answer to the ping message
     *
     * @param socketId represents the player's socket id
     */
    @Override
    public synchronized void onMissingPong(String socketId) {

        if(isPlaying(socketId) || isWaiting(socketId)) {

            Game game = getGameBySocketID(socketId);
            manageDisconnection(game.getNickname(socketId), socketId);

        }else{

            if (activeConnections.containsKey(socketId)) {
                getConnection(socketId).closeConnection();
                removeActiveConnection(socketId);
            }

        }

    }

    /**
     * manages the received message in relation to it's type
     * @param message represents the received message
     * @param socketID represents the id related to the socket connection
     */
    @Override
    public synchronized void onReceivedMessage(String message, String socketID){

        try {

            switch (MessageUtilities.instance().getType(message)) {

                case CONNECTION:
                    ConnectionMessage connectionMessage = new ConnectionMessage(message, Message.MessageType.CONNECTION);
                    String connectionNickname = connectionMessage.getNickname();
                    boolean gameHost = connectionMessage.getGameHost();
                    int playersNumber = connectionMessage.getPlayersNumber();
                    checkNickname(connectionNickname);
                    checkConnectionParameters(gameHost, playersNumber);
                    manageLobby(connectionNickname, gameHost, playersNumber, socketID);
                    return;
                case DISCONNECTION:
                    ConnectionMessage disconnectionMessage = new ConnectionMessage(message, Message.MessageType.DISCONNECTION);
                    String disconnectionNickname = disconnectionMessage.getNickname();
                    checkNickname(disconnectionNickname);
                    manageDisconnection(disconnectionNickname, socketID);
                    return;
                case RECONNECTION:
                    ConnectionMessage reconnectionMessage = new ConnectionMessage(message, Message.MessageType.CONNECTION);
                    String reconnectionNickname = reconnectionMessage.getNickname();
                    checkNickname(reconnectionNickname);
                    manageReconnection(reconnectionNickname, socketID);
                    return;
                default:
                    if(isPlaying(socketID)) {
                        getGameBySocketID(socketID).onReceivedMessage(message, socketID);
                    }
                    return;

            }
        }catch (MalformedMessageException e){
            System.out.println("[SERVER] Received a malformed message!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * closes the connection in case of nickname not available
     * @param nickname represents the player's nickname
     * @param socketID represents the id related to the socket connection
     */
    private void notAvailableNickname(String nickname, String socketID){

        try {
            String messageError = "Nickname not available. Please choose another nickname and reconnect.";
            getConnection(socketID).send(MessageFactory.buildReply(false, messageError, nickname));
            getConnection(socketID).closeConnection();
            removeActiveConnection(socketID);
            return;
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a negative reply message");
            e.printStackTrace();
            return;
        }

    }

    /**
     * closes the connection if there are no games to join
     * @param nickname represents the player's nickname
     * @param socketID represents the id related to the socket connection
     */
    private void noExistingGames(String nickname, String socketID){

        try {
            String messageError = "Can't find an existing game to join. Reconnect and create a new one.";
            getConnection(socketID).send(MessageFactory.buildReply(false, messageError, nickname));
            getConnection(socketID).closeConnection();
            removeActiveConnection(socketID);
            return;
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a negative reply message");
            e.printStackTrace();
            return;
        }

    }

    /**
     * creates a new game
     * @param nickname represents the player's nickname
     * @param playersNumber represents the number of players associates to the new game
     * @param socketID represents the id related to the socket connection
     */
    private void createGame(String nickname, int playersNumber, String socketID){

        Game game = new Game(this, nickname, socketID, getConnection(socketID), playersNumber, createId());
        addWaitingGame(game);

        try {
            String message = "Created a new " + getGameType(playersNumber) + " game.";
            getConnection(socketID).send(MessageFactory.buildReply(true, message, nickname));
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a positive reply message");
            e.printStackTrace();
            return;
        }

    }

    /**
     * joins an existing game
     * @param nickname represents the player's nickname
     * @param socketID represents the id related to the socket connection
     */
    private void joinGame(String nickname, String socketID){

        waitingConnection.get(0).addPlayer(nickname, socketID, getConnection(socketID));

        try{
            String message = "Joined a game. Please wait for the game to start.";
            getConnection(socketID).send(MessageFactory.buildReply(true, message, nickname));
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a positive reply message");
            e.printStackTrace();
            return;
        }

    }

    /**
     * makes the games begin if there are enough players
     */
    private void startGames(){

        List<Game> started = new ArrayList<>();
        for(Game game : waitingConnection){
            if(game.canStart()){
                game.setUpGame();
                addActiveGame(game);
                started.add(game);
            }
        }
        waitingConnection.removeAll(started);

    }

    /**
     * closes a game currently in the waiting list
     * @param game represents the game to close
     * @param socketId represents the id related to the socket connection of the remaining player
     */
    private void closeWaitingGame(Game game, String socketId){

        System.out.println("[SERVER] No players left in game " + game.getId() + ". The game will now be removed from the waiting list.");
        game.removePlayer(socketId);
        getConnection(socketId).closeConnection();
        removeActiveConnection(socketId);
        //disconnected.add(game);
        System.out.println("[SERVER] Game removed correctly.");

    }

    /**
     * removes a player from the game in the waiting list
     * @param game represents the game to remove the player from
     * @param nickname represents the player's nickname
     * @param socketId represents the id related to the socket connection
     */
    private void removeWaitingPlayer(Game game, String nickname, String socketId){

        System.out.println("[SERVER] " + nickname + " will now be removed from the game.");
        getConnection(socketId).closeConnection();
        removeActiveConnection(socketId);
        game.removePlayer(socketId);
        System.out.println("[SERVER] " + nickname + " correctly removed from the game.");

    }

    /**
     * adds the game to the suspended ones
     * @param game represents the game to suspend
     * @param nickname represents the player's nickname
     * @param socketId represents the id related to the socket connection
     */
    private void suspendGame(Game game, String nickname, String socketId){

        System.out.println("[SERVER] No players left in game " + game.getId() + ". The game will now be suspended.");
        game.removePlayer(socketId);
        game.startSuspensionTimer();
        getConnection(socketId).closeConnection();
        removeActiveConnection(socketId);
        addInactivePlayer(nickname, game.getId());
        addSuspendedGame(game);
        //disconnected.add(game);
        System.out.println("[SERVER] Game suspended correctly.");

    }

    /**
     * closes an active game
     * @param game represents the game to close
     * @param socketId represents the id related to the socket connection of the remaining player
     */
    private void closeActiveGame(Game game, String socketId){

        System.out.println("[SERVER] No players left in game " + game.getId() + ". The game will now be removed from the active ones.");
        clearInactivePlayers(game.getId());
        game.removePlayer(socketId);
        getConnection(socketId).closeConnection();
        removeActiveConnection(socketId);
        //disconnected.add(game);
        System.out.println("[SERVER] Game removed correctly.");

    }

    /**
     * removes a player from an active game
     * @param game represents the game to remove the player from
     * @param nickname represents the player's nickname
     * @param socketId represents the id related to the socket connection
     */
    private void removeActivePlayer(Game game, String nickname, String socketId){

        System.out.println("[SERVER] " + nickname + " will now be removed from the game.");
        game.removePlayer(socketId);
        getConnection(socketId).closeConnection();
        removeActiveConnection(socketId);
        System.out.println("Players: " + game.getPlayers());
        System.out.println("Game size: " + game.getActualPlayers());
        addInactivePlayer(nickname, game.getId());
        System.out.println("[SERVER] " + nickname + " correctly removed from the game.");
        try {
            String message = nickname + " just left the game.";
            game.sendAll(MessageFactory.buildDisconnection(message, nickname));
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a disconnection message");
            e.printStackTrace();
        }

    }

    /**
     * reconnects a player to his previous game
     * @param nickname represents the player's nickname
     * @param socketID represents the id related to the socket connection
     */
    private void reconnectPlayer(String nickname, String socketID){

        Game game = getGameById(inactivePlayers.get(nickname));
        game.addPlayer(nickname, socketID, getConnection(socketID));
        inactivePlayers.remove(nickname);
        if(suspendedGames.contains(game)) {
            removeSuspendedGame(game);
            addActiveGame(game);
        }
        System.out.println("[SERVER] " + nickname + " was reconnected to his game.");

        try {
            String message = "You have been reconnected to your previous game.";
            getConnection(socketID).send(MessageFactory.buildReply(true, message, nickname));
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a positive reply message");
            e.printStackTrace();
        }

    }

    /**
     * denies the reconnection of a player
     * @param nickname represents the player's nickname
     * @param socketID represents the id related to the socket connection
     */
    private void denyReconnection(String nickname, String socketID){

        System.out.println("[SERVER] " + nickname + " was not disconnected or his game is now over.");

        try {
            String messageError = "You were not playing any match or your game is now over.";
            getConnection(socketID).send(MessageFactory.buildReply(false, messageError, nickname));
            getConnection(socketID).closeConnection();
            removeActiveConnection(socketID);
        } catch (MalformedMessageException e){
            System.out.println("[SERVER] Error occurred while creating a negative reply message");
            e.printStackTrace();
        }

    }

    /**
     * prints the status of the games
     */
    private void printStatus(){

        System.out.println("[SERVER] Waiting list: " + waitingConnection.size());
        System.out.println("[SERVER] Active games: " + activeGames.size());
        System.out.println("[SERVER] Suspended games: " + suspendedGames.size());

    }

    /**
     * manages the creation of a new game and the addition of a player to an existing game
     * @param nickname represents the client's nickname
     * @param gameHost indicates if the player wants to create a new jame or join an existing one
     * @param playersNumber represents the number of players associates to the new game (0 if the player wants to join a game)
     * @param socketID represents the client's socket id
     */
    public void manageLobby(String nickname, boolean gameHost, int playersNumber, String socketID){

        if(isWaiting(socketID) || isPlaying(socketID)){
            System.out.println("[SERVER] " + nickname + " is already connected.");
            return;
        }
        if(!isNicknameAvailable(nickname)){
            System.out.println("[SERVER] Nickname \"" + nickname + "\" is currently not available.");
            notAvailableNickname(nickname, socketID);
            return;
        }
        if(!gameHost && waitingConnection.size() == 0){
            System.out.println("[SERVER] Can't find an existing game to join.");
            noExistingGames(nickname, socketID);
            return;
        }

        System.out.println("[SERVER] Adding " + nickname + " to the lobby.");

        if(gameHost){
            System.out.println("[SERVER] " + nickname + " created a new game.");
            createGame(nickname, playersNumber, socketID);
        }else{
            System.out.println("[SERVER] " + nickname + " joined a game.");
            joinGame(nickname, socketID);
        }

        startGames();
        printStatus();

    }

    /**
     * removes a player from the lobby
     * @param nickname represents the nickname of the removed player
     * @param socketId represents the socket's id
     */
    public void manageDisconnection(String nickname, String socketId){

        System.out.println("[SERVER] " + nickname + " requested to be disconnected.");

        if(!isWaiting(socketId) && !isPlaying(socketId)){
            System.out.println("[SERVER] " + nickname + " was not part of any match. His connection will now be closed.");
            activeConnections.get(socketId).closeConnection();
            activeConnections.remove(socketId);
            return;
        }

        List<Game> disconnected = new ArrayList<>();
        for(Game game : waitingConnection){
            if(game.containsPlayer(nickname) && game.isCorresponding(socketId, nickname)){
                if(game.getActualPlayers() == 1){
                    closeWaitingGame(game, socketId);
                    disconnected.add(game);
                }else{
                    removeWaitingPlayer(game, nickname, socketId);
                }
                System.out.println("[SERVER] Disconnection request completed.");
                break;
            }
        }
        waitingConnection.removeAll(disconnected);

        for(Game game : activeGames){
            if(game.containsPlayer(nickname) && game.isCorresponding(socketId, nickname)){
                if(game.getPlayersNumber() == 1) {
                    suspendGame(game, nickname, socketId);
                    disconnected.add(game);
                } else if (game.getPlayersNumber() > 1 && game.getActualPlayers() == 1) {
                    closeActiveGame(game, socketId);
                    disconnected.add(game);
                } else {
                    removeActivePlayer(game, nickname, socketId);
                }
            }
            System.out.println("[SERVER] Disconnection request completed.");
            break;
        }
        activeGames.removeAll(disconnected);

        printStatus();

    }

    /**
     * manages the reconnection of a player to a game he previously left
     * @param nickname represents the reconnecting player's nickname
     * @param socketID represents the client's socket connection
     */
    public void manageReconnection(String nickname, String socketID){

        System.out.println("[SERVER] " + nickname + " is trying to reconnect...");

        if(isPlaying(socketID) || isWaiting(socketID)){
            System.out.println("[SERVER] " + nickname + " is already connected or is trying to reconnect a different player.");
            return;
        }
        if(inactivePlayers.containsKey(nickname)){
            reconnectPlayer(nickname, socketID);
        }else{
            denyReconnection(nickname, socketID);
        }

        printStatus();

    }

}
