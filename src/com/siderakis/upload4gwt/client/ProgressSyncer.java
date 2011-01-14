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

	public static final ProgressSyncer getInstance() {
		return INSTANCE;
	}

	private final UploadServiceAsync uploadService = GWT.create(UploadService.class);

	private final Timer timer = new Timer() {
		@Override
		public void run() {
			uploadService.getUploadStatus(getInProgressIds(), new AsyncCallback<List<UploadStatus>>() {

				@Override
				public void onFailure(final Throwable caught) {

				}

				@Override
				public void onSuccess(final List<UploadStatus> result) {
					Boolean allDone = true;
					for (final UploadStatus us : result) {
						final HasProgress statusDisaply = statusDisplays.get(us.getName());
						statusDisaply.setProgress(us);
						if (!us.isFinished()) {
							allDone = false;
						}
					}

					if (allDone) {
						stop();
					}
				}
			});
		}
	};

	private final Map<String, HasProgress> statusDisplays = new HashMap<String, HasProgress>();

	private String baseId = null;

	private Integer nextId = 0;

	private ProgressSyncer() {
	}

	public void addStatusDisplay(final String id, final HasProgress statusDisplay) {
		statusDisplays.put(id, statusDisplay);
	}

	private List<String> getInProgressIds() {
		final List<String> result = new ArrayList<String>();

		for (final String display : statusDisplays.keySet()) {
			if (statusDisplays.get(display) == null || statusDisplays.get(display).getProgress() == null) {
			} else if (!statusDisplays.get(display).getProgress().isFinished()) {
				result.add(display);
			}
		}
		return result;
	}

	public void getNextId(final AsyncCallback<String> callback) {
		if (baseId != null) {
			callback.onSuccess(baseId + nextId++);
		} else {
			uploadService.getUploadIdPrefix(new AsyncCallback<String>() {

				@Override
				public void onFailure(final Throwable caught) {
				}

				@Override
				public void onSuccess(final String result) {
					baseId = result;
					callback.onSuccess(baseId + nextId++);
				}
			});
		}
	}
	public void start() {
		timer.scheduleRepeating(500);
	}

	public void start(final String uploadId) {
		if (statusDisplays.get(uploadId) != null) {
			statusDisplays.get(uploadId).setProgress(new UploadStatus(uploadId, 0L, 0L));
		}
		start();
	}

	public void stop() {
		timer.cancel();
	}

}
