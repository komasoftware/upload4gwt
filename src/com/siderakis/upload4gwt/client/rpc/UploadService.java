package com.siderakis.upload4gwt.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.siderakis.upload4gwt.shared.UploadStatus;

@RemoteServiceRelativePath("uploadService")
public interface UploadService extends RemoteService {
	List<UploadStatus> getUploadStatus(List<String> uploadIds) throws IllegalArgumentException;

	String getUploadIdPrefix();
}
