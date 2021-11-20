package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest {
    User currentUser;
    User followee;

    public FollowRequest() {
    }

    public FollowRequest(User currentUser, User followee) {
        this.currentUser = currentUser;
        this.followee = followee;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
