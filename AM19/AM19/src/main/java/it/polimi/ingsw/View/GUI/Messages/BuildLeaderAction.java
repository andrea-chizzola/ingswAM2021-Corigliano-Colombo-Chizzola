package it.polimi.ingsw.View.GUI.Messages;

public class BuildLeaderAction implements BuildMessage{

    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildLeaderAction();
    }
}
