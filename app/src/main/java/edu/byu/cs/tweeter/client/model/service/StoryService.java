package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService {

    public interface GetStoryObserver extends PagedObserver {
    }

    public void getStory(AuthToken authToken, User user, int limit, Status lastStatus, GetStoryObserver observer) {
        GetStoryTask getStoryTask = new GetStoryTask(authToken,
                user, limit, lastStatus, new GetStoryHandler(observer));
        BackgroundTaskUtils.runTask(getStoryTask);
    }


    private class GetStoryHandler extends PagedHandler {
        public GetStoryHandler(PagedObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get story ";
        }


    }
}
