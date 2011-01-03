package com.siderakis.upload4gwt.server;

import java.io.Serializable;
import java.util.logging.Logger;

import org.apache.commons.fileupload.ProgressListener;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class AppengineListener implements ProgressListener, Serializable {

	private static final long serialVersionUID = 1L;

	private Long bytesRead = 0L, contentLength = 0L;

	private final Integer sleepMilliseconds = 1000;
	private static final Logger log = Logger.getLogger(AppengineListener.class.getName());

	public Integer getPercentage() {
		log.info("pContentLength:bytesRead::" + contentLength + ":" + bytesRead);
		return contentLength.equals(0L) ? 0 : Math.round(bytesRead * 100 / contentLength);

	}

	@Override
	public void update(final long bytesRead, final long contentLength, final int items) {

		this.bytesRead = bytesRead;
		this.contentLength = contentLength;
		log.info("We are currently reading item " + items);
		if (contentLength == -1) {
			log.info("So far, " + bytesRead + " bytes have been read.");
		} else {
			log.info("So far, " + bytesRead + " of " + contentLength + " bytes have been read.");
		}
		if (sleepMilliseconds > 0 && bytesRead < contentLength) {
			try {
				Thread.sleep(sleepMilliseconds);
			} catch (Exception e) {
			}
		}

	}
}
