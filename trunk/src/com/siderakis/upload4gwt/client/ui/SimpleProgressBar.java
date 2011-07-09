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

	final private StatusCellSafeHTMLTemplate statusCellSafeHTMLTemplate = (StatusCellSafeHTMLTemplate) GWT
	.create(StatusCellSafeHTMLTemplate.class);

	final private HTML widget = new HTML();

	private UploadStatus uploadStatus;

	public SimpleProgressBar() {
		initWidget(widget);
	}

	@Override
	public UploadStatus getProgress() {
		return uploadStatus;
	}

	@Override
	public void setProgress(final UploadStatus uploadStatus) {
		this.uploadStatus = uploadStatus;
		widget.setHTML(statusCellSafeHTMLTemplate.status(uploadStatus.getPercentage()));
	}

}
