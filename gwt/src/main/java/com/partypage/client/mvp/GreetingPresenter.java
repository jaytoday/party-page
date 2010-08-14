package com.partypage.client.mvp;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.partypage.client.dispatch.CachingDispatchAsync;
import com.partypage.shared.FieldVerifier;
import com.partypage.shared.event.GreetingSentEvent;
import com.partypage.shared.rpc.SendGreeting;
import com.partypage.shared.rpc.SendGreetingResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;

public class GreetingPresenter extends
		WidgetPresenter<GreetingPresenter.Display> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public interface Display extends WidgetDisplay {
		public HasValue<String> getName();

		public HasClickHandlers getSend();

		public HasKeyUpHandlers getSendReturn();

		public FocusWidget getFocusable();

		public HasText getError();
	}

	private final CachingDispatchAsync dispatcher;

	@Inject
	public GreetingPresenter(final Display display, final EventBus eventBus,
			final CachingDispatchAsync dispatcher) {
		super(display, eventBus);

		this.dispatcher = dispatcher;
	}

	/**
	 * Try to send the greeting message
	 */
	private void doSend() {
		Log.info("Calling doSend");

		display.getError().setText("");

		String name = display.getName().getValue();

		// validate
		if (!FieldVerifier.isValidName(name)) {
			display.getError().setText("Please enter at least four characters");
			display.getFocusable().setFocus(true);
			return;
		}

		dispatcher.execute(new SendGreeting(name),
				new AsyncCallback<SendGreetingResult>() {

					@Override
					public void onFailure(Throwable caught) {
						Log.error("Handle Failure:", caught);

						Window.alert(SERVER_ERROR);
					}

					@Override
					public void onSuccess(SendGreetingResult result) {
						// take the result from the server and notify client
						// interested components
						eventBus.fireEvent(new GreetingSentEvent(result
								.getName(), result.getMessage()));
					}

				});
	}

	@Override
	protected void onBind() {
		// 'display' is a final global field containing the Display passed into
		// the constructor.
		display.getSend().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				doSend();
			}
		});
		display.getSendReturn().addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doSend();
				}
			}
		});
	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	@Override
	protected void onRevealDisplay() {
		// Focus the cursor on the name field when the app loads
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				display.getFocusable().setFocus(true);
			}
		});
	}

	public void setName(String name) {
		display.getName().setValue(name);
	}

	public String getName() {
		return display.getName().getValue();
	}
}
