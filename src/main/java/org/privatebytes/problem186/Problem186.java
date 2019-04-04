package org.privatebytes.problem186;

import java.util.Arrays;

import org.apache.commons.lang3.tuple.Pair;

/**
 * This problem was asked by Microsoft. Given an array of positive integers,
 * divide the array into two subsets such that the difference between the sum of
 * the subsets is as small as possible. For example, given [5, 10, 15, 20, 25],
 * return the sets {10, 25} and {5, 15, 20}, which has a difference of 5, which
 * is the smallest possible difference.
 *
 */
public class Problem186 {

	public static void main(String[] args) {
		int[] input = { 5, 10, 15, 20, 25,30,35,45 };

		Problem186 p186 = new Problem186();

		Pair<int[], int[]> result = p186.solve(input);

		System.out.println(Arrays.stream(result.getLeft()).reduce(0, (a, b) -> a + b) + " --> "
				+ Arrays.toString(result.getLeft()));
		System.out.println(Arrays.stream(result.getRight()).reduce(0, (a, b) -> a + b) + " --> "
				+ Arrays.toString(result.getRight()));

	}

	public Pair<int[], int[]> solve(int[] input) {
		Pair<SumIntList, SumIntList> subsets = initSubsets(input);

		SumIntList ss1 = subsets.getLeft();
		SumIntList ss2 = subsets.getRight();

		minimizeDifference(ss1, ss2);

		return Pair.of(ss1.stream().mapToInt(Integer::intValue).toArray(),
				ss2.stream().mapToInt(Integer::intValue).toArray());
	}

	
	private void minimizeDifference(SumIntList ss1, SumIntList ss2) {
		long diff = ss1.getElementsSum() - ss2.getElementsSum();
		
		long targetDistance = diff/2;
		
		
		//TODO:
		//move some negative elements from smaller list to bigger list
		
		//move some positive elements from greater list to smaller list 
		
		//find elements to swap between lists by computing distances between them
	}
	
	private Pair<SumIntList, SumIntList> initSubsets(int[] input) {
		int i = 0;
		int j = input.length - 1;

		SumIntList ss1 = new SumIntList();
		SumIntList ss2 = new SumIntList();
		do {
			int n1 = input[i];
			int n2 = input[j];

			pickBestList(ss1, ss2, n1).add(n1);
			
			if(i!=j) {
				pickBestList(ss1, ss2, n2).add(n2);	
			}
			
			i++;
			j--;
		} while (i <= j);

		return Pair.of(ss1, ss2);
	}

	private SumIntList pickBestList(SumIntList ss1, SumIntList ss2, int number) {
		if (number >= 0) {
			return getMinList(ss1, ss2);
		} else {
			return getMaxList(ss1, ss2);
		}
	}

	private SumIntList getMinList(SumIntList s1, SumIntList s2) {
		if (s1.getElementsSum() <= s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

	private SumIntList getMaxList(SumIntList s1, SumIntList s2) {
		if (s1.getElementsSum() > s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

}
