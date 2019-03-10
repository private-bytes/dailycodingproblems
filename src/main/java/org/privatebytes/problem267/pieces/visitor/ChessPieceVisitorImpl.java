package org.privatebytes.problem267.pieces.visitor;

import java.util.ArrayList;
import java.util.List;

import org.privatebytes.problem267.ChessBoard;
import org.privatebytes.problem267.pieces.AbstractChessPiece;
import org.privatebytes.problem267.pieces.Bishop;
import org.privatebytes.problem267.pieces.King;
import org.privatebytes.problem267.pieces.Knight;
import org.privatebytes.problem267.pieces.Pawn;
import org.privatebytes.problem267.pieces.Point;
import org.privatebytes.problem267.pieces.Queen;
import org.privatebytes.problem267.pieces.Rook;

public class ChessPieceVisitorImpl implements ChessPieceVisitor<List<AbstractChessPiece>> {

	private King target;
	private ChessBoard chessboard;

	private List<AbstractChessPiece> attackers;

	public ChessPieceVisitorImpl(ChessBoard chessboard) {
		this.attackers = new ArrayList<>();
		this.target = chessboard.getPieces().values().stream().filter(cp -> cp instanceof King).map(cp -> (King) cp)
				.findFirst().orElseThrow(() -> new IllegalArgumentException("No king on the chessboard !!!"));
		this.chessboard = chessboard;
	}

	public List<AbstractChessPiece> getResult() {
		return attackers;
	}

	@Override
	public void visit(King king) {
		// do nothing
	}

	@Override
	public void visit(Queen queen) {
		Point queenPosition = queen.getPosition();
		Point kingPosition = target.getPosition();

		int hDist = Math.abs(queenPosition.getX() - kingPosition.getX());
		int vDist = Math.abs(queenPosition.getY() - kingPosition.getY());

		
		if ((hDist == vDist //diagonal attack 
				|| Math.min(hDist, vDist) == 0) //horizontal or vertical attack
				&& isPathAvailable(queen, target)) {
			attackers.add(queen);
		}
	}

	@Override
	public void visit(Bishop bishop) {
		Point bishopPosition = bishop.getPosition();
		Point kingPosition = target.getPosition();

		int hDist = Math.abs(bishopPosition.getX() - kingPosition.getX());
		int vDist = Math.abs(bishopPosition.getY() - kingPosition.getY());

		if (hDist == vDist //diagonal attack 
				&& isPathAvailable(bishop, target)) {
			attackers.add(bishop);
		}
	}

	@Override
	public void visit(Knight knight) {
		Point knightPosition = knight.getPosition();
		Point kingPosition = target.getPosition();

		int hDist = Math.abs(knightPosition.getX() - kingPosition.getX());
		int vDist = Math.abs(knightPosition.getY() - kingPosition.getY());

		// L shaped attack: max vertical/horizontal distance is 2 and one product of the 2 distances is 2
		if (Math.max(hDist, vDist) == 2 
				&& (hDist * vDist == 2)) {
			attackers.add(knight);
		}
	}

	@Override
	public void visit(Rook rook) {
		Point rookPosition = rook.getPosition();
		Point kingPosition = target.getPosition();

		int hDist = Math.abs(rookPosition.getX() - kingPosition.getX());
		int vDist = Math.abs(rookPosition.getY() - kingPosition.getY());

		if (Math.min(hDist, vDist) == 0 //horizontal or vertical attack 
				&& isPathAvailable(rook, target)) {
			attackers.add(rook);
		}
	}

	@Override
	public void visit(Pawn pawn) {
		Point pawnPosition = pawn.getPosition();
		Point kingPosition = target.getPosition();

		if (pawnPosition.getY() - kingPosition.getY() == 1
				&& Math.abs(pawnPosition.getX() - kingPosition.getX()) == 1) {
			attackers.add(pawn);
		}

	}

	/**
	 * Check if there is another piece on the chess board which might block the line of sight between 2 other pieces
	 * @param start
	 * @param end
	 * @return true if the path is not blocked by another piece11
	 */
	private boolean isPathAvailable(AbstractChessPiece attacker, AbstractChessPiece target) {
		List<Point> points = getPathPointsBetween(attacker.getPosition(), target.getPosition());

		return points != null && points.stream().noneMatch((p -> chessboard.getPieces().get(p) != null));
	}

	/**
	 * Computes the intermediate points
	 * @param start
	 * @param end
	 * @return A list of intermediate points or null when there is no direct path between specified points
	 */
	private List<Point> getPathPointsBetween(Point start, Point end) {
		List<Point> points = new ArrayList<>();

		int hDist = end.getX() - start.getX();
		int vDist = end.getY() - start.getY();

		if (Math.abs(hDist) == Math.abs(vDist) || Math.min(Math.abs(hDist), Math.abs(vDist)) == 0) {
			for (int i = start.getX() + Integer.signum(hDist), j = start.getY() + Integer.signum(vDist); i != end.getX()
					|| j != end.getY(); i += Integer.signum(hDist), j += Integer.signum(vDist)) {
				points.add(new Point(i, j));
			}
			return points;
		}

		return null; // direct path does not exist
	}

}
