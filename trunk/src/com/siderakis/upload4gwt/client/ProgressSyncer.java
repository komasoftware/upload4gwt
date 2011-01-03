package com.siderakis.upload4gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProgressSyncer {

	final UploadServiceAsync uploadService = GWT.create(UploadService.class);
	Timer timer = new Timer() {

		@Override
		public void run() {
			uploadService.getPercentage("", new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					GWT.log(result);
				}

				@Override
				public void onFailure(Throwable caught) {

				}
			});
		}
	};

	public ProgressSyncer() {

	}

	public void stop() {
		timer.cancel();
	}

	public void start() {
		timer.scheduleRepeating(10);
	}

}
