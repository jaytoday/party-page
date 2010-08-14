package com.partypage.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class GreetingSentEvent extends GwtEvent<GreetingSentEvent.Handler> {
	public interface Handler extends EventHandler {
		void onGreetingSent(GreetingSentEvent event);
	}

	public static Type<Handler> TYPE = new Type<Handler>();

	private final String name;
	private final String message;

	public GreetingSentEvent(final String name, final String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(final Handler handler) {
		handler.onGreetingSent(this);
	}
}
