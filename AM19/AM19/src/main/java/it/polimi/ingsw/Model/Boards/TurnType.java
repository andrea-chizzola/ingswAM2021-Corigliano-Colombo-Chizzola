package it.polimi.ingsw.Model.Boards;

/**
 * This class is an enumeration and represents all the types of turn
 */
public enum TurnType {
    INITIALIZATION_LEADERS,
    INITIALIZATION_RESOURCE,
    SWAP,
    TAKE_RESOURCES,
    MANAGE_MARBLE,
    MANAGE_LEADER,
    BUY_CARD,
    DO_PRODUCTION,
    TURN_SELECTION,
    EXIT,
    WRONG_STATE
}
