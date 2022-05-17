package edu.ai.mainproj.main;

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

    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas();
        canvas.setHeight(DEFAULT_BOARD_SIZE);
        canvas.setWidth(DEFAULT_BOARD_SIZE);

        // draw stuff on canvas, placeholder for now
        // TODO
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.valueOf("#ff0000"));
        graphicsContext2D.fillRect(100, 100, 200, 200);
        graphicsContext2D.setStroke(Color.valueOf("#0000ff"));
        graphicsContext2D.strokeRect(200, 200, 200, 200);

        // populate ui controls, placeholder for now
        // TODO
        Button phButton = new Button();
        phButton.setOnAction((e) -> graphicsContext2D.fillRect(200, 200, 200, 200));
        phButton.setText("Example Button");

        // set up arrangement of items on screen
        VBox uiVBox = new VBox(phButton);
        uiVBox.setFillWidth(true);
        uiVBox.setMinWidth(UIBAR_MIN_WIDTH);
        Scene scene = new Scene(new HBox(canvas, uiVBox));

        // set up resizing
        scene.widthProperty().addListener(
                (observableValue, oldWidth, newWidth) -> {
                    canvas.setWidth(newWidth.doubleValue() - uiVBox.getWidth());
                    int x; // TODO redraw elements on canvas
                });
        scene.heightProperty().addListener(
                (observableValue, oldHeight, newHeight) -> {
                    canvas.setHeight(newHeight.doubleValue());
                    int x; // TODO redraw elements on canvas
                });

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}