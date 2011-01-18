package com.siderakis.upload4gwt.client.dnd;

import com.google.gwt.user.client.Element;

//based on http://code.google.com/p/html5uploader/
class UploadDropChromeImpl implements IsUploadDropTarget {

	@Override public void uploader(final Element place, final String targetURL) {
		uploaderImpl(place, targetURL);
	}

	// Firefox 3.6, Chrome 6, WebKit
	private final native void uploaderImpl(Element place, String targetURL) /*-{
		upload = function(file){
		this.loadEnd = function(){
		bin = reader.result;
		xhr = new XMLHttpRequest();
		xhr.open('POST', targetURL, true);
		var boundary = 'xxxxxxxxx';
		var body = '--' + boundary + "\r\n";
		body += "Content-Disposition: form-data; name='upload'; filename='" +
		file.name +
		"'\r\n";
		body += "Content-Type: application/octet-stream\r\n\r\n";
		body += bin + "\r\n";
		body += '--' + boundary + '--';
		xhr.setRequestHeader('content-type', 'multipart/form-data; boundary=' + boundary);

		if (xhr.sendAsBinary != null) {
		// Firefox 3.6 provides a feature sendAsBinary ()
		xhr.sendAsBinary(body);
		}
		else {
		// Chrome 7 sends data but you must use the base64_decode on the
		// server side
		xhr.open('POST', targetURL + '?&base64=true', true);
		xhr.setRequestHeader('UP-FILENAME', file.name);
		xhr.setRequestHeader('UP-SIZE', file.size);
		xhr.setRequestHeader('UP-TYPE', file.type);
		xhr.send(window.btoa(bin));
		}

		}

		reader = new FileReader();
		if (reader.addEventListener) {
		// Firefox 3.6, WebKit
		reader.addEventListener('loadend', this.loadEnd, false);
		}
		else {
		//Chrome
		reader.onloadend = this.loadEnd;
		}

		// The function that starts reading the file as a binary string
		reader.readAsBinaryString(file);

		}

		// Function drop file
		dropF = function(event){
		event.preventDefault();
		var files = event.dataTransfer.files;
		for (var i = 0; i < files.length; i++) {
		upload(files[i]);
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
