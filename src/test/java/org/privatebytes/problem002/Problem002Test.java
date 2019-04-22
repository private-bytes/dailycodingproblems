package org.privatebytes.problem002;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Problem002Test {

	private Problem002 unit;

	@BeforeEach
	public void setup() {
		unit = new Problem002();
	}

	@Nested
	@DisplayName("Solution with division")
	class SolutionWithDivision {

		@Test
		public void test_exception_is_thrown_when_input_is_null() {
			assertThrows(NullPointerException.class, () -> unit.product_with_division(null));
		}

		@Test
		public void test_empty_output_array_returned_when_input_is_empty() {
			int[] input = new int[0];
			long[] output = unit.product_with_division(input);

			assertEquals(input.length, output.length);
		}

		@Test
		public void test_with_one_non_zero_element() {
			int[] input = { 1 };
			long[] output = unit.product_with_division(input);

			assertEquals(0, output[0]);
		}

		@Test
		public void test_with_a_single_element_with_value_zero() {
			int[] input = { 0 };
			long[] output = unit.product_with_division(input);

			assertEquals(0, output[0]);
		}

		@Test
		public void test_with_multiple_non_zero_elements() {
			int[] input = { 1, 2, 3 };

			long[] output = unit.product_with_division(input);
			assertAll(() -> assertEquals(input.length, output.length), () -> assertEquals(6, output[0]),
					() -> assertEquals(3, output[1]), () -> assertEquals(2, output[2]));
		}

		@Test
		public void test_with_zero_and_non_zero_elements() {
			int[] input = { 0, 1, 1 };

			long[] output = unit.product_with_division(input);
			assertAll(() -> assertEquals(input.length, output.length), () -> assertEquals(1, output[0]),
					() -> assertEquals(0, output[1]), () -> assertEquals(0, output[2]));

		}

		@Test
		public void test_with_multiple_zero_elements() {
			int[] input = { 0, 157, 41, 0 };

			long[] output = unit.product_with_division(input);
			assertAll(() -> assertEquals(input.length, output.length), () -> assertEquals(0, output[0]),
					() -> assertEquals(0, output[1]), () -> assertEquals(0, output[2]),
					() -> assertEquals(0, output[3]));

		}
	}

	@Nested
	@DisplayName("Solution without division")
	class SolutionWithoutDivision {

		@Test
		public void test_exception_is_thrown_when_input_is_null() {
			assertThrows(NullPointerException.class, () -> unit.product_without_division(null));
		}

		@Test
		public void test_empty_output_array_returned_when_input_is_empty() {
			int[] input = new int[0];
			long[] output = unit.product_without_division(input);

			assertEquals(input.length, output.length);
		}

		@Test
		public void test_with_one_non_zero_element() {
			int[] input = { 1 };
			long[] output = unit.product_without_division(input);

			assertEquals(0, output[0]);
		}

		@Test
		public void test_with_a_single_element_with_value_zero() {
			int[] input = { 0 };
			long[] output = unit.product_without_division(input);

			assertEquals(0, output[0]);
		}

		@Test
		public void test_with_multiple_non_zero_elements() {
			int[] input = { 1, 2, 3 };

			long[] output = unit.product_without_division(input);
			assertAll(() -> assertEquals(input.length, output.length), () -> assertEquals(6, output[0]),
					() -> assertEquals(3, output[1]), () -> assertEquals(2, output[2]));
		}

		@Test
		public void test_with_zero_and_non_zero_elements() {
			int[] input = { 0, 1, 1 };

			long[] output = unit.product_without_division(input);
			assertAll(() -> assertEquals(input.length, output.length), () -> assertEquals(1, output[0]),
					() -> assertEquals(0, output[1]), () -> assertEquals(0, output[2]));

		}

		@Test
		public void test_with_multiple_zero_elements() {
			int[] input = { 0, 157, 41, 0 };

			long[] output = unit.product_without_division(input);
			assertAll(() -> assertEquals(input.length, output.length), () -> assertEquals(0, output[0]),
					() -> assertEquals(0, output[1]), () -> assertEquals(0, output[2]),
					() -> assertEquals(0, output[3]));

		}
	}
}
