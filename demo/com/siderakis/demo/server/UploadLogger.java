package com.siderakis.demo.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;

import com.google.inject.Singleton;
import com.siderakis.upload4gwt.server.UploadHttpServlet;

@Singleton public class UploadLogger extends UploadHttpServlet {
	private static final Logger log = Logger.getLogger(UploadLogger.class.getName());

	public UploadLogger() {
		super();
		setLogFormFields(false);

	}

	@Override public void doUpload(final HttpServletRequest req, final HttpServletResponse res,
			final FileItemIterator iterator) throws ServletException, IOException, FileUploadException {

		while (iterator.hasNext()) {
			final FileItemStream item = iterator.next();

			if (item.isFormField()) {
				log.warning("Got a form field: " + item.getFieldName());
			} else {

				final String fieldName = item.getFieldName();
				final String fileName = item.getName();
				final String contentType = item.getContentType();

				log.warning("Got an uploaded file: " + fieldName + ", name = " + fileName + ", contentType = "
						+ contentType);

			}

		}
	}
}
