package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask {
    private static String LOGIN_MESSAGE = "/login";

    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() {
        ServerFacade serverFacade = new ServerFacade();
        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginResponse loginResponse = null;
        try {
            loginResponse = serverFacade.login(loginRequest, LOGIN_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        User loggedInUser = loginResponse.getUser();
        AuthToken authToken = loginResponse.getAuthToken();

        return new Pair<>(loggedInUser, authToken);
    }
}
