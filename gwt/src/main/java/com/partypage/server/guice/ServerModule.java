package com.partypage.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import org.apache.commons.logging.Log;
import com.partypage.server.handler.SendGreetingHandler;
import com.partypage.shared.rpc.SendGreeting;
import com.google.inject.Singleton;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(SendGreeting.class, SendGreetingHandler.class);

		
		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
	}
}
