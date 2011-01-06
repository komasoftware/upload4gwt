package com.siderakis.upload4gwt.client;

import com.google.gwt.user.client.ui.FormPanel;

public class UploadFormPanel extends FormPanel {
	final Long uploadId = ProgressSyncer.getInstance().getNextId();
	final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();

	public UploadFormPanel() {
		super();

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setMethod(FormPanel.METHOD_POST);

		addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(final SubmitEvent event) {
				// This event is fired just before the form is submitted. We can
				// take this opportunity to perform validation.
				if (uploadId == null) {
					event.cancel();
					// TODO try to get an id
					// TODO after id is received set it on form
					// TODO then resubmit
				} else {

					progressSyncer.start(uploadId);
				}
			}
		});
		addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(final SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this
				// event is fired. Assuming the service returned a response of
				// type
				// text/html, we can get the result text here (see the FormPanel
				// documentation for further explanation).
				// Window.alert(event.getResults());

				// progressSyncer.stop();
			}
		});

	}

	@Override
	public void setAction(String url) {
		// if contains ? then use &
		super.setAction(url + "?uploadId=" + uploadId);
	}

	public void setStatusDisplay(HasProgress statusDisplay) {
		progressSyncer.addStatusDisplay(uploadId, statusDisplay);

	}

}
