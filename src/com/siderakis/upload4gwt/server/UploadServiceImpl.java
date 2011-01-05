package com.siderakis.upload4gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.siderakis.upload4gwt.client.rpc.UploadService;
import com.siderakis.upload4gwt.shared.UploadStatus;

@SuppressWarnings("serial")
@Singleton
public class UploadServiceImpl extends RemoteServiceServlet implements UploadService {
	@Inject
	private Provider<HttpSession> sessionProvider;
	private static final Logger log = Logger.getLogger(UploadServiceImpl.class.getName());

	@Override
	public List<UploadStatus> getUploadStatus(List<Long> uploadIds) throws IllegalArgumentException {
		List<UploadStatus> uploadStatus = new ArrayList<UploadStatus>();
		for (Long id : uploadIds) {

			log.warning("getting uploadstatus for id: " + id);

			UploadStatus status = (UploadStatus) sessionProvider.get().getAttribute(id + "");

			log.warning("uploadstatus is: " + (status == null ? "null" : status.toString()));
			status = (UploadStatus) getThreadLocalRequest().getSession().getAttribute(id + "");

			log.warning("uploadstatus is: " + (status == null ? "null" : status.toString()));
			if (status != null) {
				uploadStatus.add(status);
			} else {
				uploadStatus.add(new UploadStatus(id, 0L, 0L));
			}
		}
		return uploadStatus;
	}
}
