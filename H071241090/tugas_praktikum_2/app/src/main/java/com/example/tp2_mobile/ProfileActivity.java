package com.example.tp2_mobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tp2_mobile.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = getIntent().getParcelableExtra("user");
        if (user != null) {
            setupUI(user);
        }

        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupUI(User user) {
        binding.tvToolbarUsername.setText(user.getUsername());
        binding.tvProfileName.setText(user.getUsername());
        binding.ivProfileImage.setImageResource(user.getProfileImage());
        binding.tvCountPost.setText(String.valueOf(user.getPosts().size()));

        // Highlights
        binding.rvHighlights.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvHighlights.setAdapter(new HighlightAdapter(user.getHighlights()));

        // Grid Posts
        binding.rvProfilePosts.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvProfilePosts.setAdapter(new ProfilePostAdapter(user.getPosts()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning from AddPostActivity
        User user = getIntent().getParcelableExtra("user");
        if (user != null && user.getUsername().equals("fera_kaco")) {
            // Update counts and list
            setupUI(DataSource.feraUser);
        }
    }
}
