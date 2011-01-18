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
	private Element dropTarget;

	public UploadDropTarget() {
		uploadDropImpl = GWT.create(UploadDropImpl.class);
	}

	private void addListeners(final Element dropTarget) {
		addListeners(dropTarget, this);
	}

	private final native void addListeners(Element dropTarget, UploadDropTarget thsi)/*-{
		dropTarget.addEventListener("dragover", function(event){
		event.stopPropagation();
		event.preventDefault();
		thsi.@com.siderakis.upload4gwt.client.dnd.UploadDropTarget::dragover()();
		}, true);
		dropTarget.addEventListener("dragleave", function(event){
		event.stopPropagation();
		event.preventDefault();
		thsi.@com.siderakis.upload4gwt.client.dnd.UploadDropTarget::dragleave()();
		}, true);
		dropTarget.addEventListener("drop",function(event){
		event.preventDefault();
		thsi.@com.siderakis.upload4gwt.client.dnd.UploadDropTarget::drop()();
		} , true);
	}-*/;

	private void dragleave() {
		dropTarget.getStyle().clearBackgroundColor();

	}

	private void dragover() {
		dropTarget.getStyle().setBackgroundColor("green");

	}

	private void drop() {
		GWT.log("drop: " + key);
		progressSyncer.start(key);
	}

	public void uploader(final Element dropTarget, final String destinationURL, final HasProgress progressBar) {
		this.dropTarget = dropTarget;
		// since files are upload sequentially the same upload id can be used.
		// TODO there needs to be a way to display multiple progress bars....
		// HMM
		progressSyncer.getNextId(new AsyncCallback<String>() {

			@Override public void onFailure(final Throwable caught) {
			}

			@Override public void onSuccess(final String result) {
				GWT.log("received new uploadId: " + result);
				key = result;
				final String destinationURLWithId = destinationURL + (destinationURL.contains("?") ? "&" : "?")
				+ "uploadId=" + result;
				addListeners(dropTarget);
				uploadDropImpl.uploader(dropTarget, destinationURLWithId);
				ProgressSyncer.getInstance().addStatusDisplay(result, progressBar);
			}

		});
	}

}
