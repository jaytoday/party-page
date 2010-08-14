package com.partypage.server.guice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class LogProvider implements Provider<Log>{

	public Log get() {
		return new Log4JLogger("GreetingLogger");
	}

}
