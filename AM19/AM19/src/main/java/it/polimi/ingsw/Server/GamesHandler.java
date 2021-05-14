package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class GamesHandler {

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

    private Server server;

    private AtomicLong idCounter;

    /**
     * creates a new server accepting clients to the port selected
     */
    public GamesHandler(Server server) {

        waitingConnection = new ArrayList<>();
        activeGames = new ArrayList<>();
        inactivePlayers = new HashMap<>();
        idCounter = new AtomicLong();
        this.server = server;

    }

    /**
     *
     * @return returns a new Id associated to the client's connection
     */
    public String createId(){
        return String.valueOf(idCounter.getAndIncrement());
    }

    /**
     *
     * @param id represents the game id
     * @return returns the game related to the selected id
     */
    public Game getGameById(String id){

        int i = 0;
        while(i < activeGames.size() && !activeGames.get(i).getId().equals(id)) i++;

        return activeGames.get(i);

    }

    public Game getGameBySocketID(String socketID){
        int i = 0;
        while(i < activeGames.size() && !activeGames.get(i).containsSocketID(socketID)) i++;

        return activeGames.get(i);
    }


    /**
     * adds the player to the disconnected ones
     * @param nickName represents the player's nickname
     * @param gameId represents the game id
     */
    public void addInactivePlayer(String nickName, String gameId){
        inactivePlayers.put(nickName, gameId);
    }

    /**
     * removes the inactive players related to a deleted match
     * @param id represents the deleted match
     */
    private void clearInactivePlayers(String id){
        for(Map.Entry<String,String> entry : inactivePlayers.entrySet()){
            if(entry.getValue().equals(id)){
                inactivePlayers.remove(entry.getKey());
            }
        }
    }

    /**
     * adds a new game to the ones waiting for other players to start
     * @param gameHandler represents the new game added
     */
    private void addWaitingGame(Game gameHandler){
        waitingConnection.add(gameHandler);
    }

    /**
     * adds a game to the active ones
     * @param gameHandler represents the game added
     */
    private void addActiveGame(Game gameHandler){
        activeGames.add(gameHandler);
    }

    /**
     *
     * @param nickname represents the selected nickname
     * @return returns true if the selected nickname is currently available
     */
    public boolean isNicknameAvailable(String nickname){

        for(Game game : waitingConnection){
            if(game.containsPlayer(nickname))
                return false;
        }

        for(Game game : activeGames){
            if(game.containsPlayer(nickname))
                return false;
        }

        if(inactivePlayers.containsKey(nickname))
            return false;

        return true;

    }

    /**
     * manages the creation of a new game and the addition of a player to an existing game
     * @param nickname represents the client's nickname
     * @param gameHost indicates if the player wants to create a new jame or join an existing one
     * @param playersNumber represents the number of players associates to the new game (0 if the player wants to join a game)
     * @param socketID represents the client's socket connection
     */
    public void manageLobby(String nickname, boolean gameHost, int playersNumber, String socketID){

        if(!isNicknameAvailable(nickname)){

            System.out.println("[SERVER] Nickname " + nickname + " is currently not available.");
            //Message error = new MessageReply("Nickname not available. Please choose another nickname and reconnect.", true);
            String messageError = "Nickname not available. Please choose another nickname and reconnect.";
            server.send(socketID, messageError);
            server.closeConnection(socketID);
            return;

        }
        if(!gameHost && waitingConnection.size() == 0){

            System.out.println("[SERVER] Can't find an existing game to join.");
            //Message error = new MessageReply("Can't find an existing game to join. Reconnect and create a new one.", true);
            String messageError = "Can't find an existing game to join. Reconnect and create a new one.";
            server.send(socketID, messageError);
            server.closeConnection(socketID);
            return;

        }

        System.out.println("[SERVER] Adding " + nickname + " to the lobby.");

        if(gameHost){

            System.out.println("[SERVER] " + nickname + " created a new game.");
            Game game = new Game(server, nickname, socketID, playersNumber, createId());
            addWaitingGame(game);

            //Message message = new MessageReply("Created a new single player game.", false);
            String message = "Created a new single player game.";
            server.send(socketID, message);

        }else if(waitingConnection.size() > 0){

            System.out.println("[SERVER] " + nickname + " joined a game.");
            waitingConnection.get(0).addPlayer(nickname, socketID);

            //Message message = new MessageReply("Joined a game.", false);
            String message = "Joined a game.";
            server.send(socketID, message);

        }

        int size = waitingConnection.size();
        for(int i=0; i<size; i++){
            Game game = waitingConnection.get(i);
            if(game.canStart()){
                game.setUpGame();
                addActiveGame(game);
                waitingConnection.remove(game);
            }
        }

    }


    public void onReceivedMessage(String message, String socketID){

       try {

           switch (MessageUtilities.instance().getType(message)) {
               case CONNECTION:
                   manageLobby("Pippo", true, 1, socketID);
                   return;
               case DISCONNECTION:
                   manageDisconnection("Pippo");
                   return;
               case RECONNECTION:
                   manageReconnection("Pippo", socketID);
                   return;
               default:
                   getGameBySocketID(socketID).onReceivedMessage(message, socketID);

           }
       }catch (MalformedMessageException e){
           //volendo si può mandare il messaggio d'errore al client
           System.out.println(e.getMessage()+" SocketID "+socketID);
       }
    }


    /**
     * removes a player from the lobby
     * @param nickname represents the nickname of the removed player
     */
    public void manageDisconnection(String nickname){

        for(Game game : waitingConnection){
            if(game.containsPlayer(nickname)){
                if(game.getActualPlayers() == 1){
                    waitingConnection.remove(game);
                }else{
                    game.removePlayer(nickname);
                }
                return;
            }
        }

        for(Game game : activeGames){
            if(game.containsPlayer(nickname)){
                if(game.getActualPlayers() == 1) {
                    clearInactivePlayers(game.getId());
                    activeGames.remove(game);
                }else{
                    game.removePlayer(nickname);
                    addInactivePlayer(nickname, game.getId());
                    //Notify Controller
                    //game.getPlayerConnection(nickname).getListener().onReceivedMessage(new MessageDisconnection(nickname), nickname);
                    //game.sendAll(new MessageDisconnection(nickname));
                }
                return;
            }
        }

    }

    /**
     * manages the reconnection of a player to a game he previously left
     * @param nickname represents the reconnecting player's nickname
     * @param socketID represents the client's socket connection
     */
    public void manageReconnection(String nickname, String socketID){

        //String nickname = message.getNickName();
        System.out.println("[SERVER] Nickname " + nickname + " is trying to reconnect...");

        if(inactivePlayers.containsKey(nickname)){

            getGameById(inactivePlayers.get(nickname)).addPlayer(nickname, socketID);
            inactivePlayers.remove(nickname);

            //Notify Controller
            //connection.getListener().onReceivedMessage(new MessageReconnectionRequest(nickname), nickname);

            System.out.println("[SERVER] Nickname " + nickname + " was reconnected to his game.");
            //Message reply = new MessageReply("You have been reconnected to your previous game.", false);
            String message = "You have been reconnected to your previous game.";
            server.send(socketID,message);

            //connection.send(new UpdateGame());
            //connection.send(new UpdateLeaderCards());
            //connection.send(new MessageCurrentPlayer());

        }else{

            System.out.println("[SERVER] Nickname " + nickname + " was not disconnected.");
            //Message error = new MessageReply("You were not playing any match or your game is now over.", true);
            String messageError = "You were not playing any match or your game is now over.";
            server.send(socketID,messageError);
            server.closeConnection(socketID);

        }

    }

}
