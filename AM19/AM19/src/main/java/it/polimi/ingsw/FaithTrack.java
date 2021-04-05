package it.polimi.ingsw;

import java.util.ArrayList;

/**
 * public class representing the faith track, one for each player
 */
public class FaithTrack {

    /**
     * keeps track of the points associated to each slot of the track
     */
    private ArrayList<Integer> trackPoints;

    /**
     * keeps track of the current position in the faith track, an integer between 0 and 24
     */
    private int position;

    /**
     * contains the 3 sections associated to the faith track
     */
    private ArrayList<VaticanReportSection> sections;

    /**
     * creates a new faith track
     */
    public FaithTrack(ArrayList<Integer> trackPoints, ArrayList<VaticanReportSection> sections) {

        this.trackPoints = trackPoints;
        this.sections = sections;
        position  = 0;

    }

    /**
     * @return returns the current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return return the section related to the current position, 0 if the faith marker is currently not inside a section
     */
    public int getSection(){

        if(position >= sections.get(0).getStart() && position <= sections.get(0).getEnd()){
            return 1;
        }else if(position >= sections.get(1).getStart() && position <= sections.get(1).getEnd()){
            return 2;
        }else if(position >= sections.get(2).getStart() && position <= sections.get(2).getEnd()){
            return 3;
        }else return 0;

    }

    /**
     * activates the pope's favor tile associated to the section
     * @param section represents the section where we want to activate the pope's favor tile (1, 2 or 3)
     */
    public void activateFavor(int section){

        sections.get(section - 1).activateFavor();
    }

    /**
     * discards the pope's favor tile related to the section
     * @param section represents the section containing the pope's favor tile
     */
    public void discardFavor(int section){

        sections.get(section - 1).discard();

    }

    /**
     * @param section represents the section of the faith track (1, 2 or 3)
     * @return returns true if the current position is inside the section
     */
    public boolean isInsideSection(int section){

        if(section == getSection()){
            return true;
        }else return false;

    }

    /**
     * @param section represents the section of the faith track (1, 2 or 3)
     * @return returns true if the current position is before the section
     */
    public boolean isBeforeSection(int section){

        if(position < sections.get(section - 1).getStart()){
            return true;
        }else return false;

    }


    /**
     * @param section represents the section of the faith track (1, 2 or 3)
     * @return true if the player has reached the pope's space of the related section
     */
    public boolean isEndSection(int section){

        if(position == sections.get(section - 1).getEnd()){
            return true;
        }else return false;

    }

    /**
     * @return true only if the player has reached the last spot of the track
     */
    public boolean isEndTrack(){
        return getPosition() == 24;
    }


    /**
     * moves the faith marker ahead
     * @param amount represents how many positions we want to add to the faith marker
     */
    public void addFaith(int amount){

        if(!isEndTrack()){
            if(position + amount <= 24){
                position = position + amount;
            }else position = 24;
        }

    }

    /**
     * @return returns the total victory points in relation to the final position and the state of the faith track
     */
    public int calculatePoints(){

        int victoryPoints;
        int pos = getPosition();

        victoryPoints = sections.get(0).getPopeFavorVictoryPoints() + sections.get(1).getPopeFavorVictoryPoints() + sections.get(2).getPopeFavorVictoryPoints();

        while(pos > 0 && trackPoints.get(pos) == 0){
            pos--;
        }

        return victoryPoints + trackPoints.get(pos);

    }

}
