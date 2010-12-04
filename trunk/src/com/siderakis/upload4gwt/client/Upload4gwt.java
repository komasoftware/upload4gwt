package com.siderakis.upload4gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 */
public class Upload4gwt implements EntryPoint {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL()
	+ "upload";

	private static final UploadResources res = GWT
	.create(UploadResources.class);

	public static native void styleInput(Element input) /*-{
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

	public static native void styleInput(Element input, String defaultText) /*-{
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

	private void addMultiUploadInput() {
		final FileInput fileInput = new FileInput();
		fileInput.setAllowMultipleFiles(true);
		// Create a FileUpload widget.
		panel.add(new Label(
				fileInput.supportsFileAPI() ? "Select multiple files:"
						: "Your browser doesn't support multiple files!:"));

		fileInput.setName("uploadFormElement");

		// final FileUpload upload = new FileUpload();
		panel.add(fileInput);

		final FlexTable table = new FlexTable();
		panel.add(table);
		fileInput.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				table.removeAllRows();
				table.setHTML(0, 0, "<b>File name</b>");
				table.setHTML(0, 1, "<b>File size</b>");
				int row = 1;
				for (final File file : fileInput.getFiles()) {
					table.setText(row, 0, file.getFileName());
					table.setText(row, 1, file.getFileSize() + "");
					++row;
				}
			}
		});
	}

	private void addSingleUploadInput() {
		panel.add(new Label("Single File:"));
		final FileUpload upload = new FileUpload();
		upload.setName("singpleUploadFormElement");
		panel.add(upload);
	}

	@Override
	public void onModuleLoad() {
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
		addMultiUploadInput();
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
		panel.add(new Label("Submit:"));
		// Add a 'submit' button.
		panel.add(new Button("Submit", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				form.submit();
			}
		}));
		// Add an event handler to the form.
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(final SubmitEvent event) {
				// This event is fired just before the form is submitted. We can
				// take this opportunity to perform validation.
				if (tb.getText().length() == 0) {
					Window.alert("The text box must not be empty");
					event.cancel();
				}
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
				Window.alert(event.getResults());
			}
		});

		RootPanel.get().add(form);

	}
}
