package org.privatebytes.problem297;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This problem was asked by Amazon. At a popular bar, each customer has a set
 * of favorite drinks, and will happily accept any drink among this set. For
 * example, in the following situation, customer 0 will be satisfied with drinks
 * 0, 1, 3, or 6. preferences = { 0: [0, 1, 3, 6], 1: [1, 4, 7], 2: [2, 4, 7,
 * 5], 3: [3, 2, 5], 4: [5, 8] } A lazy bartender working at this bar is trying
 * to reduce his effort by limiting the drink recipes he must memorize. Given a
 * dictionary input such as the one above, return the fewest number of drinks he
 * must learn in order to satisfy all customers. For the input above, the answer
 * would be 2, as drinks 1 and 5 will satisfy everyone.
 *
 */
public class Problem297 {

	public static void main(String[] args) {

		// no brute force
//		int[][] prefs = new int[][] { { 0, 1, 3, 6 }, { 1, 4, 7 }, { 2, 4, 7, 5 }, { 3, 2, 5 }, { 5, 8 } };

		// will use brute force
//		int[][] prefs = new int[][] { { 0, 1, 4, 5 }, { 0, 1, 3 }, { 0, 2, 7 }, { 0, 2 }, { 1, 6, 5 }, { 2, 8 }, { 3 } };

//		int[][] prefs = new int[][] {{1,11},{2,12},{3,13},{4,14},{5,11},{6,12},{5,7,13},{8,14},{9},{10}};

		int[][] prefs = new int[][] { { 1, 2, 3, 4, 5, 6, 100 }, { 1, 2, 3, 4, 5, 6, 100 }, { 1, 2, 3, 4, 5, 6, 100 },
				{ 1, 2, 3, 4, 5, 6, 100 }, { 1, 2, 3, 4, 5, 6, 100 }, { 1, 2, 3, 4, 5, 6, 100 },
				{ 1, 7, 8, 9, 10, 11, 12, 200 }, { 1, 7, 8, 9, 10, 11, 12, 200 }, { 1, 7, 8, 9, 10, 11, 12, 200 },
				{ 7, 8, 9, 10, 11, 12, 200 }, { 7, 8, 9, 10, 11, 12, 200 }, { 7, 8, 9, 10, 11, 12, 200 },
				{ 13, 14, 15, 16, 17, 18, 300, 0 }, { 13, 14, 15, 16, 17, 18, 300 }, { 13, 14, 15, 16, 17, 18, 300 },
				{ 13, 14, 15, 16, 17, 18, 300 }, { 13, 14, 15, 16, 17, 18, 300 }, { 13, 14, 15, 16, 17, 18, 300 },
				{ 19, 20, 21, 22, 23, 24, 400 }, { 19, 20, 21, 22, 23, 24, 400 }, { 19, 20, 21, 22, 23, 24, 400 },
				{ 19, 20, 21, 22, 23, 24, 400 }, { 19, 20, 21, 22, 23, 24, 400 }, { 19, 20, 21, 22, 23, 24, 400 },
				{ 25, 26, 27, 28, 29, 30, 500 }, { 25, 26, 27, 28, 29, 30, 500 }, { 25, 26, 27, 28, 29, 30, 500 },
				{ 25, 26, 27, 28, 29, 30, 500,888 }, { 25, 26, 27, 28, 29, 30, 500 }, { 25, 26, 27, 28, 29, 30, 500 },
				{ 700,900,901,902 } };

		Map<Integer, Set<Integer>> clientPreferences = initPreferences(prefs);
		Set<Integer> drinksRecipies = new Problem297().solve(clientPreferences);

		System.out.println(drinksRecipies.size() + " --> " + drinksRecipies);
	}

	private static Map<Integer, Set<Integer>> initPreferences(int[]... drinks) {
		Map<Integer, Set<Integer>> result = new HashMap<>();
		for (int i = 0; i < drinks.length; i++) {
			result.put(i, Arrays.stream(drinks[i]).boxed().collect(Collectors.toSet()));
		}

		return result;
	}

	public Set<Integer> solve(Map<Integer, Set<Integer>> clientPreferences) {
		System.out.println("----------------------------------------");
		System.out.println("Input " + clientPreferences);

		Set<Integer> partialResult = partialSolution(clientPreferences);

		// remove clients satisfied by partial solution (reduces the number of subsets
		// analyzed in brute force solution)
		clientPreferences.entrySet().removeIf(e -> !Collections.disjoint(e.getValue(), partialResult));

		Set<Integer> result = smartSolution(clientPreferences);

		if (result.size() > 2) {
			result = bruteForceSolution(clientPreferences, result);
		}

		result.addAll(partialResult);

		System.out.println("Output " + result);
		System.out.println("----------------------------------------");

		return result;
	}

	/**
	 * Finds a possible solutions. Not necessarily the best one. Will be used an
	 * input for the brute force solution
	 * 
	 * @param clientPreferences
	 * @return
	 */
	private static Set<Integer> smartSolution(Map<Integer, Set<Integer>> clientPreferences) {
		Map<Integer, Set<Integer>> drinkPreferences = drinksDemand(clientPreferences);
		Set<Integer> satisfiedClients = new HashSet<>();
		Set<Integer> result = new HashSet<>();
		while (satisfiedClients.size() < clientPreferences.size()) {
			Integer drink = getMostDemandedDrink(drinkPreferences);

			if (drink != null) {
				result.add(drink);

				Set<Integer> clients = drinkPreferences.remove(drink); // remove drink from preferences;
				satisfiedClients.addAll(clients);
				drinkPreferences.forEach((k, v) -> v.removeAll(clients)); // remove clients of the drink from
																			// preferences
				drinkPreferences.entrySet().removeIf(e -> e.getValue().isEmpty()); // remove drinks with no remaining
																					// clients
			}
		}

		System.out.println("Smart solution " + result);
		return result;
	}

	/**
	 * Finds some results that are guaranteed to be present in the final solution.
	 * 
	 * @param clientPreferences
	 * @return
	 */
	private static Set<Integer> partialSolution(Map<Integer, Set<Integer>> clientPreferences) {
		Map<Integer, Set<Integer>> drinkPreferences = drinksDemand(clientPreferences);
		Set<Integer> result = new HashSet<>();

		for (Map.Entry<Integer, Set<Integer>> entry : clientPreferences.entrySet()) {

			Set<Integer> clientDrinks = entry.getValue();

			// client prefers only 1 drink
			// all drinks preferred by this client are not preferred by anybody else
			if (clientDrinks.size() == 1
					|| clientPreferences.entrySet().stream().filter(e -> e.getKey() != entry.getKey())
							.allMatch(e -> Collections.disjoint(e.getValue(), clientDrinks))) {
				result.add(clientDrinks.iterator().next()); //pick one drink as part of the final solution
				
				System.out.println("Drinks " + clientDrinks + " preferred only by client " + entry.getKey());
			}
		}

		System.out.println("Partial solution " + result);

		return result;
	}

	/**
	 * Applies brute force on all possible solutions with smaller size than the
	 * existingSolution
	 * 
	 * @param clientPreferences
	 * @param existingSolution
	 * @return
	 */
	private static Set<Integer> bruteForceSolution(Map<Integer, Set<Integer>> clientPreferences,
			Set<Integer> existingSolution) {
		Set<Integer> allDrinks = clientPreferences.values().stream().flatMap(Set::stream).collect(Collectors.toSet());

		Set<Integer> result = existingSolution;

		// start with all subsets of size 1
		Set<Set<Integer>> currentSubsets = allDrinks.stream().map(d -> Set.of(d)).collect(Collectors.toSet());

		for (int i = 2; i <= result.size() - 1; i++) {
			// generate all subsets of size "i"
			Set<Set<Integer>> drinkSubsets = getSubsets(allDrinks, currentSubsets);

			System.out.println("Brute forcing " + drinkSubsets.size() + " of size " + i);

			for (Set<Integer> ss : drinkSubsets) {
				if (isValidSolution(ss, clientPreferences) && ss.size() < result.size()) {
					System.out.println("Brute force solution " + ss);

					// the first solution found with brute force is the best one (there won't be any
					// other subset with smaller size to analyze)
					return result = ss;
				}
			}

			currentSubsets = drinkSubsets;
		}

		return result;

	}

	private static Integer getMostDemandedDrink(Map<Integer, Set<Integer>> drinkPreferences) {
		Integer result = null;
		int maxDrinkClients = 0;

		for (Map.Entry<Integer, Set<Integer>> entry : drinkPreferences.entrySet()) {
			int clientsOfADrink = entry.getValue().size();
			if (clientsOfADrink > maxDrinkClients) {
				result = entry.getKey();
				maxDrinkClients = clientsOfADrink;
			} else if (clientsOfADrink == maxDrinkClients) {
				result = entry.getKey();
			}
		}

		return result;
	}

	private static Map<Integer, Set<Integer>> drinksDemand(Map<Integer, Set<Integer>> clientPreferences) {
		Map<Integer, Set<Integer>> drinkPreferences = new HashMap<>();

		for (Map.Entry<Integer, Set<Integer>> pref : clientPreferences.entrySet()) {

			for (Integer drink : pref.getValue()) {
				Set<Integer> clients = drinkPreferences.computeIfAbsent(drink, HashSet::new);
				clients.add(pref.getKey());
			}
		}

		return drinkPreferences;
	}

	private static Set<Set<Integer>> getSubsets(Set<Integer> drinks, Set<Set<Integer>> currentSubsets) {
		if (drinks.isEmpty()) {
			return Collections.emptySet();
		}

		return generateSubsets(drinks, currentSubsets);
	}

	private static Set<Set<Integer>> generateSubsets(Set<Integer> origSet, Set<Set<Integer>> subsets) {

		Set<Set<Integer>> newSubsets = new HashSet<>();
		for (Set<Integer> subset : subsets) {
			for (Integer e : origSet) {
				if (!subset.contains(e)) {
					Set<Integer> newSubset = new HashSet<>(subset);
					newSubset.add(e);
					newSubsets.add(newSubset);
				}
			}
		}

		return newSubsets;
	}

	private static boolean isValidSolution(Set<Integer> drinks, Map<Integer, Set<Integer>> clientPreferences) {
		Set<Integer> clients = clientPreferences.entrySet().stream()
				.filter(e -> !Collections.disjoint(e.getValue(), drinks)).map(Map.Entry::getKey)
				.collect(Collectors.toSet());

		return clients.size() == clientPreferences.size();
	}

}