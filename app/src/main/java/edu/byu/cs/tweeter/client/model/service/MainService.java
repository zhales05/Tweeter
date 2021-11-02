package edu.byu.cs.tweeter.client.model.service;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.observer.SimpleNotificationServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainService {
    public interface LogoutObserver extends SimpleNotificationServiceObserver {
    }

    public void logout(AuthToken authToken, MainService.LogoutObserver observer){
        LogoutTask logoutTask = new LogoutTask(authToken, new LogoutHandler(observer));
        Execute.handleThread(logoutTask);
    }

    private class LogoutHandler extends SimpleNotificationHandler {
        public LogoutHandler(SimpleNotificationServiceObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to logout ";
        }
    }

    // PostStatusHandler

    public interface PostStatusObserver extends SimpleNotificationServiceObserver{}

    public void postStatus(AuthToken authToken, String post, User currentUser, String date, List<String> urls, List<String> mentions, PostStatusObserver observer) {
        Status newStatus = new Status(post, currentUser, date, urls, mentions);
        PostStatusTask statusTask = new PostStatusTask(authToken,
                newStatus, new PostStatusHandler(observer));
        BackgroundTaskUtils.runTask(statusTask);
    }


    private class PostStatusHandler extends SimpleNotificationHandler {

        public PostStatusHandler(SimpleNotificationServiceObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to post status ";
        }
    }

}
