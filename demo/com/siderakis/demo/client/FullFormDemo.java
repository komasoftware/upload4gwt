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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.siderakis.demo.client.Upload4gwt.DemoComposite;
import com.siderakis.upload4gwt.client.UploadFormPanel;
import com.siderakis.upload4gwt.client.ui.SimpleProgressBar;

/**
 * This simple demo provides the ability to a upload a single file, along with
 * other form data. After the upload has completed it allows for the form to be
 * reset.
 * 
 */
public class FullFormDemo extends DemoComposite {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

	public FullFormDemo() {

		// Create a UploadFormPanel and point it at a service.
		final UploadFormPanel form = new UploadFormPanel();
		form.setAction(UPLOAD_ACTION_URL);
		form.setWidget(panel);

		// Create a standard FileUpload.
		panel.add(new Label("Single File:"));
		final FileUpload upload = new FileUpload();
		upload.setName("singleUploadFormElement");
		panel.add(upload);

		// Create a TextBox, giving it a name so that it will be submitted.
		panel.add(new Label("Textbox:"));
		final TextBox tb = new TextBox();
		tb.setName("textBoxFormElement");
		panel.add(tb);

		// Create a ListBox, giving it a name and some values to be associated
		// with its options.
		panel.add(new Label("Listbox:"));
		final ListBox lb = new ListBox();
		lb.setName("listBoxFormElement");
		lb.addItem("foo", "fooValue");
		lb.addItem("bar", "barValue");
		lb.addItem("baz", "bazValue");
		panel.add(lb);

		// Add a 'submit' button.
		panel.add(new Button("Submit", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				form.submit();
			}
		}));

		// Create a SimpleProgressBar, add it to the panel. Also set it as the
		// status display for the form.
		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);

		form.setStatusDisplay(simpleProgressBar);

		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(final SubmitCompleteEvent event) {
				// TODO reset the form.
			}
		});
		outer.add(form);
		outer.add(new Anchor("Source Code",
		"http://code.google.com/p/upload4gwt/source/browse/trunk/demo/com/siderakis/demo/client/FullFormDemo.java"));

	}
}
