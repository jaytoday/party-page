package com.partypage.client.gin;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.ParameterTokenFormatter;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.customware.gwt.presenter.client.place.TokenFormatter;

import com.partypage.client.dispatch.CachingDispatchAsync;
import com.partypage.client.mvp.AppPresenter;
import com.partypage.client.mvp.AppView;
import com.partypage.client.mvp.GreetingPresenter;
import com.partypage.client.mvp.GreetingView;
import com.partypage.client.mvp.ResponsePresenter;
import com.partypage.client.mvp.ResponseView;
import com.partypage.client.mvp.place.AppPlaceManager;
import com.partypage.client.mvp.place.GreetingPresenterPlace;
import com.google.inject.Singleton;

public class GreetingClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);

		bind(PlaceManager.class).to(AppPlaceManager.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class);

		// presenters
		bindPresenter(GreetingPresenter.class, GreetingPresenter.Display.class,
				GreetingView.class);
		bindPresenter(ResponsePresenter.class, ResponsePresenter.Display.class,
				ResponseView.class);
		bindPresenter(AppPresenter.class, AppPresenter.Display.class,
				AppView.class);

		// places
		bind(GreetingPresenterPlace.class).in(Singleton.class);

		// other
		bind(CachingDispatchAsync.class);
	}
}
