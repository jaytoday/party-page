package com.partypage.client.mvp;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AppView extends Composite implements AppPresenter.Display {
	private FlowPanel appContainer = new FlowPanel();

	@Inject
	public AppView() {
		// appContainer.add(logoContainer);
		initWidget(appContainer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.customware.gwt.presenter.client.widget.WidgetContainerDisplay#addWidget
	 * (com.google.gwt.user.client.ui.Widget)
	 */
	public void addWidget(Widget widget) {
		// appContainer.clear();
		// appContainer.add(widget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.customware.gwt.presenter.client.widget.WidgetContainerDisplay#
	 * removeWidget(com.google.gwt.user.client.ui.Widget)
	 */
	public void removeWidget(Widget widget) {
		appContainer.remove(widget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.customware.gwt.presenter.client.widget.WidgetContainerDisplay#showWidget
	 * (com.google.gwt.user.client.ui.Widget)
	 */
	public void showWidget(Widget widget) {
		// appContainer.clear();
		appContainer.add(widget);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
