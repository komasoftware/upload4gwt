package com.siderakis.upload4gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

public class AppengineUpload extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AppengineUpload.class
			.getName());

	@Override
	public void doPost(final HttpServletRequest req,
			final HttpServletResponse res) throws ServletException, IOException {

		try {
			final ServletFileUpload upload = new ServletFileUpload();
			upload.setSizeMax(50000);
			res.setContentType("text/plain");

			// Create a progress listener
			final ProgressListener progressListener = new AppengineListener();
			upload.setProgressListener(progressListener);

			try {
				final FileItemIterator iterator = upload.getItemIterator(req);

				while (iterator.hasNext()) {
					final FileItemStream item = iterator.next();
					final InputStream in = item.openStream();

					if (item.isFormField()) {
						log.warning("Got a form field: " + item.getFieldName());
					} else {

						String fieldName = item.getFieldName();
						String fileName = item.getName();
						String contentType = item.getContentType();

						log.warning("Got an uploaded file: " + fieldName
								+ ", name = " + fileName + ", contentType = "
								+ contentType);
						try {
							String output = IOUtils.toString(in);
							log.warning(output);
						} finally {
							IOUtils.closeQuietly(in);
						}
					}

				}

			} catch (SizeLimitExceededException e) {
				PrintWriter out = res.getWriter();
				out.println("You exceeded the maximu size ("
						+ e.getPermittedSize() + ") of the file ("
						+ e.getActualSize() + ")");
			}
		} catch (final Exception ex) {
			throw new ServletException(ex);
		}
	}
}