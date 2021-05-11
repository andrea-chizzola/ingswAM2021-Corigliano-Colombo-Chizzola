package it.polimi.ingsw.Messages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UpdateFaithTrack extends UpdateGame{

    /**
     * this attribute represents the faith of a set of players
     */
    Map<String, Integer> faith;

    /**
     * the following attributes are used to represent the state of a set of players' VaticanReportSections
     * nickname represents a list of nicknames.
     * section represents a list of VaticanReportSections
     * status represents the current state of these sections.
     */
    List<String> nickname;
    List<Integer> section;
    List<ItemStatus> status;

    /**
     * this method is the constructor of the class
     * @param nickname is the list of nickname associated to the changed sections
     * @param section is a list of positions of the changed sections
     * @param status is the new status of the changed sections
     * @param faith is a map that contains the faith obtained by the players.
     */
    public UpdateFaithTrack(List<String> nickname, List<Integer> section, List<ItemStatus> status, Map<String, Integer> faith) {
        super("Update of the top ActionToken and of Lorenzo's faith", UpdateGameType.FAITH);
        this.nickname = new LinkedList<>(nickname);
        this.section = new LinkedList<>(section);
        this.status = new LinkedList<>(status);
        this.faith = new HashMap<>(faith);
    }

    /**
     * @return a copy of the attribute faith.
     */
    public Map<String, Integer> getFaith(){
        return new HashMap<>(faith);
    }

    /**
     * @return a copy of the attribute nickname
     */
    public List<String> getNickname(){
        return new LinkedList<>(nickname);
    }

    /**
     * @return a copy of the attribute section
     */
    public List<Integer> getSection(){
        return new LinkedList<>(section);
    }

    /**
     * @return a copy of the attribute status
     */
    public List<ItemStatus> getItemStatus(){
        return new LinkedList<>(status);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        StringBuilder faithString = new StringBuilder();
        for(String s :faith.keySet()){
            faithString.append(s).append(faith.get(s)).append('\'');
        }
        StringBuilder sectionString = new StringBuilder();
        for(int i=0; i<nickname.size(); i++){
            sectionString.append(nickname.get(i)).append(section.get(i)).append(status.get(i)).append('\'');
        }
        return "Message{" +
                "body='" + getBody() + '\'' +
                "faith='" + faithString + '\'' +
                "sections='" + sectionString + '\'' +
                ", messageType=" + getMessageType() +
                '}';
    }
}
