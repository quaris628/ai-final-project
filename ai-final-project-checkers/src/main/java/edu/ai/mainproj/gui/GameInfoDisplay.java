package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.main.GameRunner;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class GameInfoDisplay {

    private Label turnLabel;
    private Label turnValue;
    private PlayerType turn;
    private Label winnerLabel;
    private Label winnerValue;
    private HBox turnHBox;
    private HBox winnerHBox;
    private Node root;

    private boolean updateTurnDisplayQueued;

    public GameInfoDisplay() {
        turnLabel = new Label();
        winnerLabel = new Label();
        turnValue = new Label();
        turn = null;
        winnerValue = new Label();
        turnHBox = new HBox(turnLabel, turnValue);
        winnerHBox = new HBox(winnerLabel, winnerValue);
        root = new VBox(turnHBox, winnerHBox);
        updateTurnDisplayQueued = false;
    }

    public void initialize(GameRunner gameRunner) {
        turnLabel.setText("");
        turnValue.setText("");
        winnerLabel.setText("");
        winnerValue.setText("");
        turnHBox.setAlignment(Pos.CENTER);
        winnerHBox.setAlignment(Pos.CENTER);

        gameRunner.getGameStart().subscribe(() ->
            Platform.runLater(() -> {
                turnLabel.setText("Turn: ");
                winnerLabel.setText("");
                winnerValue.setText("");
            })
        );

        // It had some buffer overflow problems with queueing up too many
        // tasks in Platform.runLater() b/c AIs were so quick at low difficulty,
        // so I put protection on the turn update method so only one update
        // method at a time can queue in Platform.runLater().
        // It'll still show the most up-to-date info when it runs.
        gameRunner.getTurnStart().subscribe((turn) -> {
            this.turn = turn;
            // if an update is already queued, don't queue another one
            if (!updateTurnDisplayQueued) {
                updateTurnDisplayQueued = true;
                Platform.runLater(this::updateTurnDisplay);
            }
        });

        gameRunner.getGameComplete().subscribe(() ->
            Platform.runLater(() -> {
                PlayerType winner = gameRunner.getGame().getWinner();
                winnerLabel.setText("Winner: ");
                setPlayerColor(winnerValue, winner);
                winnerValue.setText(winner == null ? "Draw" : winner.toString());
            })
        );
    }

    private void updateTurnDisplay() {
        updateTurnDisplayQueued = false;
        PlayerType turnCopy = turn;
        turnValue.setText(turnCopy.toString());
        setPlayerColor(turnValue, turnCopy);
    }

    private void setPlayerColor(Label label, PlayerType player) {
        String colorCode = player == PlayerType.RED ? "#FF0000" : "#000000";
        label.setTextFill(Paint.valueOf(colorCode));
    }

    public Node getRootNode() { return root; }

}
