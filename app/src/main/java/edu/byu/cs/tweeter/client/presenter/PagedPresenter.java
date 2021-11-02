package edu.byu.cs.tweeter.client.presenter;

import android.view.View;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.observer.PagedObserver;
import edu.byu.cs.tweeter.client.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter{
    private static final int PAGE_SIZE = 10;
    User targetUser;
    AuthToken authToken;
    T lastItem;
    boolean hasMorePages = true;
    boolean isLoading = false;
    boolean isGettingUser;

    public interface View extends Presenter.View{
        void setLoading(boolean value);
    }

    View view;

    public PagedPresenter(View view, User targetUser,
                          AuthToken authToken, T lastItem, boolean hasMorePages,
                          boolean isLoading, boolean isGettingUser) {
        super(view);
        this.view = view;//unsure
        this.targetUser = targetUser;
        this.authToken = authToken;
        this.lastItem = lastItem;
        this.hasMorePages = hasMorePages;
        this.isLoading = isLoading;
        this.isGettingUser = isGettingUser;
    }

    public PagedPresenter(View view, User targetUser, AuthToken authToken){
        super(view);
        this.view = view;
        this.targetUser = targetUser;
        this.authToken = authToken;
    }

    public void loadMoreItems(){
        if (!isLoading && hasMorePages) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoading(true);
            getItems(authToken, targetUser, PAGE_SIZE, lastItem);
        }
    }
    //public void getUser(User user){}

    public void goToUser(String alias) {
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken, alias, new UserObserver());
    }

    public abstract void getItems(AuthToken authToken, User targetUser, int pageSize, T lastItem);

    //observers

    protected class PagedBaseObserver extends Presenter.BaseObserver  {

        @Override
        public void handleFailure(String message) {
            super.handleFailure(message);
            view.setLoading(false);
            isLoading = false;
        }

        @Override
        public void handleException(Exception ex) {
            super.handleException(ex);
            view.setLoading(false);
            isLoading = false;
        }
    }

    private class UserObserver extends Presenter.BaseObserver implements UserService.GetUserObserver {
        @Override
        public void handleSuccess(User user) {
            view.clearInfoMessage();
            view.navigateToUser(user);
        }
    }

}
