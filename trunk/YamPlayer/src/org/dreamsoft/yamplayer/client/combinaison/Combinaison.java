package org.dreamsoft.yamplayer.client.combinaison;

import java.util.ArrayList;

import org.dreamsoft.yamplayer.client.Dice;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class Combinaison extends Grid {
	protected boolean selected = false;
	protected int score = 0;
	protected int estimatedScore = 0;
	protected Command command = null;
	protected Label scoreLabel = new Label("");

	public Combinaison() {
		this("", null, true);
	}

	public Combinaison(String name) {
		this(name, null, true);
	}

	public Combinaison(String name, Command command) {
		this(name, command, true);
	}

	public Combinaison(String name, Command command, boolean selectable) {
		super(1, 2);
		setWidget(0, 0, new Label(name));
		setWidget(0, 1, scoreLabel);
		if (selectable) {
			addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					store();
				}
			});
		}
		this.command = command;
	}

	public void store() {
		selected = true;
		setScore(estimatedScore);
		if (command != null) {
			command.execute();
		}
	}

	public void showEstimatedScore(ArrayList<Dice> dices) {
		if (!selected) {
			scoreLabel.setText("[" + estimateScore(dices) + "]");
		}
	}
	
	public int estimateScore(ArrayList<Dice> dices) {
		estimatedScore = 0;
		return estimatedScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		scoreLabel.setText("" + score);
	}
}
