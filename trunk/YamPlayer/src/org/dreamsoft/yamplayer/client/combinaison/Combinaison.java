package org.dreamsoft.yamplayer.client.combinaison;

import org.dreamsoft.yamplayer.client.Dice;

public class Combinaison {
	protected int score = 0;

	protected boolean match(int[] dices) {
		return true;
	}

	protected int getScore(int[] dices) {
		int sumDices = 0;
		for (int i = 0; i < dices.length; i++) {
			sumDices += dices[i];
		}
		return sumDices;
	}

	public double getMedianScore() {
		return 5.0 * (Dice.MAX_VALUE + Dice.MIN_VALUE) / 2.0;
	}

	public int getMatchingScore(int[] dices) {
		return (match(dices)) ? getScore(dices) : 0;
	}

}
