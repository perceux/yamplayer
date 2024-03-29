package org.dreamsoft.yamplayer.client.combinaison;


public class IdenticalCombinaison extends Combinaison {
	protected int targetMinNumber = 3;

	public IdenticalCombinaison(int targetMinNumber) {
		this.targetMinNumber = targetMinNumber;
	}

	public boolean match(int[] dices) {
		boolean result = false;
		int n[] = { 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < dices.length; i++) {
			int d = dices[i];
			n[d]++;
		}
		for (int i = 0; i < n.length; i++) {
			if (n[i] >= targetMinNumber)
				result = true;
		}
		return result;
	}
	
}
