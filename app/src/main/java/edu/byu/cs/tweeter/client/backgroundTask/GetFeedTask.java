package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {
    private static String GET_FEED_MESSAGE = "/getfeed";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        String lastItemPost;
        if (getLastItem() == null) {
            lastItemPost = null;
        } else {
            lastItemPost = getLastItem().post;
        }
        FeedRequest feedRequest = new FeedRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), lastItemPost);
        Pair<List<Status>, Boolean> returnPair = null;
        try {
            FeedResponse feedResponse = getServerFacade().getFeed(feedRequest, GET_FEED_MESSAGE);
            List<Status> statusList = feedResponse.getStatuses();
            boolean hasMorePages = feedResponse.getHasMorePages();
            returnPair = new Pair<>(statusList, hasMorePages);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return returnPair;
    }
}
