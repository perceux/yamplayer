package org.dreamsoft.yamplayer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;

public class Dice extends Image {

	private int value = 0;
	private boolean selected = false;

	public Dice(int initialValue) {
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
		setValue(1 + Random.nextInt(6));
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

}
