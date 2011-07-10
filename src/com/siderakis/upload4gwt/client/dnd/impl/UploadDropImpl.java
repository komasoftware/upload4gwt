package com.siderakis.upload4gwt.client.dnd.impl;

import com.google.gwt.user.client.Element;
import com.siderakis.upload4gwt.client.dnd.UploadDropTarget;

class UploadDropImpl implements UploadDropTarget {

	@Override public void uploader(final Element place, final String targetURL) {
		uploaderImpl(place, targetURL);
	}

	private final native void uploaderImpl(Element place, String targetURL)/*-{
		place.innerHTML = 'DND Upload not supported!';
	}-*/;
}
