package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticationTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.observer.AuthorizationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthorizationHandler<T extends AuthorizationObserver> extends BackgroundTaskHandler<T> {
    public AuthorizationHandler(T observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(T observer, Bundle bundle) {
        User loggedInUser = (User) bundle.getSerializable(AuthenticationTask.USER_KEY);
        AuthToken authToken = (AuthToken) bundle.getSerializable(AuthenticationTask.AUTH_TOKEN_KEY);
        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.handleSuccess(authToken, loggedInUser);
    }
}
