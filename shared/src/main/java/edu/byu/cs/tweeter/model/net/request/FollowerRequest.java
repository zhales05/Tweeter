package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowerRequest {
    private AuthToken authToken;
    private String followeeAlias;
    private int limit;
    private String lastFolloweeAlias;

    public FollowerRequest() {
    }

    public FollowerRequest(AuthToken authToken, String followeeAlias, int limit, String lastFollowerAlias) {
        this.authToken = authToken;
        this.followeeAlias = followeeAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFollowerAlias;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getFollowerAlias() {
        return followeeAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followeeAlias = followerAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public void setLastFolloweeAlias(String lastFolloweeAlias) {
        this.lastFolloweeAlias = lastFolloweeAlias;
    }
}
