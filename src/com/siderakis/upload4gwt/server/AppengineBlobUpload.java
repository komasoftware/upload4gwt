package com.siderakis.upload4gwt.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class AppengineBlobUpload extends HttpServlet {
	private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private static final long serialVersionUID = 1L;

	@Override public void doPost(final HttpServletRequest req, final HttpServletResponse res) throws ServletException,
	IOException {

		// TODO use Files API
		// http://code.google.com/appengine/docs/java/blobstore/overview.html#Writing_Files_to_the_Blobstore
		final Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		final BlobKey blobKey = blobs.get("myFile");

		if (blobKey == null) {
			res.sendRedirect("/");
		} else {
			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}
	}
}
