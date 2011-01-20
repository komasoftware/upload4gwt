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

package com.siderakis.upload4gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;

public class UploadFormPanel extends FormPanel {
	private String uploadId = null;
	private HasProgress statusDisplay = null;
	final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();

	private String actionBase;

	public UploadFormPanel() {
		this(false);
	}

	public UploadFormPanel(Boolean prefetchId) {
		super();

		if (prefetchId) {
			progressSyncer.getNextId(new AsyncCallback<String>() {

				@Override
				public void onFailure(final Throwable caught) {
				}

				@Override
				public void onSuccess(final String result) {
					uploadId = result;
					setAction(actionBase);
				}
			});
		}
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
						public void onFailure(final Throwable caught) {
						}

						@Override
						public void onSuccess(final String result) {
							// after id is received set it on form
							uploadId = result;
							setAction(actionBase);
							// then resubmit
							submit();
						}
					});
				} else {
					progressSyncer.addStatusDisplay(uploadId, statusDisplay);
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
	public void setAction(final String url) {
		actionBase = url;
		super.setAction(url + (url.contains("?") ? "&" : "?") + "uploadId=" + uploadId);
	}

	public void setStatusDisplay(final HasProgress statusDisplay) {
		this.statusDisplay = statusDisplay;
		progressSyncer.addStatusDisplay(uploadId, statusDisplay);

	}

}
