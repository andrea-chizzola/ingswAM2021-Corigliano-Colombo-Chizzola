package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Boards.Board;
import it.polimi.ingsw.Model.Resources.ResQuantity;
import it.polimi.ingsw.Model.Resources.Resource;

import java.util.Objects;

/**
 * this class represents the effect 'change the color of a white marble' of a LeaderCard
 */
public class WhiteMarble extends SpecialEffect{
    /**
     * this attribute represent the resource corresponding to the new color of the marble
     */
    private Resource resource;

    public WhiteMarble(Resource resource){
        this.resource=resource;
    }

    /**
     * this method add a modification to a player's board, allowing them to turn
     * white marbles into a target resource
     * @param board represents the board associated to a player
     */
    @Override
    public void applyEffect(Board board) {
        board.getModifications().addMarbleTo(resource);
    }

    /**
     * @return a ResQuantity which represents the special effect
     */
    @Override
    public ResQuantity getSpecialEffect() {
        return new ResQuantity(resource, 0);
    }

    @Override
    public boolean isWhiteMarble() {
        return true;
    }

    @Override
    public String toString(){
        return  "+ new White Marble: \n" +
                resource.toString();
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
