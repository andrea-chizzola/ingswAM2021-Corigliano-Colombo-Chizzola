package it.polimi.ingsw.View.InteractionTranslator;

/**
 * This class implements BuildMessage and provides the method to build a ActionMarble message
 */
public class BuildActionMarble implements BuildMessage{

    /**
     * This methods builds an ActionMarble message
     * @param interactionTranslator it contains all the information useful to build the message
     * @return the message built
     */
    @Override
    public String buildMessage(InteractionTranslator interactionTranslator) {
        return interactionTranslator.buildActionMarble();
    }
}
