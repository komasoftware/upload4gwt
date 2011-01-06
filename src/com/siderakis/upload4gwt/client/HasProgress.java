package com.siderakis.upload4gwt.client;

import com.siderakis.upload4gwt.shared.UploadStatus;

public interface HasProgress {
	void setProgress(UploadStatus uploadStatus);

	UploadStatus getProgress();
}
