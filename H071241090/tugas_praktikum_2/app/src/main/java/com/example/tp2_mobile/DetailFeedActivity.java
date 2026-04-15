package com.example.tp2_mobile;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp2_mobile.databinding.ActivityDetailFeedBinding;

public class DetailFeedActivity extends AppCompatActivity {
    private ActivityDetailFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Post post = getIntent().getParcelableExtra("post");
        if (post != null) {
            binding.detailPostItem.ivProfilePost.setImageResource(post.getProfileImage());
            binding.detailPostItem.tvUsernamePost.setText(post.getUsername());
            
            if (post.getPostImageRes() != null) {
                binding.detailPostItem.ivPostImage.setImageResource(post.getPostImageRes());
            } else if (post.getPostImageUri() != null) {
                binding.detailPostItem.ivPostImage.setImageURI(Uri.parse(post.getPostImageUri()));
            }

            binding.detailPostItem.tvCaption.setText(post.getUsername() + ": " + post.getCaption());
        }

        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }
}
