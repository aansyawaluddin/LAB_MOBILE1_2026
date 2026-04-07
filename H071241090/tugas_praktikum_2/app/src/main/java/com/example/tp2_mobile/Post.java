package com.example.tp2_mobile;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Post implements Parcelable {
    private String username;
    private int profileImage;
    private Integer postImageRes;
    private String postImageUri;
    private String caption;

    public Post(String username, int profileImage, Integer postImageRes, String caption) {
        this.username = username;
        this.profileImage = profileImage;
        this.postImageRes = postImageRes;
        this.caption = caption;
    }

    public Post(String username, int profileImage, String postImageUri, String caption) {
        this.username = username;
        this.profileImage = profileImage;
        this.postImageUri = postImageUri;
        this.caption = caption;
    }

    protected Post(Parcel in) {
        username = in.readString();
        profileImage = in.readInt();
        if (in.readByte() == 0) {
            postImageRes = null;
        } else {
            postImageRes = in.readInt();
        }
        postImageUri = in.readString();
        caption = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public Integer getPostImageRes() { return postImageRes; }
    public String getPostImageUri() { return postImageUri; }
    public String getCaption() { return caption; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeInt(profileImage);
        if (postImageRes == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(postImageRes);
        }
        parcel.writeString(postImageUri);
        parcel.writeString(caption);
    }
}
