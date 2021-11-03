package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {
    private static String GET_FOLLOWING_MESSAGE = "/getfollowing";

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        String lastItemAlias;
       if (getLastItem() == null) {
            lastItemAlias = "@allen";
        }
        else {
           lastItemAlias = getLastItem().getAlias();
       }

        FollowingRequest followingRequest = new FollowingRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), lastItemAlias);
        Pair<List<User>, Boolean> returnPair = null;
        try {
            FollowingResponse  followingResponse = getServerFacade().getFollowees(followingRequest, GET_FOLLOWING_MESSAGE);
            List<User> userList = followingResponse.getFollowees();
            boolean hasMorePages = followingResponse.getHasMorePages();
            returnPair = new Pair<>(userList, hasMorePages);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        //return getFakeData().getPageOfUsers(getLastItem(), getLimit(), getTargetUser());
        return returnPair;
    }
}
