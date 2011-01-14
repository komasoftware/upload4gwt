package com.siderakis.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.siderakis.upload4gwt.client.dnd.UploadDropTarget;
import com.siderakis.upload4gwt.client.ui.SimpleProgressBar;

public class DragAndDropDemo extends Composite {
	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

	public DragAndDropDemo() {
		final FlowPanel panel = new FlowPanel();
		panel.setStyleName("panel");
		panel.add(new HTML("Upload4gwt"));
		final FlowPanel place = new FlowPanel();
		place.add(new Label("DROP HERE"));
		panel.add(place);

		initWidget(panel);
		final SimpleProgressBar simpleProgressBar = new SimpleProgressBar();
		panel.add(simpleProgressBar);
		new UploadDropTarget().uploader(place.getElement(), UPLOAD_ACTION_URL, simpleProgressBar);

	}
}
