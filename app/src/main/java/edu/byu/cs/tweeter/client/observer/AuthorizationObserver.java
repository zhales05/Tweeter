package edu.byu.cs.tweeter.client.observer;

import edu.byu.cs.tweeter.client.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthorizationObserver extends ServiceObserver {
    void handleSuccess(AuthToken authtoken, User user);
}
