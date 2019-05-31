package org.privatebytes.problem286;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;


/**
 * This problem was asked by VMware.
 * 
 * The skyline of a city is composed of several buildings of various widths and heights, possibly overlapping one
 * another when viewed from a distance. We can represent the buildings using an array of (left, right, height) tuples,
 * which tell us where on an imaginary x-axis a building begins and ends, and how tall it is. The skyline itself can be
 * described by a list of (x, height) tuples, giving the locations at which the height visible to a distant observer
 * changes, and each new height.
 * 
 * Given an array of buildings as described above, create a function that returns the skyline. For example, suppose the
 * input consists of the buildings [(0, 15, 3), (4, 11, 5), (19, 23, 4)]. In aggregate, these buildings would create a
 * skyline that looks like the one below.
 * 
 * @author b.ungureanu
 *
 */
public class Problem286 {

	public List<Pair<Integer, Integer>> solve(String input) {
		List<Triple<Integer, Integer, Integer>> buildings = parse(input);

		SortedMap<Integer, Integer> skyline = initSkyline(buildings);

		for(Triple<Integer, Integer, Integer> b : buildings) {
			int start = b.getLeft();
			int end = b.getMiddle() - 1;
			int height = b.getRight();

			SortedMap<Integer, Integer> partialSkyline = end - start > 0 ? skyline.subMap(start, end) : skyline.subMap(start, start + 1);

			partialSkyline.entrySet().forEach(e -> e.setValue(Math.max(e.getValue(), height)));
		}

		AtomicInteger prevHeight = new AtomicInteger( - 1);
		List<Pair<Integer, Integer>> result = skyline.entrySet().stream().filter(e -> {
			boolean keep = e.getValue() != prevHeight.get();
			prevHeight.set(e.getValue());
			return keep;
		}).map(e -> Pair.of(e.getKey(), e.getValue())).collect(Collectors.toList());
		printSkyline(result);
		return result;
	}

	private SortedMap<Integer, Integer> initSkyline(List<Triple<Integer, Integer, Integer>> buildings) {
		SortedMap<Integer, Integer> skyline = new TreeMap<>();

		buildings.forEach(b -> {
			skyline.putIfAbsent(b.getLeft(), 0);
			skyline.putIfAbsent(b.getMiddle(), 0);
		});

		return skyline;
	}

	private List<Triple<Integer, Integer, Integer>> parse(String input) {
		List<Triple<Integer, Integer, Integer>> result = new ArrayList<>();

		String[] buildings = input.replaceAll("\\),", "#").replaceAll("[\\[\\]\\s\\(\\)]+", "").split("#");

		for(String b : buildings) {
			String[] data = b.split(",");

			int height = Integer.parseInt(data[2]);

			Triple<Integer, Integer, Integer> building = Triple.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), height);
			result.add(building);
		}

		return result;
	}

	
	//---------------------------------------------------------------------------------
	private void printSkyline(List<Pair<Integer, Integer>> skyline) {
		int skylineStart = skyline.get(0).getLeft();
		int maxInt = skyline.get(skyline.size() - 1).getLeft() + 1;
		int maxHeight = skyline.stream().mapToInt(Pair::getRight).max().orElse(0) + 1;
		
		if(maxInt-skylineStart > 10000 || maxHeight > 10000) {
			System.out.println("Skyline too big. Skipping ");
			return;
		}
		

		String[][] matrix = new String[maxHeight][maxInt];

		Arrays.stream(matrix).forEach(l -> Arrays.fill(l, " "));

		Integer prevX = null;
		Integer prevH = 0;

		List<Pair<Integer, Integer>> indexes = new ArrayList<>();
		for(Pair<Integer, Integer> entry : skyline) {

			Integer newX = entry.getLeft();
			Integer newH = entry.getRight();

			// print vertical part
			for(int i = Math.min(maxHeight - prevH, maxHeight - newH); i < Math.max(maxHeight - prevH, maxHeight - newH); i++) {
				matrix[i][newX] = "|";
			}

			// fill interval (prevX, newX) with _ at prev height
			if(prevX != null && prevH > 0) {
				for(int i = prevX + 1; i < newX; i++) {
					matrix[maxHeight - prevH - 1][i] = "_";

					indexes.add(Pair.of(maxHeight - prevH - 1, i));
				}
			}

			prevX = newX;
			prevH = newH;

		}

		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}

		IntStream.range(0, matrix[0].length).forEach(i -> System.out.print("-"));
		System.out.println();
	}
}
