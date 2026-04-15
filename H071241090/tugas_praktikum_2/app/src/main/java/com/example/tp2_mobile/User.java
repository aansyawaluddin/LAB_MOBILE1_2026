package com.example.tp2_mobile;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class User implements Parcelable {
    private String username;
    private int profileImage;
    private ArrayList<Post> posts;
    private ArrayList<Integer> highlights;

    public User(String username, int profileImage, ArrayList<Post> posts, ArrayList<Integer> highlights) {
        this.username = username;
        this.profileImage = profileImage;
        this.posts = posts;
        this.highlights = highlights;
    }

    protected User(Parcel in) {
        username = in.readString();
        profileImage = in.readInt();
        posts = in.createTypedArrayList(Post.CREATOR);
        highlights = new ArrayList<>();
        in.readList(highlights, Integer.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public ArrayList<Post> getPosts() { return posts; }
    public ArrayList<Integer> getHighlights() { return highlights; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeInt(profileImage);
        parcel.writeTypedList(posts);
        parcel.writeList(highlights);
    }
}
