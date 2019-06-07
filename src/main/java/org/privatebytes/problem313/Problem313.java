package org.privatebytes.problem313;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;


public class Problem313 {

	public List<String> solve(String target, String deadends) {
		System.out.println("Target: " + target);
		System.out.println("Deadends: " + deadends);
		
		List<Integer> forbidden = parseDeadends(deadends);
		
		int wheels = target.length();

		Graph g = generateGraph(wheels, forbidden);
		List<Integer> result = new BellmanFord(g).shortestPath(0, Integer.parseInt(target));

		return result.stream().map(i -> String.format("%0" + wheels + "d", i)).collect(Collectors.toList());
	}
	
	private Graph generateGraph(int wheels, List<Integer> deadends) {
		Graph g = new Graph();

		int maxCombinations = (int)Math.pow(10, wheels);
		IntStream.range(0, maxCombinations).filter(i -> ! deadends.contains(i)).boxed().forEach(g::createNode);

		Collection<Node> nodes = g.nodes.values();

		for(Node n : nodes) {
			List<Integer> neighbours = computeNodeNeighbours(n.id, wheels);

			neighbours.stream().filter(i -> ! deadends.contains(i)).forEach(i -> g.addEdge(n.id, i));
		}

		return g;
	}

	private List<Integer> computeNodeNeighbours(int id, int wheels) {

		List<Integer> neighbours = new ArrayList<>();
		for(int i = 0; i < wheels; i++) {
			neighbours.add(moveUp(id, i));
			neighbours.add(moveDown(id, i));
		}

		return neighbours;
	}

	private int moveUp(int n, int wheel) {
		return move(n, wheel, a -> a+1);
	}

	private int moveDown(int n, int wheel) {
		return move(n, wheel, a -> a-1);
	}
	
	private int move(int n, int wheel, IntUnaryOperator func) {
		int result = n;

		List<Integer> numbers = new ArrayList<>();

		int wheelVal = 0;
		for(int i = 0; i <= wheel; i++) {
			wheelVal = result % 10;
			result = result / 10;
			numbers.add(wheelVal);
		}
		numbers.remove(numbers.size() - 1);
		wheelVal = func.applyAsInt(wheelVal);
		if(wheelVal >= 10) {
			wheelVal -= 10;
		}else if(wheelVal < 0) {
			wheelVal += 10;
		}
		numbers.add(wheelVal);

		for(int i = numbers.size() - 1; i >= 0; i--) {
			result = result * 10 + numbers.get(i);
		}

		return result;
	}
	
	private List<Integer> parseDeadends(String deadends) {
		String forbidden = deadends.replaceAll("[\\[\\]\\s]+", "");

		if(forbidden.isEmpty()) {
			return Collections.emptyList();
		}

		return Arrays.stream(forbidden.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
	}

	private class Node {

		private final int id;

		private final Set<Integer> edges;

		public Node(int id) {
			this.id = id;
			edges = new HashSet<>();
		}
	}

	private class Graph {

		private final Map<Integer, Node> nodes;

		public Graph() {
			nodes = new HashMap<>();
		}

		public Graph createNode(int id) {
			nodes.computeIfAbsent(id, k -> new Node(id));
			return this;
		}

		public Graph addEdge(int n1, int n2) {
			Node node1 = nodes.get(n1);
			Node node2 = nodes.get(n2);

			if(node1 == null || node2 == null) {
				throw new IllegalArgumentException("Cannot connect " + n1 + " and " + n2 + ". At least one node does node exist.");
			}

			nodes.get(n1).edges.add(n2);

			return this;
		}

		public Node getNode(int id) {
			return nodes.get(id);
		}

		public List<Pair<Integer, Integer>> getAllEdges() {
			List<Pair<Integer, Integer>> edges = new ArrayList<>();
			for(Map.Entry<Integer, Node> nodeEntry : nodes.entrySet()) {
				int from = nodeEntry.getKey();
				for(Integer to : nodeEntry.getValue().edges) {
					edges.add(Pair.of(from, to));
				}
			}

			return edges;
		}
	}

	private class BellmanFord {

		private Graph graph;

		private Map<Integer, Integer> distFromStart;

		private Map<Integer, List<Integer>> pathsFromStart;

		public BellmanFord(Graph graph) {
			this.graph = graph;

			distFromStart = new HashMap<>();
			pathsFromStart = new HashMap<>();
		}

		public List<Integer> shortestPath(int source, int destination) {
			Node start = graph.getNode(source);
			Node end = graph.getNode(destination);

			if(start == null || end == null) {
				System.out.println("There is no path from " + source + " to " + destination);
				return Collections.emptyList();
			}

			// step1: initialize distances from source to every other node with INFINITY
			for(Integer id : graph.nodes.keySet()) {
				distFromStart.put(id, Integer.MAX_VALUE);

				pathsFromStart.computeIfAbsent(id, ArrayList::new);
			}

			distFromStart.put(source, 0);
			List<Integer> nodes = pathsFromStart.get(source);
			nodes.add(source);

			// step2: relax all the edges for nodesNumber-1 times
			List<Pair<Integer, Integer>> edges = graph.getAllEdges();
			for(int i = 1; i < distFromStart.size(); i++) {
				boolean anyNodesRelaxed = false;
				for(Pair<Integer, Integer> edge : edges) {
					int from = edge.getLeft();
					int to = edge.getRight();
					int weight = 1;

					Integer fromDist = distFromStart.getOrDefault(from, Integer.MAX_VALUE);
					Integer toDist = distFromStart.getOrDefault(to, Integer.MAX_VALUE);

					if(fromDist != Integer.MAX_VALUE && (fromDist + weight < toDist)) {
						distFromStart.put(to, fromDist + weight);
						List<Integer> pathNodes = new ArrayList<>(pathsFromStart.get(from));
						pathNodes.add(to);
						pathsFromStart.put(to, pathNodes);
						anyNodesRelaxed = true;
					}
				}

				// finish early if no changes in this iteration
				if( ! anyNodesRelaxed) {
					break;
				}
			}

			return pathsFromStart.get(destination);
		}
	}
}
