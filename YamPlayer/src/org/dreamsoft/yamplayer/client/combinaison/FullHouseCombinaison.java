package org.dreamsoft.yamplayer.client.combinaison;

import com.google.gwt.user.client.Command;

public class FullHouseCombinaison extends Combinaison {
	public FullHouseCombinaison(String name, Command cmd) {
		super(name, cmd);
	}

	public int getScoreDices(int[] dices) {
		return 25;
	}

	public double getMedianScoreDices() {
		return 12.5;
	}
	
	public boolean match(int[] dices) {
		int v1 = -1;
		int v2 = -1;
		int n1 = 0;
		int n2 = 0;
		for (int i = 0; i < dices.length; i++) {
			int d = dices[i];
			if (v1 == -1)
				v1 = d;
			if (v1 == d)
				n1++;
			else {
				if (v2 == -1)
					v2 = d;
				if (v2 == d)
					n2++;
			}
		}
		return ((n1 == 3 && n2 == 2) || (n1 == 2 && n2 == 3) || (n1 == 5));
	}
}
