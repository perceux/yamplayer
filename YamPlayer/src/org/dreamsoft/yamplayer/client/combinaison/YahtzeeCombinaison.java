package org.dreamsoft.yamplayer.client.combinaison;


public class YahtzeeCombinaison extends IdenticalCombinaison {
	public YahtzeeCombinaison() {
		super(5);
	}

	public int getScore(int[] dices) {
		return 50;
	}

	public double getMedianScore() {
		return 50 / 2.0;
	}
}
