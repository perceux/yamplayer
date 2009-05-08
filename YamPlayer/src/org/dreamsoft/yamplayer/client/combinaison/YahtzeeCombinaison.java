package org.dreamsoft.yamplayer.client.combinaison;

import com.google.gwt.user.client.Command;

public class YahtzeeCombinaison extends IdenticalCombinaison {
	public YahtzeeCombinaison(String name, Command command) {
		super(5, name, command);
	}

	public int getScoreDices(int[] dices) {
		return 50;
	}

	public double getMedianScoreDices() {
		return 50 / 2.0;
	}
}
