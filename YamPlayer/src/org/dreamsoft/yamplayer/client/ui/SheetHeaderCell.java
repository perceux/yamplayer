package org.dreamsoft.yamplayer.client.ui;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;

import com.google.gwt.user.client.ui.HTML;

public class SheetHeaderCell extends HTML {

	public static final int TYPE_COMBINAISON = 1;
	public static final int TYPE_SUBTOTAL = 2;
	public static final int TYPE_BONUS = 3;

	private Combinaison combinaison;
	private int type;
	private int[] lines;
	private int bonus;
	private int threshold;

	public Combinaison getCombinaison() {
		return combinaison;
	}

	public SheetHeaderCell(String html, Combinaison combinaison) {
		super(html);
		this.setType(TYPE_COMBINAISON);
		this.combinaison = combinaison;
	}

	public SheetHeaderCell(String html, int[] lines) {
		super(html);
		this.setType(TYPE_SUBTOTAL);
		this.setLines(lines);
	}

	public SheetHeaderCell(String html, int[] lines, int threshold, int bonus) {
		super(html);
		this.setType(TYPE_BONUS);
		this.setLines(lines);
		this.setThreshold(threshold);
		this.setBonus(bonus);
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setLines(int[] lines) {
		this.lines = lines;
	}

	public int[] getLines() {
		return lines;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getBonus() {
		return bonus;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getThreshold() {
		return threshold;
	}

}
