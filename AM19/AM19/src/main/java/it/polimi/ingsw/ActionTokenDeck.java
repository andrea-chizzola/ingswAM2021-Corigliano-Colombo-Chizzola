package it.polimi.ingsw;

import java.util.*;

/**
 * public class representing the deck containing all the Action Tokens used for single player matches
 */
public class ActionTokenDeck {

    /**
     * contains the unused action tokens
     */
    private Container<Action> unusedActionTokens;

    /**
     * contains the used action tokens
     */
    private ArrayList<Action> usedActionTokens;

    /**
     * creates a new action token deck, given a List of action tokens
     * @param actions contains the action tokens
     */
    public ActionTokenDeck(List<Action> actions){

        unusedActionTokens = new Container<Action>();
        unusedActionTokens.addAll(actions);

        usedActionTokens = new ArrayList<Action>();

    }

    /**
     * @return returns a container which contains the unused tokens
     */
    public Container<Action> getUnusedActionTokens() {
        return unusedActionTokens;
    }

    /**
     * @return returns a list containing the unused tokens
     */
    public ArrayList<Action> getUsedActionTokens() {
        return usedActionTokens;
    }

    /**
     * merges the used action tokens to the unused in a single deck end then shuffles it
     */
    public void mergeAndShuffle(){

        unusedActionTokens.addAll(usedActionTokens);
        usedActionTokens.clear();
        unusedActionTokens.shuffle();

    }

    /**
     * @return returns the top action token, without removing it from the unused tokens list
     */
    public Action getTop(){ return unusedActionTokens.readTop(); }

    /**
     * moves the used action token to the usedActionTokens list and merges both lists if the unusedActionTokens list remains empty
     */
    public void changeList(){

        if(unusedActionTokens.size() > 1){

            usedActionTokens.add(unusedActionTokens.extractTop());

        }else{

            mergeAndShuffle();

        }

    }


}
