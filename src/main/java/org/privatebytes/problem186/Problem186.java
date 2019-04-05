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
//		int[] input = { 5, 10, 15, 20, 25, 200, 30, 35, -39, 50, 55, 60, 65 };
		int[] input = { -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, 180 }; // do not erase -- fix the moving strategy

		Problem186 p186 = new Problem186();

		Pair<int[], int[]> result = p186.solve(input);

		System.out.println("-------------------------------------------------");
		print(result.getLeft(), result.getRight());

	}

	private static void print(int[] ss1, int[] ss2) {
		System.out.println(Arrays.stream(ss1).reduce(0, (a, b) -> a + b) + " --> " + Arrays.toString(ss1));
		System.out.println(Arrays.stream(ss2).reduce(0, (a, b) -> a + b) + " --> " + Arrays.toString(ss2));

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
		System.out.println("start minimizing...");
		System.out.println(ss1);
		System.out.println(ss2);

		long diff = ss1.getElementsSum() - ss2.getElementsSum();

		if (diff == 0) {
			return;
		}

		SumIntList maxList = maxSum(ss1, ss2);
		SumIntList minList = minSum(ss1, ss2);

		boolean elementsMoved = moveElements(maxList, minList);
		boolean elementsSwapped = swapElements(maxList, minList);

		// repeat the process until there are no more elements to moved between the
		// subsets
		if (elementsMoved || elementsSwapped) {
			minimizeDifference(ss1, ss2);
		}

	}

	private boolean moveElements(SumIntList maxList, SumIntList minList) {
		long diff = maxList.getElementsSum() - minList.getElementsSum();

		// this is the number that IDEALLY have need to move from one list to the other
		long targetDistance = diff / 2;

		Pair<Integer, Long> valueFromMaxList = findClosestTo(maxList, targetDistance, e -> e > 0); // want a negative
																									// number from here
		Pair<Integer, Long> valueFromMinList = findClosestTo(minList, -targetDistance, e -> e < 0); // want a positve
																									// number from here

		Range<Long> acceptableRange = Range.between(0l, Math.abs(diff) - 1);

		boolean valueFromMaxListInRange = isValueInRange(valueFromMaxList, acceptableRange);
		boolean valueFromMinListInRange = isValueInRange(valueFromMinList, acceptableRange);

		if (valueFromMaxListInRange) {
			if (valueFromMinListInRange) {

				long dist1 = valueFromMaxList.getRight();
				long dist2 = valueFromMinList.getRight();

				if (dist1 < dist2) {
					return moveElement(valueFromMaxList.getLeft(), maxList, minList);
				} else {
					return moveElement(valueFromMinList.getLeft(), minList, maxList);
				}
			} else {
				return moveElement(valueFromMaxList.getLeft(), maxList, minList);
			}
		} else {
			if (valueFromMinListInRange) {
				return moveElement(valueFromMinList.getLeft(), minList, maxList);
			}
		}

		System.out.println("Nothing to move !!!");
		return false;
	}

	private boolean swapElements(SumIntList maxList, SumIntList minList) {

		Pair<Integer, Integer> candidates = null;

		long initialDist = maxList.getElementsSum() - minList.getElementsSum();
		long maxDist = 2 * initialDist - 1;
		long candidatesDist = maxDist;
		for (Integer val1 : maxList) {
//			long sum = maxList.getElementsSum() - val1;
			for (Integer val2 : minList) {

				long newDist = Math
						.abs(maxList.getElementsSum() - val1 + val2 - (minList.getElementsSum() - val2 + val1));

				if (newDist < initialDist) {
//				if (sum + val2 < maxList.getElementsSum()) {
					long currentDist = Math.abs(val1 - val2);

					if (currentDist < candidatesDist) {
						candidates = Pair.of(val1, val2);
						candidatesDist = currentDist;
						if (currentDist == initialDist) {
							break;
						}
					}
				}
			}
		}

		if (candidates != null) {
			swap(maxList, candidates.getLeft(), minList, candidates.getRight());
			return true;
		}

		System.out.println("Nothing to swap !!!");
		return false;
	}

	private void swap(SumIntList source, int fromSource, SumIntList target, int fromTarget) {
		System.out.println("swapping(" + fromSource + ", " + fromTarget + ")");
		moveElement(fromSource, source, target);
		moveElement(fromTarget, target, source);
	}

	private boolean moveElement(Integer element, SumIntList source, SumIntList target) {
		if (source.remove(element)) {
			target.add(element);

			System.out.println("moved " + element + " to " + target.getName());
			System.out.println(source);
			System.out.println(target);

			return true;
		}

		return false;
	}

	private boolean isValueInRange(Pair<Integer, Long> value, Range<Long> range) {
		return value != null && range.contains(value.getRight());
	}

	private Pair<Integer, Long> findClosestTo(SumIntList subset, long target, Predicate<Integer> filter) {

		Pair<Integer, Long> result = null;

		long min_dist = Integer.MAX_VALUE;
		for (Integer val : subset) {
			long current_dist = Math.abs(val - target);

			if (filter.test(val) && current_dist < min_dist) {
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
		if (s1.getElementsSum() == s2.getElementsSum()) {
			return minSize(s1, s2);
		}

		if (s1.getElementsSum() <= s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

	private SumIntList maxSum(SumIntList s1, SumIntList s2) {
		if (s1.getElementsSum() == s2.getElementsSum()) {
			return minSize(s1, s2);
		}

		if (s1.getElementsSum() > s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

	private SumIntList minSize(SumIntList s1, SumIntList s2) {
		if (s1.size() <= s2.size()) {
			return s1;
		}

		return s2;
	}

}
