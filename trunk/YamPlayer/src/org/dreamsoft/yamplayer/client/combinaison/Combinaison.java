package org.dreamsoft.yamplayer.client.combinaison;

import java.util.ArrayList;
import java.util.Iterator;

import org.dreamsoft.yamplayer.client.Dice;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

public class Combinaison extends Grid {
	protected boolean selected = false;
	protected int score = 0;
	protected int estimatedScore = 0;
	protected Command command = null;
	protected Label scoreLabel = new Label("");
	protected Label nameLabel = new Label("");
	protected Label annotationLabel = new Label("");

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
		super(1, 3);
		setWidth("100%");
		nameLabel.setText(name);
		nameLabel.setStyleName("small");
		scoreLabel.setStyleName("small");
		scoreLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		annotationLabel.setStyleName("small");
		annotationLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		setWidget(0, 0, nameLabel);
		setWidget(0, 2, scoreLabel);
		setWidget(0, 1, annotationLabel);
		getCellFormatter().setWidth(0, 0, "100%");
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
		if (selected == false) {
			selected = true;
			setScore(estimatedScore);
			if (command != null) {
				command.execute();
			}
		}
	}

	public void showEstimated(int[] dices) {
		if (!selected) {
			scoreLabel.setText("" + estimateScore(dices));
			scoreLabel.addStyleName("matchingScore");
		}
	}

	public boolean match(int[] dices) {
		return true;
	}

	public boolean match(int[] dices, int minScore) {
		return (match(dices) && getScoreDices(dices) >= minScore);
	}

	public double calculateChances(ArrayList<int[]> dices) {
		double result = 0;
		if (!selected) {
			int matches = 0;
			int matchesScoreMedian = 0;
			for (Iterator<int[]> iterator = dices.iterator(); iterator.hasNext();) {
				int[] testDices = iterator.next();
				if (match(testDices)) {
					matches++;
					if (getScoreDices(testDices) >= getMedianScoreDices())
						matchesScoreMedian++;
				}
			}
			result = matches / dices.size();
			setAnnotation("(" + Math.round(matchesScoreMedian * 10000.0 / dices.size()) / 100.0 + "%" + ")" + Math.round(matches * 10000.0 / dices.size()) / 100.0 + "%");
		} else {
			setAnnotation("");
		}
		return result;
	}

	public int getScoreDices(int[] dices) {
		int sumDices = 0;
		for (int i = 0; i < dices.length; i++) {
			sumDices += dices[i];
		}
		return sumDices;
	}

	public double getMedianScoreDices() {
		return 5.0 * (Dice.MAX_VALUE + Dice.MIN_VALUE) / 2.0;
	}

	public int estimateScore(int[] dices) {
		if (match(dices)) {
			estimatedScore = getScoreDices(dices);
		} else {
			estimatedScore = 0;
		}
		return estimatedScore;
	}

	public void setAnnotation(String text) {
		annotationLabel.setText("" + text);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		scoreLabel.setText("" + score);
		scoreLabel.removeStyleName("matchingScore");
		scoreLabel.addStyleName("fixedScore");
	}
}
