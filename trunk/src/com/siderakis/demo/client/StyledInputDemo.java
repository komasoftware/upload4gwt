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
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.siderakis.upload4gwt.client.UploadFormPanel;
import com.siderakis.upload4gwt.client.ui.SimpleProgressBar;
import com.siderakis.upload4gwt.client.ui.UploadResources;

public class StyledInputDemo extends Composite {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

	private static final UploadResources res = GWT.create(UploadResources.class);

	private static native void styleInput(Element input) /*-{
		var fakeFileUpload = $doc.createElement('div');
		fakeFileUpload.className = 'fakefile';
		fakeFileUpload.appendChild($doc.createElement('input'));
		var image = $doc.createElement('img');
		image.src='../button_select.gif';
		fakeFileUpload.appendChild(image);
		input.className = 'file hidden';
		var clone = fakeFileUpload.cloneNode(true);
		input.parentNode.appendChild(clone);
		input.relatedElement = clone.getElementsByTagName('input')[0];
		input.onchange = input.onmouseout = function () {
		this.relatedElement.value = this.value;
		}
	}-*/;

	private static native void styleInput(Element input, String defaultText) /*-{
		var fakeFileUpload = $doc.createElement('div');
		fakeFileUpload.appendChild($doc.createElement('input'));
		var clone = fakeFileUpload.cloneNode(true);
		input.parentNode.appendChild(clone);
		input.relatedElement = clone.getElementsByTagName('input')[0];
		input.relatedElement.value = defaultText;
		input.onchange = input.onmouseout = function () {
		this.relatedElement.value = (this.value=="")?defaultText:this.value;
		}
	}-*/;

	// Create a panel to hold all of the form widgets.
	final FlowPanel panel = new FlowPanel();

	public StyledInputDemo() {
		panel.setStyleName("panel");
		panel.add(new HTML("Upload4gwt"));
		// Create a FormPanel and point it at a service.
		final UploadFormPanel form = new UploadFormPanel();
		form.setAction(UPLOAD_ACTION_URL);

		form.setWidget(panel);
		addImageStyledInput();
		addCSSStyledInput();

		panel.add(new Button("Submit", new ClickHandler() {
			@Override public void onClick(final ClickEvent event) {
				form.submit();
			}
		}));

		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);
		form.setStatusDisplay(simpleProgressBar);

		initWidget(form);
	}

	private void addCSSStyledInput() {
		final FileUpload upload = new FileUpload();
		final FlowPanel outer = new FlowPanel();
		outer.setStyleName("style");
		final FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("fileinputs");
		upload.setStyleName("file");
		upload.setName("styledUploadFormElement");
		flowPanel.add(upload);

		panel.add(new Label("Image Styled File Input:"));
		outer.add(flowPanel);
		panel.add(outer);
		styleInput(upload.getElement());
	}

	private void addImageStyledInput() {
		final FileUpload upload = new FileUpload();
		res.style().ensureInjected();
		final FlowPanel outer = new FlowPanel();
		outer.setStyleName(res.style().styledUploader());
		outer.add(upload);

		upload.setName("styledUploadFormElement");
		panel.add(new Label("CSS Styled File Input:"));

		panel.add(outer);
		styleInput(upload.getElement(), "Select a file");
	}
}