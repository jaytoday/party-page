package com.partypage.client.gin;

import net.customware.gwt.dispatch.client.gin.StandardDispatchModule;
import net.customware.gwt.presenter.client.place.PlaceManager;
import com.partypage.client.mvp.AppPresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules({ StandardDispatchModule.class, GreetingClientModule.class })
public interface GreetingGinjector extends Ginjector {

	AppPresenter getAppPresenter();

	PlaceManager getPlaceManager();

}
