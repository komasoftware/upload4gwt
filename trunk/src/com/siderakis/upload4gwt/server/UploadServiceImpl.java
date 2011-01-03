package com.siderakis.upload4gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.siderakis.upload4gwt.client.UploadService;

@SuppressWarnings("serial")
@Singleton
public class UploadServiceImpl extends RemoteServiceServlet implements
		UploadService {

	@Inject
	private Provider<AppengineListener> progressListenerProvider;

	@Override
	public String getPercentage(String input) throws IllegalArgumentException {

		AppengineListener listener = progressListenerProvider.get();
		return listener.getPercentage() + "";
	}
}
