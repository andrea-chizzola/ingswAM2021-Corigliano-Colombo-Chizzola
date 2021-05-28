package it.polimi.ingsw.View.GUI.Messages;

public class BuildBuyCard implements BuildMessage{
    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildBuyCard();
    }
}
