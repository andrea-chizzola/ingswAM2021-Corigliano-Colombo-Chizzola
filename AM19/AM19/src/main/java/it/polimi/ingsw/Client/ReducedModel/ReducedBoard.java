package it.polimi.ingsw.Client.ReducedModel;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.*;

public class ReducedBoard {

    /**
     * this attribute represents the faith obtained by a player
     */
    private int faithPoints;

    /**
     * this attribute represents the status of a player's sections
     */
    private List<ItemStatus> sections;

    /**
     * this attribute represents the status of a player's slots
     */
    private Map<Integer, String> slots;

    /**
     * this attribute represents the IDs of a player's leader cards
     */
    private Map<Integer, String> leadersID;

    /**
     * this attribute represents the status of a player's faith track
     */
    private Map<Integer, ItemStatus> leadersStatus;

    /**
     * this attribute represents the state of a player's warehouse
     */
    private List<ResQuantity> warehouse;

    /**
     * this attribute represents the state of a player's strongbox
     */
    private List<ResQuantity> strongbox;


    public ReducedBoard(){

        slots = new HashMap<>();
        warehouse = new LinkedList<>();
        strongbox = new LinkedList<>();
        sections = new LinkedList<>();
        leadersID = new HashMap<>();
        leadersStatus = new HashMap<>();

    }

    /**
     *
     * @return the amount of faith points owned by the player
     */
    public synchronized int getFaithPoints() {
        return faithPoints;
    }

    /**
     * this method is used to set the amount of faith point owned by the player
     * @param faith is the amount of faith points
     */
    public synchronized void setFaithPoints(int faith) {
        this.faithPoints = faith;
    }

    /**
     * this method is used get the status of the player's vatican sections
     * @return a list containing the status of the player's vatican report sections
     */
    public synchronized List<ItemStatus> getSections() {
        return new LinkedList<>(sections);
    }

    /**
     * this method is used to set the status of the player's vatican report sections
     * @param sections is the new status of the player's sections
     */
    public synchronized void setSections(List<ItemStatus> sections) {
        this.sections = sections;
    }

    /**
     * this method is used to retrieve the status of the player's slots
     * @return a map that represents the state of the player's slots
     */
    public synchronized Map<Integer, String> getSlots() {
        return new HashMap<>(slots);
    }

    /**
     * this method is used to set the state of the player's slots
     * @param slots is the state of the player's slots
     */
    public synchronized void setSlots(Map<Integer, String> slots) {
        this.slots = slots;
    }

    /**
     * this method is used to get the IDs of the player's leader cards
     * @return a map that contains the position and the IDs of the player's leader cards
     */
    public synchronized Map<Integer, String> getLeadersID() {
        return leadersID;
    }

    /**
     * this method is used to set the IDs of the player's leader cards
     * @param leadersID is a map that contains the new IDs of the leader cards
     */
    public synchronized void setLeadersID(Map<Integer, String> leadersID) {
        this.leadersID = leadersID;
    }

    /**
     * this method is used to get the status of the player's leader cards
     * @return a map that contains the position and the status of the player's leader cards
     */
    public synchronized Map<Integer, ItemStatus> getLeadersStatus() {
        return leadersStatus;
    }

    /**
     * this method is used to set the status of the player's leader cards
     * @param leadersStatus is a map that contains the new status of the leader cards
     */
    public synchronized void setLeadersStatus(Map<Integer, ItemStatus> leadersStatus) {
        this.leadersStatus = leadersStatus;
    }

    /**
     * this method is used to get the status of the player's warehouse
     * @return the state of the player's warehouse
     */
    public synchronized List<ResQuantity> getWarehouse() {
        return warehouse;
    }

    /**
     * this method is used to set the state of the player's warehouse
     * @param warehouse is a list that contains the new state of the player warehouse
     */
    public synchronized void setWarehouse(List<ResQuantity> warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * this method is used to get the state of the player's strongbox
     * @return a list that contains the state of the player's strongbox
     */
    public synchronized List<ResQuantity> getStrongbox() {
        return strongbox;
    }

    /**
     * this method is used to set the status of the player's strongbox
     * @param strongbox is a list that represents the new state of the strongbox
     */
    public synchronized void setStrongbox(List<ResQuantity> strongbox) {
        this.strongbox = strongbox;
    }
}
