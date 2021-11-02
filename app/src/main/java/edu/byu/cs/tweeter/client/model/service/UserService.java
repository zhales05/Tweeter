package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handler.AuthorizationHandler;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.observer.AuthorizationObserver;
import edu.byu.cs.tweeter.client.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

//will  be called by the presenter
public class UserService {

    public interface LoginObserver extends AuthorizationObserver {
    }

    public interface GetUserObserver extends ServiceObserver {
        void handleSuccess(User user);

    }

    public interface RegisterUserObserver extends AuthorizationObserver {
    }

    public void getUser(AuthToken authtoken, String alias, GetUserObserver observer) {
        GetUserTask getUserTask = new GetUserTask(authtoken, alias, new GetUserHandler(observer));
        BackgroundTaskUtils.runTask(getUserTask);
    }


    public void login(String alias, String password, LoginObserver observer) {
        //run a login task to login a user
        LoginTask loginTask = new LoginTask(alias, password, new LoginHandler(observer));
        BackgroundTaskUtils.runTask(loginTask);
    }

    public void register(String firstName, String lastName, String alias, String password, String image, RegisterUserObserver observer) {
        //run a register task to register a user
        RegisterTask registerTask = new RegisterTask(firstName, lastName, alias, password, image, new RegisterUserHandler(observer));
        BackgroundTaskUtils.runTask(registerTask);
    }

    /**
     * Message handler (i.e., observer) for LoginTask
     */
    private class LoginHandler extends AuthorizationHandler {
        public LoginHandler(AuthorizationObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to login ";
        }


    }


    private class GetUserHandler<T extends GetUserObserver> extends BackgroundTaskHandler<T> {


        public GetUserHandler(T observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get User ";
        }

        @Override
        protected void handleSuccessMessage(T observer, Bundle bundle) {
            observer.handleSuccess((User) bundle.getSerializable(GetUserTask.USER_KEY));
        }

    }

    private class RegisterUserHandler extends AuthorizationHandler {
        public RegisterUserHandler(AuthorizationObserver observer) {
            super(observer);
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to register ";
        }


    }
}
