package it.polimi.ingsw.CLITest;

import it.polimi.ingsw.Client.ReducedModel.ReducedGameBoard;
import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.ActionTokens.Action;
import it.polimi.ingsw.Model.Cards.DevelopmentCard;
import it.polimi.ingsw.Model.Cards.LeaderCard;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.MarketBoard.MarbleBlue;
import it.polimi.ingsw.Model.MarketBoard.MarbleGray;
import it.polimi.ingsw.Model.MarketBoard.MarbleRed;
import it.polimi.ingsw.Model.Resources.*;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.xmlParser.ConfigurationParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CLITest {
    private static CLI cli;
    private ReducedGameBoard model;
    private List<String> players;

    @BeforeEach
    public void initializeTest() {
        players = new LinkedList<>();
        players.add("test1");
        players.add("test2");
        model = new ReducedGameBoard("defaultConfiguration.xml");
        model.initializeNicknames(players);
        cli = new CLI(model, System.in, System.out);
        cli.initialize();
        System.out.println(" ");
    }

    @Test
    public void mainSightTest() {
        Map<String, Integer> faith = new HashMap<>();
        Map<String, List<ItemStatus>> sections = new HashMap<>();
        List<String> nicknames = new LinkedList<>();
        nicknames.add("test1");
        nicknames.add("test2");
        model.setPersonalNickname("test1");
        model.initializeNicknames(nicknames);
        cli.initialize();

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

        List<ResQuantity> warehouse = new LinkedList<>();
        warehouse.add(new ResQuantity(new Coin(), 1));
        warehouse.add(new ResQuantity(new Faith(), 1));
        warehouse.add(new ResQuantity(new Stone(), 2));
        warehouse.add(new ResQuantity(new Coin(), 5));
        warehouse.add(new ResQuantity(new Stone(), 3));

        List<ResQuantity> strongbox = new LinkedList<>();
        strongbox.add(new ResQuantity(new Coin(), 5));
        strongbox.add(new ResQuantity(new Stone(), 3));
        strongbox.add(new ResQuantity(new Servant(), 8));
        strongbox.add(new ResQuantity(new Shield(), 4));

        cli.showBoxes(warehouse, strongbox, "test1");

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

        cli.showPersonalProduction("test1");
        cli.showMarketUpdate(marbles);

        List<DevelopmentCard> cardsD = ConfigurationParser.parseDevelopmentCard("defaultConfiguration.xml");
        List<LeaderCard> cardsL = ConfigurationParser.parseLeaderCard("defaultConfiguration.xml");
        List<Action> tokens = ConfigurationParser.parseActionTokens("defaultConfiguration.xml");

        Map<Integer, String> slots = new HashMap<>();
        slots.put(1, cardsD.get(5).getId());
        slots.put(2, cardsD.get(1).getId());
        slots.put(3, cardsD.get(2).getId());
        cli.showSlotsUpdate(slots, "test1");

        Map<Integer, String> leaders = new HashMap<>();
        Map<Integer, ItemStatus> status = new HashMap<>();
        status.put(1, ItemStatus.ACTIVE);
        status.put(2, ItemStatus.INACTIVE);
        leaders.put(1, "1");
        leaders.put(2, "2");
        cli.showLeaderCards(leaders, status, "test1");

        cli.showTopToken(Optional.of("1"));

        cli.plotView();

        leaders.put(3, "3");
        leaders.put(4, "4");
        status.put(3, ItemStatus.ACTIVE);
        status.put(4, ItemStatus.INACTIVE);
        cli.showLeaderCards(leaders, status, "test1");
        cli.plotView();
    }

}