package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.service.UserService;

public class RegisterHandler implements RequestHandler<RegisterRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(RegisterRequest registerRequest, Context context) {
        UserService userService = null;
        try {
            if (registerRequest.getPassword() == ""
                    || registerRequest.getUsername() == "") {
                throw new IOException("BadRequest");
            }
            userService = new UserService();
        } catch (IOException exception) {
            exception.getStackTrace();
        }

        userService = new UserService();
        return userService.register(registerRequest);    }
}
