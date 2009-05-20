package org.dreamsoft.yamplayer.client.combinaison.stats;

import java.util.ArrayList;
import java.util.Iterator;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;

public class CombinaisonStatsCalculator {

	public static int nextTestVal(int testVal) {
		int nextTestVal = testVal; // 426
		int coeff = 1;
		int c = 1;
		while (c > 0) {
			c = Math.round(testVal / coeff) % 10; // 6,2,4
			coeff *= 10;
			nextTestVal = Math.round(testVal / coeff) * coeff; // 42
			if (c > 0 && c < 6) {
				c++; // 2 => 3
				while (coeff >= 10) {
					coeff /= 10;
					nextTestVal += c * coeff; // 431, 432, 433, ... 436, 441..446, 451 ... 666
					c=1;
				}
				return nextTestVal;
			}
		}
		return -1;
	}

	public static ArrayList<int[]> getPotentialListDices(int[] selectedDices) {
		int nbPotentialDices = 5 - selectedDices.length;
		int selectedDicesVal = 0;
		for (int i = 0; i < selectedDices.length; i++) {
			int d = selectedDices[i];
			selectedDicesVal *= 10;
			selectedDicesVal += d;
		}
		int firtTestVal = 0;
		for (int i = 0; i < nbPotentialDices; i++) {
			selectedDicesVal *= 10;
			firtTestVal *= 10;
			firtTestVal++;
		}
		ArrayList<int[]> result = new ArrayList<int[]>();
		int testVal = firtTestVal;
		while (testVal != -1) {
			result.add(convertIntToIntArray(selectedDicesVal + testVal));
			testVal = nextTestVal(testVal);
		}

		return result;
	}

	private static int[] convertIntToIntArray(int intVal) {
		int[] result = new int[5];
		for (int i = 0; i < result.length; i++) {
			result[i] = intVal % 10;
			intVal /= 10;
		}
		return result;
	}

	public static CombinaisonStats computeStatistics(Combinaison combinaison, int[] selectedDices, int scoreCompare) {
		ArrayList<int[]> dices = getPotentialListDices(selectedDices);
		int matches = 0;
		int matchesScoreMedian = 0;
		int matchesScoreBetter = 0;
		int matchesScoreLesser = 0;
		int matchesScoreEquals = 0;

		for (Iterator<int[]> iterator = dices.iterator(); iterator.hasNext();) {
			int[] testDices = iterator.next();
			int matchingScore = combinaison.getMatchingScore(testDices);
			if (matchingScore > 0) {
				matches++;
				if (matchingScore >= combinaison.getMedianScore())
					matchesScoreMedian++;
			}
			if (matchingScore > scoreCompare)
				matchesScoreBetter++;
			else {
				if (matchingScore < scoreCompare)
					matchesScoreLesser++;
				else {
					matchesScoreEquals++;
				}
			}
		}
		return new CombinaisonStats(dices, matches, matchesScoreMedian, matchesScoreBetter, matchesScoreLesser, matchesScoreEquals);
	}

}
