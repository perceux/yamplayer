package org.dreamsoft.yamplayer.client;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;
import org.dreamsoft.yamplayer.client.ui.PlayerSheet;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;

public class YamBoard extends Composite {
	private static final int ROLL_MAX_ATTEMPT = 3;
	protected int rollAttempt = 0;
	final TextBox namePlayer = new TextBox();
	final Button rollButton = new Button("ROLL");
	final Button addPlayerButton = new Button("+");
	final Button removePlayerButton = new Button("-");
	final Button startGameButton = new Button("START");

	private YamSheet yamSheet = new YamSheet() {
		@Override
		public void fireCellClickEvent(int row) {
			Combinaison combinaison = getSheetHeaderCell(row).getCombinaison();
			PlayerSheet s = getActiveSheet();
			if (s != null && s.getScore(row) == null) {
				// calculate score
				s.setScore(row, combinaison.getMatchingScore(yamDices.getIntDices()));
				// clean player
				nextPlayer();
				// New Turn
				newTurn();
			}
		}
	};

	private YamDices yamDices = new YamDices() {
		@Override
		protected void fireDiceSelectionChangeEvent() {
			yamSheet.getScoreRenderer().setEnableEvalutedScore(true);
			yamSheet.getScoreRenderer().setEnableCompareScore(true);
			yamSheet.getScoreRenderer().setEnableMatchScore(true);
			yamSheet.refreshPlayerSheet(-1);
			yamSheet.getScoreRenderer().setEnableEvalutedScore(false);
		}
	};

	public YamBoard() {
		namePlayer.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == 13) {
					addPlayerButton.click();
				}
			}
		});
		rollButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rollDices();
			}

		});
		addPlayerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				yamSheet.addPlayer(namePlayer.getText());
				reset();
				namePlayer.setText("");
				namePlayer.setFocus(true);
			}
		});
		removePlayerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				yamSheet.removePlayer(namePlayer.getText());
				reset();
				namePlayer.setText("");
				namePlayer.setFocus(true);
			}
		});
		startGameButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				yamSheet.resetScore();
				yamSheet.setActivePlayer(0);
				newTurn();
			}
		});
		yamSheet.getScoreRenderer().setYamDices(yamDices);
		yamSheet.getScoreRenderer().setEnableCompareScore(false);

		FlexTable table = new FlexTable();
		table.setBorderWidth(1);
		table.setCellPadding(0);
		table.setCellSpacing(2);
		table.setWidget(0, 0, yamSheet);
		table.getFlexCellFormatter().setColSpan(0, 0, 4);
		//table.getCellFormatter().setWidth(0, 0, "100%");
		table.setWidget(0, 1, yamDices);
		table.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidget(1, 0, namePlayer);
		table.setWidget(1, 1, addPlayerButton);
		table.setWidget(1, 2, removePlayerButton);
		table.setWidget(1, 3, startGameButton);
		table.setWidget(1, 4, rollButton);
		reset();
		initWidget(table);

	}

	private void reset() {
		setRollAttempt(0);
		yamSheet.resetScore();
		startGameButton.setEnabled(yamSheet.getPlayerCount()>0);
	}

	protected void newTurn() {
		setRollAttempt(ROLL_MAX_ATTEMPT);
		yamDices.selectAll(false);
		rollDices();
	}

	public void setRollAttempt(int rollAttempt) {
		this.rollAttempt = rollAttempt;
		if (rollAttempt > 0) {
			rollButton.setEnabled(true);
			rollButton.setHTML("ROLL" + ("(" + rollAttempt + ")"));
		} else {
			rollButton.setHTML("ROLL");
			rollButton.setEnabled(false);
		}
	}

	protected void rollDices() {
		if (rollAttempt > 0) {
			setRollAttempt(rollAttempt - 1);
			yamDices.roll();
		}
	}

}
