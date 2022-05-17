package edu.ai.mainproj.main;

import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.players.AIPlayer;
import edu.ai.mainproj.players.AutoDifficultyAIPlayer;
import edu.ai.mainproj.players.CheckersPlayer;
import edu.ai.mainproj.ui.CheckersCanvasRenderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ResizableCanvasApplication extends Application {

    public static final double DEFAULT_BOARD_SIZE = 512;
    public static final double UIBAR_MIN_WIDTH = 128;

    private CheckersPlayer black;
    private CheckersPlayer red;
    private GameRunner gameRunner;

    private Canvas canvas;
    private CheckersCanvasRenderer renderer;
    private VBox uiVBox;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // init vars
        black = new AIPlayer(PlayerType.BLACK, 1);
        red = new AutoDifficultyAIPlayer(PlayerType.RED);
        gameRunner = new GameRunner(black, red);

        canvas = new Canvas();
        renderer = new CheckersCanvasRenderer(canvas, gameRunner);

        Button phButton = new Button();
        uiVBox = new VBox(phButton);

        scene = new Scene(new HBox(canvas, uiVBox));

        // set up canvas renderer
        gameRunner.getTurnComplete().subscribe(renderer::render);

        // populate ui controls, placeholder for now
        phButton.setOnAction((e) -> gameRunner.run());
        phButton.setText("Run Game");

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
        renderer.render();
    }

    public static void main(String[] args) {
        launch();
    }
}