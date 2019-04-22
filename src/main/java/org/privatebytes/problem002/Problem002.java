package org.privatebytes.problem002;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This problem was asked by Uber.
 * 
 * Given an array of integers, return a new array such that each element at
 * index i of the new array is the product of all the numbers in the original
 * array except the one at i. For example, if our input was [1, 2, 3, 4, 5], the
 * expected output would be [120, 60, 40, 30, 24]. If our input was [3, 2, 1],
 * the expected output would be [2, 3, 6].
 * 
 * Follow-up: what if you can't use division?
 *
 */
public class Problem002 {

	public static void main(String[] args) {
		int[] input = { 1, 2, 3, 4, 5 };

		Problem002 problem2 = new Problem002();

		long[] output = problem2.product_with_division(input);

		Arrays.stream(output).forEach(System.out::println);

		output = problem2.product_without_division(input);
		Arrays.stream(output).forEach(System.out::println);

	}

	/**
	 * The easy solution is to: 
	 * 1. compute the product of all elements from the
	 * array 
	 * 2. iterate over the array and compute the output element as:
	 * product/currentElement
	 * 
	 * Note: In case the input array contains some 0's then the above solution will
	 * fail. To fix this scenario we have to compute how many zeros are in the input
	 * array. If there are more than 1 zeros in the array then all the elements in
	 * the output array will be 0. If there is only a single 0 in the input array then
	 * we can compute the product of all the other elements and use it when the
	 * current element value is 0. The value for all the other elements will be 0.
	 * 
	 * @param input
	 * @return
	 */
	public long[] product_with_division(int[] input) {
		// count how many elements have value 0
		int zerosCount = (int) Arrays.stream(input).filter(i -> i == 0).count();
		// compute the product of all elements excluding the ones with value 0
		// identity is set to 0 in reduce() when there is more than one element with
		// value 0...otherwise is set to 1
		long product = Arrays.stream(input).filter(i -> i != 0)
				.reduce(zerosCount > 1 ? 0 : Math.min(1, Math.max(0, input.length - 1 - zerosCount)), (a, b) -> a * b);

		return Arrays.stream(input).mapToLong(e -> e == 0 ? product : (1 - Math.min(1, zerosCount)) * product / e)
				.toArray();
	}

	public long[] product_without_division(int[] input) {
		return IntStream.range(0, input.length).mapToLong(i -> product(input, i)).toArray();
	}

	private long product(int[] input, int ignorePosition) {
		return IntStream.range(0, input.length).filter(i -> i != ignorePosition).map(i -> input[i])
				.reduce(Math.min(1, input.length - 1), (a, b) -> a * b);
	}

}
