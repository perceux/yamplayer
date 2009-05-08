package org.dreamsoft.yamplayer.client;

import org.dreamsoft.yamplayer.client.combinaison.Combinaison;
import org.dreamsoft.yamplayer.client.ui.PlayerSheet;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

	private YamSheet yamSheet = new YamSheet() {
		@Override
		public void fireCombinaisonClickEvent(int i, PlayerSheet activePlayerSheet, Combinaison combinaison) {
			if (activePlayerSheet.getScore(i) == null) {
				// calculate score
				activePlayerSheet.setScore(i, combinaison.getMatchingScore(yamDices.getIntDices()));
				// clean player
				refreshActivePlayerSheet();
				// next
				setActivePlayerSheet(getNextPlayerSheet());
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
			yamSheet.refreshActivePlayerSheet();
			yamSheet.getScoreRenderer().setEnableEvalutedScore(false);
		}
	};

	public YamBoard() {

		rollButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rollDices();

			}

		});
		addPlayerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				yamSheet.addPlayer(namePlayer.getText());
			}
		});
		removePlayerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				yamSheet.removePlayer(namePlayer.getText());
			}
		});

		yamSheet.getScoreRenderer().setYamDices(yamDices);
		yamSheet.getScoreRenderer().setEnableCompareScore(false);

		FlexTable table = new FlexTable();
		table.setSize("600", "400");
		table.setBorderWidth(1);
		table.setCellPadding(0);
		table.setCellSpacing(2);
		table.setWidget(0, 0, yamSheet);
		table.getFlexCellFormatter().setColSpan(0, 0, 3);
		table.getCellFormatter().setWidth(0, 0, "100%");
		table.setWidget(0, 1, yamDices);
		table.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidget(1, 0, namePlayer);
		table.setWidget(1, 1, addPlayerButton);
		table.setWidget(1, 2, removePlayerButton);
		table.setWidget(1, 3, rollButton);
		yamSheet.addPlayer("p1");
		yamSheet.addPlayer("p2");
		newTurn();
		initWidget(table);

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
