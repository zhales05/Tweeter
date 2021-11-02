package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.GetCountTask;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.observer.CountObserver;

public abstract class CountHandler<T extends CountObserver> extends BackgroundTaskHandler<T> {
    public CountHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        observer.handleSuccess(bundle.getInt(GetCountTask.COUNT_KEY));
    }
}
