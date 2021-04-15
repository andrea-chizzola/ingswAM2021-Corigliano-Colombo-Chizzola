package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalMarketException;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarketBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MarketBoardTest {
    private final String file = "defaultConfiguration.xml";
    private MarketBoard market;

    @BeforeEach
    public void setUp(){
        market = new MarketBoard(file);
    }
    @Test
    public void testConstructor(){
        market.getTray().stream().forEach(System.out::println);
    }

    @Test
    public void testConstructor1(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        int i,j;
        for(i=0;i<3;i++) {
            for (j = 0; j < 4; j++)
                System.out.print(tray.get(j+i*4).toString()+"    ");
            System.out.println();
        }
        System.out.println();
        System.out.println(market.getSlide());
    }

    @Test
    public void testGetColumn(){
        ArrayList<Marble> tray;
        ArrayList<Marble> col = null;
        tray = market.getTray();
        int i,j;
        for(i=0;i<3;i++) {
            for (j = 0; j < 4; j++)
                System.out.print(tray.get(j+i*4).toString()+"    ");
            System.out.println();
        }
        System.out.println();
        System.out.println(market.getSlide());
        System.out.println();

        try{col = market.getColumn(0);}
        catch (IllegalMarketException e){System.out.println(e.getMessage());}
        col.stream().forEach(System.out::println);

    }

    @Test
    public void testTakeColumn(){
        ArrayList<Marble> tray;
        ArrayList<Marble> col = null;
        tray = market.getTray();
        int i,j;
        for(i=0;i<3;i++) {
            for (j = 0; j < 4; j++)
                System.out.print(tray.get(j+i*4).toString()+"    ");
            System.out.println();
        }
        System.out.println();
        System.out.println(market.getSlide());
        System.out.println();

        try{col = market.takeColumn(0);}
        catch (IllegalMarketException e){System.out.println(e.getMessage());}
        col.stream().forEach(System.out::println);

        System.out.println();
        tray = market.getTray();
        for(i=0;i<3;i++) {
            for (j = 0; j < 4; j++)
                System.out.print(tray.get(j+i*4).toString()+"    ");
            System.out.println();
        }
        System.out.println();
        System.out.println(market.getSlide());
        System.out.println();


    }


    @Test
    public void testGetRow() {
        ArrayList<Marble> tray;
        ArrayList<Marble> row = null;
        tray = market.getTray();
        int i, j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 4; j++)
                System.out.print(tray.get(j + i * 4).toString() + "    ");
            System.out.println();
        }
        System.out.println();
        System.out.println(market.getSlide());
        System.out.println();

        try {
            row = market.getRow(0);
        } catch (IllegalMarketException e) {
            System.out.println(e.getMessage());
        }
        for (Marble marble : row) {
            System.out.print(marble + "   ");
        }
    }

        @Test
        public void testTakeRow(){
            ArrayList<Marble> tray;
            ArrayList<Marble> row = null;
            tray = market.getTray();
            int i,j;
            for(i=0;i<3;i++) {
                for (j = 0; j < 4; j++)
                    System.out.print(tray.get(j+i*4).toString()+"    ");
                System.out.println();
            }
            System.out.println();
            System.out.println(market.getSlide());
            System.out.println();

            try{row = market.takeRow(0);}
            catch (IllegalMarketException e){System.out.println(e.getMessage());}
            for (Marble marble : row) {
                System.out.print(marble + "   ");
            }
            System.out.println();
            tray = market.getTray();
            System.out.println();
            for(i=0;i<3;i++) {
                for (j = 0; j < 4; j++)
                    System.out.print(tray.get(j+i*4).toString()+"    ");
                System.out.println();
            }
            System.out.println();
            System.out.println(market.getSlide());
            System.out.println();
    }
}