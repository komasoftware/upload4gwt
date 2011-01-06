package com.siderakis.upload4gwt.server.dao;

import java.util.HashMap;
import java.util.Map;

import com.siderakis.upload4gwt.shared.UploadStatus;

public class UploadStatusDAOInMemoryImpl implements UploadStatusDAO {

	private final Map<String, UploadStatus> uploadStatusMap = new HashMap<String, UploadStatus>();
	private Long id = 0L;

	@Override
	public String getBaseUploadId() {
		return id++ + "";
	}

	@Override
	public void setUploadStatus(UploadStatus uploadStatus) {
		uploadStatusMap.put(uploadStatus.getName(), uploadStatus);
	}

	@Override
	public UploadStatus getUploadStatus(String key) {
		return uploadStatusMap.get(key);
	}
}
