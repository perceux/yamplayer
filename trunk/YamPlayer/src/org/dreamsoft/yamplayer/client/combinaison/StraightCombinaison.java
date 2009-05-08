package org.dreamsoft.yamplayer.client.combinaison;

import java.util.Arrays;

public class StraightCombinaison extends Combinaison {

	public static final int SMALL = 1;
	public static final int LARGE = 2;

	private int maxScore;
	private int minLength;

	public double getMedianScore() {
		return maxScore / 2.0;
	}

	public StraightCombinaison(int typeStraight) {
		this.minLength = (typeStraight == 1) ? 4 : 5;
		this.maxScore = (typeStraight == SMALL) ? 30 : 40;
	}

	@Override
	public int getScore(int[] dices) {
		return maxScore;
	}

	@Override
	public boolean match(int[] dices) {
		Arrays.sort(dices);
		int l = 0;
		int lastDiceValue = -1;
		for (int i = 0; i < dices.length; i++) {
			int d = dices[i];
			// interval = 0
			if (d == lastDiceValue) {
				continue;
			}
			// interval = 1
			if (d - lastDiceValue == 1) {
				l++;
				if (l >= minLength)
					return true;
			} else {
				l = 1;
			}
			lastDiceValue = d;
		}
		return false;
	}

}
