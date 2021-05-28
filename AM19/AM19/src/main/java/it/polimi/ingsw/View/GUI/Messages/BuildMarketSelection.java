package it.polimi.ingsw.View.GUI.Messages;

public class BuildMarketSelection implements BuildMessage{

    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildMarketSelection();
    }
}
