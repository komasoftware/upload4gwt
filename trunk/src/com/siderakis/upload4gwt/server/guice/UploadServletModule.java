package com.siderakis.upload4gwt.server.guice;

import com.google.inject.servlet.ServletModule;
import com.siderakis.upload4gwt.server.AppengineUpload;
import com.siderakis.upload4gwt.server.UploadServiceImpl;

public class UploadServletModule extends ServletModule {
	@Override
	public void configureServlets() {
		serve("/upload4gwt/uploadService").with(UploadServiceImpl.class);
		serve("/upload4gwt/upload").with(AppengineUpload.class);
	}

}