package it.polimi.ingsw;

import java.util.Objects;

/**
 * public class representing development cards and inheriting from abstract class Card
 */
public class DevelopmentCard extends Card{

    /**
     * DevColor represents the color associated to a development card, one between GREEN, PURPLE, BLUE and YELLOW
     * level represents the level associated to a development card, an integer from 1 to 3
     */
    private final DevColor color;
    private final int level;

    /**
     * @param victoryPoint indicates the victory points associated to the card
     * @param specialEffect indicates the production of a development card or the special effect of a leader card
     * @param requirements indicates the necessary requirements to buy the card
     * @param color indicates the color associated to the development card
     * @param level indicates the level associated to the development card
     */
    public DevelopmentCard(int victoryPoint, SpecialEffect specialEffect, Requirements requirements, DevColor color, int level) {

        super(victoryPoint, specialEffect, requirements);
        this.color = color;
        this.level = level;

    }

    /**
     * @return returns the color of the development card, one between BLUE, GREY, YELLOW and PURPLE
     */
    public DevColor getCardColor(){
        return color;
    }

    /**
     * @return returns the level of the development card which goes from 1 to 3
     */
    public int getCardLevel(){
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return level == that.level &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, level);
    }
}
