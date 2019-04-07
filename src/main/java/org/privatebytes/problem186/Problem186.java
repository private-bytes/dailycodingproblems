package org.privatebytes.problem186;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntPredicate;

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
		int[] input = {34, 98, 3, 5, 6, 102, 10, 11, 76, 45, 13, 46, 14, 22, 88, 90, 31};

		Problem186 p186 = new Problem186();

		Pair<int[], int[]> result = p186.solve(input);

		System.out.println("-------------------------------------------------");
		print(result.getLeft(), result.getRight());

	}

	private static void print(int[] ss1, int[] ss2) {
		System.out.println(Arrays.stream(ss1).sum() + " --> " + Arrays.toString(ss1));
		System.out.println(Arrays.stream(ss2).sum() + " --> " + Arrays.toString(ss2));

	}

	public Pair<int[], int[]> solve(int[] input) {
		if(Objects.isNull(input) || input.length == 0) {
			throw new IllegalArgumentException("Input cannot be null or empty");
		}

		Pair<SumIntList, SumIntList> subsets = initSubsets(input);

		SumIntList ss1 = subsets.getLeft();
		SumIntList ss2 = subsets.getRight();

		minimizeDifference(ss1, ss2);

		return Pair.of(ss1.stream().mapToInt(Integer::intValue).toArray(), ss2.stream().mapToInt(Integer::intValue).toArray());
	}

	private void minimizeDifference(SumIntList ss1, SumIntList ss2) {
		System.out.println("start minimizing...");
		System.out.println(ss1);
		System.out.println(ss2);

		SumIntList maxList = maxSum(ss1, ss2);
		SumIntList minList = minSum(ss1, ss2);

		long diff = maxList.getElementsSum() - minList.getElementsSum();

		if(diff <= 1) {
			System.out.println("stopping [ difference too small: " + diff + "]");
			return;
		}

		boolean elementsMoved = moveElements(maxList, minList);
		boolean elementsSwapped = false;

		if( ! elementsMoved) {
			elementsSwapped = swapElements(maxList, minList);
		}

		if(elementsMoved || elementsSwapped) {
			minimizeDifference(ss1, ss2);
		}

	}

	/**
	 * Find in each list the element that has the most impact in reducing the difference between the subsets sum.
	 * Between the two elements pick the one with a bigger impact.
	 * 
	 * @param maxList
	 * @param minList
	 * @return
	 */
	private boolean moveElements(SumIntList maxList, SumIntList minList) {
		long initialDiff = maxList.getElementsSum() - minList.getElementsSum();
		long targetDist = initialDiff / 2;

		int fromMaxList = findClosest(maxList, - targetDist, e -> e > 0);
		int fromMinList = findClosest(minList, targetDist, e -> e < 0);

		long newDiff1 = Math.abs((maxList.getElementsSum() - fromMaxList) - (minList.getElementsSum() + fromMaxList));
		long newDiff2 = Math.abs((maxList.getElementsSum() + fromMinList) - (minList.getElementsSum() - fromMinList));

		if(targetDist == 0L || (Math.min(newDiff1, newDiff2) >= initialDiff)) {
			return false;
		} else if(newDiff1 < newDiff2) {
			return moveElement(fromMaxList, maxList, minList);
		} else if(newDiff1 > newDiff2) {
			return moveElement(fromMinList, minList, maxList);
		}

		return false;

	}

	private int findClosest(SumIntList subset, long target, IntPredicate filter) {

		int result = 0;

		long minDist = Integer.MAX_VALUE;
		for(Integer val : subset) {
			long currentDist = Math.abs(val - target);

			if(filter.test(val) && currentDist < minDist) {
				minDist = currentDist;
				result = val;
			}
		}

		return result;
	}

	/**
	 * Find an element in each list such that their distance is smaller than half of the current subsets sum difference
	 * and swap them.
	 * 
	 * @param maxList
	 * @param minList
	 * @return
	 */
	private boolean swapElements(SumIntList maxList, SumIntList minList) {

		Pair<Integer, Integer> candidates = null;

		long initialDist = maxList.getElementsSum() - minList.getElementsSum();
		long maxDist = 2 * initialDist - 1;
		long candidatesDist = maxDist;

		for(Integer val1 : maxList) {
			for(Integer val2 : minList) {
				long newSumMaxList = maxList.getElementsSum() - val1 + val2;
				long newSumMinList = minList.getElementsSum() - val2 + val1;
				long newDist = Math.abs(newSumMaxList - newSumMinList);

				if(newDist < initialDist) {
					long currentDist = Math.abs(val1 - val2);

					if(currentDist < candidatesDist) {
						candidates = Pair.of(val1, val2);
						candidatesDist = currentDist;
						if(currentDist == initialDist) {
							break;
						}
					}
				}
			}
		}

		if(candidates != null) {
			swap(maxList, candidates.getLeft(), minList, candidates.getRight());
			return true;
		}

		System.out.println("Nothing to swap !!!");
		return false;
	}

	private void swap(SumIntList source, int fromSource, SumIntList target, int fromTarget) {
		System.out.println("swapping[" + source.getName() + "(" + fromSource + "), " + target.getName() + "(" + fromTarget + ")]");
		moveElement(fromSource, source, target);
		moveElement(fromTarget, target, source);
	}

	private boolean moveElement(Integer element, SumIntList source, SumIntList target) {
		if(source.remove(element)) {
			target.add(element);

			System.out.println("moved[" + element + ": " + source.getName() + " --> " + target.getName() + "]");
			System.out.println(source);
			System.out.println(target);

			return true;
		}

		return false;
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

			if(i != j) {
				pickBestList(ss1, ss2, n2).add(n2);
			}

			i++;
			j--;
		} while(i <= j);

		return Pair.of(ss1, ss2);
	}

	private SumIntList pickBestList(SumIntList ss1, SumIntList ss2, int number) {
		if(number >= 0) {
			return minSum(ss1, ss2);
		}

		return maxSum(ss1, ss2);
	}

	private SumIntList minSum(SumIntList s1, SumIntList s2) {
		if(s1.getElementsSum() == s2.getElementsSum()) {
			return minSize(s1, s2);
		}

		if(s1.getElementsSum() <= s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

	private SumIntList maxSum(SumIntList s1, SumIntList s2) {
		if(s1.getElementsSum() == s2.getElementsSum()) {
			return minSize(s1, s2);
		}

		if(s1.getElementsSum() > s2.getElementsSum()) {
			return s1;
		}

		return s2;
	}

	private SumIntList minSize(SumIntList s1, SumIntList s2) {
		if(s1.size() <= s2.size()) {
			return s1;
		}

		return s2;
	}

}
