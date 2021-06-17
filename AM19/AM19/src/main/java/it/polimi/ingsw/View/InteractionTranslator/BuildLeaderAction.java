package it.polimi.ingsw.View.InteractionTranslator;

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
