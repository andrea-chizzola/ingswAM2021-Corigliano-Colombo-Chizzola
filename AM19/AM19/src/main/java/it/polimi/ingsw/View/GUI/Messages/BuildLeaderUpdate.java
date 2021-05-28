package it.polimi.ingsw.View.GUI.Messages;

public class BuildLeaderUpdate implements BuildMessage{

    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildLeaderUpdate();
    }
}
