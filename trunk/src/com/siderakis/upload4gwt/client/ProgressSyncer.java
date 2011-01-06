package com.siderakis.upload4gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.siderakis.upload4gwt.client.rpc.UploadService;
import com.siderakis.upload4gwt.client.rpc.UploadServiceAsync;
import com.siderakis.upload4gwt.shared.UploadStatus;

public class ProgressSyncer {
	private static final ProgressSyncer INSTANCE = new ProgressSyncer();

	private ProgressSyncer() {
	}

	public static final ProgressSyncer getInstance() {
		return INSTANCE;
	}

	private final UploadServiceAsync uploadService = GWT.create(UploadService.class);

	private final Timer timer = new Timer() {
		@Override
		public void run() {
			uploadService.getUploadStatus(getInProgressIds(), new AsyncCallback<List<UploadStatus>>() {

				@Override
				public void onSuccess(List<UploadStatus> result) {
					Boolean allDone = true;
					for (UploadStatus us : result) {
						HasProgress statusDisaply = statusDisplays.get(us.getName());
						statusDisaply.setProgress(us);
						if (!us.isFinished()) {
							allDone = false;
						}
					}

					if (allDone) {
						stop();
					}
				}

				@Override
				public void onFailure(Throwable caught) {

				}
			});
		}
	};

	private List<String> getInProgressIds() {
		List<String> result = new ArrayList<String>();

		for (String display : statusDisplays.keySet()) {
			if (statusDisplays.get(display) == null || statusDisplays.get(display).getProgress() == null) {
			} else if (!statusDisplays.get(display).getProgress().isFinished()) {
				result.add(display);
			}
		}
		return result;
	}

	private final Map<String, HasProgress> statusDisplays = new HashMap<String, HasProgress>();

	public void addStatusDisplay(String id, HasProgress statusDisplay) {
		statusDisplays.put(id, statusDisplay);
	}

	public void stop() {
		timer.cancel();
	}

	public void start() {
		timer.scheduleRepeating(500);
	}

	private String baseId = null;
	private Integer nextId = 0;

	public void getNextId(final AsyncCallback<String> callback) {
		if (baseId != null) {
			callback.onSuccess(baseId + nextId++);
		} else {
			uploadService.getUploadIdPrefix(new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					baseId = result;
					callback.onSuccess(baseId + nextId++);
				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}

	public void start(String uploadId) {
		if (statusDisplays.get(uploadId) != null) {
			statusDisplays.get(uploadId).setProgress(new UploadStatus(uploadId, 0L, 0L));
		}
		start();
	}

}
