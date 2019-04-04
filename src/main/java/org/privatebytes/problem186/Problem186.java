package org.privatebytes.problem186;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.Range;
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
		//
		int[] input = { 5, 10, 15, 20, 25, 200, 30, 35, 39, 50, 55, 60, 65 };

		Problem186 p186 = new Problem186();

		Pair<int[], int[]> result = p186.solve(input);
	
		System.out.println("-------------------------------------------------");
		print(result.getLeft(), result.getRight());

	}

	private static void print(int[] ss1, int[] ss2) {
		System.out.println(Arrays.stream(ss1).reduce(0, (a, b) -> a + b) + " --> "
				+ Arrays.toString(ss1));
		System.out.println(Arrays.stream(ss2).reduce(0, (a, b) -> a + b) + " --> "
				+ Arrays.toString(ss2));

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
		System.out.println(ss1.getElementsSum()  + " --> " + ss1);
		System.out.println(ss2.getElementsSum()  + " --> " + ss2);
		
		
		long diff = ss1.getElementsSum() - ss2.getElementsSum();

		if (diff == 0) {
			return;
		}

		// this is the number that IDEALLY have need to move from one list to the other
		long targetDistance = diff / 2;

		// find the number that reduces, more then the other, the distance between the 2
		// sums
		SumIntList maxList = maxSum(ss1, ss2);
		Pair<Integer, Long> valueFromMaxList = findClosestTo(maxList, targetDistance, e-> e> 0); //want a negative number from here

		SumIntList minList = minSum(ss1, ss2);
		Pair<Integer, Long> valueFromMinList = findClosestTo(minList, -targetDistance, e -> e < 0); //want a positve number from here

		System.out.println(valueFromMaxList);
		System.out.println(valueFromMinList);

		Range<Long> acceptableRange = Range.between(0l, Math.abs(diff) - 1);

		
		boolean valueFromMaxListInRange = isValueInRange(valueFromMaxList, acceptableRange);
		boolean valueFromMinListInRange = isValueInRange(valueFromMinList, acceptableRange);

		
		if(valueFromMaxListInRange) {
			if(valueFromMinListInRange) {
				
				long dist1 = valueFromMaxList.getRight();
				long dist2 = valueFromMinList.getRight();
				
				if(dist1 < dist2) {
					moveElement(valueFromMaxList.getLeft(), maxList, minList);
				}else {
					moveElement(valueFromMinList.getLeft(), minList, maxList);
				}
			}else {
				moveElement(valueFromMaxList.getLeft(), maxList, minList);
			}
		}else {
			if(valueFromMinListInRange) {
				moveElement(valueFromMinList.getLeft(), minList, maxList);
			}
		}
		
		
		//TODO: swap 2 elements that have their difference smaller than summs diff
		
		
		//repeat the process until there are no more elements to move between the subsets
		if(valueFromMaxListInRange || valueFromMinListInRange){
			minimizeDifference(ss1, ss2);
		}
		

	}
	
	private void moveElement(Integer element, SumIntList source, SumIntList target) {
		if(source.remove(element)) {
			target.add(element);
			
			System.out.println(source.getName() + " --> " + target.getName() + ": " + element);
		}
	}

	private boolean isValueInRange(Pair<Integer, Long> value, Range<Long> range) {
		return value != null && range.contains(value.getRight());
	}

	private Pair<Integer, Long> findClosestTo(SumIntList subset, long target, Predicate<Integer> filter) {

		Pair<Integer, Long> result = null;

		long min_dist = Integer.MAX_VALUE;
		for (Integer val : subset) {
			long current_dist = Math.abs(val - target);

			
			
			if(filter.test(val) && current_dist < min_dist) {
				min_dist = current_dist;
				result = Pair.of(val, min_dist);
			}
		}

		return result;
	}

	private Pair<SumIntList, SumIntList> initSubsets(int[] input) {
		int i = 0;
		int j = input.length - 1;

		SumIntList ss1 = new SumIntList("ss1");
		SumIntList ss2 = new SumIntList("ss2");
		do {
			int n1 = input[i];
			int n2 = input[j];

			pickBestList(ss1, ss2, n1).add(n1);

			if (i != j) {
				pickBestList(ss1, ss2, n2).add(n2);
			}

			i++;
			j--;
		} while (i <= j);

		return Pair.of(ss1, ss2);
	}

	private SumIntList pickBestList(SumIntList ss1, SumIntList ss2, int number) {
		if (number >= 0) {
			return minSum(ss1, ss2);
		}

		return maxSum(ss1, ss2);
	}

	private SumIntList minSum(SumIntList s1, SumIntList s2) {
		if (s1.getElementsSum() <= s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

	private SumIntList maxSum(SumIntList s1, SumIntList s2) {
		if (s1.getElementsSum() > s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

}
