package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.main.GameRunner;
import edu.ai.mainproj.players.AIPlayer;
import edu.ai.mainproj.players.UIPlayer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// TODO allow setting of AI difficulty

public class PlayerControls {

    private Label blackLabel;
    private Button blackButton;
    private Label redLabel;
    private Button redButton;
    private Button playButton;
    private Button endButton;
    private Node root;

    public PlayerControls() {
        blackLabel = new Label();
        blackButton = new Button();
        redLabel = new Label();
        redButton = new Button();
        playButton = new Button();
        endButton = new Button();
        root = new VBox(new HBox(blackLabel, blackButton),
                new HBox(redLabel, redButton),
                new HBox(playButton, endButton));
    }

    public void initialize(GameRunner gameRunner) {
        blackLabel.setText("  Black:  ");
        blackLabel.setMinWidth(44.0);
        blackButton.setText(gameRunner.getBlack() instanceof AIPlayer ? "AI" : "Human");
        blackButton.setMinWidth(55.0);
        blackButton.setOnAction((e) -> cycleBlackPlayer(gameRunner));
        redLabel.setText("  Red:  ");
        redLabel.setMinWidth(44.0);
        redButton.setText(gameRunner.getRed() instanceof AIPlayer ? "AI" : "Human");
        redButton.setMinWidth(55.0);
        redButton.setOnAction((e) -> cycleRedPlayer(gameRunner));

        playButton.setText("Play");
        playButton.setOnAction((e) -> {
            enableButtonsGameStart();
            gameRunner.startNewGame();
        });

        endButton.setText("End Game");
        endButton.setDisable(true);
        endButton.setOnAction((e) -> {
            enableButtonsGameEnd();
            gameRunner.stopGame();
        });

        gameRunner.getGameComplete().subscribe(this::enableButtonsGameEnd);
    }

    private void cycleBlackPlayer(GameRunner gameRunner) {
        if (gameRunner.getBlack() instanceof AIPlayer) {
            gameRunner.setBlack(new UIPlayer(PlayerType.BLACK));
            blackButton.setText("Human");
        } else {
            gameRunner.setBlack(new AIPlayer(PlayerType.BLACK,
                    CheckersApplication.DEFAULT_AI_DIFFICULTY));
            blackButton.setText("AI");
        }
    }

    private void cycleRedPlayer(GameRunner gameRunner) {
        if (gameRunner.getRed() instanceof AIPlayer) {
            gameRunner.setRed(new UIPlayer(PlayerType.RED));
            redButton.setText("Human");
        } else {
            gameRunner.setRed(new AIPlayer(PlayerType.RED,
                    CheckersApplication.DEFAULT_AI_DIFFICULTY));
            redButton.setText("AI");
        }
    }

    private void enableButtonsGameStart() {
        blackButton.setDisable(true);
        redButton.setDisable(true);
        playButton.setDisable(true);
        endButton.setDisable(false);
    }

    private void enableButtonsGameEnd() {
        blackButton.setDisable(false);
        redButton.setDisable(false);
        playButton.setDisable(false);
        endButton.setDisable(true);
    }

    public Node getRootNode() { return root; }

}
