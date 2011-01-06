package com.siderakis.upload4gwt.server.dao;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.siderakis.upload4gwt.shared.UploadStatus;

public class UploadStatusDAOSessionImpl implements UploadStatusDAO {

	@Inject
	private Provider<HttpSession> sessionProvider;

	@Override
	public String getBaseUploadId() {

		Long nextUploadId = 0L;
		while (isIdUsed(nextUploadId)) {
			nextUploadId += INITIAL_UPLOAD_ID_INCREMENT;
		}

		return nextUploadId + "";
	}

	private final static Long INITIAL_UPLOAD_ID_INCREMENT = 1000L;

	@Override
	public void setUploadStatus(UploadStatus uploadStatus) {
		sessionProvider.get().setAttribute(uploadStatus.getName(), uploadStatus);

	}

	@Override
	public UploadStatus getUploadStatus(String id) {
		return (UploadStatus) sessionProvider.get().getAttribute(id);
	}

	private Boolean isIdUsed(Long nextUploadId) {
		return sessionProvider.get().getAttribute(nextUploadId + "") != null;
	}
}
