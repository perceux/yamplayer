package org.dreamsoft.yamplayer.client.ui;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;

import com.google.gwt.user.client.ui.HTML;

public class SheetHeaderCell extends HTML {

	private Combinaison combinaison;

	public Combinaison getCombinaison() {
		return combinaison;
	}

	public SheetHeaderCell(String html, Combinaison combinaison) {
		super(html);
		this.combinaison = combinaison;
	}
}
