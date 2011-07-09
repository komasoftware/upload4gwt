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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.siderakis.demo.client.Upload4gwt.DemoComposite;
import com.siderakis.upload4gwt.client.dnd.UploadDropTarget;
import com.siderakis.upload4gwt.client.ui.SimpleProgressBar;

public class DragAndDropDemo extends DemoComposite {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

	public DragAndDropDemo() {

		final FlowPanel dropPanel = new FlowPanel();
		dropPanel.addStyleName("dropPanel");
		dropPanel.add(new Label("DROP HERE"));
		panel.add(dropPanel);

		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);
		new UploadDropTarget().uploader(dropPanel.getElement(), UPLOAD_ACTION_URL, simpleProgressBar);

		outer.add(new Anchor("Source Code",
		"http://code.google.com/p/upload4gwt/source/browse/trunk/demo/com/siderakis/demo/client/DragAndDropDemo.java"));

	}
}
