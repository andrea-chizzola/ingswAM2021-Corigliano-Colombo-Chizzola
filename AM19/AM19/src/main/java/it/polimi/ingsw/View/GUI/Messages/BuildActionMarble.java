package it.polimi.ingsw.View.GUI.Messages;

public class BuildActionMarble implements BuildMessage{

    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildActionMarble();
    }
}
