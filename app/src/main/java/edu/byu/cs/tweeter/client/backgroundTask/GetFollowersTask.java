package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerResponse;
import edu.byu.cs.tweeter.model.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask {
    private static String GET_FOLLOWERS_MESSAGE = "/getfollowers";

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        String lastItemAlias;
        if (getLastItem() == null) {
            lastItemAlias = null;
        } else {
            lastItemAlias = getLastItem().getAlias();
        }
        FollowerRequest followerRequest = new FollowerRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), lastItemAlias);
        Pair<List<User>, Boolean> returnPair = null;
        try {
            FollowerResponse followerResponse = getServerFacade().getFollowers(followerRequest, GET_FOLLOWERS_MESSAGE);
            List<User> userList = followerResponse.getFollowers();
            boolean hasMorePages = followerResponse.getHasMorePages();
            returnPair = new Pair<>(userList, hasMorePages);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return returnPair;
    }
}
