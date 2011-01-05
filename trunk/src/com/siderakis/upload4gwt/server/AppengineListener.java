package com.siderakis.upload4gwt.server;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;
import com.siderakis.upload4gwt.shared.UploadStatus;

@RequestScoped
public class AppengineListener implements ProgressListener, Serializable {

	private static final long serialVersionUID = 1L;

	private final Integer sleepMilliseconds = 1000;
	private static final Logger log = Logger.getLogger(AppengineListener.class.getName());

	private Long uploadId;

	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}

	@Inject
	private Provider<HttpSession> sessionProvider;

	@Override
	public void update(final long bytesRead, final long contentLength, final int items) {
		final UploadStatus status = new UploadStatus(uploadId, bytesRead, contentLength);
		sessionProvider.get().setAttribute(status.getName(), status);
		log.warning("putting in session: " + status.toString());
		log.warning("We are currently reading item " + items);
		if (contentLength == -1) {
			log.warning("So far, " + bytesRead + " bytes have been read.");
		} else {
			log.warning("So far, " + bytesRead + " of " + contentLength + " bytes have been read.");
		}

		if (sleepMilliseconds > 0 && bytesRead < contentLength) {
			try {
				Thread.sleep(sleepMilliseconds);
			} catch (final Exception e) {
			}
		}

	}
}
