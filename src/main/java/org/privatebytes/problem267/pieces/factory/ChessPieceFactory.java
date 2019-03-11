package org.privatebytes.problem267.pieces.factory;

import org.privatebytes.problem267.pieces.AbstractChessPiece;
import org.privatebytes.problem267.pieces.Bishop;
import org.privatebytes.problem267.pieces.King;
import org.privatebytes.problem267.pieces.Knight;
import org.privatebytes.problem267.pieces.Pawn;
import org.privatebytes.problem267.pieces.Point;
import org.privatebytes.problem267.pieces.Queen;
import org.privatebytes.problem267.pieces.Rook;

public class ChessPieceFactory {

	public static AbstractChessPiece create(char type, Point position) {
		switch (type) {
		case 'K':
			return new King(position);
		case 'Q':
			return new Queen(position);
		case 'B':
			return new Bishop(position);
		case 'N':
			return new Knight(position);
		case 'R':
			return new Rook(position);
		case 'P':
			return new Pawn(position);
		default:
			throw new IllegalArgumentException("Invalid chess piece type " + type);
		}
	}
}
