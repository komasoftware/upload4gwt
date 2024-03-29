/*
 * Copyright 2010 Nick Siderakis.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.siderakis.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 */
public class Upload4gwt implements EntryPoint {
	public static class DemoComposite extends Composite {
		// Create a panel to hold all of the form widgets.
		final FlowPanel panel = new FlowPanel();
		final FlowPanel outer = new FlowPanel();

		public DemoComposite() {

			panel.setStyleName("panel");
			panel.add(new HTML("Upload4gwt"));

			outer.add(panel);
			initWidget(outer);
		}

		public void addSourceCodeLink(final String file) {
			outer.add(new Anchor("Source Code",
					"http://code.google.com/p/upload4gwt/source/browse/trunk/demo/com/siderakis/demo/client/" + file
					+ ".java"));
		}
	}

	@Override
	public void onModuleLoad() {

		final TabLayoutPanel layoutPanel = new TabLayoutPanel(30, Unit.PX);
		layoutPanel.add(new DragAndDropDemo(), "Drag-and-Drop Demo");
		layoutPanel.add(new MultiSelectDemo(), "Multi-Select Demo");
		layoutPanel.add(new StyledInputDemo(), "Styled-Input Demo");
		layoutPanel.add(new FullFormDemo(), "Form-Data Demo");
		RootLayoutPanel.get().add(layoutPanel);

	}
}
