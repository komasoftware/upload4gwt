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
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

			// Create a progress listener
			final ProgressListener progressListener = new AppengineListener();
			upload.setProgressListener(progressListener);

			res.setContentType("text/plain");

			final FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
				final FileItemStream item = iterator.next();
				final InputStream stream = item.openStream();

				if (item.isFormField()) {
					log.warning("Got a form field: " + item.getFieldName());
				} else {
					log.warning("Got an uploaded file: " + item.getFieldName()
							+ ", name = " + item.getName());

					// You now have the filename (item.getName() and the
					// contents (which you can read from stream). Here we just
					// print them back out to the servlet output stream, but you
					// will probably want to do something more interesting (for
					// example, wrap them in a Blob and commit them to the
					// datastore).
					int len;
					final byte[] buffer = new byte[8192];
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						res.getOutputStream().write(buffer, 0, len);
					}
				}
			}
		} catch (final Exception ex) {
			throw new ServletException(ex);
		}
	}
}