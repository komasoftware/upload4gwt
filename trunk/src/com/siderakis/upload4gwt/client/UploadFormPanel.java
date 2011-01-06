package com.siderakis.upload4gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;

public class UploadFormPanel extends FormPanel {
	private String uploadId = null;
	final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();

	public UploadFormPanel() {
		super();

		progressSyncer.getNextId(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				uploadId = result;
				setAction(actionBase);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});

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
					GWT.log("UploadId is null");
					// try to get an id
					progressSyncer.getNextId(new AsyncCallback<String>() {

						@Override
						public void onSuccess(String result) {
							// after id is received set it on form
							uploadId = result;
							setAction(actionBase);
							// then resubmit
							submit();
						}

						@Override
						public void onFailure(Throwable caught) {
						}
					});
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

	private String actionBase;

	@Override
	public void setAction(String url) {
		actionBase = url;
		super.setAction(url + (url.contains("?") ? "&" : "?") + "uploadId=" + uploadId);
	}

	public void setStatusDisplay(HasProgress statusDisplay) {
		progressSyncer.addStatusDisplay(uploadId, statusDisplay);

	}

}
