package com.siderakis.upload4gwt.server.dao;

import com.google.inject.Singleton;
import com.siderakis.upload4gwt.shared.UploadStatus;

/** Uses the low level App Engine Datastore API, and Memcache */
@Singleton
public class UploadStatusDAOAppEngineImpl implements UploadStatusDAO {

	@Override
	public String getBaseUploadId() {
		return null;
	}

	@Override
	public void setUploadStatus(UploadStatus uploadStatus) {
	}

	@Override
	public UploadStatus getUploadStatus(String key) {
		return null;
	}
}
