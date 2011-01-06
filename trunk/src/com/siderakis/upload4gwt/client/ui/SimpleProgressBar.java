package com.siderakis.upload4gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.siderakis.upload4gwt.client.HasProgress;
import com.siderakis.upload4gwt.shared.UploadStatus;

public class SimpleProgressBar extends Composite implements HasProgress {

	public interface StatusCellSafeHTMLTemplate extends SafeHtmlTemplates {
		@Template("<div><div style=\"font-size:medium;height:1.2em;width:100%;cursor:default;border:thin #7ba5d5 solid;\">"
				+ "<div style=\"height:1.2em;width:{0}%; background:#8cb6e6; background-image: url('progress_background.png');\">"
				+ "</div><div style=\"height:1.2em; margin:-1.2em;font-weight:bold;color:#4e7fba;\">"
				+ "<center>{0}</center></div></div></div>")
		SafeHtml status(int percentage);
	}

	StatusCellSafeHTMLTemplate statusCellSafeHTMLTemplate = (StatusCellSafeHTMLTemplate) GWT
			.create(StatusCellSafeHTMLTemplate.class);
	HTML widget = new HTML();

	public SimpleProgressBar() {
		initWidget(widget);
	}

	private UploadStatus uploadStatus;

	@Override
	public void setProgress(UploadStatus uploadStatus) {
		this.uploadStatus = uploadStatus;
		widget.setHTML(statusCellSafeHTMLTemplate.status(uploadStatus.getPercentage()));
	}

	@Override
	public UploadStatus getProgress() {
		return uploadStatus;
	}

}
