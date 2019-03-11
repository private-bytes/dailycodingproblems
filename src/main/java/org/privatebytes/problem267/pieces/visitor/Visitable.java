package org.privatebytes.problem267.pieces.visitor;

import java.util.List;

import org.privatebytes.problem267.pieces.AbstractChessPiece;

public interface Visitable {
	void accept(ChessPieceVisitor<List<AbstractChessPiece>> visitor) ;
}
