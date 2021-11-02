package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status> {

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, Status lastItem) {
        new FeedService().getFeed(authToken, targetUser, pageSize, (Status) lastItem, new FeedObserver());
    }

    public interface View extends PagedPresenter.View {
        void addItems(List<Status> statuses, boolean hasMorePages);
    }

    private class FeedObserver extends PagedPresenter.PagedBaseObserver implements FeedService.GetFeedObserver {
        @Override
        public void handleSuccess(List items, boolean newHasMorePages) {
            lastItem = ((items.size() > 0) ? (Status) items.get(items.size() - 1) : null);
            hasMorePages = newHasMorePages;
            view.setLoading(false);
            view.addItems(items, hasMorePages);
            isLoading = false;
        }
    }


    private FeedPresenter.View view;


    public FeedPresenter(View view, AuthToken authToken, User targetUser) {
        super(view, targetUser, authToken);
        this.view = view;
    }

}
