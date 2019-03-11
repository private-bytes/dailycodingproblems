package org.privatebytes.problem267;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.privatebytes.problem267.pieces.AbstractChessPiece;
import org.privatebytes.problem267.pieces.Bishop;
import org.privatebytes.problem267.pieces.King;
import org.privatebytes.problem267.pieces.Pawn;
import org.privatebytes.problem267.pieces.Point;

public class Problem267Test {

	private Problem267 unit;

	private King king = new King(new Point(3, 3));

	@BeforeEach
	public void setup() {
		unit = new Problem267();
	}

	@Nested
	@DisplayName("Pawn attacks")
	class PawnAttack {

		private Pawn not_attacking_pawn = new Pawn(new Point(3, 4));
		private Pawn pawn_attacking_left = new Pawn(new Point(4, 4));
		private Pawn pawn_attacking_right = new Pawn(new Point(2, 4));

		@Test
		@DisplayName("Pawn attacks to the left side")
		public void test_pawn_left_side_attack() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, pawn_attacking_left);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertEquals(1, attackers.size(), "Exactly 1 attacker should be in the list"),
					() -> assertEquals(pawn_attacking_left, attackers.get(0),
							"First attacker must be " + pawn_attacking_left.toString()));
		}

		@Test
		@DisplayName("Pawn attacks to the right side")
		public void test_pawn_right_side_attack() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, pawn_attacking_right);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertEquals(1, attackers.size(), "Exactly 1 attacker should be in the list"),
					() -> assertEquals(pawn_attacking_right, attackers.get(0),
							"First attacker must be " + pawn_attacking_right.toString()));
		}

		@Test
		@DisplayName("Pawn does not attack")
		public void test_pawn_not_attacking() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, not_attacking_pawn);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertTrue(attackers.isEmpty(), "King should not be under attack"));
		}
	}

	@Nested
	@DisplayName("Bishop attacks")
	class BishopAttack {
		private Bishop bishop_attacking_top_left = new Bishop(new Point(7, 7));
		private Bishop bishop_attacking_top_right = new Bishop(new Point(0, 6));
		private Bishop bishop_attacking_bottom_left = new Bishop(new Point(6, 0));
		private Bishop bishop_attacking_bottom_right = new Bishop(new Point(0, 0));
		private Bishop not_attacking_bishop = new Bishop(new Point(7, 3));

		@Test
		@DisplayName("Bishop attacks to the top left side")
		public void test_bishop_attack_to_top_left_side() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_top_left);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertEquals(1, attackers.size(), "Exactly 1 attacker should be in the list"),
					() -> assertEquals(bishop_attacking_top_left, attackers.get(0),
							"First attacker must be " + bishop_attacking_top_left.toString()));
		}

		@Test
		@DisplayName("Bishop attacks to the top right side")
		public void test_bishop_attack_to_top_right_side() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_top_right);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertEquals(1, attackers.size(), "Exactly 1 attacker should be in the list"),
					() -> assertEquals(bishop_attacking_top_right, attackers.get(0),
							"First attacker must be " + bishop_attacking_top_right.toString()));
		}

		@Test
		@DisplayName("Bishop attacks to the bottom left side")
		public void test_bishop_attack_to_bottom_left_side() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_bottom_left);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertEquals(1, attackers.size(), "Exactly 1 attacker should be in the list"),
					() -> assertEquals(bishop_attacking_bottom_left, attackers.get(0),
							"First attacker must be " + bishop_attacking_bottom_left.toString()));

		}

		@Test
		@DisplayName("Bishop attacks to the bottom right side")
		public void test_bishop_attack_to_bottom_right_side() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_bottom_right);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertEquals(1, attackers.size(), "Exactly 1 attacker should be in the list"),
					() -> assertEquals(bishop_attacking_bottom_right, attackers.get(0),
							"First attacker must be " + bishop_attacking_bottom_right.toString()));

		}

		@Test
		@DisplayName("Bishop not attacking")
		public void test_bishop_not_attacking() {
			char[][] chessboardMatrix = generateChessboardMatrix(king, not_attacking_bishop);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertTrue(attackers.isEmpty(), "King should not be under attack"));

		}

		@Test
		@DisplayName("Bishop blocked attack to the top left")
		public void test_bishop_blocked_attack_to_top_left() {
			Pawn blocker = new Pawn(new Point(bishop_attacking_top_left.getPosition().getX() - 1,
					bishop_attacking_top_left.getPosition().getY() - 1));
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_top_left, blocker);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertTrue(attackers.isEmpty(), "King should not be under attack"));
		}

		@Test
		@DisplayName("Bishop blocked attack to the top right")
		public void test_bishop_blocked_attack_to_top_right() {
			Pawn blocker = new Pawn(new Point(bishop_attacking_top_right.getPosition().getX() + 1,
					bishop_attacking_top_right.getPosition().getY() - 1));
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_top_right, blocker);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertTrue(attackers.isEmpty(), "King should not be under attack"));
		}

		@Test
		@DisplayName("Bishop blocked attack to the bottom left")
		public void test_bishop_blocked_attack_to_bottom_left() {
			Pawn blocker = new Pawn(new Point(bishop_attacking_bottom_left.getPosition().getX() - 1,
					bishop_attacking_bottom_left.getPosition().getY() + 1));
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_bottom_left, blocker);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertTrue(attackers.isEmpty(), "King should not be under attack"));
		}

		@Test
		@DisplayName("Bishop blocked attack to the bottom right")
		public void test_bishop_blocked_attack_to_bottom_right() {
			Pawn blocker = new Pawn(new Point(bishop_attacking_bottom_right.getPosition().getX() + 1,
					bishop_attacking_bottom_right.getPosition().getY() + 1));
			char[][] chessboardMatrix = generateChessboardMatrix(king, bishop_attacking_bottom_right, blocker);

			List<AbstractChessPiece> attackers = unit.getAttackers(chessboardMatrix);

			assertAll(() -> assertNotNull(attackers, "Attackers list should not be null"),
					() -> assertTrue(attackers.isEmpty(), "King should not be under attack"));
		}

	}

	@Nested
	@DisplayName("Knight attacks")
	class KnightAttacks {

	}

	@Nested
	@DisplayName("Rook attacks")
	class RookAttacks {

	}

	@Nested
	@DisplayName("Queen attacks")
	class QueenAttacks {

	}

	@Nested
	@DisplayName("Multiple attackers")
	class MultipleAttackers {

	}

	private static char[][] generateChessboardMatrix(AbstractChessPiece... pieces) {
		char[][] chessboardMatrix = new char[8][8];

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				chessboardMatrix[r][c] = '.';
			}
		}

		Stream.of(pieces).forEach(p -> chessboardMatrix[p.getPosition().getY()][p.getPosition().getX()] = p.getType());

		return chessboardMatrix;
	}
}
