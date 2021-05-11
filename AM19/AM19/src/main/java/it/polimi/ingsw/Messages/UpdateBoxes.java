package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.HashMap;
import java.util.Map;

public class UpdateBoxes extends UpdateBoard {

    /**
     * this attribute represent the state of a set of shelves in a Warehouse
     */
    Map<Integer, ResQuantity> warehouse;

    /**
     * this attribute represent the state of a set of resource in a StrongBox
     */
    Map<Resource, Integer> strongBox;

    /**
     * this method is the constructor of the class
     * @param warehouse represent the state of a set of shelves in a Warehouse
     * @param strongBox represent the state of a set of resource in a StrongBox
     */
    public UpdateBoxes(Map<Integer, ResQuantity> warehouse, Map<Resource, Integer> strongBox) {
        super("Update of the current player's warehouse and strongbox", UpdateBoardType.BOX_UPDATE);
        this.warehouse = new HashMap<>(warehouse);
        this.strongBox = new HashMap<>(strongBox);
    }

    /**
     * @return a copy of the attribute warehouse
     */
    public Map<Integer, ResQuantity> getWarehouse(){
        return new HashMap<>(warehouse);
    }

    /**
     * @return a copy of the attribute strongbox
     */
    public Map<Resource, Integer> getStrongBox(){
        return new HashMap<>(strongBox);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        StringBuilder wString = new StringBuilder();
        StringBuilder sString = new StringBuilder();
        for(Integer i : warehouse.keySet()){
            wString.append("slot").append(i).append(warehouse.get(i).toString()).append('\'');
        }
        for(Resource r : strongBox.keySet()){
            sString.append(r.toString()).append(strongBox.get(r)).append('\'');
        }
        return "Message{" +
                "body='" + getBody() + '\'' +
                ((sString.length() > 0) ? "StrongBox='"+ sString + '\'': "") +
                ((wString.length() > 0) ? "WareHouse='"+ wString + '\'': "") +
                ", messageType=" + getMessageType() +
                '}';
    }
}
