package it.polimi.ingsw.View.InteractionTranslator;

/**
 * This class implements BuildMessage and provides the method to build a DoProduction message
 */
public class BuildDoProduction implements BuildMessage{

    /**
     * This methods builds a DoProduction message
     * @param interactionTranslator it contains all the information useful to build the message
     * @return the message built
     */
    @Override
    public String buildMessage(InteractionTranslator interactionTranslator) {
        return interactionTranslator.buildDoProduction();
    }
}
