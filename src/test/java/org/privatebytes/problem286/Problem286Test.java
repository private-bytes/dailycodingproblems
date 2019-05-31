package org.privatebytes.problem286;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Problem286Test {

	private Problem286 unit;


	@BeforeEach
	public void setup() {
		unit = new Problem286();
	}

	@Test
	void test_default_input() {
		List<Pair<Integer, Integer>> output = unit.solve("[(0, 15, 3), (4, 11, 5), (19, 23, 4)]");
		System.out.println(output);
		
		assertEquals(6, output.size());
	}
	
	@Test
	void test_far_away_building() {
		List<Pair<Integer, Integer>> output = unit.solve("[(0, 15, 3), (" + (Integer.MAX_VALUE-2) + ", " + (Integer.MAX_VALUE-1) + ", 4)]");
		
		System.out.println(output);
	}
	
	@Test
	void test_two_sticks() {
		List<Pair<Integer, Integer>> output = unit.solve("[(3, 3, 10), (9, 9, 5)]");
		System.out.println(output);
		
		assertEquals(2, output.size());
	}
	
	@Test
	void test_gauss_shape() {
		List<Pair<Integer, Integer>> output = unit.solve("[(0, 1, 1), (4,5,2), (7,8,3), (9,10,5), (11,12,7), (13,14,10), (15,16,7), (17,18,5), (19,20,3), (22,23,2), (26,27,1)]");
		System.out.println(output);
		
		
	}
	
	@Test
	void test_thin_building() {
		List<Pair<Integer, Integer>> output = unit.solve("[(3, 4, 3), (8,9,5), (13,14,3)]");
		System.out.println(output);
		
		assertEquals(6, output.size());
	}
	
	@Test
	void test_psd() {
		List<Pair<Integer, Integer>> output = unit.solve("[(1,17,1), (5,15,2), (7,13,3), (9,11,6), (19,28,5), (21, 25, 6), (28,38,1), (40,42,6), (42,55,1), (47,55,6), (58,60,5),(60,68,6), (68,70,5)]");
		System.out.println(output);
		
		assertEquals(21, output.size());
	}	
	
	@Test
	void test_touching_buildings() {
		List<Pair<Integer, Integer>> output = unit.solve("[(0,5,3), (5,15,3), (15,20,3)]");
		System.out.println(output);
		
		assertEquals(2, output.size());
	}
	
	@Test
	void test_nested_buildings_same_width() {
		List<Pair<Integer, Integer>> output = unit.solve("[(0,20,3), (0,20,4), (0,20,5)]");
		System.out.println(output);
		assertEquals(2, output.size());
	}	
}
