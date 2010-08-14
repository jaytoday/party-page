package com.partypage.server.guice;

import com.allen_sauer.gwt.log.server.RemoteLoggerServiceImpl;
import com.partypage.server.servlet.AppDispatchServlet;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {

	@Override
	public void configureServlets() {
		// NOTE: the servlet context will probably need changing
		serve("/bitmenugwtapp/dispatch").with(
				AppDispatchServlet.class);

		serve("/*/gwt-log").with(RemoteLoggerServiceImpl.class);
		bind(RemoteLoggerServiceImpl.class).in(Singleton.class);
	}

}
