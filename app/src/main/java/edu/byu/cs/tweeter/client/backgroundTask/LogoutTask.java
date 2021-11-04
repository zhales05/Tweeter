package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthorizedTask {
    private static String LOGOUT_MESSAGE = "/logout";

    public LogoutTask(AuthToken authToken, Handler messageHandler) {
        super(authToken, messageHandler);
    }

    @Override
    protected void runTask() {
        // We could do this from the presenter, without a task and handler, but we will
        // eventually remove the auth token from  the DB and will need this then.
        LogoutRequest logoutRequest = new LogoutRequest(getAuthToken());
        try {
            Response success = getServerFacade().logout(logoutRequest, LOGOUT_MESSAGE);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void loadBundle(Bundle msgBundle) {
        // Nothing to load
    }
}