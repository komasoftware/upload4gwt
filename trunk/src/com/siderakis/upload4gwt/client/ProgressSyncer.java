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
					for (UploadStatus us : result) {
						HasProgress statusDisaply = statusDisplays.get(us.getId());
						statusDisaply.setProgress(us.getPercentage());
					}
				}

				@Override
				public void onFailure(Throwable caught) {

				}
			});
		}
	};

	private List<Long> getInProgressIds() {
		return new ArrayList<Long>(statusDisplays.keySet());
	}

	private final Map<Long, HasProgress> statusDisplays = new HashMap<Long, HasProgress>();

	public void addStatusDisplay(Long id, HasProgress statusDisplay) {
		statusDisplays.put(id, statusDisplay);
	}

	public void stop() {
		timer.cancel();
	}

	public void start() {
		timer.scheduleRepeating(100);
	}

	private Long nextId = 0L;

	public Long getNextId() {
		return nextId++;
	}

}
