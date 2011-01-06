package com.siderakis.upload4gwt.server.dao;

import com.google.inject.ImplementedBy;
import com.siderakis.upload4gwt.shared.UploadStatus;

/**
 * 
 * The DAO implementation must be aware if the deployment environment is
 * distributed.
 * 
 * */
@ImplementedBy(UploadStatusDAOSessionImpl.class)
public interface UploadStatusDAO {

	/**
	 * Returns a unique string that is the base of the upload id. For each
	 * upload form in an application instance a distinct integer will be
	 * appended to this string.
	 */
	String getBaseUploadId();

	/** Returns the upload status for a given key. */
	UploadStatus getUploadStatus(String key);

	/** Saves the upload status for a given key */
	void setUploadStatus(UploadStatus uploadStatus);
}
