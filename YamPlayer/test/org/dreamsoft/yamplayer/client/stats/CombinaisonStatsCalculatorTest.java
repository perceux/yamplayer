package org.dreamsoft.yamplayer.client.stats;

import org.dreamsoft.yamplayer.client.combinaison.YahtzeeCombinaison;
import org.dreamsoft.yamplayer.client.combinaison.stats.CombinaisonStats;
import org.dreamsoft.yamplayer.client.combinaison.stats.CombinaisonStatsCalculator;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class CombinaisonStatsCalculatorTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.dreamsoft.yamplayer.YamPlayer";
	}

	@Test
	public void testYam() throws Exception {
		YahtzeeCombinaison combinaison = new YahtzeeCombinaison();
		int[] selectedDices = new int[]{};
		CombinaisonStats result = CombinaisonStatsCalculator.computeStatistics(combinaison, selectedDices, 0);
		System.out.println(result.getMatchesScoreBetter()/(1.0*result.getSize())+"="+((1.0/(6*6*6*6))));
		assertTrue(result.getMatchesScoreBetter()/(1.0*result.getSize())==((1.0/(6*6*6*6))));
	}
}
