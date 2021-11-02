package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status>  {

    public interface View extends PagedPresenter.View{
        void addItems(List<Status> statuses, boolean hasMorePages);
    }
    private class StoryObserver extends PagedPresenter.PagedBaseObserver implements StoryService.GetStoryObserver {

        @Override
        public void handleSuccess(List items, boolean newHasMorePages) {
            lastItem = (items.size() > 0) ? (Status) items.get(items.size() - 1) : null;
            isLoading = false;
            hasMorePages = newHasMorePages;
            view.setLoading(false);
            view.addItems(items, hasMorePages);
        }
    }

    private StoryPresenter.View view;

    public StoryPresenter(View view, AuthToken authToken, User targetUser){
        super(view, targetUser, authToken);
        this.view = view;
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, Status lastItem) {
        new StoryService().getStory(authToken, targetUser, pageSize, lastItem, new StoryObserver());
    }

}
