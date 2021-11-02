package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.observer.PagedObserver;

public abstract class PagedHandler<T extends PagedObserver<U>, U> extends BackgroundTaskHandler<T> {
    public PagedHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        List<U> items = (List<U>) bundle.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = bundle.getBoolean(PagedTask.MORE_PAGES_KEY);
        observer.handleSuccess(items, hasMorePages);
    }
}
