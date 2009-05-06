package org.dreamsoft.yamplayer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

public class YamBoard extends Composite {
	private Command afterScoreCommand = new Command () {
		public void execute() {
			yamDices.reset();
		};
	};
	private YamCombinaisons yamCombinaisons = new YamCombinaisons(afterScoreCommand);
	private Command afterRollCommand = new Command () {
		public void execute() {
			ArrayList<Dice> dices = yamDices.getDices();
			yamCombinaisons.showEstimatedScore(dices);
		};
	};
	private YamDices yamDices = new YamDices(afterRollCommand);

	public YamBoard() {
		FlexTable table = new FlexTable();
		table.setSize("400", "400");
		table.setBorderWidth(1);
		table.setCellPadding(0);
		table.setCellSpacing(5);
		table.setWidget(0, 0, yamCombinaisons);
		table.setWidget(0, 1, yamDices);
		initWidget(table);
	}
}
