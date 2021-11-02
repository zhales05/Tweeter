package edu.byu.cs.tweeter.client.presenter;

import android.view.View;

import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.client.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class Presenter {
    View view;

    public MainService getMainService() {
        return new MainService();
    }

    public interface View {
        void navigateToUser(User user);

        void displayErrorMessage(String message);
        void clearErrorMessage();

        void displayInfoMessage(String message);
        void clearInfoMessage();
    }

    public Presenter(View view) {
        this.view = view;
    }


    public View getView() {
        return view;
    }

    public abstract class BaseObserver implements ServiceObserver{

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception ex) {
            view.clearInfoMessage();
            view.displayErrorMessage(ex.getMessage());
        }
    }
}
