package org.privatebytes.problem267.pieces.visitor;

import org.privatebytes.problem267.pieces.Bishop;
import org.privatebytes.problem267.pieces.King;
import org.privatebytes.problem267.pieces.Knight;
import org.privatebytes.problem267.pieces.Pawn;
import org.privatebytes.problem267.pieces.Queen;
import org.privatebytes.problem267.pieces.Rook;

public interface ChessPieceVisitor<T> {
	void visit(King king);
	void visit(Queen queen);
	void visit(Bishop bishop);
	void visit(Knight knight);
	void visit(Rook rook);
	void visit(Pawn pawn);
	
	public T getResult();
}
