package org.dreamsoft.yamplayer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YamPlayer implements EntryPoint {
	private YamBoard board = new YamBoard();

	public void onModuleLoad() {
		VerticalPanel vpanel = new VerticalPanel();
		//vpanel.setSize("100%", "100%");
		vpanel.add(board);
		vpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		RootPanel.get().add(vpanel);
	}
}
