package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.main.CheckersApplication;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class GameInfoDisplay {

    private Label turnLabel;
    private Label turnValue;
    private Label winnerLabel;
    private Label winnerValue;
    private HBox turnHBox;
    private HBox winnerHBox;
    private Node root;

    public GameInfoDisplay() {
        turnLabel = new Label();
        winnerLabel = new Label();
        turnValue = new Label();
        winnerValue = new Label();
        turnHBox = new HBox(turnLabel, turnValue);
        winnerHBox = new HBox(winnerLabel, winnerValue);
        root = new VBox(turnHBox, winnerHBox);
    }

    public void initialize(CheckersApplication app) {
        clear();
        turnHBox.setAlignment(Pos.CENTER);
        winnerHBox.setAlignment(Pos.CENTER);

        app.gameRunner.getGameStart().subscribe(() ->
                turnLabel.setText("Turn: "));

        app.gameRunner.getTurnStart().subscribe((turn) -> {
            setPlayerColor(turnValue, turn);
            turnValue.setText(turn.toString());
        });

        app.gameRunner.getGameComplete().subscribe(() -> {
            PlayerType winner = app.gameRunner.getGame().getWinner();
            winnerLabel.setText("Winner: ");
            setPlayerColor(winnerValue, winner);
            winnerValue.setText(winner == null ? "Draw" : winner.toString());
        });
    }

    private void clear() {
        turnLabel.setText("");
        winnerLabel.setText("");
        turnValue.setText("");
        winnerValue.setText("");
    }

    private void setPlayerColor(Label label, PlayerType player) {
        String colorCode = player == PlayerType.RED ? "#FF0000" : "#000000";
        label.setTextFill(Paint.valueOf(colorCode));
    }

    public Node getRootNode() { return root; }

}
