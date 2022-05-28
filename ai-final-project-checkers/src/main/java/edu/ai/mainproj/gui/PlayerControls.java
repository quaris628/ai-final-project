package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.PlayerType;
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

    public void initialize(CheckersApplication app) {
        blackLabel.setText("  Black:  ");
        blackLabel.setMinWidth(44.0);
        blackButton.setText("Human");
        blackButton.setMinWidth(55.0);
        redLabel.setMinWidth(44.0);
        redLabel.setText("  Red:  ");
        redButton.setText("AI");
        redButton.setMinWidth(55.0);
        blackButton.setOnAction((e) -> {
            if (app.gameRunner.getBlack() instanceof AIPlayer) {
                app.gameRunner.setBlack(new UIPlayer(PlayerType.BLACK));
                blackButton.setText("Human");
            } else {
                app.gameRunner.setBlack(new AIPlayer(PlayerType.BLACK,
                        CheckersApplication.DEFAULT_AI_DIFFICULTY));
                blackButton.setText("AI");
            }
        });
        redButton.setOnAction((e) -> {
            if (app.gameRunner.getRed() instanceof AIPlayer) {
                app.gameRunner.setRed(new UIPlayer(PlayerType.RED));
                redButton.setText("Human");
            } else {
                app.gameRunner.setRed(new AIPlayer(PlayerType.RED,
                        CheckersApplication.DEFAULT_AI_DIFFICULTY));
                redButton.setText("AI");
            }
        });

        playButton.setText("Play");
        endButton.setText("End Game");
        playButton.setOnAction((e) -> {
            blackButton.setDisable(true);
            redButton.setDisable(true);
            playButton.setDisable(true);
            endButton.setDisable(false);
            app.gameRunner.setGame(new CheckersGame());
            app.gameRunner.setName("Game & AI Thread");
            app.gameRunner.start();
        });
        app.gameRunner.getGameComplete().subscribe(() -> {
            blackButton.setDisable(false);
            redButton.setDisable(false);
            playButton.setDisable(false);
            endButton.setDisable(true);
        });
        endButton.setDisable(true);
        endButton.setOnAction((e) -> {
            blackButton.setDisable(false);
            redButton.setDisable(false);
            playButton.setDisable(false);
            endButton.setDisable(true);
            // TODO figure out how to stop gameRunner thread here
            app.gameRunner.interrupt();
            app.gameRunner.setGame(new CheckersGame());
        });
    }

    public Node getRootNode() { return root; }

}
