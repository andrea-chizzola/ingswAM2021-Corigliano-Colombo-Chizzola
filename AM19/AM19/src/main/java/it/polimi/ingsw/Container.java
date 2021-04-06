package it.polimi.ingsw;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This public class represents a generic Deck.
 * @param <T> in our project, T can be DevelopmentCards, LeaderCards, ActionToken or PopeFavor
 */
public class Container<T> {

    /**
     * this attribute is a list of all the items of the deck
     */
    private LinkedList<T> rep;

    public Container(){
        rep = new LinkedList<>();
    }

    /**
     *
     * this function add all the items of a list inside the container
     */
    public void addAll(List<T> elements){
        rep.addAll(elements);
    }

    /**
     *
     * @return the number of items in the deck
     */
    public int size(){
        return rep.size();
    }

    /**
     *
     * @return the first element of the deck (the one in position 0).
     * @throws IllegalArgumentException when the deck is empty
     */
    public T readTop() throws IllegalArgumentException{
        if(this.size() == 0) throw new IllegalArgumentException("The deck is empty");
        return rep.get(0);
    }

    /**
     * this method extracts the first element of the deck
     * @return the first element of the deck
     * @throws IllegalArgumentException when the deck is empty
     */
    public T extractTop() throws IllegalArgumentException{
        if(this.size() == 0) throw new IllegalArgumentException("The deck is empty");
        return rep.remove(0);
    }

    /**
     *
     * @param num is the number of items to be extracted
     * @return the extracted items
     * @throws IllegalArgumentException when the deck is empty
     */
    public LinkedList<T> extractTop(int num) throws IllegalArgumentException{

        if(this.size() == 0) throw new IllegalArgumentException("The deck is empty");

        LinkedList<T> extracted = new LinkedList<>();
        while(num>0 && this.size()>0){
            extracted.add(rep.remove(0));
            num--;
        }
        return extracted;
    }

    /**
     * this method shuffle the items in the deck.
     */
    public void shuffle(){
        Collections.shuffle(rep);
    }

}
