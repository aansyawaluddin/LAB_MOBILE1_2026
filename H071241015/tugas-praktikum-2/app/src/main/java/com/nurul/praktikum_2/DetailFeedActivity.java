package com.nurul.praktikum_2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailFeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        Post post = (Post) getIntent().getSerializableExtra("EXTRA_POST");

        if (post != null) {
            ImageView ivPostImage = findViewById(R.id.ivPostImageDetail);
            ImageView ivProfilePic = findViewById(R.id.ivProfilePicDetail);
            TextView tvUsername = findViewById(R.id.tvUsernameDetail);
            TextView tvCaption = findViewById(R.id.tvCaptionDetail);

            if (post.getPostImageUri() != null) {
                ivPostImage.setImageURI(Uri.parse(post.getPostImageUri()));
            } else {
                ivPostImage.setImageResource(post.getPostImage());
            }
            
            ivProfilePic.setImageResource(post.getUserProfileImage());
            tvUsername.setText(post.getUsername());
            tvCaption.setText(post.getCaption());
        }
    }
}
