package it.polimi.ingsw.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"tray","number"})
public class MessageMarketSelection extends Message {

    /**
     * ENUM which represents the possible choices of the player for the market tray
     */
    public enum Tray {ROW, COLUMN}

    /**
     * the choice of the player for the market tray
     */
    @JsonProperty("tray")
    private Tray tray;

    /**
     * the number or the row or column
     */
    @JsonProperty("number")
    private int number;

    /**
     * it sets MessageType as MARKET_SELECTION
     * @param tray the choice of the player for the market tray
     * @param number the number or the row or column
     */
    @JsonCreator
    public MessageMarketSelection(@JsonProperty("tray") Tray tray, @JsonProperty("number") int number) {
        super("Selection of a row or a column from the market.", MessageType.MARKET_SELECTION);
        this.tray = tray;
        this.number = number;
    }

    /**
     * @return the choice of the player for the market tray
     */
    @JsonIgnore
    public Tray getTray() {
        return tray;
    }

    /**
     * @return the number or the row or column
     */
    @JsonIgnore
    public int getNumber() {
        return number;
    }
}
