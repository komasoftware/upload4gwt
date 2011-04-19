package com.siderakis.demo.server.guice;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.siderakis.demo.server.UploadLogger;
import com.siderakis.upload4gwt.server.UploadServiceImpl;
import com.siderakis.upload4gwt.server.dao.UploadStatusDAO;
import com.siderakis.upload4gwt.server.dao.UploadStatusDAOAppEngineImpl;

public class UploadServletModule extends ServletModule {
	@Override
	public void configureServlets() {
		serve("*/uploadService").with(UploadServiceImpl.class);
		serve("*/upload").with(UploadLogger.class);

		bind(UploadStatusDAO.class).to(UploadStatusDAOAppEngineImpl.class).in(Singleton.class);
	}

}