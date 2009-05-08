package org.dreamsoft.yamplayer.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;

public class PlayerSheet extends HTML {
	HashMap<Integer, Integer> scores = new HashMap<Integer, Integer>();
	private int number;

	public PlayerSheet(String playerName, int number) {
		super(playerName);
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public Integer getScore(Integer line) {
		return scores.get(line);
	}

	public void setScore(Integer line, Integer value) {
		scores.put(line, value);
	}

	public Integer getSubTotal(List<Integer> lines) {
		int subTotal = 0;
		for (Iterator<Integer> iterator = lines.iterator(); iterator.hasNext();) {
			Integer line = iterator.next();
			if (getScore(line) != null) {
				subTotal += getScore(line);
			}
		}
		return new Integer(subTotal);
	}

	public void setSubTotal(int i, int[] lines) {
		setScore(i, getSubTotal(lines));
	}

	public int getSubTotal(int[] lines) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int j = 0; j < lines.length; j++) {
			l.add(new Integer(lines[j]));
		}
		return getSubTotal(l);
	}
}
