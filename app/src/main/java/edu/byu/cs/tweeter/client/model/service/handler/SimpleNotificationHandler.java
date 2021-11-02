package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.observer.SimpleNotificationServiceObserver;

public abstract class SimpleNotificationHandler<T extends SimpleNotificationServiceObserver> extends BackgroundTaskHandler<T> {

    public SimpleNotificationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        observer.handleSuccess();
    }
}
