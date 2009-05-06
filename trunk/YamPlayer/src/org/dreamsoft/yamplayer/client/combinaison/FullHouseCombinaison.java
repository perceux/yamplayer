package org.dreamsoft.yamplayer.client.combinaison;

import java.util.ArrayList;
import java.util.Iterator;

import org.dreamsoft.yamplayer.client.Dice;

import com.google.gwt.user.client.Command;

public class FullHouseCombinaison extends Combinaison {
	public FullHouseCombinaison(String name, Command cmd) {
		super(name, cmd);
	}

	public int estimateScore(ArrayList<Dice> dices) {
		estimatedScore = 0;
		int v1 = -1;
		int v2 = -1;
		int n1 = 0;
		int n2 = 0;
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice dice = iterator.next();
			if (v1 == -1)
				v1 = dice.getValue();
			if (v1 == dice.getValue())
				n1++;
			else {
				if (v2 == -1)
					v2 = dice.getValue();
				if (v2 == dice.getValue())
					n2++;
			}
		}
		if ((n1 == 3 && n2 == 2) || (n1 == 2 && n2 == 3) || (n1 == 5))
			estimatedScore = 25;
		return estimatedScore;
	}
}
