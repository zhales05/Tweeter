package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService {

    public interface GetFeedObserver extends PagedObserver {}

    public void getFeed(AuthToken authToken, User user, int limit, Status lastStatus, FeedService.GetFeedObserver observer) {
        GetFeedTask getFeedTask = new GetFeedTask(authToken,
                user, limit, lastStatus, new GetFeedHandler(observer));
        BackgroundTaskUtils.runTask(getFeedTask);
    }

    private class GetFeedHandler extends PagedHandler {
        public GetFeedHandler(PagedObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get feed ";
        }

    }
}
