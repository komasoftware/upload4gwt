package com.siderakis.upload4gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UploadServiceAsync {
	void getPercentage(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
