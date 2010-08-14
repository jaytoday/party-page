package com.partypage.server.guice;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {

	private ServletContext context;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		context = null;
		super.contextDestroyed(servletContextEvent);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		context = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServerModule(),
				new DispatchServletModule());
		// return Guice.createInjector(new
		// GuiceServerModule(context.getRealPath("/")),new
		// DispatchServletModule());
	}

}
