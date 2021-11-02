package edu.byu.cs.tweeter.client.observer;

import java.util.List;

import edu.byu.cs.tweeter.client.observer.ServiceObserver;

public interface PagedObserver<U> extends ServiceObserver {
    void handleSuccess(List<U> items, boolean hasMorePages);
}

