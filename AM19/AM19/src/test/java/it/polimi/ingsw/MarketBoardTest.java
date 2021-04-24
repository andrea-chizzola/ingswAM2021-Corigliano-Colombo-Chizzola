package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.IllegalMarketException;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarketBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketBoardTest {
    private final String file = "defaultConfiguration.xml";
    private MarketBoard market;

    @BeforeEach
    public void setUp(){
        market = new MarketBoard(file);
    }


    @Test
    public void constructor(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        int white = 0;
        int blue = 0;
        int red = 0;
        int yellow = 0;
        int purple = 0;
        int gray = 0;

        for(Marble marble : tray){
            if(marble.toString().equals("MarbleWhite"))
                white++;
            if(marble.toString().equals("MarbleRed"))
                red++;
            if(marble.toString().equals("MarbleBlue"))
                blue++;
            if(marble.toString().equals("MarblePurple"))
                purple++;
            if(marble.toString().equals("MarbleYellow"))
                yellow++;
            if(marble.toString().equals("MarbleGray"))
                gray++;
        }

        Marble marble = market.getSlide();
        if(marble.toString().equals("MarbleWhite"))
            white++;
        if(marble.toString().equals("MarbleRed"))
            red++;
        if(marble.toString().equals("MarbleBlue"))
            blue++;
        if(marble.toString().equals("MarblePurple"))
            purple++;
        if(marble.toString().equals("MarbleYellow"))
            yellow++;
        if(marble.toString().equals("MarbleGray"))
            gray++;

        assertEquals(white,4);
        assertEquals(red,1);
        assertEquals(blue,2);
        assertEquals(purple,2);
        assertEquals(yellow,2);
        assertEquals(gray,2);

    }

    @Test
    public void testGetColumnIllegal(){
        ArrayList<Marble> tray;
        tray = market.getTray();

        Marble slide = market.getSlide();


        Exception exception;
        exception = assertThrows(IllegalMarketException.class, () ->market.getColumn(5));
        assertEquals(exception.getMessage(), "This column does not exist!");


        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }



    @Test
    public void testGetColumn0(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(0);
        Marble marble2 = tray.get(4);
        Marble marble3 = tray.get(8);
        Marble slide = market.getSlide();


        try{col = market.getColumn(0);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }

    @Test
    public void testGetColumn1(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(1);
        Marble marble2 = tray.get(5);
        Marble marble3 = tray.get(9);
        Marble slide = market.getSlide();


        try{col = market.getColumn(1);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }

    @Test
    public void testGetColumn2(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(2);
        Marble marble2 = tray.get(6);
        Marble marble3 = tray.get(10);
        Marble slide = market.getSlide();


        try{col = market.getColumn(2);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }

    @Test
    public void testGetColumn3(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(3);
        Marble marble2 = tray.get(7);
        Marble marble3 = tray.get(11);
        Marble slide = market.getSlide();


        try{col = market.getColumn(3);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }

    @Test
    public void testTakeColumnIllegal(){
        ArrayList<Marble> tray;
        tray = market.getTray();

        Marble slide = market.getSlide();


        Exception exception;
        exception = assertThrows(IllegalMarketException.class, () ->market.takeColumn(5));
        assertEquals(exception.getMessage(), "This column does not exist!");


        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }

    @Test
    public void testTakeColumn0(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(0);
        Marble marble2 = tray.get(4);
        Marble marble3 = tray.get(8);
        Marble slide = market.getSlide();


        try{col = market.takeColumn(0);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        assertEquals(tray.get(4).toString(),trayNew.get(0).toString());
        assertEquals(tray.get(8).toString(),trayNew.get(4).toString());
        assertEquals(slide.toString(),trayNew.get(8).toString());
        assertEquals(tray.get(0).toString(),market.getSlide().toString());


        for(int i=0; i<tray.size(); i++){
            if(i!=0 && i!=4 && i!=8)
                 assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }


    }

    @Test
    public void testTakeColumn1(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(1);
        Marble marble2 = tray.get(5);
        Marble marble3 = tray.get(9);
        Marble slide = market.getSlide();


        try{col = market.takeColumn(1);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        assertEquals(tray.get(5).toString(),trayNew.get(1).toString());
        assertEquals(tray.get(9).toString(),trayNew.get(5).toString());
        assertEquals(slide.toString(),trayNew.get(9).toString());
        assertEquals(tray.get(1).toString(),market.getSlide().toString());


        for(int i=0; i<tray.size(); i++){
            if(i!=1 && i!=5 && i!=9)
                assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }


    }

    @Test
    public void testTakeColumn2(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(2);
        Marble marble2 = tray.get(6);
        Marble marble3 = tray.get(10);
        Marble slide = market.getSlide();


        try{col = market.takeColumn(2);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        assertEquals(tray.get(6).toString(),trayNew.get(2).toString());
        assertEquals(tray.get(10).toString(),trayNew.get(6).toString());
        assertEquals(slide.toString(),trayNew.get(10).toString());
        assertEquals(tray.get(2).toString(),market.getSlide().toString());


        for(int i=0; i<tray.size(); i++){
            if(i!=2 && i!=6 && i!=10)
                assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }


    }

    @Test
    public void testTakeColumn3(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(3);
        Marble marble2 = tray.get(7);
        Marble marble3 = tray.get(11);
        Marble slide = market.getSlide();


        try{col = market.takeColumn(3);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        assertEquals(tray.get(7).toString(),trayNew.get(3).toString());
        assertEquals(tray.get(11).toString(),trayNew.get(7).toString());
        assertEquals(slide.toString(),trayNew.get(11).toString());
        assertEquals(tray.get(3).toString(),market.getSlide().toString());


        for(int i=3; i<tray.size(); i++){
            if(i!=3 && i!=7 && i!=11)
                assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }


    }


    @Test
    public void testGetRowIllegal(){
        ArrayList<Marble> tray;
        tray = market.getTray();

        Marble slide = market.getSlide();


        Exception exception;
        exception = assertThrows(IllegalMarketException.class, () ->market.getRow(3));
        assertEquals(exception.getMessage(), "This row does not exist!");


        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }
    @Test
    public void testGetRow0(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(0);
        Marble marble2 = tray.get(1);
        Marble marble3 = tray.get(2);
        Marble marble4 = tray.get(3);
        Marble slide = market.getSlide();


        try{col = market.getRow(0);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());
        assertEquals(col.get(3).toString(),marble4.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());
    }

    @Test
    public void testGetRow1(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(4);
        Marble marble2 = tray.get(5);
        Marble marble3 = tray.get(6);
        Marble marble4 = tray.get(7);
        Marble slide = market.getSlide();


        try{col = market.getRow(1);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());
        assertEquals(col.get(3).toString(),marble4.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());
    }


    @Test
    public void testGetRow2(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(8);
        Marble marble2 = tray.get(9);
        Marble marble3 = tray.get(10);
        Marble marble4 = tray.get(11);
        Marble slide = market.getSlide();


        try{col = market.getRow(2);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());
        assertEquals(col.get(3).toString(),marble4.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());
    }


    @Test
    public void testTakeRowIllegal(){
        ArrayList<Marble> tray;
        tray = market.getTray();

        Marble slide = market.getSlide();


        Exception exception;
        exception = assertThrows(IllegalMarketException.class, () ->market.takeRow(3));
        assertEquals(exception.getMessage(), "This row does not exist!");


        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        for(int i=0; i<tray.size(); i++){
            assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }
        assertEquals(slide.toString(),market.getSlide().toString());

    }
   @Test
   public void TakeRow0(){
       ArrayList<Marble> tray;
       tray = market.getTray();
       ArrayList<Marble> col = null;
       Marble marble1 = tray.get(0);
       Marble marble2 = tray.get(1);
       Marble marble3 = tray.get(2);
       Marble marble4 = tray.get(3);
       Marble slide = market.getSlide();


       try{col = market.takeRow(0);}
       catch (IllegalMarketException e){fail();}

       assertEquals(col.get(0).toString(),marble1.toString());
       assertEquals(col.get(1).toString(),marble2.toString());
       assertEquals(col.get(2).toString(),marble3.toString());
       assertEquals(col.get(3).toString(),marble4.toString());

       ArrayList<Marble> trayNew;
       trayNew = market.getTray();

       assertEquals(tray.get(1).toString(),trayNew.get(0).toString());
       assertEquals(tray.get(2).toString(),trayNew.get(1).toString());
       assertEquals(tray.get(3).toString(),trayNew.get(2).toString());
       assertEquals(slide.toString(),trayNew.get(3).toString());
       assertEquals(tray.get(0).toString(),market.getSlide().toString());


       for(int i=0; i<tray.size(); i++){
           if(i!=0 && i!=1 && i!=2 && i!=3)
               assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
       }

   }

    @Test
    public void TakeRow1(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(4);
        Marble marble2 = tray.get(5);
        Marble marble3 = tray.get(6);
        Marble marble4 = tray.get(7);
        Marble slide = market.getSlide();


        try{col = market.takeRow(1);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());
        assertEquals(col.get(3).toString(),marble4.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        assertEquals(tray.get(5).toString(),trayNew.get(4).toString());
        assertEquals(tray.get(6).toString(),trayNew.get(5).toString());
        assertEquals(tray.get(7).toString(),trayNew.get(6).toString());
        assertEquals(slide.toString(),trayNew.get(7).toString());
        assertEquals(tray.get(4).toString(),market.getSlide().toString());


        for(int i=0; i<tray.size(); i++){
            if(i!=4 && i!=5 && i!=6 && i!=7)
                assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }

    }

    @Test
    public void TakeRow2(){
        ArrayList<Marble> tray;
        tray = market.getTray();
        ArrayList<Marble> col = null;
        Marble marble1 = tray.get(8);
        Marble marble2 = tray.get(9);
        Marble marble3 = tray.get(10);
        Marble marble4 = tray.get(11);
        Marble slide = market.getSlide();


        try{col = market.takeRow(2);}
        catch (IllegalMarketException e){fail();}

        assertEquals(col.get(0).toString(),marble1.toString());
        assertEquals(col.get(1).toString(),marble2.toString());
        assertEquals(col.get(2).toString(),marble3.toString());
        assertEquals(col.get(3).toString(),marble4.toString());

        ArrayList<Marble> trayNew;
        trayNew = market.getTray();

        assertEquals(tray.get(9).toString(),trayNew.get(8).toString());
        assertEquals(tray.get(10).toString(),trayNew.get(9).toString());
        assertEquals(tray.get(11).toString(),trayNew.get(10).toString());
        assertEquals(slide.toString(),trayNew.get(11).toString());
        assertEquals(tray.get(8).toString(),market.getSlide().toString());


        for(int i=0; i<tray.size(); i++){
            if(i!=8 && i!=9 && i!=10 && i!=11)
                assertEquals(tray.get(i).toString(),trayNew.get(i).toString());
        }

    }


}