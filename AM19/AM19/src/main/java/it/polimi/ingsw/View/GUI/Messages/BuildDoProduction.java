package it.polimi.ingsw.View.GUI.Messages;

public class BuildDoProduction implements BuildMessage{
    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildDoProduction();
    }
}
