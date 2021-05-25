package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;
import it.polimi.ingsw.Messages.ActionMessage;
import it.polimi.ingsw.Model.Boards.GameBoardHandler;
import it.polimi.ingsw.Model.Cards.Production;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.*;

/**
 * Utilities class for the MessageHandler
 */
public class Utilities {

    /**
     * This method creates a ArrayList of Production from the ActionMessage
     * @param gameBoardHandler the handler of the GameBoard
     * @param actionMessage the message
     * @return ArrayList of Production which represents the productions chosen by the player
     * @throws InvalidActionException if the productions selected are wrong
     * @throws MalformedMessageException if the message is not well formed
     */
    public static ArrayList<Production> createProductions(GameBoardHandler gameBoardHandler, ActionMessage actionMessage) throws InvalidActionException,MalformedMessageException{

        ArrayList<Production> productions = new ArrayList<>();
        int numberChoiceProducts = 0;
        int numberChoiceMaterials = 0;

        if(actionMessage.getSelectedLeaderCards().size()==0 && actionMessage.getActivatedDevelopmentCards().size()==0 && !actionMessage.isPersonalProduction())
            throw new InvalidActionException("No productions selected");

        //check development cards
        for(int i : actionMessage.getActivatedDevelopmentCards().keySet()) {
            Production production = gameBoardHandler.getDevProduction(i);
            productions.add(production);
        }

        //check leader cards
        for(int i : actionMessage.getSelectedLeaderCards().keySet()){
            Production production = gameBoardHandler.getLeaderProduction(i);
            productions.add(production);
            numberChoiceProducts += production.getCustomProducts();
            numberChoiceMaterials += production.getCustomMaterials();
        }

        //personal board production
        if(actionMessage.isPersonalProduction()){
            numberChoiceProducts += gameBoardHandler.getBoardProduction().getCustomProducts();
            numberChoiceMaterials += gameBoardHandler.getBoardProduction().getCustomMaterials();
            productions.add(gameBoardHandler.getBoardProduction());
        }

        //check of number chosen resources
        if(numberChoiceMaterials != actionMessage.getChosenMaterials().stream().mapToInt(ResQuantity::getQuantity).sum())
            throw new InvalidActionException("Wrong number chosen materials");

        if(numberChoiceProducts != actionMessage.getChosenProducts().stream().mapToInt(ResQuantity::getQuantity).sum())
            throw new InvalidActionException("Wrong number chosen products");

        //creating new production
        LinkedList<ResQuantity> materials = new LinkedList<>(actionMessage.getChosenMaterials());
        LinkedList<ResQuantity> products = new LinkedList<>(actionMessage.getChosenProducts());
        Production choiceProduction = new Production(materials,products,0,0);
        productions.add(choiceProduction);

        return productions;
    }


    /**
     * This method creates an ArrayList or resQuantity that meets the requirements for the parameter strongbox that has to be passed in DoProduction and BuyCard
     * @param strongbox List of ResQuantity
     * @return an ArrayList or ResQuantity that meets the requirements for the parameter strongbox
     */
    public static ArrayList<ResQuantity> createCorrectStrongbox(List<ResQuantity> strongbox){

        Map<Resource,Integer> map = new HashMap<>();
        ArrayList<ResQuantity> list = new ArrayList<>();

        for(ResQuantity resQuantity : strongbox){
            if(map.containsKey(resQuantity.getResource())){
                map.put(resQuantity.getResource(), map.get(resQuantity.getResource()) + resQuantity.getQuantity());}
            else{map.put(resQuantity.getResource(), resQuantity.getQuantity());}
        }

        for (Resource resource : map.keySet()){
            list.add(new ResQuantity(resource,map.get(resource)));
        }
        return list;
    }
}
