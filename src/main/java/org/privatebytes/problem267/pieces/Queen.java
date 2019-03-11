package org.privatebytes.problem267.pieces;

import java.util.List;

import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitor;

public class Queen extends AbstractChessPiece {

	public Queen(Point position) {
		super('Q', position);
	}

	public void accept(ChessPieceVisitor<List<AbstractChessPiece>> visitor) {
		visitor.visit(this);
	}

}
