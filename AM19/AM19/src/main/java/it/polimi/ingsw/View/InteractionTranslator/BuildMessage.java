package it.polimi.ingsw.View.InteractionTranslator;

/**
 * This interface provides methods useful to build a message
 */
public interface BuildMessage {

    /**
     * This methods builds a message using the information present in the InteractionTranslator
     * @param interactionTranslator it contains all the information useful to build the message
     * @return String which is the message
     */
    String buildMessage(InteractionTranslator interactionTranslator);
}
