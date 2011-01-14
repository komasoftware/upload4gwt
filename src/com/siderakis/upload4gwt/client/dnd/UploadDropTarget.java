package com.siderakis.upload4gwt.client.dnd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.siderakis.upload4gwt.client.HasProgress;
import com.siderakis.upload4gwt.client.ProgressSyncer;

public class UploadDropTarget {

	private final IsUploadDropTarget uploadDropImpl;
	private final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();
	private String key = null;

	public UploadDropTarget() {
		uploadDropImpl = GWT.create(UploadDropImpl.class);

	}

	// private void upload(final File file) {
	// uploadDropImpl.upload(file);
	// }

	void start() {
		progressSyncer.start(key);
	}

	private final native void startTimer(Element place)/*-{
		place.addEventListener("drop", 	this.@com.siderakis.upload4gwt.client.dnd.UploadDropTarget::start()(), false);
	}-*/;

	public void uploader(final Element place, final String targetURL) {
		// since files are upload sequentially the same upload id can be used.
		// TODO there needs to be a way to display multiple progress bars....
		// HMM
		progressSyncer.getNextId(new AsyncCallback<String>() {

			@Override public void onFailure(final Throwable caught) {
			}

			@Override public void onSuccess(final String result) {
				key = result;
				final String temp = targetURL + (targetURL.contains("?") ? "&" : "?") + "uploadId=" + result;
				uploadDropImpl.uploader(place, temp);
				startTimer(place);
			}
		});
	}

	// private final native void uploaderImpl(Element place, String
	// targetURL)/*-{
	// // Function drop file
	// dropF = function(event){
	// event.preventDefault();
	// var dt = event.dataTransfer;
	// var files = dt.files;
	// for (var i = 0; i < files.length; i++) {
	// var file = files[i];
	// this.@com.siderakis.upload4gwt.client.dnd.UploadDrop::upload(Lcom/siderakis/upload4gwt/client/ui/File;)(file);
	// }
	// }
	//
	// // The inclusion of the event listeners (DragOver and drop)
	//
	// place.addEventListener("dragover", function(event){
	// event.stopPropagation();
	// event.preventDefault();
	// }, true);
	// place.addEventListener("drop", dropF, false);
	// }-*/;

	public void uploader(final Element place, final String targetURL, final HasProgress progressBar) {
		uploader(place, targetURL);
		ProgressSyncer.getInstance().addStatusDisplay(targetURL, progressBar);
	}

}
