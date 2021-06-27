package it.polimi.ingsw.View.InteractionTranslator;

/**
 * This class implements BuildMessage and provides the method to build a LeaderAction message
 */
public class BuildLeaderAction implements BuildMessage{

    /**
     * This methods builds a LeaderAction message
     * @param interactionTranslator it contains all the information useful to build the message
     * @return the message built
     */
    @Override
    public String buildMessage(InteractionTranslator interactionTranslator) {
        return interactionTranslator.buildLeaderAction();
    }
}
