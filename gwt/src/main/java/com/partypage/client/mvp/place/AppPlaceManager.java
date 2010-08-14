package com.partypage.client.mvp.place;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.DefaultPlaceManager;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.place.TokenFormatter;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;

public class AppPlaceManager extends DefaultPlaceManager {
	EventBus eventBus;

	@Inject
	public AppPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter,
			GreetingPresenterPlace greetingPresenterPlace) {
		super(eventBus, tokenFormatter, greetingPresenterPlace);
		this.eventBus = eventBus;
	}

	/**
	 * Handle firing of default place if no history token is present.
	 */
	@Override
	public boolean fireCurrentPlace() {

		if (!super.fireCurrentPlace()) {
			goToDefaultView();

			// go back, so the browser-back works to previous website
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					History.back();
				}
			});

		}

		return true;
	}

	/**
	 * Called by fireCurrentPlace to publish default view as PlaceRequestEvent
	 * on the EventBus.
	 */
	protected void goToDefaultView() {
		// Nothing in URL, load default page
		eventBus.fireEvent(new PlaceRequestEvent(new PlaceRequest(
				GreetingPresenterPlace.NAME)));
	}

}