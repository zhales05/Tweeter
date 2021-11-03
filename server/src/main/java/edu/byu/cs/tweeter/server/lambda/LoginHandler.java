package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.service.UserService;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
        UserService userService = null;
        try {
            if (loginRequest.getPassword() == ""
            || loginRequest.getUsername() == "") {
                throw new IOException("BadRequest");
            }
            userService = new UserService();
        } catch (IOException exception) {
            exception.getStackTrace();
        }
        userService = new UserService();

        return userService.login(loginRequest);
    }
}
