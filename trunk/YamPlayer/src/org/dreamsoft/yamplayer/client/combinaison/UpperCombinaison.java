package org.dreamsoft.yamplayer.client.combinaison;

import com.google.gwt.user.client.Command;

public class UpperCombinaison extends Combinaison {

	private int targetValue = 0;

	public UpperCombinaison(int targetValue, String name, Command command) {
		super(name, command, true);
		this.targetValue = targetValue;
	}

	public int getScoreDices(int[] dices) {
		int result = 0;
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == targetValue) {
				result += targetValue;
			}
		}
		return result;
	}

	public double getMedianScoreDices() {
		return targetValue * 5.0 / 2.0;
	}

}
