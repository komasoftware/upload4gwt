package com.siderakis.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.siderakis.upload4gwt.client.UploadFormPanel;
import com.siderakis.upload4gwt.client.ui.File;
import com.siderakis.upload4gwt.client.ui.FileInput;
import com.siderakis.upload4gwt.client.ui.SimpleProgressBar;

public class MultiSelectDemo extends Composite {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

	final FlowPanel panel = new FlowPanel();

	private void addMultiUploadInput() {
		final FileInput fileInput = new FileInput();
		fileInput.setAllowMultipleFiles(true);
		panel.add(new Label(fileInput.supportsFileAPI() ? "Select multiple files:"
				: "Your browser doesn't support multiple files!:"));

		fileInput.setName("uploadFormElement");

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

	public MultiSelectDemo() {
		panel.setStyleName("panel");
		panel.add(new HTML("Upload4gwt"));
		final UploadFormPanel form = new UploadFormPanel();
		form.setAction(UPLOAD_ACTION_URL);

		form.setWidget(panel);
		addMultiUploadInput();
		panel.add(new Button("Submit", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				form.submit();
			}
		}));

		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);
		form.setStatusDisplay(simpleProgressBar);
		initWidget(form);

	}
}
