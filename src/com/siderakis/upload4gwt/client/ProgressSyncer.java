package com.siderakis.upload4gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProgressSyncer {

	private final UploadServiceAsync uploadService = GWT.create(UploadService.class);
	private final Timer timer = new Timer() {

		@Override
		public void run() {
			uploadService.getPercentage("", new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					hasProgress.setProgress(Integer.parseInt(result));
				}

				@Override
				public void onFailure(Throwable caught) {

				}
			});
		}
	};
	private final HasProgress hasProgress;

	public ProgressSyncer(HasProgress hasProgress) {
		this.hasProgress = hasProgress;
	}

	public void stop() {
		timer.cancel();
	}

	public void start() {
		timer.scheduleRepeating(10);
	}

}
