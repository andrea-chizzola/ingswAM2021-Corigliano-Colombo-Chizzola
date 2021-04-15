package it.polimi.ingsw.Model.Boards.FaithTrack;

import java.util.Objects;

/**
 * public class representing the pope's favor tiles
 */
public class PopeFavor {

    /**
     * represents the victory points associated to the pope's favor tile
     */
    private final int victoryPoints;

    /**
     * represents the status of the pope's favor tile, which can be active (true) or inactive (false)
     */
    private boolean status;

    /**
     * creates a new pope's favor tile, initialising it with the related victory points (2,3 or 4) and the inactive status
     * @param victoryPoints represents the victory points associated to the pope's favor tile
     */
    public PopeFavor(int victoryPoints) {

        this.victoryPoints = victoryPoints;
        status = false;

    }

    /**
     * @return returns the victory points associated to the pope's favor tile only if it is currently active
     */
    public int getVictoryPoints() {

        return victoryPoints;

    }

    /**
     * @return returns true if the pope's favor tile is active, false otherwise
     */
    public boolean isActive() {

        return status;

    }

    /**
     * sets the status of the pope's favor tile, active or inactive
     * @param status is true if the pope's favor tile has to be activated, false otherwise
     */
    public void setStatus(boolean status) {

        this.status = status;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopeFavor popeFavor = (PopeFavor) o;
        return victoryPoints == popeFavor.victoryPoints &&
                status == popeFavor.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(victoryPoints, status);
    }
}
