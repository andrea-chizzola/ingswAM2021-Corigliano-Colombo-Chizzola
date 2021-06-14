package it.polimi.ingsw.View.GUI.Messages;

public class BuildDoProduction implements BuildMessage{

    /**
     * This methods builds a DoProduction message
     * @param accumulator it contains all the information useful to build the message
     * @return the message built
     */
    @Override
    public String buildMessage(Accumulator accumulator) {
        return accumulator.buildDoProduction();
    }
}
