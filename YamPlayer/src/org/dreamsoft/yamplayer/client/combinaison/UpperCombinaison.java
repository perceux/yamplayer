package org.dreamsoft.yamplayer.client.combinaison;

import java.util.ArrayList;
import java.util.Iterator;

import org.dreamsoft.yamplayer.client.Dice;

import com.google.gwt.user.client.Command;

public class UpperCombinaison extends Combinaison {

	private int targetValue = 0;

	public UpperCombinaison(int targetValue, String name, Command command) {
		super(name, command, true);
		this.targetValue = targetValue;
	}

	public int estimateScore(ArrayList<Dice> dices) {
		estimatedScore = 0;
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice dice = iterator.next();
			if (dice.getValue() == targetValue) {
				estimatedScore += targetValue;
			}
		}
		return estimatedScore;
	}
}
