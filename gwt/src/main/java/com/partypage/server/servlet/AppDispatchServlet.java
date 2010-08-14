package com.partypage.server.servlet;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AppDispatchServlet extends GuiceStandardDispatchServlet {

	private static final long serialVersionUID = 1L;

	private Log logger;

	@Inject
	public AppDispatchServlet(Dispatch dispatch, Log logger) {
		super(dispatch);
		this.logger = logger;
	}

	@Override
	public Result execute(Action<?> action) throws ActionException {
		try {
			logger.info("AppDispatchServlet: executing: "
					+ action.getClass().getName().replaceAll("^.*\\.", ""));

			Result res = super.execute(action);

			logger.info("AppDispatchServlet: finished: "
					+ action.getClass().getName().replaceAll("^.*\\.", ""));
			return res;
		} catch (ActionException e) {
			logger.error("AppDispatchServlet returns an ActionException:"
					+ e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AppDispatchServlet unexpected Exception:"
					+ e.getMessage());
			return null;
		}
	}

}
