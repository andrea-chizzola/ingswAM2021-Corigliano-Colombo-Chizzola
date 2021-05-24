package it.polimi.ingsw.Messages.Enumerations;

import it.polimi.ingsw.View.CLI.CLIColors;

public enum ItemStatus {
    ACTIVE(CLIColors.F_GREEN.getColor() + "✚", true),
    INACTIVE(CLIColors.F_BLACK.getColor() + "✚", false),
    DISCARDED(CLIColors.F_RED.getColor() + "✚", false);

    private final String faithSymbol;
    private final boolean value;

    ItemStatus(String faithSymbol, boolean value){
        this.faithSymbol = faithSymbol;
        this.value=value;
    }

    public String getFaithSymbol(){
        return faithSymbol;
    }

    public boolean getBoolValue() {
        return value;
    }
}
