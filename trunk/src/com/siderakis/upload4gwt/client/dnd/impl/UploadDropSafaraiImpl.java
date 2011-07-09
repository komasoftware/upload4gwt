package com.siderakis.upload4gwt.client.dnd.impl;

import com.google.gwt.user.client.Element;
import com.siderakis.upload4gwt.client.dnd.IsUploadDropTarget;

//based on http://code.google.com/p/html5uploader/
class UploadDropSafaraiImpl implements IsUploadDropTarget {

	@Override public void uploader(final Element place, final String targetURL) {
		uploaderImpl(place, targetURL);
	}

	private final native void uploaderImpl(Element place, String targetURL) /*-{
		// Safari 5 does not support FileReader
		upload = function(file){
		xhr = new XMLHttpRequest();
		xhr.open('POST', targetURL + '?', true);
		xhr.setRequestHeader('UP-FILENAME', file.name);
		xhr.setRequestHeader('UP-SIZE', file.size);
		xhr.setRequestHeader('UP-TYPE', file.type);
		xhr.send(file);
		}

		// Function drop file
		dropF = function(event){
		event.preventDefault();
		var dt = event.dataTransfer;
		var files = dt.files;
		for (var i = 0; i < files.length; i++) {
		var file = files[i];
		upload(file);
		}
		}

		// The inclusion of the event listeners (DragOver and drop)
		place.addEventListener("dragover", function(event){
		event.stopPropagation();
		event.preventDefault();
		}, true);
		place.addEventListener("drop", dropF, false);
	}-*/;
}
