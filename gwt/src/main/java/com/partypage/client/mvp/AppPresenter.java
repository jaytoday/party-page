package com.partypage.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetContainerDisplay;
import net.customware.gwt.presenter.client.widget.WidgetContainerPresenter;

import com.google.inject.Inject;

public class AppPresenter extends
		WidgetContainerPresenter<AppPresenter.Display> {

	public interface Display extends WidgetContainerDisplay {
	}

	@Inject
	public AppPresenter(Display display, DispatchAsync dispatcher,
			final EventBus bus, GreetingPresenter greetingPresenter,
			ResponsePresenter responsePresenter) {

		super(display, bus, greetingPresenter, responsePresenter);
	}

	@Override
	protected void onBind() {
		super.onBind();
		showMain();
	}

	/**
	 * Pass any initialization state as an argument here.
	 */
	private void showMain() {
		// greetingPresenter.revealDisplay();
	}
}
