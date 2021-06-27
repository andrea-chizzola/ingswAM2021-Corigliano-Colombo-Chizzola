package it.polimi.ingsw.View.InteractionTranslator;

/**
 * This class implements BuildMessage and provides the method to build a SelectedResources message
 */
public class BuildSelectedResources implements BuildMessage{

    /**
     * This methods builds a SelectedResources message
     * @param interactionTranslator it contains all the information useful to build the message
     * @return the message built
     */
    @Override
    public String buildMessage(InteractionTranslator interactionTranslator) {
        return interactionTranslator.buildSelectedResources();
    }
}
