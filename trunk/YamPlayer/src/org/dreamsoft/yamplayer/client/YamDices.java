package org.dreamsoft.yamplayer.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class YamDices extends Composite {
	private Grid diceGrid = new Grid(6, 1);
	private Button rollButton = new Button("Roll");
	private ArrayList<Dice> dices = new ArrayList<Dice>();
	private int rollNumber = 3;
	private Command afterRollCommand = null;

	public YamDices(Command afterRollCommand, Command afterToggleSelectCommand) {
		this.afterRollCommand = afterRollCommand;
		rollButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				roll();
			}
		});

		for (int i = 0; i < 5; i++) {
			Dice d = new Dice(i + 1, afterToggleSelectCommand);
			dices.add(d);
			diceGrid.setWidget(i, 0, d);
		}
		diceGrid.setWidget(5, 0, rollButton);
		initWidget(diceGrid);
	}

	public void roll() {
		if (rollNumber > 0) {
			rollNumber--;
			for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
				Dice d = (Dice) iterator.next();
				if (!d.isSelected()) {
					d.roll();
					if (rollNumber == 0)
						d.setSelected(true);
				}
			}
		}
		if (afterRollCommand != null) {
			afterRollCommand.execute();
		}
	}

	public void reset() {
		setRollNumber(3);
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice d = (Dice) iterator.next();
			d.setSelected(false);
		}
		roll();
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public ArrayList<Dice> getDices() {
		return dices;
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

	public String getStringDices() {
		String result = "";
		for (Iterator<Dice> iterator = dices.iterator(); iterator.hasNext();) {
			Dice d = iterator.next();
			result += d.getValue();
		}
		return result;
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
}
