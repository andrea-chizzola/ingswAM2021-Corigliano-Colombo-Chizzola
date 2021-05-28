package it.polimi.ingsw.View.GUI.Messages;

public class BuildSwap implements BuildMessage{

    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildSwap();
    }
}
