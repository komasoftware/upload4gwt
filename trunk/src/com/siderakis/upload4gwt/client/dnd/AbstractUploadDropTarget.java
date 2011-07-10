package com.siderakis.upload4gwt.client.dnd;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.siderakis.upload4gwt.client.HasProgress;
import com.siderakis.upload4gwt.client.ProgressSyncer;

public abstract class AbstractUploadDropTarget {

	private final UploadDropTarget uploadDropImpl;
	private final ProgressSyncer progressSyncer = ProgressSyncer.getInstance();
	private String key = null;
	protected Element dropTarget;
	private final String destinationURL;

	public AbstractUploadDropTarget(final String destinationURL) {
		this.destinationURL = destinationURL;
		uploadDropImpl = GWT.create(UploadDropTarget.class);
	}

	private void addListeners(final Element dropTarget) {
		addListeners(dropTarget, this);
	}

	private final native void addListeners(Element dropTarget, AbstractUploadDropTarget thsi)/*-{
		dropTarget
				.addEventListener(
						"dragover",
						function(event) {
							event.stopPropagation();
							event.preventDefault();
							thsi.@com.siderakis.upload4gwt.client.dnd.AbstractUploadDropTarget::dragover()();
						}, true);
		dropTarget
				.addEventListener(
						"dragleave",
						function(event) {
							event.stopPropagation();
							event.preventDefault();
							thsi.@com.siderakis.upload4gwt.client.dnd.AbstractUploadDropTarget::dragleave()();
						}, true);
		dropTarget
				.addEventListener(
						"drop",
						function(event) {
							event.preventDefault();
							thsi.@com.siderakis.upload4gwt.client.dnd.AbstractUploadDropTarget::drop()();
						}, true);
	}-*/;

	protected abstract void dragdrop();

	protected abstract void dragleave();

	protected abstract void dragover();

	private void drop() {
		dragdrop();
		GWT.log("drop: " + key);
		progressSyncer.start(key);
	}

	/** Registers the progressBar to the dropTarget */
	public void register(final Widget dropTargetWidget, final HasProgress progressBar) {

		this.dropTarget = dropTargetWidget.getElement();
		// since files are upload sequentially the same upload id can be used.
		// TODO there needs to be a way to display multiple progress bars....
		// HMM
		progressSyncer.getNextId(new AsyncCallback<String>() {

			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final String result) {
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
