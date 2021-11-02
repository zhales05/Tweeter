package edu.byu.cs.tweeter.client.observer;

public interface CountObserver extends ServiceObserver {
    void handleSuccess(int count);
}
