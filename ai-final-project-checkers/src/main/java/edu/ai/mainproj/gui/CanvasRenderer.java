package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.main.GameRunner;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class CanvasRenderer {

    // fraction of the tile size that the pice is set to
    // reasonable values: 0.5 - 1.0
    public static final double PIECE_SIZE_FRAC = .8;

    private Canvas canvas;
    private GameRunner gameRunner;
    private boolean renderIsQueued;
    private List<CheckersPiece.PieceData> piecesData;
    private final Object piecesDataGatekeeper = new Object();

    // convenience reference
    private GraphicsContext gc;

    public CanvasRenderer(Canvas canvas, GameRunner gameRunner) {
        this.canvas = canvas;
        this.gameRunner = gameRunner;
        this.renderIsQueued = false;

        this.gc = canvas.getGraphicsContext2D();
    }

    public void initialize() {
        // board state is changed mid-turn by AI players via execute() and unexecute()
        //     and render() may be called during this time if the screen size changes
        // so, at the beginning of each turn, get a deep copy of each piece on the board
        gameRunner.getTurnComplete().subscribe((e) -> updatePiecesData());
        updatePiecesData();
    }

    private void updatePiecesData() {
        List<CheckersPiece.PieceData> piecesData = new LinkedList<CheckersPiece.PieceData>();
        //this.piecesData = new LinkedList<CheckersPiece.PieceData>();

        // executing move and updating display's copy of the pieces
        //     should not be done concurrently
        // See GameRunner.doTurn(player)
        synchronized (gameRunner.getGame()) {
            List<CheckersPiece> pieces = ((CheckersGame)(gameRunner.getGame())).getPieces();
            // deep copy each piece
            for (CheckersPiece piece : pieces) {
                piecesData.add(piece.getData());
            }
        }
        //synchronized (piecesDataGatekeeper) {
            this.piecesData = piecesData;
        //}
    }

    public void render() {
        // it's fine if this if isn't quite thread-safe
        // worst case 2 renders get queued
        if (!renderIsQueued) {
            renderIsQueued = true;
            Platform.runLater(() -> {
                CheckersBoard board = gameRunner.getGame().getBoardState();

                // update each render b/c canvas is resizable
                double tileSize = Math.min(canvas.getHeight() / board.getNumRows(),
                        canvas.getWidth() / board.getNumColumns());
                drawTiles(board, tileSize);

                /*
                Iterable<CheckersPiece.PieceData> piecesDataToIter;
                //synchronized (piecesDataGatekeeper) {
                    piecesDataToIter = piecesData;
                //}
                */
                // TODO there once be a spooky concurrent modification exception here
                // I've tried to reproduce it but I can't *shrug*
                for (CheckersPiece.PieceData pieceData : piecesData) {
                    drawPiece(pieceData, tileSize);
                }

                // if empty space b/c scaling stuff, overwrite with white
                if (canvas.getHeight() > canvas.getWidth()) {
                    double x = 0;
                    double y = board.getNumRows() * tileSize;
                    double width = canvas.getWidth();
                    double height = canvas.getHeight() - board.getNumRows() * tileSize;
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x, y, width, height);
                }
                renderIsQueued = false;
            });
        }
    }

    private void drawTiles(CheckersBoard board, double tileSize) {
        for (int i = 0; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumColumns(); j++) {
                CheckersTile tile = board.getCheckersTile(i, j);
                if (tile == null) {
                    gc.setFill(Color.RED);
                } else {
                    gc.setFill(Color.BLACK);
                }
                gc.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }
    }

    private void drawPiece(CheckersPiece.PieceData pieceData, double tileSize) {
        if (pieceData.player == PlayerType.RED) {
            gc.setFill(Color.valueOf("#d00000"));
        } else if (pieceData.player == PlayerType.BLACK) {
            gc.setFill(Color.valueOf("#404040"));
        }
        double x = pieceData.column * tileSize;
        double y = pieceData.row * tileSize;
        double circleDiameter = tileSize * PIECE_SIZE_FRAC;
        double circleCornerOffset = tileSize * (1.0 - PIECE_SIZE_FRAC) / 2.0;
        gc.fillOval(x + circleCornerOffset, y + circleCornerOffset, circleDiameter, circleDiameter);
        if (pieceData.isKing) {
            if (pieceData.player == PlayerType.RED) {
                gc.setFill(Color.valueOf("#600000"));
            } else if (pieceData.player == PlayerType.BLACK) {
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
