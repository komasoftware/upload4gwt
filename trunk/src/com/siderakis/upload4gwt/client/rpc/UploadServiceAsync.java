package com.siderakis.upload4gwt.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.siderakis.upload4gwt.shared.UploadStatus;

public interface UploadServiceAsync {
	void getUploadStatus(List<String> uploadIds, AsyncCallback<List<UploadStatus>> callback)
			throws IllegalArgumentException;

	void getUploadIdPrefix(AsyncCallback<String> callback);
}
