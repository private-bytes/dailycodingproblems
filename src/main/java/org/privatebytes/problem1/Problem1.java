package org.privatebytes.problem1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Given a list of numbers and a number k, return whether any two numbers from
 * the list add up to k. For example, given [10, 15, 3, 7] and k of 17, return
 * true since 10 + 7 is 17.
 * 
 * Bonus: Can you do this in one pass?
 *
 */
public class Problem1 {

	public static void main(String[] args) {
		List<Integer> numbers = Stream.of(10, 15, 3, 7).collect(Collectors.toList());
		int k = 17;

		System.out.println(hasNumbersWithSum(numbers, k));

	}

	private static boolean hasNumbersWithSum(List<Integer> numbers, int sum) {
		List<Integer> complements = new ArrayList<>();

		for (Integer n : numbers) {
			int c = sum - n;

			if (c != 0 && !complements.contains(n)) {
				complements.add(c);
			} else {
				return true;
			}
		}

		return false;
	}

}
