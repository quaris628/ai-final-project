package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.main.GameRunner;
import edu.ai.mainproj.players.AIPlayer;
import edu.ai.mainproj.players.CheckersPlayer;
import edu.ai.mainproj.players.UIPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApplication extends Application {

    // GUI constants
    public static final double DEFAULT_BOARD_SIZE = 512;
    public static final double UIBAR_MIN_WIDTH = 128;
    public static final double UIBAR_SPACING = 10;

    // other constants
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

        /*
        // time control
        gameRunner.getTurnComplete().subscribe((turn) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //*/

        canvas = new Canvas();
        renderer = new CanvasRenderer(canvas, gameRunner);

        PlayerControls playerControls = new PlayerControls();
        GameInfoDisplay gameInfoDisplay = new GameInfoDisplay();
        uiVBox = new VBox(playerControls.getRootNode(), gameInfoDisplay.getRootNode());

        scene = new Scene(new HBox(canvas, uiVBox));

        // set up canvas renderer
        renderer.initialize();
        gameRunner.getTurnComplete().subscribe((e) -> renderer.render());

        // initialize ui ribbon stuff
        uiVBox.setSpacing(UIBAR_SPACING);
        playerControls.initialize(this);
        gameInfoDisplay.initialize(this);

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
    // TODO maybe have UI bar elements scale with size too

    public static void main(String[] args) {
        launch();
    }
}
