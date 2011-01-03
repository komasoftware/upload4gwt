package com.siderakis.upload4gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("uploadService")
public interface UploadService extends RemoteService {
	String getPercentage(String name) throws IllegalArgumentException;
}
