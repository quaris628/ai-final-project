package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Tile;

import java.util.LinkedList;
import java.util.List;


/**
 * A checkers board
 * The board consists of a grid of tiles, where only half of the
 *     tiles, in a checkerboard pattern, are in-play. The others
 *     are never used.
 * The tile at position 0,0 is out-of-play
 * The tiles at positions 0,1 and 1,0 are in-play
 *
 * @author Nathan Swartz
 */
public class CheckersBoard extends GridBoard {

    public static final int SIZE = 8;

    public CheckersBoard() {
        // use only a half-size array for better memory space usage
        super(SIZE, SIZE / 2);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE / 2; j++) {
                board[i][j] = new CheckersTile(this, i, 2 * j + (1 - i % 2));
            }
        }
    }

    public static CheckersBoard CreateInitialBoard() {
        CheckersBoard toReturn = new CheckersBoard();
        // Place Red pieces
        for (Tile tile : toReturn.getTilesInRow(0)) {
            new CheckersPiece(PlayerType.RED, (CheckersTile)tile);
        } for (Tile tile : toReturn.getTilesInRow(1)) {
            new CheckersPiece(PlayerType.RED, (CheckersTile)tile);
        } for (Tile tile : toReturn.getTilesInRow(2)) {
            new CheckersPiece(PlayerType.RED, (CheckersTile)tile);
        // Place Black pieces
        } for (Tile tile : toReturn.getTilesInRow(5)) {
            new CheckersPiece(PlayerType.BLACK, (CheckersTile)tile);
        } for (Tile tile : toReturn.getTilesInRow(6)) {
            new CheckersPiece(PlayerType.BLACK, (CheckersTile)tile);
        } for (Tile tile : toReturn.getTilesInRow(7)) {
            new CheckersPiece(PlayerType.BLACK, (CheckersTile)tile);
        }
        return toReturn;
    }

	// TODO is this needed, or is super.getTilesInRow() okay?
    // Apparently it's okay because I'm not seeing any problems with how it is now...
	/*
	@Override
	public Iterable<Tile> getTilesInRow(int row) {
		Iterable<Tile> rowTiles = super.getTilesInRow(row);
		// filter out null tiles from the results list
		List<Tile> toReturn = LinkedList<Tile>();
		for (Tile tile : rowTiles) {
			if (tile != null) {
				toReturn.add(tile);
			}
		}
		return toReturn;
	}
	//*/

    @Override
    public Iterable<Tile> getTilesInColumn(int column) {
        List<Tile> columnTiles = new LinkedList<Tile>();
        for (int r = 1 - column % 2; r < getNumColumns(); r+=2) {
            columnTiles.add(getTile(r, column));
        }
        return columnTiles;
    }

    // TODO is this needed, or is super.getAllTiles() okay?
	/*
	@Override
	public Iterable<Tile> getAllTiles() {
		Iterable<Tile> allTiles = super.getAllTiles();
		// filter out null tiles from the results list
		List<Tile> toReturn = LinkedList<Tile>();
		for (Tile tile : allTiles) {
			if (tile != null) {
				toReturn.add(tile);
			}
		}
		return toReturn;
	}
	//*/

    public CheckersTile getCheckersTile(int row, int column) {
        return (CheckersTile) getTile(row, column);
    }

    @Override
    public Tile getTile(int row, int column) {
        // Do these exceptions for out of bounds cases because
        //     otherwise the column bound for the natural array
        //     exception would be confusing when debugging
        if (row < 0 || SIZE <= row) {
            throw new IndexOutOfBoundsException(
                    "Row must be >= 0 and < " + SIZE);
        } else if (column < 0 || SIZE <= column) {
            throw new IndexOutOfBoundsException(
                    "Column must be >= 0 and < " + SIZE);
        }

        if (column % 2 != row % 2) {
            return board[row][column / 2];
        } else {
            // if square is not in play
            return null;
        }
    }
    // super.getNumRows will work and does not need to be overridden
    @Override
    public int getNumColumns() { return SIZE; }

}
