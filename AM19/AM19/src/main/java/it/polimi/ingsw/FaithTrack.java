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
     * represents the vatican report section associated to the pope's favor tile containing 2 victory points
     */
    private VaticanReportSection vaticanReportSection2;

    /**
     * represents the vatican report section associated to the pope's favor tile containing 3 victory points
     */
    private VaticanReportSection vaticanReportSection3;

    /**
     * represents the vatican report section associated to the pope's favor tile containing 4 victory points
     */
    private VaticanReportSection vaticanReportSection4;

    /**
     * creates a new faith track initialising each spot with the related victory points
     */
    public FaithTrack() {

        trackPoints = new ArrayList<Integer>(25);
        for(int i = 0; i < 25; i++){
            trackPoints.add(i, 0);
            if(i % 3 == 0){
                if(i <= 6){
                    trackPoints.set(i, i / 3);
                }
                else if(i <= 12){
                    trackPoints.set(i, i / 2);
                }else if(i <= 18){
                    trackPoints.set(i, i - 6);
                }else if(i == 21){
                    trackPoints.set(i, 16);
                }else {
                    trackPoints.set(i, 20);
                }
            }
        }

        position = 0;
        vaticanReportSection2 = new VaticanReportSection(5, 8, 2);
        vaticanReportSection3 = new VaticanReportSection(12, 16, 3);
        vaticanReportSection4 = new VaticanReportSection(19, 24, 4);

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

        if(position >= 5 && position <= 8){
            return 1;
        }else if(position >= 12 && position <= 16){
            return 2;
        }else if(position >= 19 && position <= 24){
            return 3;
        }else return 0;

    }

    /**
     * activates the pope's favor tile associated to the section
     * @param section represents the section where we want to activate the pope's favor tile (1, 2 or 3)
     */
    public void activateFavor(int section){

        if(section == 1){
            vaticanReportSection2.activateFavor();
        }else if(section == 2){
            vaticanReportSection3.activateFavor();
        }else if(section == 3){
            vaticanReportSection4.activateFavor();
        }
    }

    /**
     * discards the pope's favor tile related to the section
     * @param section represents the section containing the pope's favor tile
     */
    public void discardFavor(int section){

        if(section == 1){
            vaticanReportSection2.discard();
        }else if(section == 2){
            vaticanReportSection3.discard();
        }else if(section == 3){
            vaticanReportSection4.discard();
        }

    }

    /**
     * @param section represents the section of the faith track (1, 2 or 3)
     * @return returns true if the current position is inside the section
     */
    public boolean isInsideSection(int section){

        if(section == 1 && getSection() == 1){
            return true;
        }else if(section == 2 && getSection() == 2){
            return true;
        }else if(section == 3 && getSection() == 3){
            return true;
        }else return false;

    }

    /**
     * @param section represents the section of the faith track (1, 2 or 3)
     * @return returns true if the current position is before the section
     */
    public boolean isBeforeSection(int section){

        if(section == 1 && position < 5){
            return true;
        }else if(section == 2 && position < 12){
            return true;
        }else if(section == 3 && position < 19){
            return true;
        }else return false;

    }


    /**
     * @param section represents the section of the faith track (1, 2 or 3)
     * @return true if the player has reached the pope's space of the related section
     */
    public boolean isEndSection(int section){

        if(section == 1 && position == 8){
            return true;
        }else if(section == 2 && position == 16){
            return true;
        }else if(section == 3 && position == 24){
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

        victoryPoints = vaticanReportSection2.getPopeFavorVictoryPoints() + vaticanReportSection3.getPopeFavorVictoryPoints() + vaticanReportSection4.getPopeFavorVictoryPoints();

        if(position <= 2){
            return victoryPoints;
        }
        if(position <= 5){
            return victoryPoints + 1;
        }else if(position <= 8){
            return victoryPoints + 2;
        }else if(position <= 11){
            return victoryPoints + 4;
        }else if(position <= 14){
            return victoryPoints + 6;
        }else if(position <= 17){
            return victoryPoints + 9;
        }else if(position <= 20){
            return victoryPoints + 12;
        }else if(position <= 23){
            return victoryPoints + 16;
        }else return victoryPoints + 20;

    }

}
