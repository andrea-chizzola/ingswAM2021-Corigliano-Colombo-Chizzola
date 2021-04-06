package it.polimi.ingsw;

import java.util.Objects;

/**
 * this class represents the effect 'change the color of a white marble' of a LeaderCard
 */
public class WhiteMarble implements SpecialEffect{
    /**
     * this attribute represent the resource corresponding to the new color of the marble
     */
    private Resource resource;

    public WhiteMarble(Resource resource){
        this.resource=resource;
    }
    @Override
    public void applyEffect(Board board) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhiteMarble that = (WhiteMarble) o;
        return Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }
}
