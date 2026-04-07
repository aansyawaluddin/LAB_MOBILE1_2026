package com.nurul.praktikum_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileActivity extends AppCompatActivity {

    private ProfileFeedAdapter feedAdapter;
    private TextView tvPostCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvPostCount = findViewById(R.id.tvPostCount);

        // Ambil data Post dari intent (jika ada) untuk sinkronisasi nama
        Post clickedPost = (Post) getIntent().getSerializableExtra("EXTRA_POST");

        if (clickedPost != null) {
            TextView tvToolbarUsername = findViewById(R.id.tvToolbarUsername);
            TextView tvRealName = findViewById(R.id.tvRealName);
            TextView tvUsernameHandle = findViewById(R.id.tvUsernameHandle);
            ImageView ivProfilePicture = findViewById(R.id.ivProfilePicture);

            tvToolbarUsername.setText(clickedPost.getUsername());
            tvRealName.setText(clickedPost.getUsername()); // Anggap real name sama dengan username atau sesuaikan
            tvUsernameHandle.setText("@" + clickedPost.getUsername().toLowerCase().replace(" ", "_"));
            ivProfilePicture.setImageResource(clickedPost.getUserProfileImage());

            // Sinkronisasi data postingan di grid agar menggunakan nama user yang diklik
            for (Post post : DataSource.profilePosts) {
                post.setUsername(clickedPost.getUsername());
                post.setUserProfileImage(clickedPost.getUserProfileImage());
            }
        }

        // Update jumlah postingan awal
        updatePostCount();

        // 1. Setup Story (Horizontal)
        RecyclerView rvStory = findViewById(R.id.rvStory);
        rvStory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StoryAdapter storyAdapter = new StoryAdapter(DataSource.profileStories, story -> {
            Intent intent = new Intent(ProfileActivity.this, DetailStoryActivity.class);
            intent.putExtra("EXTRA_STORY", story);
            startActivity(intent);
        });
        rvStory.setAdapter(storyAdapter);

        // 2. Setup Feed Grid (3 Kolom)
        RecyclerView rvProfileFeed = findViewById(R.id.rvProfileFeed);
        rvProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));
        feedAdapter = new ProfileFeedAdapter(DataSource.profilePosts, post -> {
            Intent intent = new Intent(ProfileActivity.this, DetailFeedActivity.class);
            intent.putExtra("EXTRA_POST", post);
            startActivity(intent);
        });
        rvProfileFeed.setAdapter(feedAdapter);

        // 3. Tombol Tambah Postingan Baru (+)
        ImageView btnAdd = findViewById(R.id.btnAddPost);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PostActivity.class);
            startActivity(intent);
        });
    }

    private void updatePostCount() {
        if (tvPostCount != null) {
            tvPostCount.setText(String.valueOf(DataSource.profilePosts.size()));
        }
    }

    // Gunakan onResume agar grid otomatis ter-update saat kembali dari PostActivity
    @Override
    protected void onResume() {
        super.onResume();
        if (feedAdapter != null) {
            feedAdapter.notifyDataSetChanged();
            updatePostCount(); // Update angka saat kembali dari upload
        }
    }
}
