package com.example.tp2_mobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tp2_mobile.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvHome.setLayoutManager(new LinearLayoutManager(this));
        HomeAdapter adapter = new HomeAdapter(DataSource.feeds);
        binding.rvHome.setAdapter(adapter);

        binding.btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", DataSource.feraUser);
            startActivity(intent);
        });

        binding.btnPost.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPostActivity.class);
            startActivity(intent);
        });

        binding.btnHome.setOnClickListener(v -> {
            binding.rvHome.smoothScrollToPosition(0);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh feed in case a new post was added
        if (binding.rvHome.getAdapter() != null) {
            binding.rvHome.getAdapter().notifyDataSetChanged();
        }
    }
}
