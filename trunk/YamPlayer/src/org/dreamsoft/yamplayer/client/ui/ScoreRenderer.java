package org.dreamsoft.yamplayer.client.ui;

import org.dreamsoft.yamplayer.client.YamDices;
import org.dreamsoft.yamplayer.client.combinaison.Combinaison;
import org.dreamsoft.yamplayer.client.combinaison.stats.CombinaisonStats;
import org.dreamsoft.yamplayer.client.combinaison.stats.CombinaisonStatsCalculator;

public class ScoreRenderer {

	private YamDices yamDices = null;
	private boolean enableEvalutedScore = false;
	private boolean enableCompareScore = false;
	
	public String renderScore(int i, PlayerSheet activePlayerSheet, Combinaison combinaison) {
		String result = "";
		Integer s = activePlayerSheet.getScore(i);
		if (s == null) {
			if (combinaison != null && yamDices != null) {
				if (enableEvalutedScore) {
					int evaluatedScore = combinaison.getMatchingScore(yamDices.getIntDices());
					result += "<table class=stats><tr><td class=evaluated>" + evaluatedScore + "</td>";
					if (enableCompareScore) {
						CombinaisonStats cs = CombinaisonStatsCalculator.computeStatistics(combinaison, yamDices.getIntSeletedDices(), evaluatedScore);
						if (enableCompareScore) {
							result += "<td class=cmp><sup>&gt;</sup>"+cs.format(cs.getMatchesScoreBetter())+"</td><td class=cmp><sup>&lt;</sup>"+cs.format(cs.getMatchesScoreLesser())+"</td><td class=cmp><sup>=</sup>"+cs.format(cs.getMatchesScoreEquals())+"</td>";
						}
					}
					result += "</tr></table>";
				}
			}
		} else {
			result += "<b>" + s + "</b>";
		}
		return result;
	}

	public void setYamDices(YamDices yamDices) {
		this.yamDices = yamDices;
	}

	public boolean isEnableEvalutedScore() {
		return enableEvalutedScore;
	}

	public void setEnableEvalutedScore(boolean enableEvalutedScore) {
		this.enableEvalutedScore = enableEvalutedScore;
	}

	public boolean isEnableBetterScore() {
		return enableCompareScore;
	}

	public void setEnableBetterScore(boolean enableBetterScore) {
		this.enableCompareScore = enableBetterScore;
	}

	public void setEnableCompareScore(boolean enableCompareScore) {
		this.enableCompareScore = enableCompareScore;
	}

	public boolean isEnableCompareScore() {
		return enableCompareScore;
	}

}
