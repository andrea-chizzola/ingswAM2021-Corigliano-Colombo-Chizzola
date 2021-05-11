package it.polimi.ingsw;
import it.polimi.ingsw.Exceptions.IllegalShelfException;
import it.polimi.ingsw.Messages.ItemStatus;
import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.Boards.Warehouse;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleBlue;
import it.polimi.ingsw.Model.MarketBoard.MarbleGray;
import it.polimi.ingsw.Model.MarketBoard.MarbleRed;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.CLI;
import it.polimi.ingsw.View.CLIColors;
import it.polimi.ingsw.View.CLIPainter;
import it.polimi.ingsw.View.ViewModel;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CLITest {
    private CLI cli;
    private ViewModel model;
    private List<String> players;

    @BeforeEach
    public void initializeTest(){
        players = new LinkedList<>();
        players.add("test1");
        players.add("test2");
        model = new ViewModel("defaultConfiguration.xml", players);
        cli = new CLI(model);
        System.out.println(" ");
    }

    @Test
    public void mainSightTest(){
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
        cli.showFaithUpdate(faith, sections, Optional.empty(), Optional.empty());

        Map<Integer, ResQuantity> warehouse = new HashMap<>();
        warehouse.put(1, new ResQuantity(new Coin(), 1));
        warehouse.put(2, new ResQuantity(new Faith(), 1));
        warehouse.put(3, new ResQuantity(new Stone(), 2));
        warehouse.put(4, new ResQuantity(new Coin(),5));
        warehouse.put(5, new ResQuantity(new Stone(),3));
        cli.showWarehouseUpdate(warehouse);

        List<ResQuantity> status = new LinkedList<>();
        status.add(new ResQuantity(new Coin(),5));
        status.add(new ResQuantity(new Stone(),3));
        status.add(new ResQuantity(new Servant(),8));
        status.add(new ResQuantity(new Shield(),4));
        cli.showStrongboxUpdate(status);

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

        cli.showPersonalProduction();
        cli.showMarketUpdate(marbles);

        List<DevelopmentCard> cardsD = ConfigurationParser.parseDevelopmentCard("defaultConfiguration.xml");
        List<LeaderCard> cardsL = ConfigurationParser.parseLeaderCard("defaultConfiguration.xml");
        List<Action> tokens = ConfigurationParser.parseActionTokens("defaultConfiguration.xml");

        Map<Integer, String> slots = new HashMap<>();
        slots.put(1, cardsD.get(5).getId());
        slots.put(2, cardsD.get(1).getId());
        slots.put(3, cardsD.get(2).getId());
        cli.showSlotsUpdate(slots);

        Map<String, ItemStatus> leaders = new HashMap<>();
        leaders.put("1", ItemStatus.ACTIVE);
        leaders.put("2", ItemStatus.INACTIVE);
        cli.showLeaderCards(leaders);



        cli.showTopToken(Optional.of("1"));

        cli.plot();
    }

}
