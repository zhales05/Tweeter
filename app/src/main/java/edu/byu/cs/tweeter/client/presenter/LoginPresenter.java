package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends Presenter {

    public LoginPresenter(View view) {
        super(view);
    }

    public void login(String alias, String password){
        view.clearErrorMessage();
        view.clearInfoMessage();

        String message = validateLogin(alias, password);
        if(message == null) {
            view.displayInfoMessage("Logging In...");
            new UserService().login(alias, password, new FinalLoginObserver());
        }
        else {
            view.displayErrorMessage("Login failed: " + message);
        }
    }

    public String validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return ("Password cannot be empty.");
        }
        return null;
    }

    private class FinalLoginObserver extends Presenter.BaseObserver implements UserService.LoginObserver  {

        @Override
        public void handleSuccess(AuthToken authtoken, User user) {
            Cache.getInstance().setCurrUser(user);
            Cache.getInstance().setCurrUserAuthToken(authtoken);
            view.navigateToUser(user);
            view.clearErrorMessage();
            view.displayInfoMessage("Hello " + user.getName());
        }

    }


}
