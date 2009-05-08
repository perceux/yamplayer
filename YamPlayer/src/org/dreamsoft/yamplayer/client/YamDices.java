package org.dreamsoft.yamplayer.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class YamDices extends Composite {
	private Grid diceGrid = new Grid(5, 1);
	private ArrayList<Dice> dices = new ArrayList<Dice>();

	public YamDices() {
		for (int i = 0; i < 5; i++) {
			final Dice d = new Dice(i + 1);
			d.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					d.toggleSelect();
					fireDiceSelectionChangeEvent();
				}
			});
			dices.add(d);
			diceGrid.setWidget(i, 0, d);
		}
		initWidget(diceGrid);
	}

	public void roll() {
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice d = (Dice) iterator.next();
			if (!d.isSelected()) {
				d.roll();
			}
		}
		fireDiceSelectionChangeEvent();
	}

	public void selectAll(boolean selected) {
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice d = (Dice) iterator.next();
			d.setSelected(selected);
		}
		fireDiceSelectionChangeEvent();
	}

	public ArrayList<Dice> getSelectedDices() {
		ArrayList<Dice> selectedDice = new ArrayList<Dice>();
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice d = iterator.next();
			if (d.isSelected()) {
				selectedDice.add(d);
			}
		}
		return selectedDice;
	}

	public int[] getIntDices() {
		int[] result = new int[dices.size()];
		int i = 0;
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			result[i] = (iterator.next().getValue());
			i++;
		}
		return result;
	}

	public int[] getIntSeletedDices() {
		ArrayList<Dice> selectedDices = getSelectedDices();
		int[] result = new int[selectedDices.size()];
		int i = 0;
		for (Iterator<Dice> iterator = selectedDices.iterator(); iterator.hasNext();) {
			result[i] = (iterator.next().getValue());
			i++;
		}
		return result;
	}

	protected void fireDiceSelectionChangeEvent() {

	}
}
