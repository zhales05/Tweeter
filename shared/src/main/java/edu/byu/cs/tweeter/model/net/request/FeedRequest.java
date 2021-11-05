package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FeedRequest {
    private AuthToken authToken;
    private String targetAlias;
    private int limit;
    private String lastPost;

    public FeedRequest() {
    }

    public FeedRequest(AuthToken authToken, String targetAlias, int limit, String lastPost) {
        this.authToken = authToken;
        this.targetAlias = targetAlias;
        this.limit = limit;
        this.lastPost = lastPost;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getTargetAlias() {
        return targetAlias;
    }

    public void setTargetAlias(String targetAlias) {
        this.targetAlias = targetAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastPost() {
        return lastPost;
    }

    public void setLastPost(String lastPost) {
        this.lastPost = lastPost;
    }
}
