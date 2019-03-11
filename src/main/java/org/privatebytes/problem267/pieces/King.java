package org.privatebytes.problem267.pieces;

import java.util.List;

import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitor;

public class King extends AbstractChessPiece {

	public King(Point position) {
		super('K', position);
	}

	public void accept(ChessPieceVisitor<List<AbstractChessPiece>> visitor) {
		visitor.visit(this);
	}

}
