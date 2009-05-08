package org.dreamsoft.yamplayer.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

public class YamBoard extends Composite {
	private Command afterScoreCommand = new Command() {
		public void execute() {
			yamDices.reset();
		};
	};
	private YamCombinaisons yamCombinaisons = new YamCombinaisons(afterScoreCommand);
	private Command afterRollCommand = new Command() {
		public void execute() {
			if (yamCombinaisons != null && yamDices != null)
				yamCombinaisons.showEstimated(yamDices.getIntDices());
		};
	};
	private Command afterSelectedDiceCommand = new Command() {
		public void execute() {
			if (yamCombinaisons != null && yamDices != null)
				yamCombinaisons.calculateChances(yamDices.getIntSeletedDices());
		};
	};
	private YamDices yamDices = new YamDices(afterRollCommand, afterSelectedDiceCommand);

	public YamBoard() {

		FlexTable table = new FlexTable();
		table.setSize("400", "400");
		table.setBorderWidth(1);
		table.setCellPadding(0);
		table.setCellSpacing(2);
		table.setWidget(0, 0, yamCombinaisons);
		table.setWidget(0, 1, yamDices);
		initWidget(table);
		yamDices.reset();
	}
}
