package org.dreamsoft.yamplayer.client.proba;

import java.util.ArrayList;

public class ProbaCalculator {
	// TODO combinaison plutot que produit cartésien

	public static int nextTestVal(int testVal) {
		int nextTestVal = testVal;
		int coeff = 1;
		int c = 1;
		while (c > 0) {
			c = Math.round(testVal / coeff) % 10;
			coeff *= 10;
			nextTestVal = Math.round(testVal / coeff) * coeff;
			if (c > 0 && c < 6) {
				c++;
				while (coeff >= 10) {
					coeff /= 10;
					nextTestVal += c * coeff;
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

}
