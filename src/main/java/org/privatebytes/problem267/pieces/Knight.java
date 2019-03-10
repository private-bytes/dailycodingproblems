package org.privatebytes.problem267.pieces;

import java.util.List;

import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitor;

public class Knight extends AbstractChessPiece {

	public Knight(Point position) {
		super('N', position);
	}

	public void accept(ChessPieceVisitor<List<AbstractChessPiece>> visitor) {
		visitor.visit(this);
	}

}
