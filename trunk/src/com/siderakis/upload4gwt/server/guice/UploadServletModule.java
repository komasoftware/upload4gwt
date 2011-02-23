package com.siderakis.upload4gwt.server.guice;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.siderakis.upload4gwt.server.UploadHttpServlet;
import com.siderakis.upload4gwt.server.UploadServiceImpl;
import com.siderakis.upload4gwt.server.dao.UploadStatusDAO;
import com.siderakis.upload4gwt.server.dao.UploadStatusDAOAppEngineImpl;

public class UploadServletModule extends ServletModule {
	@Override
	public void configureServlets() {
		serve("*/uploadService").with(UploadServiceImpl.class);
		serve("*/upload").with(UploadHttpServlet.class);

		bind(UploadStatusDAO.class).to(UploadStatusDAOAppEngineImpl.class).in(Singleton.class);
	}

}