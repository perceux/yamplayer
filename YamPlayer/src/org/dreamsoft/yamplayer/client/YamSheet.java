package org.dreamsoft.yamplayer.client;

import java.util.ArrayList;
import java.util.Iterator;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;
import org.dreamsoft.yamplayer.client.combinaison.FullHouseCombinaison;
import org.dreamsoft.yamplayer.client.combinaison.IdenticalCombinaison;
import org.dreamsoft.yamplayer.client.combinaison.StraightCombinaison;
import org.dreamsoft.yamplayer.client.combinaison.UpperCombinaison;
import org.dreamsoft.yamplayer.client.combinaison.YahtzeeCombinaison;
import org.dreamsoft.yamplayer.client.ui.PlayerSheet;
import org.dreamsoft.yamplayer.client.ui.ScoreRenderer;
import org.dreamsoft.yamplayer.client.ui.SheetHeaderCell;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class YamSheet extends Composite {
	private static final int FIRST_COLUMN_PLAYER_SHEET = 1;

	private FlexTable playerTable = new FlexTable();
	private PlayerSheet activePlayerSheet = null;
	private ScoreRenderer scoreRenderer = new ScoreRenderer();

	private ArrayList<Command> refreshCommands = new ArrayList<Command>();

	public YamSheet() {
		playerTable.setCellPadding(0);
		playerTable.setCellSpacing(0);
		playerTable.setWidth("100%");
		playerTable.setBorderWidth(1);
		playerTable.setHTML(0, 0, "");

		addLine(1, "ACES", new UpperCombinaison(1));
		addLine(2, "TWOS", new UpperCombinaison(2));
		addLine(3, "THREES", new UpperCombinaison(3));
		addLine(4, "FOURS", new UpperCombinaison(4));
		addLine(5, "FIVES", new UpperCombinaison(5));
		addLine(6, "SIXES", new UpperCombinaison(6));

		addBonusLine(7, "BONUS", new int[] { 1, 2, 3, 4, 5, 6 });
		addSubTotalLine(8, "UPPER TOTAL", new int[] { 1, 2, 3, 4, 5, 6, 7 });

		addLine(9, "3 OF A KIND", new IdenticalCombinaison(3));
		addLine(10, "4 OF A KIND", new IdenticalCombinaison(4));
		addLine(11, "FULL HOUSE", new FullHouseCombinaison());
		addLine(12, "SMALL STRAIGHT", new StraightCombinaison(StraightCombinaison.SMALL));
		addLine(13, "LARGE STRAIGHT", new StraightCombinaison(StraightCombinaison.LARGE));
		addLine(14, "CHANCE", new Combinaison());
		addLine(15, "YAHTZEE", new YahtzeeCombinaison());

		addSubTotalLine(16, "LOWER TOTAL", new int[] { 9, 10, 11, 12, 13, 14, 15 });
		addSubTotalLine(17, "TOTAL", new int[] { 8, 16 });

		initWidget(playerTable);
	}

	public void addPlayer(String name) {
		if (name != null && name.length() > 0) {
			int n = playerTable.getCellCount(0);
			PlayerSheet playerSheet = new PlayerSheet(name, n);
			playerTable.setWidget(0, n, playerSheet);
			setActivePlayerSheet(playerSheet);
			refreshActivePlayerSheet();
		}
	}

	public void refreshActivePlayerSheet() {
		// refresh
		for (Iterator<Command> iterator = refreshCommands.iterator(); iterator.hasNext();) {
			iterator.next().execute();
		}
		// draw
		int nRow = playerTable.getRowCount();
		for (int i = 1; i < nRow; i++) {
			SheetHeaderCell sheetHeaderCell = (SheetHeaderCell) playerTable.getWidget(i, 0);
			playerTable.setHTML(i, activePlayerSheet.getNumber(), scoreRenderer.renderScore(i, activePlayerSheet, sheetHeaderCell.getCombinaison()));
		}
	}

	private void addBonusLine(final int i, String text, final int[] lines) {
		SheetHeaderCell cell = new SheetHeaderCell(text, null);
		playerTable.setWidget(i, 0, cell);
		refreshCommands.add(new Command() {
			public void execute() {
				if (activePlayerSheet.getSubTotal(lines) >= 63)
					activePlayerSheet.setScore(i, 35);
			}
		});
	}

	private void addSubTotalLine(final int i, String text, final int[] lines) {
		SheetHeaderCell cell = new SheetHeaderCell(text, null);
		playerTable.setWidget(i, 0, cell);
		refreshCommands.add(new Command() {
			public void execute() {
				activePlayerSheet.setSubTotal(i, lines);
			}
		});
	}

	private void addLine(final int i, String text, final Combinaison combinaison) {
		SheetHeaderCell cell = new SheetHeaderCell(text, combinaison);
		cell.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireCombinaisonClickEvent(i, activePlayerSheet, combinaison);
			}
		});
		playerTable.setWidget(i, 0, cell);
	}

	public PlayerSheet getNextPlayerSheet() {
		PlayerSheet nextPlayerSheet = null;
		int n = 0;
		if (activePlayerSheet != null)
			n = activePlayerSheet.getNumber();
		n++;
		if (n >= playerTable.getCellCount(0))
			n = FIRST_COLUMN_PLAYER_SHEET;
		Widget w = playerTable.getWidget(0, n);
		if (w instanceof PlayerSheet)
			nextPlayerSheet = (PlayerSheet) w;
		return nextPlayerSheet;
	}

	public void setActivePlayerSheet(PlayerSheet p) {
		if (activePlayerSheet != null) {
			playerTable.getColumnFormatter().setStyleName(activePlayerSheet.getNumber(), "playerunselected");
		}
		playerTable.getColumnFormatter().setStyleName(p.getNumber(), "playerselected");
		activePlayerSheet = p;
	}

	public ScoreRenderer getScoreRenderer() {
		return scoreRenderer;
	}

	public void setScoreRenderer(ScoreRenderer scoreRenderer) {
		this.scoreRenderer = scoreRenderer;
	}

	public void fireCombinaisonClickEvent(int i, PlayerSheet activePlayerSheet, Combinaison combinaison) {

	}

	public void removePlayer(String name) {
		if (name != null && name.length() > 0) {
			int nCol = playerTable.getCellCount(0);
			int nRow = playerTable.getRowCount();
			for (int i = 0; i < nCol; i++) {
				if (name.equals(playerTable.getText(0, i))) {
					activePlayerSheet = null;
					for (int j = 0; j < nRow; j++) {
						playerTable.removeCell(j, i);
					}
					activePlayerSheet = getNextPlayerSheet();
				}
			}
		}
	}
}
