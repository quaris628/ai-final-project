package edu.ai.mainproj.ui;

import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.main.GameRunner;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasRenderer {

    // fraction of the tile size that the pice is set to
    // reasonable values: 0.5 - 1.0
    public static final double PIECE_SIZE_FRAC = .8;

    private Canvas canvas;
    private GraphicsContext gc;
    private GameRunner gameRunner;

    public CanvasRenderer(Canvas canvas, GameRunner gameRunner) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.gameRunner = gameRunner;
    }

    public void render() {
        CheckersBoard board = gameRunner.getGame().getBoardState();

        // update each render b/c canvas is resizable
        double tileSize = Math.min(canvas.getHeight()  / board.getNumRows(),
                canvas.getWidth() / board.getNumColumns());
        for (int i = 0; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumColumns(); j++) {
                CheckersTile tile = board.getCheckersTile(i, j);
                // calculate x/y position
                double y = i * tileSize;
                double x = j * tileSize;
                if (tile == null) {
                    // draw null tile
                    drawNullTile(x, y, tileSize);
                } else {
                    // draw non-null tile
                    drawNonNullTile(x, y, tileSize);
                    if (!tile.isBlank()) {
                        // draw piece
                        drawPiece(x, y, tileSize, tile.getCheckersPiece());
                    }
                }
            }
        }

        // if empty space b/c scaling stuff, overwrite with white
        //     in case something was there before this frame
        if (canvas.getHeight() > canvas.getWidth()) {
            double x = 0;
            double y = board.getNumRows() * tileSize;
            double width = canvas.getWidth();
            double height = canvas.getHeight() - board.getNumRows() * tileSize;
            gc.setFill(Color.WHITE);
            gc.fillRect(x, y, width, height);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {

        }
    }

    private void drawNullTile(double x, double y, double tileSize) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(x, y, tileSize, tileSize);
    }

    private void drawNonNullTile(double x, double y, double tileSize) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, tileSize, tileSize);
    }

    private void drawPiece(double x, double y, double tileSize, CheckersPiece piece) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (piece.getPlayer() == PlayerType.RED) {
            gc.setFill(Color.valueOf("#d00000"));
        } else if (piece.getPlayer() == PlayerType.BLACK) {
            gc.setFill(Color.valueOf("#404040"));
        }
        double circleDiameter = tileSize * PIECE_SIZE_FRAC;
        double circleCornerOffset = tileSize * (1.0 - PIECE_SIZE_FRAC) / 2.0;
        gc.fillOval(x + circleCornerOffset, y + circleCornerOffset, circleDiameter, circleDiameter);
        if (piece.isKing()) {
            if (piece.getPlayer() == PlayerType.RED) {
                gc.setFill(Color.valueOf("#600000"));
            } else if (piece.getPlayer() == PlayerType.BLACK) {
                gc.setFill(Color.BLACK);
            }
            drawKing(x + tileSize / 2, y + tileSize / 2, circleDiameter / 2);
        }
    }

    private void drawKing(double centerX, double centerY, double circleRadius) {
        // these points are for circle radius = 1
        int numPoints = 7;
        double[] xPoints = new double[] {
                -0.7, -0.35, 0, 0.35, 0.7, 0.6, -0.6};
        double[] yPoints = new double[] {
                -0.35, -0.15, -0.35, -0.15, -0.35, 0.35, 0.35};
        for (int i = 0; i < numPoints; i++) {
            // scale the points according to the circle radius
            xPoints[i] *= circleRadius;
            yPoints[i] *= circleRadius;
            // offset the points according to centerX, centerY
            xPoints[i] += centerX;
            yPoints[i] += centerY;
        }
        gc.fillPolygon(xPoints, yPoints, numPoints);
    }

}
