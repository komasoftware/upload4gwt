package com.siderakis.demo.client;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.siderakis.upload4gwt.client.ProgressSyncer;
import com.siderakis.upload4gwt.client.ui.SimpleProgressBar;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 */
public class FullFormDemo extends Composite {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

	// Create a panel to hold all of the form widgets.
	final FlowPanel panel = new FlowPanel();

	private void addSingleUploadInput() {
		panel.add(new Label("Single File:"));
		final FileUpload upload = new FileUpload();
		upload.setName("singpleUploadFormElement");
		panel.add(upload);
	}

	public FullFormDemo() {
		panel.setStyleName("panel");
		panel.add(new HTML("Upload4gwt"));
		// Create a FormPanel and point it at a service.
		final Long uploadId = ProgressSyncer.getInstance().getNextId();
		final FormPanel form = new FormPanel();
		form.setAction(UPLOAD_ACTION_URL + "?uploadId=" + uploadId);

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		form.setWidget(panel);
		addSingleUploadInput();

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
		panel.add(lb);
		// Add a 'submit' button.
		panel.add(new Button("Submit", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				form.submit();
			}
		}));

		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);
		final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();

		// Add an event handler to the form.
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(final SubmitEvent event) {
				// This event is fired just before the form is submitted. We can
				// take this opportunity to perform validation.
				// if () {
				// event.cancel();
				// }
				progressSyncer.addStatusDisplay(uploadId, simpleProgressBar);
				progressSyncer.start();
			}
		});
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(final SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this
				// event is fired. Assuming the service returned a response of
				// type
				// text/html, we can get the result text here (see the FormPanel
				// documentation for further explanation).
				// Window.alert(event.getResults());
				progressSyncer.stop();
			}
		});

		initWidget(form);

	}
}
