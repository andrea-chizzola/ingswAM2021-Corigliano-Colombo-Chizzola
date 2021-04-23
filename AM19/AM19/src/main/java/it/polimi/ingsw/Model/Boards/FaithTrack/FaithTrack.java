package it.polimi.ingsw.Model.Boards.FaithTrack;

import java.util.ArrayList;
import java.util.Objects;

/**
 * public class representing the faith track, one for each player
 */
public class FaithTrack {

    /**
     * keeps track of the points associated to each slot of the track
     */
    private ArrayList<Integer> trackPoints;

    /**
     * keeps track of the current position in the faith track
     */
    private int position;

    /**
     * contains the sections associated to the faith track
     */
    private ArrayList<VaticanReportSection> sections;

    /**
     * creates a new faith track
     */
    public FaithTrack(ArrayList<Integer> trackPoints, ArrayList<VaticanReportSection> sections) {

        this.trackPoints = new ArrayList<>();
        this.trackPoints.addAll(trackPoints);
        this.sections = new ArrayList<>();
        this.sections.addAll(sections);

        position  = 0;

    }

    /**
     * @return returns the current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return returns the section related to the current position, 0 if the faith marker is currently not inside a section
     */
    public int getSectionNumber(){


        for(VaticanReportSection section : sections){
            if(position >= section.getStart() && position <= section.getEnd()){
                return sections.indexOf(section) + 1;
            }
        }

        return 0;

    }

    /**
     * @param section represents the number of the section required
     * @return returns the section required
     */
    public VaticanReportSection getSection(int section) throws IndexOutOfBoundsException{


        if (section > 0 && section <= sections.size()) {

            return sections.get(section - 1);

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");

    }

    /**
     * @return returns an ArrayList containing the sections associated to the faith track
     */
    public ArrayList<VaticanReportSection> getSections() {
        return sections;
    }

    /**
     * activates the pope's favor tile associated to the section
     * @param section represents the section where we want to activate the pope's favor tile
     * @throws IndexOutOfBoundsException if the section related to the parameter doesn't exist
     */
    public void activateFavor(int section) throws IndexOutOfBoundsException {

        if (section > 0 && section <= sections.size()) {

            sections.get(section - 1).activateFavor();

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");
    }

    /**
     * discards the pope's favor tile related to the section
     * @param section represents the section containing the pope's favor tile
     * @throws IndexOutOfBoundsException if the section related to the parameter doesn't exist
     */
    public void discardFavor(int section) throws IndexOutOfBoundsException{

        if (section > 0 && section <= sections.size()) {

            sections.get(section - 1).discard();

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");

    }

    /**
     * @param section represents the section of the faith track
     * @return returns true if the current position is inside the section
     * @throws IndexOutOfBoundsException if the section related to the parameter doesn't exist
     */
    public boolean isInsideSection(int section) throws IndexOutOfBoundsException{

        if (section > 0 && section <= sections.size()) {

            return section == getSectionNumber();

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");

    }

    /**
     * @param section represents the section of the faith track
     * @return returns true if the current position is before the section
     * @throws IndexOutOfBoundsException if the section related to the parameter doesn't exist
     */
    public boolean isBeforeSection(int section) throws IndexOutOfBoundsException{

        if (section > 0 && section <= sections.size()) {

            return position < sections.get(section - 1).getStart();

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");

    }


    /**
     * @param section represents the section of the faith track
     * @return true if the player has reached the pope's space of the related section
     * @throws IndexOutOfBoundsException if the section related to the parameter doesn't exist
     */
    public boolean isEndSection(int section) throws IndexOutOfBoundsException{

        if (section > 0 && section <= sections.size()) {

            return position == sections.get(section - 1).getEnd();

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");


    }

    /**
     * @param section represents the section of the faith track
     * @return returns true if the current position is after the section
     * @throws IndexOutOfBoundsException if the section related to the parameter doesn't exist
     */
    public boolean isAfterSection(int section) throws IndexOutOfBoundsException{

        if (section > 0 && section <= sections.size()) {

            return !isBeforeSection(section) && !isInsideSection(section);

        }else throw new IndexOutOfBoundsException("Nonexistent Vatican Report Section");
    }

    /**
     * @return true only if the player has reached the last spot of the track
     */
    public boolean isEndTrack(){

        return getPosition() == sections.get(sections.size() - 1).getEnd();

    }


    /**
     * moves the faith marker ahead of 'amount' positions
     * @param amount represents how many positions we want to add to the faith marker
     */
    public void addFaith(int amount){

        if(!isEndTrack()){
            if(position + amount <= sections.get(sections.size() - 1).getEnd()){
                position = position + amount;
            }else position = sections.get(sections.size() - 1).getEnd();
        }

    }

    /**
     * @return returns the total victory points in relation to the final position and the state of the faith track
     */
    public int calculatePoints(){

        int victoryPoints = 0;
        int pos = getPosition();

        for(VaticanReportSection section : sections) {
            victoryPoints = victoryPoints + section.getPopeFavorVictoryPoints();
        }

        while(pos > 0 && trackPoints.get(pos) == 0){
            pos--;
        }

        return victoryPoints + trackPoints.get(pos);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaithTrack that = (FaithTrack) o;
        return position == that.position &&
                Objects.equals(trackPoints, that.trackPoints) &&
                Objects.equals(sections, that.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackPoints, position, sections);
    }
}
