package org.privatebytes.problem267;

import java.util.List;

import org.privatebytes.problem267.pieces.AbstractChessPiece;
import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitor;
import org.privatebytes.problem267.pieces.visitor.ChessPieceVisitorImpl;

/**
 * This problem was asked by Oracle.
 * You are presented with an 8 by 8 matrix representing the positions of pieces on a chess board. 
 * The only pieces on the board are the black king and various white pieces. Given this matrix, determine whether the king is in check.
 * For details on how each piece moves, see here.
 * For example, given the following matrix:
 * ...K....
 * ........
 * .B......
 * ......P.
 * .......R
 * ..N.....
 * ........
 * .....Q..
 * You should return True, since the bishop is attacking the king diagonally.
 * 
 *
 */
public class Problem267 {

	public static void main(String[] args) {
		
		
		char[][] chessboardMatrix = {
				"...K....".toCharArray(),
				"........".toCharArray(),
				".B......".toCharArray(),
				"......P.".toCharArray(),
				".......R".toCharArray(),
				"..N.....".toCharArray(),
				"........".toCharArray(),
				".....Q..".toCharArray()
		};
		
		List<AbstractChessPiece> attackers = new Problem267().getAttackers(chessboardMatrix);

		System.out.println(attackers);
		System.out.println("King under attack: " + !attackers.isEmpty());
	}
	
	public List<AbstractChessPiece> getAttackers(char[][] chessboardMatrix) {
		ChessBoard chessboard = new ChessBoard(chessboardMatrix);
		ChessPieceVisitor<List<AbstractChessPiece>> visitor = new ChessPieceVisitorImpl(chessboard);

		chessboard.getPieces().values().forEach(p -> p.accept(visitor));

		return visitor.getResult();
	}
}