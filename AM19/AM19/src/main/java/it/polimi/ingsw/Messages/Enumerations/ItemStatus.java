package it.polimi.ingsw.Messages.Enumerations;

import it.polimi.ingsw.View.CLIColors;

public enum ItemStatus {
    ACTIVE(CLIColors.F_GREEN.getColor() + "✚"),
    INACTIVE(CLIColors.F_BLACK.getColor() + "✚"),
    DISCARDED(CLIColors.F_RED.getColor() + "✚");

    private final String faithSymbol;

    ItemStatus(String faithSymbol){
        this.faithSymbol = faithSymbol;
    }

    public String getFaithSymbol(){
        return faithSymbol;
    }
}
