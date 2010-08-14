package com.partypage.client.mvp;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ResponseView extends Composite implements
		ResponsePresenter.Display {

	interface Binder extends UiBinder<DialogBox, ResponseView> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	@UiField
	DialogBox dialog;
	@UiField
	Label textToServerLabel;
	@UiField
	HTML serverResponseLabel;
	@UiField
	Button closeButton;
	@UiField
	VerticalPanel verticalPanel;

	public ResponseView() {
		// NOTE: dialog box is not added to dom here
		SimplePanel sp = new SimplePanel();
		sp.add(BINDER.createAndBindUi(this));
		initWidget(sp);

		closeButton.getElement().setId("closeButton");
		verticalPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
	}

	public HasText getTextToServer() {
		return textToServerLabel;
	}

	public HasHTML getServerResponse() {
		return serverResponseLabel;
	}

	public HasClickHandlers getClose() {
		return closeButton;
	}

	public DialogBox getDialogBox() {
		return dialog;
	}

	@Override
	public FocusWidget getCloseFocus() {
		return closeButton;
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return this;
		// return null;
	}
}
