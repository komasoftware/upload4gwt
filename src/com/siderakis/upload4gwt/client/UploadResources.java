package com.siderakis.upload4gwt.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface UploadResources extends ClientBundle {

	public interface Style extends CssResource {
		String styledUploader();

	}

	@Source("Style.css")
	Style style();
}
