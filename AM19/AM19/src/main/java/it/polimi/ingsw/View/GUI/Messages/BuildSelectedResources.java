package it.polimi.ingsw.View.GUI.Messages;

public class BuildSelectedResources implements BuildMessage{

    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildSelectedResources();
    }
}
