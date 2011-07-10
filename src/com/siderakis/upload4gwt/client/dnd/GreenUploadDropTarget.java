package com.siderakis.upload4gwt.client.dnd;


public class GreenUploadDropTarget extends AbstractUploadDropTarget {

	public GreenUploadDropTarget(final String destinationURL) {
		super(destinationURL);
	}

	@Override
	protected void dragdrop() {

	}

	@Override
	protected void dragleave() {
		dropTarget.getStyle().clearBackgroundColor();

	}

	@Override
	protected void dragover() {
		dropTarget.getStyle().setBackgroundColor("green");

	}

}
