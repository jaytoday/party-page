package com.partypage.client.dispatch;

import java.util.ArrayList;
import java.util.HashMap;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Dispatcher which supports caching of data in memory
 * 
 * In order for caching to work, Action classes must override equals() and
 * hashCode() appropriately! Alternatively, you can pass the same instance of an
 * Action with subsequent requests (i.e., use new only once).
 * 
 * @see <a href=
 *      "http://blog.hivedevelopment.co.uk/2009/08/google-web-toolkit-gwt-mvp-example.html"
 *      >Chris Lowe's blog post at Hive Development</a>
 * @author Chris Lowe
 * @author David Chandler <a href=
 *         "http://turbomanage.wordpress.com/2010/03/02/tips-for-using-cachingdispatchasync-with-gwt-dispatch/"
 *         >added Cacheable</a>
 */
@Singleton
public class CachingDispatchAsync implements DispatchAsync {
	private DispatchAsync dispatcher;
	private static HashMap<Action<Result>, Result> cache = new HashMap<Action<Result>, Result>();
	private static HashMap<Action<Result>, ArrayList<AsyncCallback<Result>>> pendingCallbacks = new HashMap<Action<Result>, ArrayList<AsyncCallback<Result>>>();

	@Inject
	public CachingDispatchAsync(final DispatchAsync dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see net.customware.gwt.dispatch.client.DispatchAsync#execute(A,
	 *      com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	public <A extends Action<R>, R extends Result> void execute(final A action,
			final AsyncCallback<R> callback) {
		if (action instanceof Cacheable) {
			executeWithCache(action, callback);
		} else {
			dispatcher.execute(action, callback);
		}
	}

	/**
	 * Execute the give Action. If the Action was executed before it will get
	 * fetched from the cache
	 * 
	 * @param Action
	 *            implementation
	 * @param Result
	 *            implementation
	 * @param action
	 *            the action
	 * @param callback
	 *            the callback
	 */
	@SuppressWarnings("unchecked")
	private <A extends Action<R>, R extends Result> void executeWithCache(
			final A action, final AsyncCallback<R> callback) {
		GWT.log("Executing with cache " + action.toString());
		final ArrayList<AsyncCallback<Result>> pending = pendingCallbacks
				.get(action);
		// TODO need a timeout here?
		if (pending != null) {
			GWT.log("Command pending for " + action, null);
			// Add to pending commands for this action
			pending.add((AsyncCallback<Result>) callback);
			return;
		}
		Result r = cache.get(action);

		if (r != null) {
			GWT.log("Cache hit for " + action, null);
			callback.onSuccess((R) r);
		} else {
			GWT.log("Calling real service for " + action, null);
			pendingCallbacks.put((Action<Result>) action,
					new ArrayList<AsyncCallback<Result>>());
			dispatcher.execute(action, new AsyncCallback<R>() {
				public void onFailure(Throwable caught) {
					// Process all pending callbacks for this action
					ArrayList<AsyncCallback<Result>> callbacks = pendingCallbacks
							.remove((Action<Result>) action);
					for (AsyncCallback<Result> pendingCallback : callbacks) {
						pendingCallback.onFailure(caught);
					}
					callback.onFailure(caught);
				}

				public void onSuccess(R result) {
					GWT.log("Real service returned successfully " + action,
							null);
					// Process all pending callbacks for this action
					ArrayList<AsyncCallback<Result>> callbacks = pendingCallbacks
							.remove((Action<Result>) action);
					for (AsyncCallback<Result> pendingCallback : callbacks) {
						pendingCallback.onSuccess(result);
					}
					cache.put((Action) action, (Result) result);
					callback.onSuccess(result);
				}
			});
		}
	}

	/**
	 * Clear the cache
	 */
	public void clear() {
		cache.clear();
	}

	/**
	 * Clear the cache for a specific Action
	 * 
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	public <A extends Action<R>, R extends Result> void clear(A action) {
		cache.put((Action<Result>) action, null);
	}

}
