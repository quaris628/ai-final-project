package edu.ai.mainproj.gui;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.main.GameRunner;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// TODO draw a separate canvas in the sidebar that flashes
//      the board states that the AI is searching through!

public class BoardCanvasRenderer {

    // fraction of the tile size that the piece is set to
    // reasonable values: 0.5 - 1.0
    public static final double PIECE_SIZE_FRAC = .8;
    // vertices of the crown-shaped polygon that denotes kinged pieces
    // coordinates are rectangular, 0,0 is the center of the piece circle,
    //     these points are for circle radius = 1 (and scaling is done later dynamically)
    public static final double[] KING_POINTS_X = new double[] {
            -0.7, -0.35, 0, 0.35, 0.7, 0.6, -0.6};
    public static final double[] KING_POINTS_Y = new double[] {
            -0.35, -0.15, -0.35, -0.15, -0.35, 0.35, 0.35};

    private final Canvas canvas;
    private final GameRunner gameRunner;
    private boolean renderIsQueued;
    private List<CheckersPiece.PieceData> piecesData;
    // convenience reference
    private GraphicsContext gc;

    public BoardCanvasRenderer(Canvas canvas, GameRunner gameRunner) {
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
        renderIsQueued = false; // TODO shouldn't be needed? but may fix game not re-rendering
        //     ... when a second game is started after a first one until a piece is moved
        updatePiecesData();
    }

    private void updatePiecesData() {
        List<CheckersPiece.PieceData> piecesData = new LinkedList<CheckersPiece.PieceData>();

        // executing move and updating display's copy of the pieces
        //     should not be done concurrently
        // See GameRunner.doTurn(player)
        synchronized (gameRunner.getGame()) {
            List<CheckersPiece> pieces = ((CheckersGame)(gameRunner.getGame())).getPieces();
            // deep copy each piece's data
            for (CheckersPiece piece : pieces) {
                piecesData.add(piece.getData());
            }
        }
        // updating piecesData to reference a new object should not be done
        //     at the same time as grabbing the object that is
        synchronized (this) {
            this.piecesData = piecesData;
            queueRender();
        }
    }

    public synchronized void queueRender() {
        if (!renderIsQueued) {
            renderIsQueued = true;
            Platform.runLater(this::render);
        }
    }

    private void render() {
        // TODO? if size doesn't change, only re-render (changes in?) pieces (leaving tiles)
        //       for performance optimization
        double size = Math.min(canvas.getHeight(), canvas.getWidth());

        drawBoard(gameRunner.getGame().getBoardState(), size);
    }

    private void drawBoard(CheckersBoard board, double size) {
        // update each render b/c canvas is resizable
        double tileSize = Math.min(size / board.getNumRows(), size / board.getNumColumns());
        drawTiles(board, tileSize);

        // draw pieces last so that there's the most time given for an
        //     update to the pieces list to happen before they're drawn
        Iterable<CheckersPiece.PieceData> piecesDataToIter;
        synchronized (this) {
            piecesDataToIter = piecesData;
            // allow more renders to queue after this point
            //     in case pieces are updated
            // TODO? iff pieces are updated
            renderIsQueued = false;
        }

        for (CheckersPiece.PieceData pieceData : piecesDataToIter) {
            drawPiece(pieceData, tileSize);
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
            drawKingSymbol(x + tileSize / 2, y + tileSize / 2, circleDiameter / 2);
        }
    }

    private void drawKingSymbol(double centerX, double centerY, double circleRadius) {
        double[] xPoints = Arrays.copyOf(KING_POINTS_X, KING_POINTS_X.length);
        double[] yPoints = Arrays.copyOf(KING_POINTS_Y, KING_POINTS_Y.length);
        for (int i = 0; i < KING_POINTS_X.length; i++) {
            // scale the points according to the circle radius
            xPoints[i] *= circleRadius;
            yPoints[i] *= circleRadius;
            // offset the points according to centerX, centerY
            xPoints[i] += centerX;
            yPoints[i] += centerY;
        }
        gc.fillPolygon(xPoints, yPoints, KING_POINTS_X.length);
    }
}
