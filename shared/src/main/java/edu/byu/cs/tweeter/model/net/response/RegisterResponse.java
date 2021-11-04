package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

//might not need this
public class RegisterResponse extends Response{
    private User user;
    private AuthToken authToken;

    RegisterResponse(boolean success) {
        super(success);
    }

    RegisterResponse(boolean success, String message) {
        super(success, message);
    }
}
