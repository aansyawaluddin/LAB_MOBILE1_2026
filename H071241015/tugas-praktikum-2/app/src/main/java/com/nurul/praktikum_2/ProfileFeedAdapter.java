package com.nurul.praktikum_2;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {
    private ArrayList<Post> posts;
    private OnFeedClickListener listener;

    public interface OnFeedClickListener {
        void onFeedClick(Post post);
    }

    public ProfileFeedAdapter(ArrayList<Post> posts, OnFeedClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        
        if (post.getPostImageUri() != null) {
            holder.ivGridPost.setImageURI(Uri.parse(post.getPostImageUri()));
        } else {
            holder.ivGridPost.setImageResource(post.getPostImage());
        }

        // Klik feed pindah ke DetailFeedActivity
        holder.itemView.setOnClickListener(v -> listener.onFeedClick(post));
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGridPost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGridPost = itemView.findViewById(R.id.ivGridPost);
        }
    }
}
