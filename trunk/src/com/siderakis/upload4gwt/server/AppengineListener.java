package com.siderakis.upload4gwt.server;

import org.apache.commons.fileupload.ProgressListener;

public class AppengineListener implements ProgressListener {
	private long megaBytes = -1;

	@Override
	public void update(final long BytesRead, final long pContentLength,
			final int pItems) {
		final long mBytes = BytesRead / 1000000;
		if (megaBytes == mBytes) {
			return;
		}
		megaBytes = mBytes;
		System.out.println("We are currently reading item " + pItems);
		if (pContentLength == -1) {
			System.out.println("So far, " + BytesRead
					+ " bytes have been read.");
		} else {
			System.out.println("So far, " + BytesRead + " of " + pContentLength
					+ " bytes have been read.");
		}
	}
}
