package it.polimi.ingsw.Model.ActionTokens;

import it.polimi.ingsw.Model.Boards.SinglePlayer;

import java.util.Objects;

/**
 * public interface implemented by the actions made possible only in a single player match
 */
public abstract class Action {
    /**
     * this attribute represents the ID of the ActionToken
     */
    private String id;

    /**
     * this attribute represent the image path of the ActionToken
     */
    private String image;

    /**
     * this method is the constructor of the class
     * @param id is the id of the ActionToken
     */
    public Action(String id, String image){
        this.id = id;
        this.image = image;
    }

    /**
     * performs the action related to the action token picked
     * @param singlePlayer is the board of Lorenzo il Magnifico
     */
    public abstract void doAction(SinglePlayer singlePlayer);

    /**
      * @return the id of the ActionToken
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(id, action.id) &&
                Objects.equals(image, action.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image);
    }
}
