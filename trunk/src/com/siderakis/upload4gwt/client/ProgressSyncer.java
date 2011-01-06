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
						HasProgress statusDisaply = statusDisplays.get(us.getId());
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

	private List<Long> getInProgressIds() {
		List<Long> result = new ArrayList<Long>();

		for (Long display : statusDisplays.keySet()) {
			if (statusDisplays.get(display) == null || statusDisplays.get(display).getProgress() == null) {
			} else if (!statusDisplays.get(display).getProgress().isFinished()) {
				result.add(display);
			}
		}
		return result;
	}

	private final Map<Long, HasProgress> statusDisplays = new HashMap<Long, HasProgress>();

	public void addStatusDisplay(Long id, HasProgress statusDisplay) {
		statusDisplays.put(id, statusDisplay);
	}

	public void stop() {
		timer.cancel();
	}

	public void start() {
		timer.scheduleRepeating(500);
	}

	private Long nextId = 0L;

	public Long getNextId() {
		return nextId++;
	}

	public void start(Long uploadId) {
		if (statusDisplays.get(uploadId) != null) {
			statusDisplays.get(uploadId).setProgress(new UploadStatus(uploadId, 0L, 0L));
		}
		start();
	}

}
