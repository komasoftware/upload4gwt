package com.siderakis.upload4gwt.client;

/**
 * 
 */

import com.google.gwt.core.client.JavaScriptObject;

public class File extends JavaScriptObject {

	protected File() {
	}

	public final native String getFileName() /*-{
		return this.fileName;
	}-*/;

	public final native int getFileSize() /*-{
		return this.fileSize;
	}-*/;
}