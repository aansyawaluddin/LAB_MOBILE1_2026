package com.nurul.praktikum_2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailStoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        Story story = (Story) getIntent().getSerializableExtra("EXTRA_STORY");

        if (story != null) {
            ImageView ivStoryFullscreen = findViewById(R.id.ivStoryFullscreen);
            TextView tvStoryTitle = findViewById(R.id.tvStoryTitle);

            ivStoryFullscreen.setImageResource(story.getImageResource());
            tvStoryTitle.setText(story.getTitle());
        }

        // Klik layar untuk menutup story
        findViewById(R.id.ivStoryFullscreen).setOnClickListener(v -> finish());
    }
}
