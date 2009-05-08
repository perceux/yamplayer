package org.dreamsoft.yamplayer.client.combinaison;


public class UpperCombinaison extends Combinaison {

	private int targetValue = 0;

	public UpperCombinaison(int targetValue) {
		this.targetValue = targetValue;
	}

	public int getScore(int[] dices) {
		int result = 0;
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == targetValue) {
				result += targetValue;
			}
		}
		return result;
	}

	public double getMedianScore() {
		return targetValue * 5.0 / 2.0;
	}

}
