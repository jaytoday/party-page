package com.partypage.client;

import com.partypage.client.gin.GreetingGinjector;
import com.partypage.client.mvp.AppPresenter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class GreetMvp implements EntryPoint {
	private final GreetingGinjector injector = GWT
			.create(GreetingGinjector.class);

	public void onModuleLoad() {
		com.google.gwt.user.client.Element loading = DOM
				.getElementById("loading");

		DOM.removeChild(RootPanel.getBodyElement(), loading);

		AppPresenter appPresenter = injector.getAppPresenter();
		appPresenter.bind();

		Widget w = appPresenter.getDisplay().asWidget();
		RootPanel.get().add(w);

		injector.getPlaceManager().fireCurrentPlace();
	}
}
