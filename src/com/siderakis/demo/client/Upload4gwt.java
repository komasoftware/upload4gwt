package com.siderakis.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 */
public class Upload4gwt implements EntryPoint {

	@Override
	public void onModuleLoad() {

		final TabLayoutPanel layoutPanel = new TabLayoutPanel(30, Unit.PX);
		layoutPanel.add(new DragAndDropDemo(), "Drag-and-Drop Demo");
		layoutPanel.add(new FullFormDemo(), "Form-Data Demo");
		layoutPanel.add(new MultiSelectDemo(), "Multi-Select Demo");
		layoutPanel.add(new StyledInputDemo(), "Styled-Input Demo");
		RootLayoutPanel.get().add(layoutPanel);

	}
}
