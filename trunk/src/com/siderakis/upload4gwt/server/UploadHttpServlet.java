package com.siderakis.upload4gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.siderakis.upload4gwt.server.dao.UploadStatusDAO;
import com.siderakis.upload4gwt.shared.UploadStatus;

@Singleton
public abstract class UploadHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UploadHttpServlet.class.getName());

	@Inject
	private Provider<UploadListener> progressListenerProvider;
	@Inject
	private UploadStatusDAO uploadStatusDAO;

	public abstract void doUpload(final HttpServletRequest req, final HttpServletResponse res, FileItemIterator iterator)
			throws ServletException, IOException;

	int maxSize = -1;

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public void doPost(final HttpServletRequest req, final HttpServletResponse res) throws ServletException,
			IOException {
		log.warning("Getting a post");
		try {
			final ServletFileUpload upload = new ServletFileUpload();
			if (maxSize != -1) {
				upload.setSizeMax(maxSize);
			}
			res.setContentType("text/plain");

			// Create a progress listener
			final UploadListener progressListener = progressListenerProvider.get();
			final String uploadId = req.getParameter("uploadId");
			log.warning("uploadId: " + uploadId);

			progressListener.setUploadId(uploadId);

			upload.setProgressListener(progressListener);

			try {
				final FileItemIterator iterator = upload.getItemIterator(req);
				doUpload(req, res, iterator);
				logFiles(req, upload);

			} catch (final SizeLimitExceededException e) {
				final String message = "You exceeded the maximu size (" + e.getPermittedSize() + ") of the file ("
						+ e.getActualSize() + ")";
				final UploadStatus status = new UploadStatus(uploadId, UploadStatus.Errors.SizeLimitExceededException,
						message);
				uploadStatusDAO.setUploadStatus(status);
			}
		} catch (final Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void logFiles(final HttpServletRequest req, final ServletFileUpload upload) throws FileUploadException,
			IOException {
		final FileItemIterator logIterator = upload.getItemIterator(req);
		while (logIterator.hasNext()) {
			final FileItemStream item = logIterator.next();

			if (item.isFormField()) {
				log.warning("Got a form field: " + item.getFieldName());
			} else {

				final InputStream in = item.openStream();
				final String fieldName = item.getFieldName();
				final String fileName = item.getName();
				final String contentType = item.getContentType();

				log.warning("Got an uploaded file: " + fieldName + ", name = " + fileName + ", contentType = "
						+ contentType);
				try {
					final String output = IOUtils.toString(in);
					log.warning(output);
				} finally {
					IOUtils.closeQuietly(in);
				}
			}

		}
	}
}
