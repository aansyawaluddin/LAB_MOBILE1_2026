package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class DetailStoryActivity extends AppCompatActivity {

    private ViewPager2 viewPagerStory;
    private TextView tvDetailStoryTitle;
    private LinearLayout layoutProgress;
    private List<ProgressBar> progressBars = new ArrayList<>();
    private List<Story> storyList;
    private int currentStoryIndex = 0;
    private static final int STORY_DURATION = 3000; // 3 seconds
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable progressRunnable;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        viewPagerStory = findViewById(R.id.viewPagerStory);
        tvDetailStoryTitle = findViewById(R.id.tvDetailStoryTitle);
        layoutProgress = findViewById(R.id.layoutProgress);
        ImageView btnStoryBack = findViewById(R.id.btnStoryBack);
        View viewNext = findViewById(R.id.viewNext);
        View viewPrevious = findViewById(R.id.viewPrevious);

        storyList = DataRepository.getInstance().getUserStories();
        int startIndex = 0;
        Story initialStory = (Story) getIntent().getSerializableExtra("story");
        
        if (initialStory != null) {
            for (int i = 0; i < storyList.size(); i++) {
                if (storyList.get(i).getTitle().equals(initialStory.getTitle())) {
                    startIndex = i;
                    break;
                }
            }
        }

        setupProgressBars();

        StoryPagerAdapter adapter = new StoryPagerAdapter(storyList);
        viewPagerStory.setAdapter(adapter);
        viewPagerStory.setUserInputEnabled(false); // Disable manual swipe to mimic IG auto-advance
        viewPagerStory.setCurrentItem(startIndex, false);
        
        currentStoryIndex = startIndex;
        updateUIForStory(currentStoryIndex);

        btnStoryBack.setOnClickListener(v -> finish());

        viewNext.setOnClickListener(v -> {
            progressBars.get(currentStoryIndex).setProgress(100);
            moveToNextStory();
        });

        viewPrevious.setOnClickListener(v -> moveToPreviousStory());
        
        startStoryTimer();
    }

    private void setupProgressBars() {
        layoutProgress.removeAllViews();
        progressBars.clear();
        layoutProgress.setWeightSum(storyList.size());

        for (int i = 0; i < storyList.size(); i++) {
            ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(4, 0, 4, 0);
            progressBar.setLayoutParams(params);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            layoutProgress.addView(progressBar);
            progressBars.add(progressBar);
        }
    }

    private void startStoryTimer() {
        handler.removeCallbacks(progressRunnable);
        startTime = System.currentTimeMillis();
        
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                int progress = (int) (elapsedTime * 100 / STORY_DURATION);

                if (progress <= 100) {
                    progressBars.get(currentStoryIndex).setProgress(progress);
                    handler.postDelayed(this, 30);
                } else {
                    progressBars.get(currentStoryIndex).setProgress(100);
                    moveToNextStory();
                }
            }
        };
        handler.post(progressRunnable);
    }

    private void moveToNextStory() {
        if (currentStoryIndex < storyList.size() - 1) {
            currentStoryIndex++;
            viewPagerStory.setCurrentItem(currentStoryIndex, false);
            updateUIForStory(currentStoryIndex);
            startStoryTimer();
        } else {
            finish();
        }
    }

    private void moveToPreviousStory() {
        progressBars.get(currentStoryIndex).setProgress(0);
        if (currentStoryIndex > 0) {
            currentStoryIndex--;
            viewPagerStory.setCurrentItem(currentStoryIndex, false);
            updateUIForStory(currentStoryIndex);
        }
        // Restart timer for the (new or same) current story
        startStoryTimer();
    }

    private void updateUIForStory(int index) {
        tvDetailStoryTitle.setText(storyList.get(index).getTitle());
        // Set all previous progress bars to 100
        for (int i = 0; i < index; i++) {
            progressBars.get(i).setProgress(100);
        }
        // Set all next progress bars to 0
        for (int i = index + 1; i < progressBars.size(); i++) {
            progressBars.get(i).setProgress(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(progressRunnable);
    }
}