package it.polimi.ingsw.View.GUI.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.*;

/**
 * controller of the selection of the end of the game
 */
public class EndGameController extends ViewController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelPlayer1;

    @FXML
    private Label labelPoints1;

    @FXML
    private Label labelPlayer2;

    @FXML
    private Label labelPoints2;

    @FXML
    private Label labelPlayer3;

    @FXML
    private Label labelPoints3;

    @FXML
    private Label labelPlayer4;

    @FXML
    private Label labelPoints4;


    private List<Label> players;

    private List<Label> points;


    @FXML
    public void initialize() {
        players = new LinkedList<>();
        points = new LinkedList<>();

        players.add(labelPlayer1);
        points.add(labelPoints1);
        players.add(labelPlayer2);
        points.add(labelPoints2);
        players.add(labelPlayer3);
        points.add(labelPoints3);
        players.add(labelPlayer4);
        points.add(labelPoints4);

        for (Label label : players)
            label.setVisible(false);
        for (Label label : points)
            label.setVisible(false);
    }


    /**
     * manages the end of the game
     * @param map contains the nicknames of the players and the score associated
     */
    public void showEndGame(Map<String, Integer> map) {

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        int i = 0;

        for (String player : sortedMap.keySet()) {
            players.get(i).setText(player);
            players.get(i).setVisible(true);
            points.get(i).setText(map.get(player).toString());
            points.get(i).setVisible(true);
            i++;
        }
    }

}
