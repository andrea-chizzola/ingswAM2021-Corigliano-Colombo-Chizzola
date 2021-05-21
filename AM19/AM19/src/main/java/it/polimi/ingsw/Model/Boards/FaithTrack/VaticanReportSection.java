package it.polimi.ingsw.Model.Boards.FaithTrack;

import java.util.Objects;

/**
 * public class representing the vatican report section
 */
public class VaticanReportSection {

    /**
     * represents the first tile of the vatican report section
     */
    private final int start;

    /**
     * represents the last tile of the vatican report section
     */
    private final int end;

    /**
     * true if the related pope's favor tile was discarded and it is no more inside the section, false otherwise
     */
    private boolean discarded;

    /**
     * contains the pope's favor tile associated to the vatican report section
     */
    private PopeFavor popeFavor;

    /**
     * creates the vatican report section and the related pope's favor tile
     * @param start represents the first tile of the vatican report section
     * @param end represents the last tile of the vatican report section
     * @param victoryPoints represents the victory points of the pope's favor tile associated to the vatican report section
     */
    public VaticanReportSection(int start, int end, int victoryPoints) {

        this.start = start;
        this.end = end;
        discarded = false;
        popeFavor = new PopeFavor(victoryPoints);

    }

    /**
     * @return returns the first tile of the vatican report section
     */
    public int getStart() {
        return start;
    }

    /**
     * @return returns the last tile of the vatican report section
     */
    public int getEnd() {
        return end;
    }

    /**
     * @return true only if the pope's favor tile associated to the report section was discarded
     */
    public boolean isDiscarded() {
        return discarded;
    }

    /**
     * discards the pope's favor tile
     */
    public void discard(){

        if(!isDiscarded()){

            discarded = true;
            popeFavor.setStatus(false);

        }

    }

    /**
     * activates the pope's favor tile, only if it hadn't been discarded
     */
    public void activateFavor(){

        if(!isDiscarded()){

            popeFavor.setStatus(true);

        }

    }

    /**
     * @return returns the status of the pope's favor tile, true if it is active(and not discarded) and false if it is inactive/discarded
     */
    public boolean getStatus(){

        return !isDiscarded() && popeFavor.isActive();

    }

    /**
     * @return returns the pope's favor tile victory points, only if it is active and it is not discarded
     */
    public int getPopeFavorVictoryPoints(){

        if(!isDiscarded() && popeFavor.isActive()){

            return popeFavor.getVictoryPoints();

        }else return 0;

    }

    /**
     * @return the value of the pope favour in the section
     */
    public int getPopeFavourValue(){
        return popeFavor.getVictoryPoints();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaticanReportSection that = (VaticanReportSection) o;
        return start == that.start &&
                end == that.end &&
                discarded == that.discarded &&
                Objects.equals(popeFavor, that.popeFavor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, discarded, popeFavor);
    }
}
