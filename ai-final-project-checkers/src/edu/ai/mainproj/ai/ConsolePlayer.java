package edu.ai.mainproj.ai;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.checkers.moves.CheckersMoveJumpMulti;
import edu.ai.mainproj.checkers.moves.CheckersMoveJumpSingle;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ConsolePlayer implements Player {

    private final Scanner scanner;

    public ConsolePlayer() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void executeTurn(CheckersGamePlayable game) {
        System.out.println("Which piece do you want to move? (X,Y)");
        CheckersTile selectedTile = getPiece(game);
        List<CheckersMove> possibleMoves = getValidMoves(game, selectedTile);
        System.out.println("Which move would you like to make?");
        int index = 0;
        for (CheckersMove move : possibleMoves) {
            System.out.print(index++ + ": ");
            if (move instanceof CheckersMoveJumpSingle)
                System.out.print("Single jump to ");
            else if (move instanceof CheckersMoveJumpMulti)
                System.out.print("Multi jump to ");
            else
                System.out.print("Move to ");
            System.out.println((move.destination.row+1) + ", " + (move.destination.column+1));
        }
        CheckersMove selectedMove = getMove(possibleMoves);
        game.execute(selectedMove);
    }

    private CheckersMove getMove(List<CheckersMove> possibleMoves) {
        String in = scanner.nextLine();
        int move;
        try {
            move = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            move = -1;
        }
        if (move < 0 || move > possibleMoves.size()-1) {
            System.out.println("Please select a valid move");
            return getMove(possibleMoves);
        }
        return possibleMoves.get(move);
    }

    private CheckersTile getPiece(CheckersGamePlayable game) {
        String in = scanner.nextLine();
        String[] coords = in.split(",");
        if (coords.length != 2) {
            System.out.println("Please try again in the format of X,Y");
            return getPiece(game);
        }
        int col = Integer.parseInt(coords[0]) - 1;
        int row = Integer.parseInt(coords[1]) - 1;
        CheckersTile ret = game.getBoardState().getCheckersTile(row, col);
        if (ret == null || ret.getPiece() == null || ret.getCheckersPiece().getPlayer() != game.getTurn()) {
            System.out.println("Please select your own piece");
            return getPiece(game);
        }
        if (getValidMoves(game, ret).isEmpty()) {
            System.out.println("There are no valid moves for this piece, please try again");
            return getPiece(game);
        }
        return ret;
    }

    private List<CheckersMove> getValidMoves(CheckersGamePlayable game, CheckersTile givenTile) {
        List<CheckersMove> possibleMoves = new LinkedList<>();
        for (CheckersMove move : game.getPossibleMoves()) {
            CheckersTile tile = move.piece.getCheckersTile();
            if (tile.equals(givenTile))
                possibleMoves.add(move);
        }
        return possibleMoves;
    }

    // do nothing, doesn't matter to this class
    @Override
    public void notifyGameEnd(CheckersGamePlayable endGame) {}
}
