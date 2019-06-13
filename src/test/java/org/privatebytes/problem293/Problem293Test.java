package org.privatebytes.problem293;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Problem293Test {

	private Problem293 unit;


	@BeforeEach
	public void setup() {
		unit = new Problem293();
	}

	@Test
	void test1() {
		long cost = solve("[1, 1, 3, 3, 2, 1]");
		
		assertEquals(2, cost);
	}
	
	@Test
	void test2() {
		long cost = solve("[10, 0, 4, 3, 38, 1]");
		
		assertEquals(52, cost);
	}
	
	@Test
	void test_with_no_stone() {
		String stones = IntStream.range(1, 10).map(i -> 0).boxed().collect(Collectors.toList()).toString();
		long cost = solve(stones);
		
		assertEquals(0, cost);
	}
	
	@Test
	void test_with_pyramid_input() {
		long cost = solve("[1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1]");
		
		assertEquals(0, cost);
	}
	
	
	@Test
	void test_with_2500_elements() {
		String stones = IntStream.range(1, 2500).map(i -> (i % 5 == 0) ? i : 100).boxed().collect(Collectors.toList()).toString();
		
		long cost = solve(stones);
		
		assertEquals(813549, cost);
		
	}
	
	@Test
	void test_with_100000_elements_ordered_ascending() {
		String stones = IntStream.range(1, 100000).boxed().collect(Collectors.toList()).toString();
		
		long cost = solve(stones);
		
		assertEquals(2499950000L, cost);
		
	}
	
	
	@Test
	void test_with_100000_elements_ordered_descending() {
		String stones = IntStream.range(1, 100000).boxed().map(i -> 100000-i).collect(Collectors.toList()).toString();
		
		long cost = solve(stones);
		
		assertEquals(2499950000L, cost);
	}
	
	@Test
	void test_with_100000_elements_and_all_even_indexes_are_zero() {
		String stones = IntStream.range(1, 100000).boxed().map(i -> (i%2==0)? 0 : i).collect(Collectors.toList()).toString();
		
		long cost = solve(stones);
		
		assertEquals(2499999999L, cost);
	}
	
	
	private long solve(String input) {
		long start = System.currentTimeMillis();
		long cost = unit.solve(input);
		long duration = System.currentTimeMillis()-start;
		System.out.println("Time: " + duration + "ms");
		
		return cost;
	}
}
