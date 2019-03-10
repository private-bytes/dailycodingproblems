package org.privatebytes.problem267.pieces;

import java.util.List;

import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitor;

public class Pawn extends AbstractChessPiece {

	public Pawn(Point position) {
		super('P', position);
	}

	public void accept(ChessPieceVisitor<List<AbstractChessPiece>> visitor) {
		visitor.visit(this);
	}

}
