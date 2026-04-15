package com.example.tp2_mobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tp2_mobile.databinding.ActivityDetailStoryBinding;

public class DetailStoryActivity extends AppCompatActivity {
    private ActivityDetailStoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int imageRes = getIntent().getIntExtra("image", 0);
        if (imageRes != 0) {
            binding.ivStory.setImageResource(imageRes);
        }

        binding.btnClose.setOnClickListener(v -> finish());
    }
}
