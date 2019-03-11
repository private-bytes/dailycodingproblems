package org.privatebytes.problem267.pieces;

import java.util.List;

import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitor;

public class Rook extends AbstractChessPiece {

	public Rook(Point position) {
		super('R', position);
	}

	public void accept(ChessPieceVisitor<List<AbstractChessPiece>> visitor) {
		visitor.visit(this);
	}

}
