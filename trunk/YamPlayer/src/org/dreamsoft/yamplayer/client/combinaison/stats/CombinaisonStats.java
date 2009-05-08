package org.dreamsoft.yamplayer.client.combinaison.stats;

import java.util.ArrayList;

public class CombinaisonStats {
	private int size;
	private int matches;
	private int matchesScoreMedian;
	private int matchesScoreBetter;
	private int matchesScoreLesser;
	private int matchesScoreEquals;

	public CombinaisonStats(ArrayList<int[]> dices, int matches, int matchesScoreMedian, int matchesScoreBetter, int matchesScoreLesser, int matchesScoreEquals) {
		this.size = dices.size();
		this.matches = matches;
		this.matchesScoreMedian = matchesScoreMedian;
		this.matchesScoreBetter = matchesScoreBetter;
		this.matchesScoreLesser = matchesScoreLesser;
		this.matchesScoreEquals = matchesScoreEquals;
	}

	public String format(int a) {
		return (Math.round((a * 100.0) / getSize())) + "%";
	}

	public int getSize() {
		return size;
	}

	public int getMatches() {
		return matches;
	}

	public int getMatchesScoreMedian() {
		return matchesScoreMedian;
	}

	public void setMatchesScoreLesser(int matchesScoreLesser) {
		this.matchesScoreLesser = matchesScoreLesser;
	}

	public int getMatchesScoreLesser() {
		return matchesScoreLesser;
	}

	public void setMatchesScoreBetter(int matchesScoreBetter) {
		this.matchesScoreBetter = matchesScoreBetter;
	}

	public int getMatchesScoreBetter() {
		return matchesScoreBetter;
	}

	public void setMatchesScoreEquals(int matchesScoreEquals) {
		this.matchesScoreEquals = matchesScoreEquals;
	}

	public int getMatchesScoreEquals() {
		return matchesScoreEquals;
	}

}