package org.dreamsoft.yamplayer.client;

import java.util.ArrayList;
import java.util.Iterator;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;
import org.dreamsoft.yamplayer.client.combinaison.FullHouseCombinaison;
import org.dreamsoft.yamplayer.client.combinaison.UpperCombinaison;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class YamCombinaisons extends Composite {
	private Grid combinaisonGrid = new Grid(17, 1);
	private ArrayList<Combinaison> upperTotal = new ArrayList<Combinaison>();
	private ArrayList<Combinaison> lowerTotal = new ArrayList<Combinaison>();

	final Combinaison bonusUpper = new Combinaison("BONUS", null, false);
	final Combinaison sumUpperTotal = new Combinaison("UPPER TOTAL", null,
			false);
	final Combinaison sumLowerTotal = new Combinaison("LOWER TOTAL", null,
			false);
	final Combinaison sumTotal = new Combinaison("BONUS", null, false);
	
	public YamCombinaisons(final Command afterScoreCommand) {

		Command cmd = new Command() {
			public void execute() {
				refreshSubTotals();
				afterScoreCommand.execute();
			}
		};

		upperTotal.add(new UpperCombinaison(1,"ACES", cmd));
		upperTotal.add(new UpperCombinaison(2,"TWOS", cmd));
		upperTotal.add(new UpperCombinaison(3,"THREES", cmd));
		upperTotal.add(new UpperCombinaison(4,"FOURS", cmd));
		upperTotal.add(new UpperCombinaison(5,"FIVES", cmd));
		upperTotal.add(new UpperCombinaison(6,"SIXES", cmd));

		int i = 0;
		for (Iterator<Combinaison> iterator = upperTotal.iterator(); iterator
				.hasNext();) {
			Combinaison c1 = iterator.next();
			combinaisonGrid.setWidget(i, 0, c1);
			i++;
		}

		combinaisonGrid.setWidget(i, 0, bonusUpper);
		i++;
		combinaisonGrid.setWidget(i, 0, sumUpperTotal);
		i++;

		lowerTotal.add(new Combinaison("3 OF A KIND", cmd));
		lowerTotal.add(new Combinaison("4 OF A KIND", cmd));
		lowerTotal.add(new FullHouseCombinaison("FULL HOUSE", cmd));
		lowerTotal.add(new Combinaison("SMALL STRAIGHT", cmd));
		lowerTotal.add(new Combinaison("LARGE STRAIGHT", cmd));
		lowerTotal.add(new Combinaison("CHANCE", cmd));
		lowerTotal.add(new Combinaison("YAHTZEE", cmd));

		for (Iterator<Combinaison> iterator = lowerTotal.iterator(); iterator
				.hasNext();) {
			Combinaison c2 = iterator.next();
			combinaisonGrid.setWidget(i, 0, c2);
			i++;
		}

		combinaisonGrid.setWidget(i, 0, sumLowerTotal);
		i++;
		combinaisonGrid.setWidget(i, 0, sumTotal);
		i++;
		
		initWidget(combinaisonGrid);
	}

	public void refreshSubTotals() {
		int sumUpperVal = 0;
		int bonusUpperVal = 0;
		for (Iterator<Combinaison> iterator = upperTotal.iterator(); iterator
				.hasNext();) {
			Combinaison c = iterator.next();
			sumUpperVal += c.getScore();
		}
		if (sumUpperVal > 63)
			bonusUpperVal = 35;
		sumUpperVal += bonusUpperVal;

		int sumLowerVal = 0;
		for (Iterator<Combinaison> iterator = lowerTotal.iterator(); iterator
				.hasNext();) {
			Combinaison c = iterator.next();
			sumLowerVal += c.getScore();
		}

		bonusUpper.setScore(bonusUpperVal);
		sumUpperTotal.setScore(sumUpperVal);
		sumLowerTotal.setScore(sumLowerVal);
		sumTotal.setScore(sumLowerVal + sumUpperVal);

	}

	public void showEstimatedScore(ArrayList<Dice> dices) {		
		for (Iterator<Combinaison> iterator = upperTotal.iterator(); iterator
				.hasNext();) {
			Combinaison c1 = iterator.next();
			c1.showEstimatedScore(dices);
		}
		for (Iterator<Combinaison> iterator = lowerTotal.iterator(); iterator
				.hasNext();) {
			Combinaison c2 = iterator.next();
			c2.showEstimatedScore(dices);
		}
	}

}
