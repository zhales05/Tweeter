package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class CountRequest {
    String user;

    public CountRequest(String user) {
        this.user = user;
    }
    public CountRequest(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
