package com.siderakis.upload4gwt.server.dao;

import com.google.inject.ImplementedBy;
import com.siderakis.upload4gwt.shared.UploadStatus;

//@ImplementedBy(UploadStatusDAOSessionImpl.class)
@ImplementedBy(UploadStatusDAOInMemoryImpl.class)
public interface UploadStatusDAO {
	String getBaseUploadId();

	UploadStatus getUploadStatus(String key);

	void setUploadStatus(UploadStatus uploadStatus);
}
