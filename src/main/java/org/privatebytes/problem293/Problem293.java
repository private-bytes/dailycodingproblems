package org.privatebytes.problem293;

import java.util.Arrays;

public class Problem293 {

	public long solve(String stones) {
		int[] arrStones = parse(stones);

		long start = System.currentTimeMillis();
		long cost = solve(arrStones);
		System.out.println("Time without parse: " + (System.currentTimeMillis() - start) + "ms");
		System.out.println("Cost: " + cost);
		return cost;
	}

	private long solve(int[] stones) {
		Pyramid out = new Pyramid();
		Pyramid tmp = new Pyramid();
		tmp.height=stones[0];

		int neededHeight = 1;
		while (tmp.end < stones.length - 1) {
			skipZeros(stones, tmp);

			// go up hill
			goUpHill(stones, tmp, neededHeight);

			// go down hill
			neededHeight = tmp.height - 1;
			goDownHill(stones, tmp, neededHeight);

			// update result
			if (tmp.height > out.height) {
				out.height = tmp.height;
				out.end = tmp.end;
				out.start = tmp.start;
			}

			// reset for next iteration
			if (tmp.end < stones.length - 1) {
				tmp.height--;
				neededHeight = tmp.height;
				tmp.start++;
				tmp.end = tmp.start + tmp.height;

				if (tmp.height < 1) {
					tmp.height = 1;
					neededHeight = 1;
				}
			}
		}

		System.out.println("Max height: " + out.height);
		System.out.println("Start index: " + out.start);
		System.out.println("End index: " + out.end);

		Long maxCost = Arrays.stream(stones).mapToLong(Long::valueOf).reduce(0, (a, b) -> a + b);

		return maxCost - pyramidValue(out.height);
	}

	private void goDownHill(int[] stones, Pyramid tmp, int neededHeight) {
		while (neededHeight >= 1 && tmp.end < stones.length) {
			int diff = stones[tmp.end] - neededHeight;
			if (stones[tmp.end] >= neededHeight) {
				neededHeight--;

				if (tmp.end < stones.length - 1) {
					tmp.end++;
				}
			} else {
				tmp.height += diff; // reduce the height
				neededHeight += diff - 1;

				tmp.end += diff;
			}
		}
	}

	private void goUpHill(int[] stones, Pyramid tmp, int neededHeight) {
		while (stones[tmp.end] >= neededHeight) {
			int remainingElements = stones.length - tmp.end;
			if (tmp.end < stones.length - 1 &&  remainingElements>=tmp.height) {
				tmp.height = neededHeight;
				neededHeight++;
				tmp.end++;
			} else {
				break;
			}
		}
	}

	private void skipZeros(int[] arrStones, Pyramid p) {
		while (arrStones[p.start] == 0) {
			if (p.start < arrStones.length - 1) {
				p.start++;
			} else {
				break;
			}
		}

		if (p.end < p.start) {
			p.end = p.start;
		}
	}

	private Long pyramidValue(int maxHeight) {
		return  maxHeight + maxHeight*(maxHeight - 1L);
	}

	private int[] parse(String input) {
		return Arrays.stream(input.replaceAll("[\\[\\]\\s]+", "").split(",")).mapToInt(Integer::parseInt).toArray();
	}
	
	private static class Pyramid{
		int start;
		int end;
		int height;
	}
}
