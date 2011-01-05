package com.siderakis.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.siderakis.upload4gwt.client.ProgressSyncer;
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

	public StyledInputDemo() {
		panel.setStyleName("panel");
		panel.add(new HTML("Upload4gwt"));
		// Create a FormPanel and point it at a service.
		final FormPanel form = new FormPanel();
		form.setAction(UPLOAD_ACTION_URL);

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		form.setWidget(panel);
		addImageStyledInput();
		addCSSStyledInput();

		panel.add(new Button("Submit", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				form.submit();
			}
		}));

		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);
		final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(final SubmitEvent event) {
				progressSyncer.start();
			}
		});

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(final SubmitCompleteEvent event) {
				progressSyncer.stop();
			}
		});
		initWidget(form);
	}
}
