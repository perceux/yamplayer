package org.dreamsoft.yamplayer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;

public class Dice extends Image implements Comparable<Dice> {

	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 6;

	private int value = 0;
	private boolean selected = false;
	private Command afterToggleSelectCommand;

	public Dice(int initialValue) {
		this(initialValue, null);
	}

	public Dice(int initialValue, Command afterToggleSelectCommand) {
		this.afterToggleSelectCommand = afterToggleSelectCommand;
		setValue(initialValue);
		setSelected(false);
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toggleSelect();
			}
		});
	}

	public void toggleSelect() {
		setSelected(!selected);
	}

	public void roll() {
		setSelected(false);
		setValue(MIN_VALUE + Random.nextInt(MAX_VALUE));
	}

	public void setValue(int value) {
		this.value = value;
		setUrl("images/dice" + value + ".gif");
	}

	public int getValue() {
		return value;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			setStyleName("diceselected");
		} else {
			setStyleName("diceunselected");
		}
		if (afterToggleSelectCommand != null) {
			afterToggleSelectCommand.execute();
		}
	}

	public int compareTo(Dice o) {
		return this.getValue() - o.getValue();
	}

	@Override
	public String toString() {
		return "Dice" + getValue();
	}

}
