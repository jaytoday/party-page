package com.partypage.shared.rpc;

import net.customware.gwt.dispatch.shared.Action;

import com.partypage.client.dispatch.Cacheable;

public class SendGreeting implements Action<SendGreetingResult>, Cacheable {

	private static final long serialVersionUID = 5804421607858017477L;

	private String name;

	@SuppressWarnings("unused")
	private SendGreeting() {
	}

	public SendGreeting(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int hashCode() {
		return name.hashCode();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SendGreeting))
			return false;
		SendGreeting o = (SendGreeting) obj;

		return this.name.equals(o.name);
	}

}
