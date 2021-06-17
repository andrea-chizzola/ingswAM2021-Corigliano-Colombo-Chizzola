package it.polimi.ingsw.View.InteractionTranslator;

public class BuildSwap implements BuildMessage{


    /**
     * This methods builds a Swap message
     * @param interactionTranslator it contains all the information useful to build the message
     * @return the message built
     */
    @Override
    public String buildMessage(InteractionTranslator interactionTranslator) {
        return interactionTranslator.buildSwap();
    }
}
