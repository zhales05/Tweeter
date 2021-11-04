package edu.byu.cs.tweeter.model.net.request;

public class RegisterRequest {
    private String username;
    private String password;
    private  String firstName;
    private  String lastName;
    private  String image;

    public RegisterRequest(String username, String password, String firstName, String lastName, String image) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImage() {
        return image;
    }
}
