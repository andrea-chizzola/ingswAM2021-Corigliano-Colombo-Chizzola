package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.MarketBoard.Marble;

import java.util.LinkedList;
import java.util.List;

public class UpdateMarket extends UpdateGame{

    /**
     * this attribute represents the current state of the MarketBoard tray
     */
    private List<Marble> tray;

    /**
     * this is the constructor of the class
     * @param tray represents the current state of the MarketBoard tray
     */
    public UpdateMarket(List<Marble> tray) {
        super("Update of the MarketBoard", UpdateGameType.MARKET);
        this.tray = new LinkedList<>(tray);
    }

    /**
     * @return a copy of the attribute tray.
     */
    public List<Marble> getTray(){
        return new LinkedList<>(tray);
    }

    /**
     * @return returns a string containing the message details
     */
    @Override
    public String toString(){
        StringBuilder marketString = new StringBuilder();
        for(Marble marble : tray){
            marketString.append(marble.toString()).append('\'');
        }
        return "Message{" +
                "body='" + getBody() + '\'' +
                marketString +
                ", messageType=" + getMessageType() +
                '}';
    }
}
