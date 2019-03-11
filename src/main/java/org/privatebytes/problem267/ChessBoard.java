package org.privatebytes.problem267;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.privatebytes.problem267.pieces.AbstractChessPiece;
import org.privatebytes.problem267.pieces.Point;
import org.privatebytes.problem267.pieces.factory.ChessPieceFactory;

public class ChessBoard {

	private Map<Point, AbstractChessPiece> pieces;

	public ChessBoard(char[][] board) {
		this.pieces = Collections.unmodifiableMap(parse(board));
	}

	private Map<Point, AbstractChessPiece> parse(char[][] board) {
		Map<Point, AbstractChessPiece> pieces = new HashMap<>();

		if (board.length != 8) {
			throw new IllegalArgumentException("Chessboard size is not 8x8.");
		}

		for (int i = 0; i < board.length; i++) {

			if (board[i].length != 8) {
				throw new IllegalArgumentException("Chessboard size is not 8x8.");
			}

			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != '.') {
					Point position = new Point(j, i);
					pieces.put(position, ChessPieceFactory.create(board[i][j], position));
				}
			}
		}

		return pieces;
	}

	public Map<Point, AbstractChessPiece> getPieces() {
		return pieces;
	}

}
