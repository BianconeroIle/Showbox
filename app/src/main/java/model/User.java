package model;

/**
 * Created by Ilija Angeleski on 8/15/2016.
 */
public class User {
    public static final int FACEBOOK_USER = 1;
    public static final int APP_USER = 2;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String fbImage;
    private String fbId;

    private int userType;

    public User(int userType) {
        this.userType = userType;
    }

    public User(int userType, String username, String password) {
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFbImage() {
        return fbImage;
    }

    public void setFbImage(String fbImage) {
        this.fbImage = fbImage;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
