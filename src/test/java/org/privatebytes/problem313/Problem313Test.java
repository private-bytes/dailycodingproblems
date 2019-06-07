package org.privatebytes.problem313;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class Problem313Test {

	private static final String FOUR_WHEELS_TARGET = "4527";

	private static final String THREE_WHEEL_TARGET = "584";

	private Problem313 unit;

	@BeforeEach
	public void setup() {
		unit = new Problem313();
	}

	@Test
	void test_with_4_wheels() {
		List<String> output = solve(FOUR_WHEELS_TARGET, IntStream.range(1, 10000).filter(i -> i
				% 5 == 0).boxed().map(i -> String.format("%04d", i)).collect(Collectors.toList()).toString());

		print(output);
	}

	@Test
	void test_with_no_deadends() {
		List<String> output = solve(THREE_WHEEL_TARGET, Collections.emptyList().toString());
		print(output);

		assertEquals(11, output.size() - 1);
	}

	@Test
	void test_with_unreachable_target() {
		List<String> output = solve(THREE_WHEEL_TARGET, List.of("484", "684", "574", "594", "583", "585").toString());

		print(output);

		assertEquals(0, output.size());
	}

	@Test
	void test_with_deadend_target() {
		List<String> output = solve(THREE_WHEEL_TARGET, Collections.singletonList(THREE_WHEEL_TARGET).toString());

		print(output);

		assertEquals(0, output.size());
	}

	@Test
	void test_with_may_even_numbers_as_deadends() {
		List<String> output = solve(THREE_WHEEL_TARGET, IntStream.range(3, 1000).filter(i -> i % 2 == 0
				&& i != 584).boxed().map(i -> String.format("%03d", i)).collect(Collectors.toList()).toString());

		print(output);

		assertEquals(11, output.size() - 1);
	}

	@Test
	void test_with_many_odd_numbers_as_deadends() {
		List<String> output = solve(THREE_WHEEL_TARGET, IntStream.range(2, 995).filter(i -> i
				% 2 == 1).boxed().map(i -> String.format("%03d", i)).collect(Collectors.toList()).toString());

		print(output);

		assertEquals(13, output.size() - 1);
	}

	@Test
	void test_with_unique_solution() {
		List<String> allowedCombinations = List.of("000", "010", "020", "029", "028", "128", "228", "227", "327", "337", "336", "436", "446", "456", "556", "555", "554", "564", "574", THREE_WHEEL_TARGET);

		List<String> output = solve(THREE_WHEEL_TARGET, IntStream.range(1, 1000).boxed().map(i -> String.format("%03d", i)).filter(s -> ! allowedCombinations.contains(s)).collect(Collectors.toList()).toString());

		print(output);

		assertEquals(allowedCombinations.size(), output.size());
	}

	private List<String> solve(String target, String deadends) {
		long start = System.currentTimeMillis();
		List<String> output = unit.solve(target, deadends);
		System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
		return output;
	}

	private void print(List<String> solution) {
		if( ! solution.isEmpty()) {
			System.out.println("Solution: " + solution.stream().collect(Collectors.joining(" --> ")));
			System.out.println("Target reached in " + (solution.size() - 1) + " steps");
		} else {
			System.out.println("Impossible to solve.");
		}

		System.out.println("------------------------------------------");
	}

}
