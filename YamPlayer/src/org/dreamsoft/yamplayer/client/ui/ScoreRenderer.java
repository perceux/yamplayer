package org.dreamsoft.yamplayer.client.ui;

import org.dreamsoft.yamplayer.client.YamDices;
import org.dreamsoft.yamplayer.client.combinaison.stats.CombinaisonStats;
import org.dreamsoft.yamplayer.client.combinaison.stats.CombinaisonStatsCalculator;

public class ScoreRenderer {

	private YamDices yamDices = null;
	private boolean enableEvalutedScore = false;
	private boolean enableCompareScore = false;
	private boolean enableMatchScore = true;

	public String renderScore(int i, PlayerSheet activePlayerSheet, SheetHeaderCell sheetHeaderCell) {
		String result = "";
		switch (sheetHeaderCell.getType()) {
		case SheetHeaderCell.TYPE_BONUS:
			if (activePlayerSheet.getSubTotal(sheetHeaderCell.getLines()) >= sheetHeaderCell.getThreshold()) {
				activePlayerSheet.setScore(i, sheetHeaderCell.getBonus());
			}
			break;
		case SheetHeaderCell.TYPE_SUBTOTAL:
			activePlayerSheet.setSubTotal(i, sheetHeaderCell.getLines());
			break;
		}
		Integer s = activePlayerSheet.getScore(i);
		if (s == null) {
			if (sheetHeaderCell.getCombinaison() != null && yamDices != null) {
				if (enableEvalutedScore) {
					int evaluatedScore = sheetHeaderCell.getCombinaison().getMatchingScore(yamDices.getIntDices());
					result += "<table class=stats><tr><td class=evaluated>" + evaluatedScore + "</td>";
					if (enableCompareScore || enableMatchScore) {
						CombinaisonStats cs = CombinaisonStatsCalculator.computeStatistics(sheetHeaderCell.getCombinaison(), yamDices.getIntSeletedDices(), evaluatedScore);
						if (enableCompareScore) {
							result += "<td class=cmp><sup>&gt;</sup>" + cs.format(cs.getMatchesScoreBetter()) + "</td><td class=cmp><sup>&lt;</sup>" + cs.format(cs.getMatchesScoreLesser()) + "</td><td class=cmp><sup>=</sup>" + cs.format(cs.getMatchesScoreEquals()) + "</td>";
						}
						if (enableMatchScore) {
							result += "<td class=cmp><sup>?</sup>" + cs.format(cs.getMatches()) + "</td><td class=cmp><sup>m</sup>" + cs.format(cs.getMatchesScoreMedian()) + "</td>";
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

	public boolean isEnableCompareScore() {
		return enableCompareScore;
	}

	public void setEnableCompareScore(boolean enableCompareScore) {
		this.enableCompareScore = enableCompareScore;
	}

	public boolean isEnableMatchScore() {
		return enableMatchScore;
	}

	public void setEnableMatchScore(boolean enableMatchScore) {
		this.enableMatchScore = enableMatchScore;
	}

}
