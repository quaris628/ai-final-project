package edu.ai.mainproj.main;

import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.players.AIPlayer;
import edu.ai.mainproj.players.CheckersPlayer;
import edu.ai.mainproj.players.UIPlayer;
import edu.ai.mainproj.ui.CanvasRenderer;
import edu.ai.mainproj.ui.PlayerControls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApplication extends Application {

    public static final double DEFAULT_BOARD_SIZE = 512;
    public static final double UIBAR_MIN_WIDTH = 128;
    public static final int DEFAULT_AI_DIFFICULTY = 2;

    public GameRunner gameRunner;

    public Canvas canvas;
    public CanvasRenderer renderer;
    public VBox uiVBox;
    public Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // init vars
        CheckersPlayer black = new UIPlayer(PlayerType.BLACK);
        CheckersPlayer red = new AIPlayer(PlayerType.RED, DEFAULT_AI_DIFFICULTY);
        gameRunner = new GameRunner(black, red);

        canvas = new Canvas();
        renderer = new CanvasRenderer(canvas, gameRunner);

        PlayerControls playerControls = new PlayerControls();
        uiVBox = new VBox(playerControls.getRootNode());

        scene = new Scene(new HBox(canvas, uiVBox));

        // set up canvas renderer
        gameRunner.getTurnComplete().subscribe((e) -> renderer.render());

        // initialize ui controls
        playerControls.initialize(this);

        // set up arrangement of items on screen
        uiVBox.setMinWidth(UIBAR_MIN_WIDTH);
        uiVBox.setFillWidth(true);
        canvas.setHeight(DEFAULT_BOARD_SIZE);
        canvas.setWidth(DEFAULT_BOARD_SIZE);

        // set up window resize responses
        scene.widthProperty().addListener(
                (observableValue, oldWidth, newWidth) -> {
                    recalculateCanvasSize(newWidth.doubleValue(), scene.getHeight());
                });
        scene.heightProperty().addListener(
                (observableValue, oldHeight, newHeight) -> {
                    recalculateCanvasSize(scene.getWidth(), newHeight.doubleValue());
                });

        // finalize things
        renderer.render();
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }

    private void recalculateCanvasSize(double sceneWidth, double sceneHeight) {
        canvas.setHeight(Math.min(sceneWidth - UIBAR_MIN_WIDTH, sceneHeight));
        canvas.setWidth(canvas.getHeight());
        renderer.render();
    }

    public static void main(String[] args) {
        launch();
    }
}
