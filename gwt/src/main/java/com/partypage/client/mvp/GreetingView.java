package com.partypage.client.mvp;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GreetingView extends Composite implements
		GreetingPresenter.Display {
	interface Binder extends UiBinder<VerticalPanel, GreetingView> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	@UiField
	TextBox nameBox;
	@UiField
	Button sendButton;
	@UiField
	Label errorLabel;

	public GreetingView() {
		initWidget(BINDER.createAndBindUi(this));
	}

	@Override
	public HasValue<String> getName() {
		return nameBox;
	}

	@Override
	public HasKeyUpHandlers getSendReturn() {
		return nameBox;
	}

	@Override
	public HasClickHandlers getSend() {
		return sendButton;
	}

	@Override
	public HasText getError() {
		return errorLabel;
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	public Widget asWidget() {
		return this;
	}

	@Override
	public FocusWidget getFocusable() {
		return sendButton;
	}

}
