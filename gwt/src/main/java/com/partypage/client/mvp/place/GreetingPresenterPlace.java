package com.partypage.client.mvp.place;

import net.customware.gwt.presenter.client.gin.ProvidedPresenterPlace;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.partypage.client.mvp.GreetingPresenter;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GreetingPresenterPlace extends
		ProvidedPresenterPlace<GreetingPresenter> {

	public static final String NAME = "Greeting";

	@Inject
	public GreetingPresenterPlace(Provider<GreetingPresenter> presenter) {
		super(presenter);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void preparePresenter(PlaceRequest request,
			GreetingPresenter presenter) {
		String name = request.getParameter("name", "GWT Developer");
		presenter.setName(name);
	}

	@Override
	protected PlaceRequest prepareRequest(PlaceRequest request,
			GreetingPresenter presenter) {

		return request.with("name", presenter.getName());
	}
}
