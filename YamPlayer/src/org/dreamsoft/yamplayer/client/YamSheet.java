package org.dreamsoft.yamplayer.client;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class YamSheet extends Composite {
	private static final int FIRST_COLUMN_PLAYER_SHEET = 1;

	private FlexTable playerTable = new FlexTable();
	private int player = -1;
	private ScoreRenderer scoreRenderer = new ScoreRenderer();

	public YamSheet() {
		playerTable.setCellPadding(0);
		playerTable.setCellSpacing(0);
		playerTable.setBorderWidth(1);
		playerTable.setHTML(0, 0, "");
		playerTable.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell c = playerTable.getCellForEvent(event);
				fireCellClickEvent(c.getRowIndex());
			}
		});

		playerTable.setWidget(1, 0, new SheetHeaderCell("ACES", new UpperCombinaison(1)));
		playerTable.setWidget(2, 0, new SheetHeaderCell("TWOS", new UpperCombinaison(2)));
		playerTable.setWidget(3, 0, new SheetHeaderCell("THREES", new UpperCombinaison(3)));
		playerTable.setWidget(4, 0, new SheetHeaderCell("FOURS", new UpperCombinaison(4)));
		playerTable.setWidget(5, 0, new SheetHeaderCell("FIVES", new UpperCombinaison(5)));
		playerTable.setWidget(6, 0, new SheetHeaderCell("SIXES", new UpperCombinaison(6)));

		playerTable.setWidget(7, 0, new SheetHeaderCell("BONUS", new int[] { 1, 2, 3, 4, 5, 6 }, 63, 35));
		playerTable.setWidget(8, 0, new SheetHeaderCell("UPPER TOTAL", new int[] { 1, 2, 3, 4, 5, 6, 7 }));

		playerTable.setWidget(9, 0, new SheetHeaderCell("3 OF A KIND", new IdenticalCombinaison(3)));
		playerTable.setWidget(10, 0, new SheetHeaderCell("4 OF A KIND", new IdenticalCombinaison(4)));
		playerTable.setWidget(11, 0, new SheetHeaderCell("FULL HOUSE", new FullHouseCombinaison()));
		playerTable.setWidget(12, 0, new SheetHeaderCell("SMALL STRAIGHT", new StraightCombinaison(StraightCombinaison.SMALL)));
		playerTable.setWidget(13, 0, new SheetHeaderCell("LARGE STRAIGHT", new StraightCombinaison(StraightCombinaison.LARGE)));
		playerTable.setWidget(14, 0, new SheetHeaderCell("CHANCE", new Combinaison()));
		playerTable.setWidget(15, 0, new SheetHeaderCell("YAHTZEE", new YahtzeeCombinaison()));

		playerTable.setWidget(16, 0, new SheetHeaderCell("LOWER TOTAL", new int[] { 9, 10, 11, 12, 13, 14, 15 }));
		playerTable.setWidget(17, 0, new SheetHeaderCell("TOTAL", new int[] { 8, 16 }));
		playerTable.getColumnFormatter().setStyleName(0, "header");
		playerTable.getRowFormatter().setStyleName(0, "names");
		initWidget(playerTable);
	}

	public void addPlayer(String name) {
		if (name != null && name.length() > 0) {
			int n = getPlayerCount();
			final PlayerSheet playerSheet = new PlayerSheet(name, n);
			playerSheet.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					setActivePlayer(playerSheet.getNumber());
				}
			});
			playerTable.setWidget(0, n + FIRST_COLUMN_PLAYER_SHEET, playerSheet);
			resetScore();
		}
	}

	public PlayerSheet getPlayerSheet(int index) {
		PlayerSheet playerSheet = null;
		int n = playerTable.getCellCount(0);
		if (index + FIRST_COLUMN_PLAYER_SHEET < n)
			playerSheet = (PlayerSheet) playerTable.getWidget(0, index + FIRST_COLUMN_PLAYER_SHEET);
		return playerSheet;
	}

	public SheetHeaderCell getSheetHeaderCell(int indexRow) {
		SheetHeaderCell sheetHeaderCell = null;
		if (indexRow > 0)
			sheetHeaderCell = (SheetHeaderCell) playerTable.getWidget(indexRow, 0);
		return sheetHeaderCell;
	}

	public PlayerSheet getActiveSheet() {
		return getPlayerSheet(player);
	}

	public void refreshPlayerSheet(int index) {
		PlayerSheet s = index == -1 ? getActiveSheet() : getPlayerSheet(index);
		if (s != null) {
			int nRow = playerTable.getRowCount();
			for (int i = 1; i < nRow; i++) {
				SheetHeaderCell sheetHeaderCell = (SheetHeaderCell) playerTable.getWidget(i, 0);
				playerTable.setHTML(i, s.getNumber() + FIRST_COLUMN_PLAYER_SHEET, scoreRenderer.renderScore(i, s, sheetHeaderCell));
			}
		}
	}

	protected void nextPlayer() {
		refreshPlayerSheet(-1);
		setActivePlayer(getNextPlayer());
	}

	public int getNextPlayer() {
		int p = player + 1;
		if (p + FIRST_COLUMN_PLAYER_SHEET > getPlayerCount()) {
			p = 0;
		}
		return p;
	}

	public int getPlayerCount() {
		return playerTable.getCellCount(0) - FIRST_COLUMN_PLAYER_SHEET;
	}

	public void setActivePlayer(int p) {
		highlightPlayer(player, false);
		highlightPlayer(p, true);
		player = p;
	}

	public void highlightPlayer(int index, boolean selected) {
		if (index >= 0) {
			playerTable.getColumnFormatter().setStyleName(index + FIRST_COLUMN_PLAYER_SHEET, selected ? "playerselected" : "playerunselected");
		}
	}

	public ScoreRenderer getScoreRenderer() {
		return scoreRenderer;
	}

	public void setScoreRenderer(ScoreRenderer scoreRenderer) {
		this.scoreRenderer = scoreRenderer;
	}

	public void removePlayer(String name) {
		if (name != null && name.length() > 0) {
			int removedCol = 0;
			for (int i = 0; i < getPlayerCount(); i++) {
				PlayerSheet s = getPlayerSheet(i);
				s.setNumber(s.getNumber() - removedCol);
				if (s != null && name.equals(s.getPlayerName())) {
					for (int j = 0; j < playerTable.getRowCount(); j++) {
						playerTable.removeCell(j, s.getNumber() + FIRST_COLUMN_PLAYER_SHEET);
					}
					removedCol++;
					i--;
				}
			}
			setActivePlayer(0);
		}
	}

	public void resetScore() {
		scoreRenderer.setEnableEvalutedScore(false);
		for (int i = 0; i < getPlayerCount(); i++) {
			getPlayerSheet(i).clearScores();
			refreshPlayerSheet(i);
			highlightPlayer(i, false);
		}
		scoreRenderer.setEnableEvalutedScore(true);
		setActivePlayer(0);
	}

	public void fireCellClickEvent(int row) {
		return;
	}
}
