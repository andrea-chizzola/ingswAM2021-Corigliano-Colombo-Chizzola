package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.ActionTokenParser;
import it.polimi.ingsw.xmlParser.CardParser;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

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
     * @param file is the name of the file that contains the characteristics of the Actions
     */
    public ActionTokenDeck(String file){

        unusedActionTokens = new Container<>();
        usedActionTokens = new ArrayList<>();

        unusedActionTokens.addAll(ConfigurationParser.parseActionTokens(file));
        unusedActionTokens.shuffle();
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
