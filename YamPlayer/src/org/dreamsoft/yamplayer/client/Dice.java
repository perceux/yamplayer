package org.dreamsoft.yamplayer.client;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;

public class Dice extends Image implements Comparable<Dice> {

	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 6;

	private int value = 0;
	private boolean selected = false;

	public Dice(int initialValue) {
		setValue(initialValue);
		setSelected(false);
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
	}

	public int compareTo(Dice o) {
		return this.getValue() - o.getValue();
	}

	@Override
	public String toString() {
		return "Dice" + getValue();
	}

}
