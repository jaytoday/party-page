package com.partypage.client.mvp;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.inject.Inject;
import com.partypage.shared.event.GreetingSentEvent;

public class ResponsePresenter extends
		WidgetPresenter<ResponsePresenter.Display> {
	public interface Display extends WidgetDisplay {
		HasText getTextToServer();

		HasHTML getServerResponse();

		HasClickHandlers getClose();

		FocusWidget getCloseFocus();

		DialogBox getDialogBox();
	}

	@Inject
	public ResponsePresenter(final Display display, final EventBus eventBus) {
		super(display, eventBus);
		// display.getDialogBox().hide();
	}

	@Override
	protected void onBind() {
		// Window.alert("onBind");

		// Add a handler to close the DialogBox
		display.getClose().addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				display.getDialogBox().hide();

				// Not sure of a nice place to put these!
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});

		eventBus.addHandler(GreetingSentEvent.TYPE,
				new GreetingSentEvent.Handler() {

					// @Override
					public void onGreetingSent(final GreetingSentEvent event) {
						Log.info("Handling GreetingSent event");

						display.getTextToServer().setText(event.getName());
						display.getServerResponse().setHTML(event.getMessage());

						// always center the dialog
						display.getDialogBox().setPopupPositionAndShow(
								new PositionCallback() {
									@Override
									public void setPosition(int offsetWidth,
											int offsetHeight) {
										int left = (Window.getClientWidth() - offsetWidth) / 2;
										int top = (Window.getClientHeight() - offsetHeight) / 2;
										display.getDialogBox()
												.setPopupPosition(left, top);
									}
								});

						display.getCloseFocus().setFocus(true);
					}
				});
	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	@Override
	protected void onRevealDisplay() {
		Window.alert("resposnePresenter.onRevealDisplay");
		// Nothing to do. This is more useful in UI which may be buried
		// in a tab bar, tree, etc.
	}

}
