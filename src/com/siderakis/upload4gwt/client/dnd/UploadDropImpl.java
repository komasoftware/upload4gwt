package com.siderakis.upload4gwt.client.dnd;

import com.google.gwt.user.client.Element;

class UploadDropImpl implements IsUploadDropTarget {

	@Override public void uploader(final Element place, final String targetURL) {
		uploaderImpl(place, targetURL);
	}

	private final native void uploaderImpl(Element place, String targetURL)/*-{
		place.innerHTML = 'DND Upload not supported!';
	}-*/;
}
