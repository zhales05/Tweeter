package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends Presenter {

    public interface View extends Presenter.View {

        void navigateToUser(User user);

        void displayErrorMessage(String message);

        void clearErrorMessage();

        void displayInfoMessage(String message);

        void clearInfoMessage();
    }

    private View view;

    public RegisterPresenter(View view) {
        super(view);
        this.view = view;
    }

    public void register(String firstName, String lastName, String alias, String password, Bitmap imageToUpload) {
        view.clearErrorMessage();
        view.clearInfoMessage();

        String message = validateRegistration(firstName, lastName, alias, password, imageToUpload);
        if (message == null) {
            // Convert image to byte array.
            String image = imageToByteArray(imageToUpload);
            view.displayInfoMessage("Registering...");
            new UserService().register(firstName, lastName, alias, password, image, new RegisterObserver());
        } else {
            view.displayErrorMessage("Failed to register: " + message);
        }
    }

    private String imageToByteArray(Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();
        String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return imageBytesBase64;
    }

    public String validateRegistration(String firstName, String lastName, String alias, String password, Bitmap imageToUpload) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        if (imageToUpload == null) {
            return "Profile image must be uploaded.";
        }

        return null;
    }

    private class RegisterObserver extends Presenter.BaseObserver implements UserService.RegisterUserObserver {

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
