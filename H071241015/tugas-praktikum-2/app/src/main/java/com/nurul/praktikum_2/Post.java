package com.nurul.praktikum_2;

import java.io.Serializable;

public class Post implements Serializable {
    private String username;
    private int userProfileImage;
    private int postImageRes;
    private String postImageUri;
    private String caption;

    // Constructor untuk Resource ID
    public Post(String username, int userProfileImage, int postImageRes, String caption) {
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.postImageRes = postImageRes;
        this.postImageUri = null;
        this.caption = caption;
    }

    // Constructor untuk Uri (Gallery)
    public Post(String username, int userProfileImage, String postImageUri, String caption) {
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.postImageRes = 0;
        this.postImageUri = postImageUri;
        this.caption = caption;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getUserProfileImage() { return userProfileImage; }
    public void setUserProfileImage(int userProfileImage) { this.userProfileImage = userProfileImage; }

    public int getPostImage() { return postImageRes; }
    public String getPostImageUri() { return postImageUri; }
    public String getCaption() { return caption; }
}
