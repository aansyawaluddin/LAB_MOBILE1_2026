package com.nurul.praktikum_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private ArrayList<Story> stories;
    private OnStoryClickListener listener;

    public interface OnStoryClickListener {
        void onStoryClick(Story story);
    }

    public StoryAdapter(ArrayList<Story> stories, OnStoryClickListener listener) {
        this.stories = stories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.tvStoryTitle.setText(story.getTitle());
        holder.ivStory.setImageResource(story.getImageResource());
        // Klik story pindah ke DetailStoryActivity
        holder.itemView.setOnClickListener(v -> listener.onStoryClick(story));
    }

    @Override
    public int getItemCount() { return stories.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStory;
        TextView tvStoryTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.ivStory);
            tvStoryTitle = itemView.findViewById(R.id.tvStoryTitle);
        }
    }
}
