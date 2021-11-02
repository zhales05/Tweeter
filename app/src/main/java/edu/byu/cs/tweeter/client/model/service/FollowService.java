package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.handler.CountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.observer.CountObserver;
import edu.byu.cs.tweeter.client.observer.PagedObserver;
import edu.byu.cs.tweeter.client.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.observer.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public interface GetFollowingUserObserver extends PagedObserver {
    }

    public void getFollowing(AuthToken authToken,
                             User targetUser, int limit, User lastFollowee,
                             GetFollowingUserObserver observer) {

        GetFollowingTask getFollowingTask = new GetFollowingTask(authToken,
                targetUser, limit, lastFollowee, new GetFollowingHandler(observer));
        BackgroundTaskUtils.runTask(getFollowingTask);
    }

    /**
     * Message handler (i.e., observer) for GetFollowingTask.
     */
    private class GetFollowingHandler extends PagedHandler {
        public GetFollowingHandler(PagedObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get followers count: ";
        }

    }

    //switch to getFollowers

    public interface GetFollowersUserObserver extends PagedObserver {

    }

    public void getFollowers(AuthToken authToken,
                             User targetUser, int limit, User lastFollowee,
                             GetFollowersUserObserver observer) {

        GetFollowersTask getFollowersTask = new GetFollowersTask(authToken,
                targetUser, limit, lastFollowee, new GetFollowersHandler(observer));
        BackgroundTaskUtils.runTask(getFollowersTask);
    }


    /**
     * Message handler (i.e., observer) for GetFollowersTask.
     */
    private class GetFollowersHandler extends PagedHandler {
        public GetFollowersHandler(PagedObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get followers: ";
        }

    }

    public void getFollowersAndFollowingCounts(AuthToken authToken, User selectedUser, GetFollowersCountObserver followersCountObserver, GetFollowingCountObserver followingCountObserver) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(authToken,
                selectedUser, new GetFollowersSingleParamHandler(followersCountObserver));
        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(authToken,
                selectedUser, new GetFollowingSingleParamHandler(followingCountObserver));
        executor.execute(followingCountTask);
    }

    public interface GetFollowersCountObserver extends CountObserver {

    }

    private class GetFollowersSingleParamHandler extends CountHandler {
        public GetFollowersSingleParamHandler(CountObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get followers count";
        }

    }

    public interface GetFollowingCountObserver extends CountObserver {
    }

    private class GetFollowingSingleParamHandler extends CountHandler {

        public GetFollowingSingleParamHandler(CountObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get following count";
        }


    }

    public interface IsFollowerHandlerObserver extends ServiceObserver {
        void isFollowerSucceeded(boolean isFollower);
    }

    public void isFollower(AuthToken authToken, User currUser, User selectedUser, IsFollowerHandlerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(authToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        Execute.handleThread(isFollowerTask);
    }

    private class IsFollowerHandler<T extends IsFollowerHandlerObserver> extends BackgroundTaskHandler<T> {


        public IsFollowerHandler(T observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to determine following relationship";
        }

        @Override
        protected void handleSuccessMessage(T observer, Bundle bundle) {
            observer.isFollowerSucceeded(bundle.getBoolean(IsFollowerTask.SUCCESS_KEY));
        }
    }

    // FollowHandler

    public interface FollowHandlerObserver extends SimpleNotificationServiceObserver {
    }

    public void follow(AuthToken authToken, User selectedUser, FollowHandlerObserver observer) {
        FollowTask followTask = new FollowTask(authToken,
                selectedUser, new FollowHandler(observer));
        BackgroundTaskUtils.runTask(followTask);
    }

    private class FollowHandler extends SimpleNotificationHandler {

        public FollowHandler(FollowHandlerObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to follow";
        }

    }

    // UnfollowHandler
    public interface UnfollowHandlerObserver extends SimpleNotificationServiceObserver {
    }

    public void unfollow(AuthToken authToken, User selectedUser, UnfollowHandlerObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(authToken,
                selectedUser, new UnfollowHandler(observer));
        BackgroundTaskUtils.runTask(unfollowTask);
    }


    private class UnfollowHandler extends SimpleNotificationHandler {
        public UnfollowHandler(UnfollowHandlerObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to unfollow";
        }
    }
}
