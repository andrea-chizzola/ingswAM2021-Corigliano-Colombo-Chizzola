package it.polimi.ingsw.CLITest;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleBlue;
import it.polimi.ingsw.Model.MarketBoard.MarbleGray;
import it.polimi.ingsw.Model.MarketBoard.MarbleRed;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.CLI.CLIColors;
import it.polimi.ingsw.View.CLI.CLIPainter;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.*;

import java.util.*;

public class CLIPainterTest {

    public static void plot(String[][] data){
        for (int i = 0; i < data.length; i++) {
            System.out.println();
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]);
            }
        }
    }

    public static void fill(String[][] data){
        for (String[] s : data) {
            Arrays.fill(s, CLIColors.RESET.getColor() + " ");
        }
    }

    @Test
    public void printLogoTest(){
        CLIPainter.printLogo();
    }

    @Test
    public void warehouseStructure(){
        String[][] test = new String[50][50];
        fill(test);

        List<ResQuantity> colors = new LinkedList<>();
        colors.add(new ResQuantity(new Coin(), 1));
        colors.add(new ResQuantity(new Faith(), 1));
        colors.add(new ResQuantity(new Stone(), 2));


        CLIPainter.paintWarehouse(test, 0, 0, ConfigurationParser.getCapacityWarehouse("defaultConfiguration.xml"), colors);
        plot(test);
    }

    @Test
    public void personalProduction1(){
        String[][] test = new String[100][100];
        fill(test);

        CLIPainter.paintPersonalProduction(test, 0, 0, new LinkedList<ResQuantity>(), new LinkedList<ResQuantity>(), 2, 1);
        plot(test);

    }

    @Test
    public void personalProduction2(){
        String[][] test = new String[100][100];
        fill(test);
        List<ResQuantity> materials = new LinkedList<>();
        List<ResQuantity> products = new LinkedList<>();
        materials.add(new ResQuantity(new Shield(), 2));
        materials.add(new ResQuantity(new Coin(), 1));
        materials.add(new ResQuantity(new Faith(), 1));
        products.add(new ResQuantity(new Stone(), 1));

        CLIPainter.paintPersonalProduction(test, 0, 0, materials, products, 1, 2);
        plot(test);

    }

    @Test
    public void token(){
        String[][] test = new String[100][100];
        fill(test);
        CLIPainter.paintToken(test, 0,0,"Action Token: \nDiscard: 3 Color: Yellow");
        plot(test);
    }

    @Test
    public void marketBoard(){
        String[][] test = new String[100][100];
        List<Marble> marbles = new LinkedList<>();
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleRed());
        marbles.add(new MarbleGray());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleRed());
        marbles.add(new MarbleGray());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleBlue());
        marbles.add(new MarbleRed());
        marbles.add(new MarbleGray());
        marbles.add(new MarbleGray());
        fill(test);
        CLIPainter.paintMarketBoard(test, 0,0 , marbles, 3, 4);
        plot(test);
    }

    @Test
    public void paintFaithTrack(){
        String[][] test = new String[50][150];
        fill(test);
        List<Integer> track = ConfigurationParser.parseTrack("defaultConfiguration.xml");
        List<String> players = new LinkedList<>();
        players.add("test1");
        players.add("test2");
        Map<String, Integer> faith = new HashMap<>();
        Map<String, List<ItemStatus>> sections = new HashMap<>();
        faith.put("test1", 1);
        faith.put("test2", 19);
        List<ItemStatus> test1 = new LinkedList<>();
        test1.add(ItemStatus.ACTIVE);
        test1.add(ItemStatus.ACTIVE);
        test1.add(ItemStatus.DISCARDED);
        List<ItemStatus> test2 = new LinkedList<>();
        test2.add(ItemStatus.DISCARDED);
        test2.add(ItemStatus.INACTIVE);
        test2.add(ItemStatus.DISCARDED);
        sections.put("test1", test1);
        sections.put("test2", test2);
        List<Integer> start = new LinkedList<>();
        start.add(5);
        start.add(12);
        start.add(19);
        List<Integer> end = new LinkedList<>();
        end.add(8);
        end.add(16);
        end.add(24);
        List<Integer> sectionPoints = new LinkedList<>();
        sectionPoints.add(2);
        sectionPoints.add(3);
        sectionPoints.add(4);
        CLIPainter.paintFaithTrack(test, 0,0 , track,
                players, faith, start, end);
        CLIPainter.paintPopeFavours(test, 0, 0, players.size(), track, sectionPoints);
        CLIPainter.paintSectionsStatus(test, 0, 0, sections, players);
        plot(test);

    }

    @Test
    public void paintStrongBox(){
        String[][] test = new String[50][150];
        fill(test);
        List<ResQuantity> status = new LinkedList<>();
        status.add(new ResQuantity(new Coin(),5));
        status.add(new ResQuantity(new Stone(),3));
        status.add(new ResQuantity(new Servant(),8));
        status.add(new ResQuantity(new Shield(),4));


        CLIPainter.paintStrongbox(test, 0,0 , status);
        CLIPainter.paintStrongbox(test, 10,15 , status);
        plot(test);
    }


    @Test
    public void paintExtraSlot(){
        String[][] test = new String[50][150];
        fill(test);
        List<ResQuantity> status = new LinkedList<>();
        status.add(new ResQuantity(new Coin(),5));
        status.add(new ResQuantity(new Stone(),3));

        CLIPainter.paintExtraSlots(test, 0,0 , status);
        plot(test);
    }

    @Test
    public void paintTwoCards(){
        String[][] test = new String[50][150];
        fill(test);
        List<LeaderCard> cardsL = ConfigurationParser.parseLeaderCard("defaultConfiguration.xml");
        List<DevelopmentCard> cardsD = ConfigurationParser.parseDevelopmentCard("defaultConfiguration.xml");
        Random rand = new Random();
        int x = rand.nextInt(cardsL.size());
        int y = rand.nextInt(cardsD.size());

        CLIPainter.leaderCardPainter(test,0, 0, cardsL.get(x).toString());
        CLIPainter.devCardPainter(test,0, 30, cardsD.get(y).toString());

        plot(test);
    }

    @Test
    public void paintAllCards(){
        List<LeaderCard> cardsL = ConfigurationParser.parseLeaderCard("defaultConfiguration.xml");
        List<DevelopmentCard> cardsD = ConfigurationParser.parseDevelopmentCard("defaultConfiguration.xml");

        for(int x=0; x<cardsL.size(); x++){
            String[][] test = new String[50][50];
            fill(test);
            CLIPainter.leaderCardPainter(test,0, 0, cardsL.get(x).toString());
            plot(test);
        }

        for(int y=0; y<cardsD.size(); y++){
            String[][] test = new String[50][50];
            fill(test);
            CLIPainter.devCardPainter(test,0, 0, cardsD.get(y).toString());
            plot(test);
        }
    }

}
