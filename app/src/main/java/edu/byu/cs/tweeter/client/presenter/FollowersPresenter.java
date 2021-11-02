package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User> {

    public interface View extends PagedPresenter.View {
        void addItems(List<User> followers, boolean hasMorePages);
    }

    private class FollowersObserver extends PagedPresenter.PagedBaseObserver implements FollowService.GetFollowersUserObserver {

        @Override
        public void handleSuccess(List items, boolean newHasMorePages) {
            lastItem = (items.size() > 0) ? (User) items.get(items.size() - 1) : null;
            isLoading = false;
            hasMorePages = newHasMorePages;
            view.setLoading(false);
            view.addItems(items, hasMorePages);
        }
    }


    private FollowersPresenter.View view;

    public FollowersPresenter(View view, AuthToken authToken, User targetUser) {
        super(view, targetUser, authToken);
        this.view = view;
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        new FollowService().getFollowers(authToken, targetUser, pageSize, lastItem, new FollowersObserver());
    }
}
