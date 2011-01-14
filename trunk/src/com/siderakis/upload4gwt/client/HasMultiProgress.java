package com.siderakis.upload4gwt.client;

import com.siderakis.upload4gwt.shared.UploadStatus;

public interface HasMultiProgress {
	UploadStatus getProgress(String key);

	void setProgress(String key, UploadStatus uploadStatus);
}
