package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter  {


    public interface View  extends Presenter.View{

        //logout
        void logoutUser();

        //set follower and following Count
        void setFollowerCount(int count);
        void setFollowingCount(int count);

        //isFollowing
        void setFollowingButtonTrue();
        void setFollowingButtonFalse();

        //follow
        void updateFollowButton(boolean removed);
        void setEnabledFollowButton(boolean enable);
    }



    private static final int PAGE_SIZE = 10;
    private View view;
    private User selectedUser;
    private User currentUser;
    private AuthToken authToken;
    private boolean hasMorePages = true;
    private boolean isLoading = false;

    public void getFollowingAndFollowersCount(){
        new FollowService().getFollowersAndFollowingCounts(authToken,selectedUser, new GetFollowersObserver(), new GetFollowingObserver());
    }

    private class GetFollowersObserver extends Presenter.BaseObserver implements FollowService.GetFollowersCountObserver {
        @Override
        public void handleSuccess(int count) {
            view.setFollowerCount(count);
        }
    }


    private class GetFollowingObserver extends Presenter.BaseObserver implements FollowService.GetFollowingCountObserver {
        @Override
        public void handleSuccess(int count) {
            view.setFollowingCount(count);
        }
    }

//logout

    private class LogoutObserver extends Presenter.BaseObserver implements MainService.LogoutObserver {

        @Override
        public void handleSuccess() {
            Cache.getInstance().clearCache();
            view.clearInfoMessage();
            view.logoutUser();
        }
    }
    public void logout() {
        view.displayInfoMessage("Logging Out...");
        new MainService().logout(authToken, new LogoutObserver());
    }


    public MainPresenter(MainPresenter.View view, AuthToken authToken, User selectedUser, User currentUser){
        super(view);
        this.view = view;
        this.selectedUser = selectedUser;
        this.authToken = authToken;
        this.currentUser = currentUser;

      //  view.navigateToUser(selectedUser); this was the issue that took all day to resolve
    }

    public MainPresenter(MainPresenter.View view, AuthToken authToken, User currentUser){

        super(view);
        this.view = view;
        this.authToken =authToken;
        this.currentUser = currentUser;

    }
    //isFollower

    public void isFollower(){
        new FollowService().isFollower(authToken, currentUser, selectedUser, new IsFollowerObserver() );
    }

    private class IsFollowerObserver extends Presenter.BaseObserver implements FollowService.IsFollowerHandlerObserver{

        @Override
        public void isFollowerSucceeded(boolean isFollower) {
            if (isFollower) {
                view.setFollowingButtonTrue();
            } else {
                view.setFollowingButtonFalse();
            }
        }
    }

    private class FollowObserver extends Presenter.BaseObserver implements FollowService.FollowHandlerObserver {

        @Override
        public void handleSuccess() {
            getFollowingAndFollowersCount();
            view.updateFollowButton(false);
            view.setEnabledFollowButton(true);
        }
    }

    //follow
    public void follow() {
        new FollowService().follow(authToken, selectedUser, new FollowObserver());
        view.displayInfoMessage("Adding " + selectedUser.getName() + "...");
    }

    //unfollow

    private class UnfollowObserver extends Presenter.BaseObserver implements FollowService.UnfollowHandlerObserver {

        @Override
        public void handleSuccess() {
            getFollowingAndFollowersCount();
            view.updateFollowButton(true);
            view.setEnabledFollowButton(true);
        }
    }
    public void unfollow() {
        new FollowService().unfollow(authToken, selectedUser, new UnfollowObserver());
        view.displayInfoMessage("Removing " + selectedUser.getName());
    }


    private class PostStatusObserver extends Presenter.BaseObserver implements MainService.PostStatusObserver {

        @Override
        public void handleSuccess() {
            view.clearInfoMessage();
            view.displayInfoMessage("Successfully Posted!");
        }
    }
    public void postStatus(String post){
        view.displayInfoMessage("Posting Status...");
        try {
            getMainService().postStatus(authToken, post, currentUser, getFormattedDateTime(),parseURLs(post), parseMentions(post), new PostStatusObserver() );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) throws MalformedURLException {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
}
