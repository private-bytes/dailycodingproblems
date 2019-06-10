package org.privatebytes.problem329;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

/**
 *  This problem was asked by Amazon.
 *  The stable marriage problem is defined as follows:
 *  Suppose you have N men and N women, and each person has ranked their prospective opposite-sex partners in order of preference.
 *  For example, if N = 3, the input could be something like this:
 *  guy_preferences = {
 *  	'andrew': ['caroline', 'abigail', 'betty'],
 *  	'bill': ['caroline', 'betty', 'abigail'],
 *  	'chester': ['betty', 'caroline', 'abigail'],
 *  }
 *  
 *  gal_preferences = {
 *  	'abigail': ['andrew', 'bill', 'chester'],
 *  	'betty': ['bill', 'andrew', 'chester'],
 *  	'caroline': ['bill', 'chester', 'andrew']
 *  }
 *  Write an algorithm that pairs the men and women together in such a way that no two people of opposite sex would both rather be with each other than with their current partners. 
 */
public class Problem329 {

	public static void main(String[] args) {

		Map<String, List<String>> guy_preferences = new HashMap<>();
		guy_preferences.put("andrew", new ArrayList<>(List.of("caroline", "abigail", "betty")));
		guy_preferences.put("bill", new ArrayList<>(List.of("caroline", "betty", "abigail")));
		guy_preferences.put("chester", new ArrayList<>(List.of("betty", "caroline", "abigail")));

		Map<String, List<String>> gal_preferences = new HashMap<>();
		gal_preferences.put("abigail", new ArrayList<>(List.of("andrew", "bill", "chester")));
		gal_preferences.put("betty", new ArrayList<>(List.of("bill", "andrew", "chester")));
		gal_preferences.put("caroline", new ArrayList<>(List.of("bill", "chester", "andrew")));
		
		
		new Problem329().solve(guy_preferences, gal_preferences);
		
	}
	
	public List<Pair<String, String>> solve(Map<String, List<String>> guy_preferences,
			Map<String, List<String>> gal_preferences) {

		Map<Integer, Set<Triple<Integer, String, String>>> possibleMatches = computePossibleMatches(guy_preferences,
				gal_preferences);
		
		Set<String> alreadyMatchedPeople = new HashSet<>();
		List<Pair<String, String>> matches = new ArrayList<>();
		
		for(Map.Entry<Integer, Set<Triple<Integer, String, String>>> entry : possibleMatches.entrySet()) {
			for(Triple<Integer, String, String> tripleEntry : entry.getValue()) {
				if(!alreadyMatchedPeople.contains(tripleEntry.getMiddle()) && !alreadyMatchedPeople.contains(tripleEntry.getRight())) {
					matches.add(Pair.of(tripleEntry.getMiddle(), tripleEntry.getRight()));
					alreadyMatchedPeople.add(tripleEntry.getMiddle());
					alreadyMatchedPeople.add(tripleEntry.getRight());
					
				}
			}
		}
		
		System.out.println(matches);
		
		return matches;
	}
	
	private static Map<Integer, Set<Triple<Integer, String, String>>> computePossibleMatches(
			Map<String, List<String>> guy_preferences, Map<String, List<String>> gal_preferences) {
		Map<Integer, Set<Triple<Integer, String, String>>> possibleMatches = new TreeMap<>();
		
		int countAdded = 0;
		int countSkipped = 0;
		for (Map.Entry<String, List<String>> entry : guy_preferences.entrySet()) {

			String guy = entry.getKey();

			for (int i = 0; i < entry.getValue().size(); i++) {
				String girl = entry.getValue().get(i);
				
				
				int j = gal_preferences.get(girl).indexOf(guy);
				
				int dist = Math.abs(i-j);
				int worstIndex = Math.max(i, j);
				
				Triple<Integer, String, String> newMatch  = Triple.of(dist , guy, girl);
				Set<Triple<Integer, String, String>> pairs = possibleMatches.get(worstIndex);
				
				if(pairs==null) {
					pairs = new TreeSet<>();
					possibleMatches.put(worstIndex, pairs);
				}
				
				if(pairs.add(newMatch)) {
					countAdded++;
				}else {
					countSkipped++;
				}
			}
		}
		
		System.out.println(countAdded + " (" + countSkipped + " skipped) possible matches: " + possibleMatches);
		
		return possibleMatches;
	}
}
