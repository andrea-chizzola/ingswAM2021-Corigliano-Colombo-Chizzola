package it.polimi.ingsw.Model.MarketBoard;

import it.polimi.ingsw.Exceptions.IllegalMarketException;
import it.polimi.ingsw.xmlParser.ConfigurationParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * This class represents the market board which contains the tray with the marbles and the slide.
 */
public class MarketBoard {

    /**
     * nRows represents the number of rows of the tray.
     */
    private int nRows;
    /**
     * nColumns represents the number of columns of the tray.
     */
    private int nColumns;
    /**
     * tray represents the market tray.
     */
    private ArrayList<Marble> tray;
    /**
     * slide represents the marble which is temporarily out of the tray.
     */
    private Marble slide;

    /**
     * This the constructor of this class, no parameters are needed.
     * It crates the tray and the slide and it randomizes the position of the marbles
     * this method asserts that the dimensions of the rows, columns, and the tray size are coherent.
     * The coherence check is made by the parser
     */
    public MarketBoard(String file) {
        nRows = ConfigurationParser.parseMarketRows(file);
        nColumns = ConfigurationParser.parseMarketColumns(file);
        tray = ConfigurationParser.parseMarketTray(file);
        Collections.shuffle(tray);
        slide = tray.remove(nRows*nColumns);
    }

    /**
     * This method gets the marble which is temporarily out of the tray.
     * @return Marble
     */
    public Marble getSlide(){
        return slide;
    }

    /**
     * This method gets a list which contains the row (with index 'row') of the tray.
     * An IllegalMarketException is thrown if the row with index 'row' does not exist.
     * @param row the index of the row
     * @return an ArrayList which contains the row
     * @throws IllegalMarketException if the row does not exists
     */
    public ArrayList<Marble> getRow(int row) throws IllegalMarketException{

        if(row<0 || row>=nRows)
            throw new IllegalMarketException("This row does not exist!");

        ArrayList<Marble> list = new ArrayList<>(nColumns);
        int i;
        int j = row*nColumns;
        int k = j + nColumns - 1;
        for(i=j; i<=k; i++){
            list.add(tray.get(i));
        }
        return list;
    }

    /**
     * This method returns a list which contains the row (with index 'row') of the tray and
     * it modifies the tray and the slide according to the rules of the game.
     * An IllegalMarketException is thrown if the row with index 'row' does not exist.
     * @param row the index of the row
     * @return an ArrayList which contains the row
     * @throws IllegalMarketException if the row does not exist
     */
    public ArrayList<Marble> takeRow(int row) throws IllegalMarketException{

        if(row<0 || row>=nRows)
            throw new IllegalMarketException("This row does not exist!");

        ArrayList<Marble> list = new ArrayList<>(nColumns);
        Marble temp;
        int i;
        int j = row*nColumns;
        int k = j + nColumns - 1;
        temp = tray.get(j);
        for(i=j; i<=k; i++){
            list.add(tray.get(i));
            if(i != k)
                tray.set(i, tray.get(i+1));
        }
        tray.set(k, slide);
        slide = temp;
        return list;
    }

    /**
     *This method gets a list which contains the column (with index 'column') of the tray.
     *An IllegalMarketException is thrown if the column with index 'column' does not exist.
     * @param column the index of the column
     * @return an ArrayList which contains the column
     * @throws IllegalMarketException if the column does not exist
     */
    public ArrayList<Marble> getColumn(int column) throws IllegalMarketException{

        if(column<0 || column>=nColumns)
            throw new IllegalMarketException("This column does not exist!");

        ArrayList<Marble> list = new ArrayList<>(nRows);
        int i;
        for(i=0; i<nRows; i++){
            list.add(tray.get(column + i*nColumns));
        }
        return list;
    }

    /**
     * This method returns a list which contains the column (with index 'column') of the tray and
     * it modifies the tray and the slide according to the rules of the game.
     * An IllegalMarketException is thrown if the column with index 'column' does not exist.
     * @param column the index of the column
     * @return an ArrayList which contains the column
     * @throws IllegalMarketException if the column does not exist
     */
    public ArrayList<Marble> takeColumn(int column) throws IllegalMarketException{

        if(column<0 || column>=nColumns)
            throw new IllegalMarketException("This column does not exist!");

        ArrayList<Marble> list = new ArrayList<>(nRows);
        Marble temp;
        int i;
        int j = nRows-1;
        temp = tray.get(column);
        for(i=0; i<=j; i++){
            list.add(tray.get(column + i*nColumns));
            if(i != j)
                tray.set(column + i*nColumns, tray.get(column + (i+1)*nColumns));
        }
        tray.set(column + j*nColumns, slide);
        slide = temp;
        return list;
    }

    /**
     * This method gets the tray.
     * @return an ArrayList which contains the tray
     */
    public ArrayList<Marble> getTray(){
        ArrayList<Marble> list = new ArrayList<>(tray.stream().collect(Collectors.toList()));
        return list;
    }


}
